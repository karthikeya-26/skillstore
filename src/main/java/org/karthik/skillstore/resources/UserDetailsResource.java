package org.karthik.skillstore.resources;

import org.karthik.skillstore.exceptions.AlreadyExistsException;
import org.karthik.skillstore.exceptions.DataNotFoundException;
import org.karthik.skillstore.models.UserDetails;
import org.karthik.skillstore.services.UserDetailsService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path(value = "/userDetails")
public class UserDetailsResource {

    private final UserDetailsService userDetailsService = new UserDetailsService();

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDetails> getUserDetails() {
        return userDetailsService.getAllUserDetails();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUserDetails(UserDetails userDetails) {
        if(userDetailsService.getUserDetails(userDetails.getUserName())!= null){
            throw new AlreadyExistsException("UserName already exists, please try again with new username");
        }
        return Response.status(Response.Status.CREATED).entity(userDetailsService.addUserDetails(userDetails)).build();
    }

    @GET
    @Path("/{userName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDetails getUserDetails(@Context UriInfo uriInfo, @PathParam("userName") String userName) {
        System.out.println("userName = " + userName);
        UserDetails userDetails = userDetailsService.getUserDetails(userName);
        if(userDetails == null){
            throw new DataNotFoundException("User does not exist");
        }
        String uri= uriInfo.getBaseUriBuilder().path(UserDetailsResource.class).path(userName).toString();
        System.out.println(uri);
        userDetails.addLink("self", uri);
        return userDetails;
    }

    @PUT
    @Path("/{userName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDetails updateUserDetails(@PathParam("userName") String userName, UserDetails userDetails) {
        userDetails.setUserName(userName);
        return userDetailsService.updateUserDetails(userDetails);
    }

    @DELETE
    @Path("/{userName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteUserDetails(@PathParam("userName") String userName) {
        if(userDetailsService.getUserDetails(userName)==null){
            throw new DataNotFoundException("User does not exist");
        }
        userDetailsService.removeUserDetails(userName);
    }

    @Path("/{userName}/skills")
    public SkillsResource getSkillsResource(@PathParam("userName") String userName) {
        System.out.println("getSkillsResource "+userName);
        return new SkillsResource();
    }
}
