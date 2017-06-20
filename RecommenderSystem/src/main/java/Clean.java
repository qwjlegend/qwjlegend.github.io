import java.util.ArrayList;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Clean {
	
	public static void main(String[] args) throws Exception {
		//read file from hdfs://rawdata
		//output to hdfs://input
		int numOfFile = Integer.parseInt(args[2]);
		for (int i = 1; i<=numOfFile; i++){
			String file_name = "/mv_00" + String.format("%05d",i) + ".txt";

	        FileSystem fs = FileSystem.get(Driver.conf);
	        Path inFile = new Path(args[0] + file_name);
	        FSDataInputStream reader = fs.open(inFile);
	        ArrayList<String> lst = new ArrayList<String>(); 
	        String line;
	        String movie_id;
	        line = reader.readLine().trim();
	        movie_id =line.substring(0, line.length() - 1);
	        
	        while ((line = reader.readLine()) != null){
	        	String[] pair = line.split(",");
	        	lst.add(pair[0] + "," + movie_id + "," + pair[1]);
	        }        
	        reader.close();
	
	        Path outFile = new Path(args[1] + file_name);
	        FSDataOutputStream writer = fs.create(outFile);
	        for (String key : lst){
	        	writer.writeChars(key);
	        	//writer.write(key);
	        	writer.writeUTF("\n");
	        }
	        writer.close();
		}
		
		
	}

}

