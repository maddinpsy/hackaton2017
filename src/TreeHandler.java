

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TreeHandler implements HttpHandler {

	private Store store;

	public TreeHandler(Store store) {
		this.store = store;
	}

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		try {
			if (arg0.getRequestMethod().equals("GET")) {
				String response =store.getTrees().toString();
				arg0.sendResponseHeaders(200, response.length());
				OutputStream os = arg0.getResponseBody();
				os.write(response.getBytes());
				os.close();
			} else {
				arg0.sendResponseHeaders(404, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
