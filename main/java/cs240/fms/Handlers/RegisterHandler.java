package cs240.fms.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;

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
        String registerInfo = stream.toString();
        Gson g = new Gson();
        RegisterRequest rr = g.fromJson(registerInfo, RegisterRequest.class);
        Facade facade = new Facade();
        RegisterResponse registerResponse = facade.register(rr);
        String response = "";
        if(registerResponse == null) {
            try {
                exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            response = "{ message: unable to register user }";

        }
        else {
            try {
                exchange.sendResponseHeaders(HTTP_OK, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            response = "{ authToken:" + registerResponse.getAuthToken() + ","
                    + "userName:" + registerResponse.getUsername() + ","
                    + "personID:" + registerResponse.getPersonId() + "}";

        }
            PrintWriter out = new PrintWriter(exchange.getResponseBody());
            out.print(response);
            out.close();


    }
}
