import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TSVRead{
	List fullfile;

    public static void main(String[] arg) throws Exception{
    	new TSVRead().readData();
    }
    
    public List<List<String>> readData() throws IOException{
    	List<List<String>> fullfile = new ArrayList<List<String>>();

        StringTokenizer st ;
        BufferedReader TSVFile = new BufferedReader(new FileReader("/home/nuno/dev.csv"));
        String dataRow = TSVFile.readLine(); // Read first line.

        while (dataRow != null){
            st = new StringTokenizer(dataRow,",");
            List<String>dataArray = new ArrayList<String>() ;
            while(st.hasMoreElements()){
                dataArray.add(st.nextElement().toString());
            }
            for (String item:dataArray) { 
                //System.out.print(item + "  "); 
            }
            // System.out.println(); // Print the data line.
            dataRow = TSVFile.readLine(); // Read next line of data.
            fullfile.add(dataArray);
        }
        // Close the file once all data has been read.
        TSVFile.close();

        // End the printout with a blank line.
        System.out.println("data loaded...");
		return fullfile;

    } //main()
} // TSVRead