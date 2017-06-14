import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Normalize {

    public static class NormalizeMapper extends Mapper<LongWritable, Text, Text, Text> {

        // map method
        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            //movieA:movieB \t relation
            //collect the relationship list for movieA
        	String[] line = value.toString().split("\t");
        	String[] pair = line[0].split(":");
        	String outKey = pair[0];
        	String outVal = pair[1] + ":" + line[1];
        	context.write(new Text(outKey), new Text(outVal));
        }
    }

    public static class NormalizeReducer extends Reducer<Text, Text, Text, Text> {
        // reduce method
        @Override
        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            //key = movieA, value=<movieB:relation, movieC:relation...>
            //normalize each unit of co-occurrence matrix
        	List<String> movieList = new ArrayList<String>();
        	List<Integer> relation = new ArrayList<Integer>();
        	int sum = 0;
        	for (Text val: values){
        		String[] pair = val.toString().split(":");
        		movieList.add(pair[0]);
        		int rl = Integer.parseInt(pair[1]);
        		relation.add(rl);
        		sum += rl;
        	}
        	for (int i = 0; i < movieList.size(); i++){
        		String outKey = movieList.get(i);
        		double normalizedRelation = (double)relation.get(i) / sum;
        		String outVal = key.toString() + "=" + String.valueOf(normalizedRelation);
        		context.write(new Text(outKey), new Text(outVal));
        	}
        	
        	
        }
    }

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);
        job.setMapperClass(NormalizeMapper.class);
        job.setReducerClass(NormalizeReducer.class);

        job.setJarByClass(Normalize.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        TextInputFormat.setInputPaths(job, new Path(args[0]));
        TextOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }
}
