package org.karthik.skillstore.filters;

import org.karthik.skillstore.dao.SessionDao;
import org.karthik.skillstore.models.Link;
import org.karthik.skillstore.models.Sessions;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    private boolean isPublicUrl(String requestUri) {
        return requestUri.matches("^(login|api/login|public/).*");
    }
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (isPublicUrl(requestContext.getUriInfo().getPath())) {
            return;
        }
        if (requestContext.getCookies().get("session_id") == null) {
            handleUnAuthorizedRequest(requestContext);
            return;
        }
        String sessionId = requestContext.getCookies().get("session_id").getValue();
        Sessions session = new SessionDao().getSession(sessionId);
        if (session == null) {
            handleUnAuthorizedRequest(requestContext);
            return;
        }
        requestContext.setProperty("session_id", session.getSessionId());
        requestContext.setProperty("user_id", session.getUserId());
    }

    private void handleUnAuthorizedRequest(ContainerRequestContext requestContext) {
        UriInfo uriInfo = requestContext.getUriInfo();
        String loginUrl = uriInfo.getBaseUriBuilder().path("login").build().toString();
        Link notAuthenticatedMessage = new Link();
        notAuthenticatedMessage.setUrl(loginUrl);
        notAuthenticatedMessage.setRel("login");
        Response response = Response.status(Response.Status.UNAUTHORIZED).entity(notAuthenticatedMessage).build();
        requestContext.abortWith(response);
    }
}
