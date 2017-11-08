package cs240.fms.Handlers;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.json.JSONObject;

import static java.net.HttpURLConnection.HTTP_OK;

import java.io.IOException;
import java.io.InputStream;

public class LoginHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if(exchange.getRequestMethod().toLowerCase().equals("post")) {
            getRequestBody(exchange);
            exchange.sendResponseHeaders(HTTP_OK,0);
            sendResponseBody(exchange);
        }

    }

    private void sendResponseBody(HttpExchange exchange) {
    }

    public void getRequestBody(HttpExchange exchange) {
        InputStream reqBody = exchange.getRequestBody();
        String reqInfo = readJson(reqBody);
    }
    public String readJson(InputStream reqBody){
        //JSONObject json = new JSONObject(reqBody);
        return "";
    }
}
