package org.karthik.skillstore.endpoints;

import org.karthik.skillstore.models.UserDetails;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.List;

@Path("/users")
public class UserDetailsResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDetails> getUserDetails() {
        // return all users (not needed)
        return null;
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDetails getUserDetails(@PathParam("userId") Long userId) {
        return null;
        // return user from db
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public UserDetails createUserDetails(UserDetails userDetails) {
        return null;
    }

}
