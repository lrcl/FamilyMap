package cs240.fms.Handlers;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import static java.net.HttpURLConnection.HTTP_OK;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.*;



class FileHandler implements HttpHandler {
    final String BASE_PATH = "/home/grant/AndroidStudioProjects/FMS/app/libs/web";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handle(HttpExchange exchange) throws IOException {
       try{
           URI u = exchange.getRequestURI();
           String uri = u.toString();
           String filePath = "";
           if(uri.equals("/")) {
               filePath = BASE_PATH + "/index.html";
           }
           else {
               filePath = BASE_PATH + uri;

           }
           exchange.sendResponseHeaders(HTTP_OK, 0);
           Path fileP = FileSystems.getDefault().getPath(filePath);
           Files.copy(fileP, exchange.getResponseBody());
           exchange.getResponseBody().close();
       } catch (FileNotFoundException f){
           f.printStackTrace();
        } catch (Exception e) {
           e.printStackTrace();
       }
    }
}
