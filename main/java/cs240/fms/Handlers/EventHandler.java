package cs240.fms.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import cs240.fms.Models.Event;
import cs240.fms.ServerFacade.AllEventsResponse;
import cs240.fms.ServerFacade.Facade;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;


class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Headers requestHdrs = exchange.getRequestHeaders();
        String authToken = requestHdrs.getFirst("Authorization");
        URI uri = exchange.getRequestURI();
        String ur = uri.toString();
        StringBuilder sb = new StringBuilder(ur);
        String eventId = null;
        if(ur.length() > 7) {
            eventId = sb.substring(7); //7 or 8?
        }
        Facade facade = new Facade();
        String jsonStr = "";
        if (eventId == null) {
            Event[] data = facade.findEvents(authToken);
            AllEventsResponse aer = new AllEventsResponse(data);
            jsonStr = new Gson().toJson(aer);
            if (data == null) {
                try {
                    exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                    jsonStr = "{" + "\"message\":" + "\"could not retrieve events\"" + "}";
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
        else {
            Event event = facade.findEvent(authToken,eventId);
            jsonStr = new Gson().toJson(event);
            if(event == null) {
                try {
                    exchange.sendResponseHeaders(HTTP_BAD_REQUEST,0);
                    jsonStr = "{" + "\"message\":" + "\"could not retrieve event\"" + "}";
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
        OutputStream os = exchange.getResponseBody();
        try {
            os.write(jsonStr.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
