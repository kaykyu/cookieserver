import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

    private static String SERVER;
    private static int PORT;
    
    public static void main(String[] args) throws Exception {
        
        if (args.length == 0) {
            System.out.println("Please enter server");
        }

        System.out.printf("Attempting to connect to %s\n", args[0]);
        String[] serverPort = args[0].split(":");
        SERVER = serverPort[0];
        PORT = Integer.parseInt(serverPort[1]);
        Socket socket = new Socket(SERVER, PORT);

        System.out.println("Connected to server");

        Console cons = System.console();
        String line = cons.readLine("""
                Type get-cookie to get a Fortune Cookie
                Close to exit
                """);
            
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        while (!line.equals("close")) {
            bw.write(line + "\n");
            bw.flush();

            while (true) {
                line = br.readLine();
                if (line.equals("end")) {
                    break;
                }
                System.out.println(line);                
            }                
            line = cons.readLine();
        }
            bw.write("close\n");
            bw.flush();
            os.close();
            is.close();

            socket.close();
            System.out.println("Thank you for using Cookie Server");
    }
}
