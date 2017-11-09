package cs240.fms.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import cs240.fms.ServerFacade.Facade;

import static java.net.HttpURLConnection.HTTP_OK;


class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Facade facade = new Facade();
        boolean success = facade.clear();
        String jsonStr = "";
        if(!success) {
            try {
                exchange.sendResponseHeaders(HTTP_OK,0);
                jsonStr = "{" + "\"message\":" + "\"could not clear\"" + "}";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Success
        else {
            try {
                exchange.sendResponseHeaders(HTTP_OK, 0);
                jsonStr = "{" + "\"message\":" + "\"successfully cleared the database\"" + "}";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //send json back to user
        OutputStream os = exchange.getResponseBody();
        try {
            os.write(jsonStr.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
