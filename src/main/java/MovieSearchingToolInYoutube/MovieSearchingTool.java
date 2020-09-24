package MovieSearchingToolInYoutube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MovieSearchingTool {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 //To save processing time when writing CSV, use List of String[] not class to save the video info 
    	List<String[]> list_video_info = new ArrayList<String[]>();
    	String url = "https://www.youtube.com/results?search_query=";
    	String movie ="tenet";
    	String ChromedriverPath = "/Users/zn/Downloads/chromedriver";
    	String SearchResults = null;
    	String html = new String();
    	
    	System.out.println("Please input the chromedriver file-path in your machine (for example: /bin/chromedriver):");
		BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
		ChromedriverPath = reader.readLine();
    	
		System.out.println("Please input the movie name:");
		BufferedReader reader1 = new BufferedReader (new InputStreamReader(System.in));
		movie = reader1.readLine();
		
    	
    	/*set up the webdriver*/
    	System.setProperty("webdriver.chrome.driver", ChromedriverPath);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        DesiredCapabilities caps = DesiredCapabilities.chrome(); 
        caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        WebDriver driver = new ChromeDriver(caps);
        WebDriverWait wait = new WebDriverWait(driver,5);
        
        /*emulate the chrome browser to access the youtube for searching*/
        try {
        	
			Thread.sleep(500); 
			driver.get(url+movie);
            wait.until(ExpectedConditions.titleContains(movie));
			long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.documentElement.scrollHeight");
	            while (true) {
	                for (int i = 0; i < 10; i++) {
	                	Random rand = new Random();
	                	int a = (rand.nextInt(3000)+500000);
	         	        //emulate the scrolling to the chrome browser
	                    ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, "+Integer.toString(a)+")");
	                    int b = (rand.nextInt(1000)+100);
	                    Thread.sleep(b);  
	                 // wait.until(ExpectedConditions.titleContains(movie));
	                  //  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("style-scope ytd-video-renderer")));
	           
	                }
	                
	                long newHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.documentElement.scrollHeight");
	                if (newHeight == lastHeight) {// no more new results
	                	String javascript = "return arguments[0].innerHTML";
	                	//getting the html source code
	                	String pageSource=(String)((JavascriptExecutor)driver).executeScript(javascript, driver.findElement(By.tagName("html")));
	                	html = "<html>"+pageSource +"</html>";
	                    break;
	                }
	                lastHeight = newHeight;
	            }
	          
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}finally {
			 driver.quit();
		}

    /*using Jsoup to parse the html and extract the video info.*/
    Document doc = Jsoup.parse(html);
	// get video info element from html
	Elements videos = doc.select("ytd-video-renderer.style-scope.ytd-item-section-renderer");
	for(Element video: videos) {
		/*extract the needed video info*/
		String[] csv_data = new String[4];
		csv_data[0] = video.select("#video-title").text();
		csv_data[2] = video.select("yt-formatted-string.style-scope.ytd-channel-name.complex-string").
				select("a").text();
		Elements e = video.select("#metadata-line.style-scope.ytd-video-meta-block").
				select("span.style-scope.ytd-video-meta-block");
		
		if(e.size()==2)
		{
			csv_data[3] = e.get(0).text().split(" ")[0];
			csv_data[1] = e.get(1).text();
		}
		else
		{
			if(e.size()==1)//sometimes the video does not have the upload time info.
			{
				if(e.get(0).text().contains("views"))
					csv_data[3] = e.get(0).text().split(" ")[0];
				else
					csv_data[1] = e.get(0).text();
			}
		}
		
//		String aria_label = video.select("#video-title").attr("aria-label");	
		list_video_info.add(csv_data);
	}
	
	System.out.println("The number of videos it scraped are "+list_video_info.size());
	//write the video info into CSV file
	ExportCSV EC = new ExportCSV();
	EC.ExportIntoCSV(movie, list_video_info);
    
 }
	

}
