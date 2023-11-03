import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Cookie {

    private static List<String> cookies;
    
    public static int randInt() {
        Random rand = new Random();
        int min = 0, max = cookies.size();
        return rand.nextInt((max - min) + min);
    }
    
    public static List<String> getCookies() {return cookies;}

    public static List<String> readFile(String file) throws IOException{        
        try (FileReader fr = new FileReader(file)) {
        BufferedReader br = new BufferedReader(fr);
        cookies = (br.lines().toList());
        br.close();
        return cookies;
        }
    }
}