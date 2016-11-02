package server.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.info.TokenStorage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by valentin on 23.10.16.
 */

/*
*curl -i -X GET -H "Host: localhost:8080" -i localhost:8080/data/users
 */
@Path("/data")
public class GetInfo {
    private static final Logger log = LogManager.getLogger(GetInfo.class);
    @GET
    @Path("users")
    @Produces("application/json")
    public String getInfo() throws JsonProcessingException {
        log.info("Get it!!!");
        return TokenStorage.writeJson();
    }
}
