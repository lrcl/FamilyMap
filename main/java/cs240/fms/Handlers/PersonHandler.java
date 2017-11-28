package cs240.fms.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import cs240.fms.Models.Person;
import cs240.fms.ServerFacade.AllPersonsResponse;
import cs240.fms.ServerFacade.Facade;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;


class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Headers requestHdrs = exchange.getRequestHeaders();
        String authToken = requestHdrs.getFirst("Authorization");
        URI uri = exchange.getRequestURI();
        String ur = uri.toString();
        StringBuilder sb = new StringBuilder(ur);
        String personId = null;
        if(ur.length() > 8) {
            personId = sb.substring(8);
        }
        Facade facade = new Facade();
        String jsonStr = "";
        //GET ALL PERSONS
        if(personId == null) {
            Person[] data =  facade.findPersons(authToken);
            AllPersonsResponse apr = new AllPersonsResponse(data);
            jsonStr = new Gson().toJson(apr);
            if(data == null){
                try {
                    exchange.sendResponseHeaders(HTTP_BAD_REQUEST,0);
                    jsonStr = "{" + "\"message\":" + "\"could not retrieve persons\"" + "}";
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else {
                try {
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //GET JUST ONE PERSON BASED ON GIVEN ID
        else {
            Person person = facade.findPerson(authToken, personId);
            jsonStr = new Gson().toJson(person);
            if(person ==  null) {
                try {
                    exchange.sendResponseHeaders(HTTP_BAD_REQUEST,0);
                    jsonStr = "{" + "\"message\":" + "\"could not retrieve person\"" + "}";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
