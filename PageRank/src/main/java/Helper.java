import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Helper {

    public static void main(String[] args) throws IOException{
    	for (int i = 0;i<=0;i++){
    		BufferedReader pr = new BufferedReader(new FileReader("/Users/WeijunQian/Documents/GoogleDrive/Development/CS502/hadoop/PagerankV2/pagerank30/part-r-00000"));
            BufferedReader transition = new BufferedReader(new FileReader("/Users/WeijunQian/Documents/GoogleDrive/Development/CS502/hadoop/PagerankV2/transition/part-r-00000"));
            FileWriter fileWriter = new FileWriter("/Users/WeijunQian/Documents/GoogleDrive/Development/CS502/hadoop/PagerankV2/transition/result.csv");

            Map<String, String> page_pr = new HashMap<String, String>();

            String line = pr.readLine();
            while (line != null) {
                page_pr.put(line.split("\t")[0], line.split("\t")[1]);
                line = pr.readLine();
            }
            pr.close();

            line = transition.readLine();
            fileWriter.write("source,target,value\n");

            while (line != null) {

                String[] from_tos = line.split("\t");
                String[] tos = from_tos[1].split(",");
                for (String to: tos) {
                    String value = page_pr.get(to);
                    fileWriter.write(from_tos[0] + "," + to + "," + value + "\n");
                }
                line = transition.readLine();
            }
            transition.close();
            fileWriter.close();
    	}
        

    }
}
