import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
                String[] parts = line.split(",");
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

    public String identifyOpening(String moves)
    {
        if (moves.equals(""))
        {
            return "Starting Position";
        }
        for (Map.Entry<String, String> entry : openingsMap.entrySet())
        {
            if (moves.startsWith(entry.getKey()))
            {
                return entry.getValue();
            }
        }
        return "Unknown Opening";
    }
}
