package cs240.fms.Handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import static java.net.HttpURLConnection.HTTP_OK;

import java.io.IOException;
import java.net.URI;


class RootHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

           // getRequestHeaders(exchange);
           // getRequestBody(exchange);
            exchange.sendResponseHeaders(HTTP_OK, 0);
            //sendResponseBody(exchange);


    }
    public void getRequestHeaders(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        URI uri = exchange.getRequestURI();

    }
}
