package cs240.fms.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import cs240.fms.ServerFacade.Facade;
import cs240.fms.ServerFacade.LoadRequest;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;


class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        getRequestBody(exchange);
    }
    public void getRequestBody(HttpExchange exchange) {
        InputStream stream = exchange.getRequestBody();
        InputStreamReader loadInfo = new InputStreamReader(stream);
        Gson g = new Gson();
        LoadRequest loadRequest = g.fromJson(loadInfo, LoadRequest.class);
        Facade facade = new Facade();
        boolean loadSuccessful = facade.load(loadRequest);
        String response = "";
        if(!loadSuccessful) {
            try {
                exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            response = "{" + "\"message\": " +  "\"unable to load data\"" + "}";
        }
        //successful load
        else {
            try {
                exchange.sendResponseHeaders(HTTP_OK, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            response = "{" + "\"message\": " +  "\"successfully added users, persons, and events to the database.\"" + "}";
        }
        //output to user
        OutputStream os = exchange.getResponseBody();
        try {
            os.write(response.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

