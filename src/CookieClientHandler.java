import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class CookieClientHandler implements Runnable {

    private Socket socket;

    public CookieClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        String line = "";
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
        } catch (IOException e) {
        System.out.println("Error!");
        }
    }
}