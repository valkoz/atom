package server;

import server.api.ApiServlet;
import org.jetbrains.annotations.NotNull;

/**
 * Created by valentin on 23.10.16.
 */
public class Server {
    public static void main(@NotNull String[] args) throws Exception {
        ApiServlet.start();
    }
}
