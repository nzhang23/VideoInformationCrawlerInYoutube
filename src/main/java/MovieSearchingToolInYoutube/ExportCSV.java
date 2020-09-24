package MovieSearchingToolInYoutube;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import com.opencsv.CSVWriter;


public class ExportCSV {

	public ExportCSV() {
		
	}
    public void ExportIntoCSV(String movie, List<String[]> YoutubeVideoList) {
    	// name of generated csv 
       final String CSV_LOCATION = "searching_"+movie+"_video_info.csv"; 
        try {
        	
            FileWriter outputfile = new 
                       FileWriter(CSV_LOCATION);
            CSVWriter writer = new CSVWriter(outputfile); 
            String[] header = { "title", "upload_time", "uploader", "view_count"}; 
            writer.writeNext(header); 
            writer.writeAll(YoutubeVideoList);
            writer.close(); 
        }catch(Exception e) {
        	e.printStackTrace(); 
        }
        
        System.out.println("The information of these videos are stored in the file "+CSV_LOCATION+"!");
    }
    
}


