package cs240.fms.Handlers;


import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.json.JSONObject;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import cs240.fms.ServerFacade.Facade;
import cs240.fms.ServerFacade.LoginRequest;
import cs240.fms.ServerFacade.RegisterResponse;

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        getRequestBody(exchange);

    }
    public void getRequestBody(HttpExchange exchange) {
        InputStream stream = exchange.getRequestBody();
        InputStreamReader loginInfo = new InputStreamReader(stream);
        Gson g = new Gson();
        LoginRequest loginRequest = g.fromJson(loginInfo, LoginRequest.class);
        Facade facade = new Facade();
        RegisterResponse loginResponse = facade.login(loginRequest);
        String response = "";
        //unsuccessful
        if(loginResponse == null) {
            try {
                exchange.sendResponseHeaders(HTTP_OK, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            response = "{" + "\"message\": " +  "\"unable to log in\"" + "}";
        }
        //successful login
        else {
            try {
                exchange.sendResponseHeaders(HTTP_OK, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            response = "{ \"authToken\":" + "\"" + loginResponse.getAuthToken() + "\"" + ","
                    + "\"userName\":" + "\"" + loginResponse.getUsername() + "\"" + ","
                    + "\"personID\":" + "\"" + loginResponse.getPersonId() + "\"" + "}";
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