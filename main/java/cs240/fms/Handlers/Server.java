package cs240.fms.Handlers;

import java.io.*;
import java.net.*;
import java.sql.Connection;

import com.sun.net.httpserver.*;

import cs240.fms.DataAccess.Database;

public class Server {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    private void run(String portNumber) {

        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)),MAX_WAITING_CONNECTIONS);
            server.setExecutor(null);
            server.createContext("/", new FileHandler());
            server.createContext("/user/register", new RegisterHandler());
            server.createContext("/user/login", new LoginHandler());
            server.createContext("/clear", new ClearHandler());
            server.createContext("/fill/*", new FillHandler());
            server.createContext("/load", new LoadHandler());
            server.createContext("/person/*", new PersonHandler());
            server.createContext("/person", new PeopleHandler());
            server.createContext("/event/*", new EventHandler());
            server.createContext("/event", new EventsHandler());

            server.start();

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        try{
            String portNumber = args[0];
            Database db = new Database();
            Connection connection = null;
            connection = db.openConnection(connection);
            db.createTables(connection);
            new Server().run(portNumber);
        } catch (Exception e){
            e.printStackTrace();

        }

    }
}
