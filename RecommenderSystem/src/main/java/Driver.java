import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.GenericOptionsParser;

public class Driver {
	public static Configuration conf = new Configuration();

	public static void main(String[] args) throws Exception {
		DataDividerByUser dataDividerByUser = new DataDividerByUser();
		CoOccurrenceMatrixGenerator coOccurrenceMatrixGenerator = new CoOccurrenceMatrixGenerator();
		Normalize normalize = new Normalize();
		Multiplication multiplication = new Multiplication();
		Sum sum = new Sum();
    	String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

		String rawInput = otherArgs[0];
		String userMovieListOutputDir = otherArgs[1];
		String coOccurrenceMatrixDir = otherArgs[2];
		String normalizeDir = otherArgs[3];
		String multiplicationDir = otherArgs[4];
		String sumDir = otherArgs[5];
		String[] path1 = {rawInput, userMovieListOutputDir};
		String[] path2 = {userMovieListOutputDir, coOccurrenceMatrixDir};
		String[] path3 = {coOccurrenceMatrixDir, normalizeDir};
		String[] path4 = {normalizeDir, rawInput, multiplicationDir};
		String[] path5 = {multiplicationDir, sumDir};
		
		dataDividerByUser.main(path1);
		coOccurrenceMatrixGenerator.main(path2);
		normalize.main(path3);
		multiplication.main(path4);
		sum.main(path5);

	}

}
