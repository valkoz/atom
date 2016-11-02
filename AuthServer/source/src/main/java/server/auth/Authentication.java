package server.auth;

/**
 * Created by valentin on 23.10.16.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.info.Token;
import server.info.TokenStorage;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/auth")
public class Authentication {
    private static final Logger log = LogManager.getLogger(Authentication.class);

    // curl -i
    //      -X POST
    //      -H "Content-Type: application/x-www-form-urlencoded"
    //      -H "Host: {IP}:8080"
    //      -d "login={}&password={}"
    // "{IP}:8080/auth/register"
    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("user") String user,
                             @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (TokenStorage.registerNewUser(user, password) != null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        log.info("New user '{}' registered", user);
        return Response.ok("User " + user + " registered.").build();
    }

    // curl -X POST
    //      -H "Content-Type: application/x-www-form-urlencoded"
    //      -H "Host: localhost:8080"
    //      -d "login=admin&password=admin"
    // "http://localhost:8080/auth/login"
    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response authenticateUser(@FormParam("user") String user,
                                     @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            // Authenticate the user using the credentials provided
            if (!TokenStorage.authenticate(user, password)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            // Issue a token for the user
            Token token = TokenStorage.issueToken(user);
            log.info("User '{}' logged in", user);

            // Return the token on the response
            return Response.ok(Long.toString(token.getToken())).build();

        } catch (Exception e) {
            log.info("Exception");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    //curl -X POST -H
    // "content-Type:application/x-www-form-urlencoded"
    // -H "Authorization: Bearer 1"
    // -H "Host:localhost:8080"
    // "localhost:8080/auth/logout"
    @POST
    @Path("logout")
    @Produces("text/plain")
    @Authorized
    public Response logoutUser(ContainerRequestContext requestContext) {
        String userToken = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION).substring("Bearer".length()).trim();
        Token token = new Token(Long.valueOf(userToken));
        try {
            if (TokenStorage.logOut(token)) {
                log.info("User with token '{}' logged out", userToken);
                return Response.ok("User logged out").build();
            } else {
                log.info("No user with token '{}'", userToken);
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
