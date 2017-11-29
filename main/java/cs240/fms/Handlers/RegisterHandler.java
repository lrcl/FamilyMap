package cs240.fms.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;


import cs240.fms.ServerFacade.Facade;
import cs240.fms.ServerFacade.RegisterRequest;
import cs240.fms.ServerFacade.RegisterResponse;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;


class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        getRequestBody(exchange);
    }
    public void getRequestBody(HttpExchange exchange) {
        InputStream stream = exchange.getRequestBody();
        InputStreamReader registerInfo = new InputStreamReader(stream);
        Gson g = new Gson();
        RegisterRequest rr = g.fromJson(registerInfo, RegisterRequest.class);
        Facade facade = new Facade();
        RegisterResponse registerResponse = facade.register(rr);
        String response = "";
        if(registerResponse == null) {
            try {
                exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response = "{" + "\"message\": " +  "\"unable to register user\"" + "}";

        }
        else {
            try {
                exchange.sendResponseHeaders(HTTP_OK, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response = new Gson().toJson(registerResponse);
        }

        OutputStream os = exchange.getResponseBody();
        try {
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
