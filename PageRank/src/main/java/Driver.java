//import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FSDataInputStream;
//import org.apache.hadoop.fs.FSDataOutputStream;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.GenericOptionsParser;

public class Driver {
	public static Configuration conf = new Configuration();
	//teleporting parameter
	public static float beta = 0.2f;
    public static void main(String[] args) throws Exception {
        Multiplication multiplication = new Multiplication();
        Sum sum = new Sum();
        Group gp = new Group();
        Initialization init = new Initialization();
        //args0: dir of transition.txt
        //args1: dir of PageRank.txt
        //args2: dir of unitMultiplication result
        //args3: times of convergence
        //args4: dir of rawdata.txt
    	String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        String transitionMatrix = otherArgs[0];
        String prMatrix = otherArgs[1];
        String unitState = otherArgs[2];
        int count = Integer.parseInt(otherArgs[3]);
        String rawdata = otherArgs[4];
        String[] argsGroup = {rawdata, transitionMatrix};
        String[] argsInitial = {transitionMatrix, prMatrix + "/" + prMatrix + "0"};
        
        gp.main(argsGroup);
        init.main(argsInitial);        
        
        for(int i=0;  i<count;  i++) {
            String[] args1 = {transitionMatrix, prMatrix+i, unitState+i};
            multiplication.main(args1);
            String[] args2 = {unitState + i, prMatrix + i, prMatrix+(i+1)};
            sum.main(args2);
        }
    }
}
