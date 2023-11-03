import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static int PORT;
    private static String path;

    public static void main(String[] args) throws IOException {
        
        if (args.length == 0) {
            System.out.println("Please enter port and file");
        }

        PORT = Integer.parseInt(args[0]);
        path = args[1];
        Cookie.readFile(path);
        String line = "";

        System.out.printf("Listening on port %d\n", PORT);
        ServerSocket server = new ServerSocket(PORT);

        System.out.println("Waiting for client connection");
        Socket client = server.accept();

        System.out.printf("Connected to client: %d\n", client.getLocalPort());

        InputStream is = client.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        OutputStream os = client.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        while (!line.equals("close")) {

            line = br.readLine();
            System.out.printf("Client's request: %s\n", line);

            if (line.equals("get-cookie")) {
                line = Cookie.getCookies().get(Cookie.randInt());
                bw.write("cookie:\n");
                bw.write(line + "\n");
                bw.write("end\n");
                bw.flush(); 

            } else if (line.startsWith("get-cookie")) {
                String[] nums = line.split(" ");
                int num = Integer.parseInt(nums[1]);
                // String[] lines = new String[num];
                bw.write(String.format("%d cookies:\n", num));

                for (int i = 0; i < num; i++) {
                    line = Cookie.getCookies().get(Cookie.randInt());  
                    bw.write(line + "\n");
                }
                bw.write("end\n");
                bw.flush();

            } else if (line.equals("close")) {
                System.out.println("Client disconnected");
                is.close();
                os.close(); 

            } else {
                line = "invalid command";
                bw.write(line +"\n");
                bw.flush(); 
            }
        }
        
        server.close();
    }
}