package org.karthik.skillstore.resources;

import org.karthik.skillstore.models.LoginRequest;
import org.karthik.skillstore.models.Sessions;
import org.karthik.skillstore.services.LoginService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;


@Path("/login")
public class LoginResource {

    private final LoginService loginService = new LoginService();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {
        Sessions session = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
        NewCookie cookie = new NewCookie("session", session.getSessionId(), "/", null, null, 60 * 60 * 24 , false, true);
        return Response.ok().cookie(cookie).build();
    }
}
