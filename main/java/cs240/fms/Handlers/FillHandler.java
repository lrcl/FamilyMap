package cs240.fms.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import cs240.fms.ServerFacade.Facade;
import cs240.fms.ServerFacade.FillRequest;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;


class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String ur = uri.toString();
        StringBuilder sb = new StringBuilder(ur);
       //parse URI
        String[] info = sb.toString().split("/");
        String fill = info[1];
        String username = info[2];
        int gen = -10;
        if(info.length >= 4 ) {
            String gens = info[3];
            if(Character.isDigit(gens.charAt(0)));
            gen = Integer.parseInt(gens);
        }
        Facade facade = new Facade();
        boolean filled = false;
        String jsonStr = "";
        if(gen == -10) {
            FillRequest fr = new FillRequest(username,4);
            filled = facade.fill(fr);
        }
        else {
            FillRequest fr = new FillRequest(username, gen);
            filled = facade.fill(fr);
        }
        //failed
        if(!filled) {
            try {
                exchange.sendResponseHeaders(HTTP_OK,0);
                jsonStr = "{" + "\"message\":" + "\"could not add persons and events to database\"" + "}";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //success
        else {
            try {
                exchange.sendResponseHeaders(HTTP_OK, 0);
                jsonStr = "{" + "\"message\":" + "\"successfully added persons and events to the database\"" + "}";
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

   /* private String parseUserName(StringBuilder sb) {
        StringBuilder content = new StringBuilder();
        int slashCount = 0;
        String username = "";
        for(int i = 0; i < sb.length(); i++) {
            if(sb.charAt(i) == '/') {
                slashCount++;
            }
            if(sb.charAt(i) != '/') {
                if(slashCount == 3) {
                    break;
                }
                content.append(sb.charAt(i));

            }
        }
        username = content.substring(4);
        return username;
    }
    private int parseGen(StringBuilder sb) {
        int slashCount = 0;
        int gen = -10;
        for(int i = 0; i < sb.length(); i++){
            if(sb.charAt(i) == '/') {
                slashCount++;
            }
            if(slashCount == 3) {
                gen = sb.charAt(sb.length());
                break;
            }
        }
        return gen;
    }*/
}

