import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Multiplication {
	
    public static class TransitionMapper extends Mapper<Object, Text, Text, Text> {

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            //input format: fromPage\t toPage1,toPage2,toPage3
            //target: build transition matrix unit -> fromPage\t toPage=probability
        	String[] pair = value.toString().split("\t");
        	String from = pair[0];
        	String[] tos = pair[1].split(",");
        	for (String to: tos){
        		String newTo = to + "=" + 1d/tos.length;
        		context.write(new Text(from), new Text(newTo));
        	}
        	
        }
    }

    public static class PRMapper extends Mapper<Object, Text, Text, Text> {

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            //input format: Page\t PageRank
            //target: write to reducer
        	String[] pair = value.toString().split("\t");
        	String from = pair[0]; 
        	String prVal = pair[1];
        	context.write(new Text(from), new Text(prVal));
        }
    }

    public static class MultiplicationReducer extends Reducer<Text, Text, Text, Text> {


        @Override
        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            //input key = fromPage value=<toPage=probability..., pageRank>
            //target: get the unit multiplication
        	
        	String outKey;
        	String outVal;
        	double trCell = 0;
        	double prCell = 0;
			List<String> trlist = new ArrayList<String>();
        	for (Text val: values){
        		String ele = val.toString();
        		if (ele.contains("=")){
        			trlist.add(ele);
        		}else{
        			prCell = Double.parseDouble(ele);
        		}
        	}
        	for (String tr:trlist){
    			String[] pair = tr.split("=");
    			outKey = pair[0];
    			trCell = Double.parseDouble(pair[1]);
    			//TELEPORTING MULTIPLICATION
        		outVal = String.valueOf(trCell * prCell * (1 - Driver.beta));  
				context.write(new Text(outKey), new Text(outVal));
        	}
        }
    }

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(Multiplication.class);

//        ChainMapper.addMapper(job, TransitionMapper.class, Object.class, Text.class, Text.class, Text.class, conf);
//        ChainMapper.addMapper(job, PRMapper.class, Object.class, Text.class, Text.class, Text.class, conf);
        
        job.setReducerClass(MultiplicationReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, TransitionMapper.class);
        MultipleInputs.addInputPath(job, new Path("pagerank/" + args[1]), TextInputFormat.class, PRMapper.class);

        FileOutputFormat.setOutputPath(job, new Path("subpagerank/" + args[2]));
        job.waitForCompletion(true);
    }

}
