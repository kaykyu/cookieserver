import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MTServer {
    
    private static int PORT;
    private static String path;

    public static void main(String[] args) throws Exception {
 
       if (args.length == 0) {
            System.out.println("Please enter port and file");
        }

        PORT = Integer.parseInt(args[0]);
        path = args[1];
        Cookie.readFile(path);
 
       System.out.printf("Starting threaded server on port %d\n", PORT);
       ServerSocket server = new ServerSocket(PORT);
 
       ExecutorService thrPool = Executors.newFixedThreadPool(2);
 
       while (true) {
          Socket client = server.accept();
          System.out.println("New client connection");
 
          Runnable handler = new CookieClientHandler(client);
          thrPool.submit(handler);
}
    }
}

