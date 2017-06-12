import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

import java.io.IOException;
//import java.text.DecimalFormat;

public class Sum {
    public static class SumMapper extends Mapper<Object, Text, Text, Text> {

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            //input format: toPage\t unitMultiplication
            //target: pass to reducer
        	String[] pair = value.toString().split("\t");
        	context.write(new Text(pair[0]), new Text(pair[1]));
        }
    }
    //this is for TELEPORTING
    public static class PRMapper extends Mapper<Object, Text, Text, Text> {

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            //input format: Page\t PageRank
            //target: write to reducer
        	String[] pair = value.toString().split("\t");
        	String from = pair[0]; 
        	String prVal = String.valueOf(Double.parseDouble(pair[1]) * Driver.beta);
        	context.write(new Text(from), new Text(prVal));
        }
    }
    
    
    public static class SumReducer extends Reducer<Text, Text, Text, Text> {
    	
        @Override
        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {

           //input key = toPage value = <unitMultiplication>
            //target: sum!
        	
        	//TELEPORTING ADD + Key/value pair grouping
        	Double sum = 0d;
        	for (Text val:values){
        		sum += Double.parseDouble(val.toString());
        	}
        	
        	context.write(key, new Text(sum.toString()));	
        }
    }

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(Sum.class);
        job.setMapperClass(SumMapper.class);
        job.setReducerClass(SumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        MultipleInputs.addInputPath(job, new Path("subpagerank/" + args[0]), TextInputFormat.class, SumMapper.class);
        MultipleInputs.addInputPath(job, new Path("pagerank/" + args[1]), TextInputFormat.class, PRMapper.class);
        
//        FileInputFormat.addInputPath(job, new Path("subpagerank/" + args[0]));
        FileOutputFormat.setOutputPath(job, new Path("pagerank/" + args[2]));
        job.waitForCompletion(true);
    }
}
