import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OpeningRecogniser
{
    private Map<String, String> openingsMap = new HashMap<>(); // Map to store openings and their strings

    public OpeningRecogniser()
    {
        loadOpenings();
    }

    private void loadOpenings()
    {
        try (BufferedReader br = new BufferedReader(new FileReader("Openings.txt")))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(";");
                if (parts.length == 2)
                {
                    openingsMap.put(parts[0], parts[1]);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String identifyOpening(String moves) {
        System.out.println(moves);
        if (moves.isEmpty()) {
            return "Starting Position";
        }
    
        List<String> matchingOpenings = new ArrayList<>();
    
        for (Map.Entry<String, String> entry : openingsMap.entrySet()) {
            String opening = entry.getKey();
            if (moves.startsWith(opening)) {
                matchingOpenings.add(entry.getValue());
            }
        }
    
        if (matchingOpenings.isEmpty()) {
            return "Unknown Opening";
        }
    
        // Find the longest matching opening
        String longestOpening = matchingOpenings.get(0);
        for (String opening : matchingOpenings) {
            if (opening.length() > longestOpening.length()) {
                longestOpening = opening;
            }
        }
    
        return longestOpening;
    }
    
    
}
