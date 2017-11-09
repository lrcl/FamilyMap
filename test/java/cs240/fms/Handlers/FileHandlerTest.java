package cs240.fms.Handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import static org.junit.Assert.*;

public class FileHandlerTest {
    @Test
    public void handle() throws Exception {
        FileHandler fh = new FileHandler();
        //fh.handle(exchange);
    }

}