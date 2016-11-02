package server.profile;

/**
 * Created by valentin on 23.10.16.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authorized;
import server.info.Token;
import server.info.TokenStorage;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/profile")
public class ChangeName {
    private static final Logger log = LogManager.getLogger(ChangeName.class);
    @POST
    @Path("name")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Authorized
    public Response changeName(ContainerRequestContext requestContext, @FormParam("name") String name) {
        String userToken = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION).substring("Bearer".length()).trim();
        Token token = new Token(Long.valueOf(userToken));
        if (name == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String oldName = TokenStorage.changeName(token, name);
        if (oldName != null) {
            log.info("name Changed from '{}' to '{}'", oldName, name);
            return Response.ok("User " + oldName + " changed name to " + name + ".").build();
        }
        else{
            log.info("Can't change name");
            Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        return Response.ok("User " + token + " changed name.").build();
    }
}
