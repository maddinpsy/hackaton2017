
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class Test {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        Store store = new Store();
        
		server.createContext("/markers", new MarkerHandler(store));
        server.createContext("/trees", new TreeHandler(store));
        
        server.start();
    }
}