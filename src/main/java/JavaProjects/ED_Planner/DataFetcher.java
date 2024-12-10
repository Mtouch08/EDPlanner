package JavaProjects.ED_Planner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFetcher {

    //private static final String API_URL = "https://api.data.gov/ed/collegescorecard/v1/schools";
    private static final String API_KEY = "jrxBVz7rOQggVGbWld3jnuDrfXVy2Q7Nh3EsRHbI"; 

    public List<Map<String, String>> fetchColleges(String stateFilter, String cityFilter, String schoolNameFilter) {
    List<Map<String, String>> colleges = new ArrayList<>();
    try {
        // Base query string
        String query = String.format(
            "api_key=%s&fields=school.name,latest.cost.tuition.in_state,latest.cost.tuition.out_of_state,latest.completion.rate_suppressed.overall,latest.earnings.10_yrs_after_entry.median",
            API_KEY
        );

        // Add filters
        if (stateFilter != null && !stateFilter.isEmpty()) {
            query += "&school.state=" + stateFilter;
        }
        if (cityFilter != null && !cityFilter.isEmpty()) {
            query += "&school.city=" + cityFilter;
        }
        if (schoolNameFilter != null && !schoolNameFilter.isEmpty()) {
            query += "&school.name=" + URLEncoder.encode(schoolNameFilter, "UTF-8");
        }

        // Construct the URI
        URI uri = new URI(
            "https",                     // Scheme
            "api.data.gov",              // Host
            "/ed/collegescorecard/v1/schools", // Path
            query,                       // Query
            null                         // Fragment
        );

        // Convert URI to URL
        URL url = uri.toURL();

        // Log the query for debugging
        System.out.println("API Query: " + url);

        // Make the API request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the response
            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonArray results = jsonResponse.getAsJsonArray("results");

            // Process each college result
            for (JsonElement result : results) {
                JsonObject college = result.getAsJsonObject();
                Map<String, String> collegeData = new HashMap<>();

                // Handle missing or null fields safely
                collegeData.put("Name", getSafeString(college, "school.name"));
                collegeData.put("In-State Tuition", getSafeString(college, "latest.cost.tuition.in_state"));
                collegeData.put("Out-of-State Tuition", getSafeString(college, "latest.cost.tuition.out_of_state"));
                collegeData.put("Completion Rate", getSafeString(college, "latest.completion.rate_suppressed.overall"));
                collegeData.put("Median Earnings", getSafeString(college, "latest.earnings.10_yrs_after_entry.median"));

                colleges.add(collegeData);
            }
        } else {
            // Handle error response
            System.err.println("API response error: " + responseCode);
            BufferedReader errorStream = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();
            String line;
            while ((line = errorStream.readLine()) != null) {
                errorResponse.append(line);
            }
            System.err.println("Error Response: " + errorResponse.toString());
            errorStream.close();
        }
    } catch (Exception e) {
        System.err.println("Error fetching college data: " + e.getMessage());
    }
    return colleges;
}

// Helper method to safely extract a string from a JSON object
private String getSafeString(JsonObject jsonObject, String key) {
    if (jsonObject.has(key) && !jsonObject.get(key).isJsonNull()) {
        return jsonObject.get(key).getAsString();
    }
    return "N/A"; // Default value for missing or null fields
}
}
