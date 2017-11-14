package ohtu;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        // vaihda oma opiskelijanumerosi seuraavaan, ÄLÄ kuitenkaan laita githubiin omaa opiskelijanumeroasi
        String studentNr = "014610570";
        if ( args.length>0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/ohtustats/students/"+studentNr+"/submissions";
        String courseUrl = "https://studies.cs.helsinki.fi/ohtustats/courseinfo";
        String statsUrl = "https://studies.cs.helsinki.fi/ohtustats/stats";
        
        String bodyText = Request.Get(url).execute().returnContent().asString();
        String courseInfo = Request.Get(courseUrl).execute().returnContent().asString();
        String stats = Request.Get(statsUrl).execute().returnContent().asString();

        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        CourseInfo info = mapper.fromJson(courseInfo, CourseInfo.class);
        
        JsonParser parser = new JsonParser();
        JsonObject statsData = parser.parse(stats).getAsJsonObject();
        
        System.out.println(info + "\n");
        System.out.println("opiskelijanumero: " + studentNr + "\n");
        
        int allExer = 0; 
        int allHours = 0;
        int totalExer = 0;
        int totalSubs = 0;
        for (Submission submission : subs) {
            System.out.println(" " + submission.tulosta(info.getExercises()));
            allExer += submission.getExercises().length;
            allHours += submission.getHours();
            
        }
        System.out.println("\nyhteensä: " + allExer + " tehtävää " + allHours + " tuntia");
        
        System.out.println("\nkurssilla  yhteensä:" + totalSubs 
                + " palautusta, palautettuja tehtäviä: " + totalExer + "kpl");

    }
}
