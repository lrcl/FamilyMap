package cs240.fms.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import static java.net.HttpURLConnection.HTTP_OK;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;



class FileHandler implements HttpHandler {
    final String BASE_PATH = "/home/grant/AndroidStudioProjects/FMS/app/libs/web";
    @Override
    public void handle(HttpExchange exchange) throws IOException {
       try{
           URI u = exchange.getRequestURI();
           String uri = u.toString();
           File file = null;
           if(uri.equals("/")) {
               //serve up "index.html"
               //"/home/grant/AndroidStudioProjects/FMS/app/libs/web/index.html"
               file = new File(BASE_PATH + "/index.html");
           }
           else {
               file = new File(BASE_PATH + uri);

           }
           exchange.sendResponseHeaders(HTTP_OK, 0);
           FileInputStream fs = new FileInputStream(file);
           String fileContent = fs.toString();
           PrintWriter out = new PrintWriter(exchange.getResponseBody());
           out.print(fileContent);
           out.close();

       } catch (FileNotFoundException f){
           f.printStackTrace();
        } catch (Exception e) {
           e.printStackTrace();
       }
    }
}
