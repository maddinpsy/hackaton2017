package RestServer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class FileHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		try{
			
			System.out.println(System.getProperty("user.dir"));
			Path localuri = Paths.get("web"+arg0.getRequestURI().getPath());
			if(!Files.exists(localuri, LinkOption.NOFOLLOW_LINKS)){
				localuri=Paths.get("web/index.html");
			}
			byte[] respose = Files.readAllBytes(localuri);
			arg0.sendResponseHeaders(200, respose.length);
			OutputStream os = arg0.getResponseBody();
			os.write(respose);
			os.close();
		}catch(Exception e){
			arg0.sendResponseHeaders(500, 0);
			e.printStackTrace();
		}
	}

}
