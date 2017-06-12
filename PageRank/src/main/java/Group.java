import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.StringUtils;


public class Group {
	public static class GroupMapper extends Mapper<Object, Text, Text, Text> {

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        	String line = value.toString().trim();
        	String[] pair = line.split(" ");
        	context.write(new Text(pair[0]), new Text(pair[1]));
        	
        }
    }
	
	public static class GroupReducer extends Reducer<Text, Text, Text, Text> {

        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        	
        	ArrayList<String> tos = new ArrayList<String>(); 
        	for (Text to:values){
        		tos.add(to.toString());
        	}
        	String val = StringUtils.join(",", tos);
        	context.write(new Text(key), new Text(val));
        }
    }
	//may use two reducers to generate transition matrix + pr0 matrix
	
	
	public static void main(String[] args) throws Exception {
        Configuration conf = Driver.conf;
        Job job = Job.getInstance(conf);
        
        job.setJarByClass(Group.class);
        
        job.setMapperClass(GroupMapper.class);
        job.setReducerClass(GroupReducer.class);

        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        job.waitForCompletion(true);

        
	}

}
