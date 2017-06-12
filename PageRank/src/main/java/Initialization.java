import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Cluster;
//import org.apache.hadoop.mapreduce.Counter;
//import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class Initialization {
	static enum numberOfLine{num};

	public static class InitializationMapper extends Mapper<Object, Text, Text, Text> {
		
	    @Override
	    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        	context.getCounter(numberOfLine.num).increment(1);
        	String word = value.toString().split("\t")[0];
        	context.write(new Text(word), new Text("1"));
	    }
	}
	 
	public static class InitializationReducer extends Reducer<Text, Text, Text, DoubleWritable> {	
		//get the total number of lines
		private long total;
	    public void setup(Context context) throws IOException, InterruptedException{
	        Configuration conf = context.getConfiguration();
	        Cluster cluster = new Cluster(conf);
	        Job currentJob = cluster.getJob(context.getJobID());
	        total = currentJob.getCounters().findCounter(numberOfLine.num).getValue();  
	    }
		
	    @Override
	    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
	    	//no need...
	    	context.write(key, new DoubleWritable(1d/total));
	    }
	}
	public static void main(String[] args) throws Exception{
		
        Configuration conf = Driver.conf;
        Job job = Job.getInstance(conf);
        job.setJarByClass(Initialization.class);
        
        job.setMapperClass(InitializationMapper.class);
        job.setReducerClass(InitializationReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.waitForCompletion(true);
         
	}

}
