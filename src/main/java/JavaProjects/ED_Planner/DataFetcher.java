package JavaProjects.ED_Planner;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;

public class DataFetcher
{

	public Map<String, String> fetchOnetResults(String interestCode) {
	    // Simulated results for testing
	    if ("RIASEC".equals(interestCode)) {
	        Map<String, String> simulatedResults = new HashMap<>();
	        simulatedResults.put("15-1121", "Software Developers, Applications");
	        simulatedResults.put("17-2141", "Mechanical Engineers");
	        simulatedResults.put("19-4099", "Life, Physical, and Social Science Technicians, All Other");
	        return simulatedResults;
	    }

	    // Add API fetching logic if necessary
	    return new HashMap<>(); // Return empty map if no results
	}

	public JsonArray fetchCareersByInterests(String hollandCode)
	{
		return null;
	}

}
