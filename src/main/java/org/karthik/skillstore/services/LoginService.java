package org.karthik.skillstore.services;

import org.karthik.skillstore.dao.SessionDao;
import org.karthik.skillstore.models.Sessions;
import org.karthik.skillstore.models.UserDetails;

import javax.ws.rs.NotAuthorizedException;
import java.util.UUID;

public class LoginService {
    private SessionDao sessionDao = new SessionDao();

    public Sessions login(String userName, String password) {
        UserDetails userDetails = new UserDetailsService().getUserDetails(userName);
        if(userDetails == null){
            throw new NotAuthorizedException("User does not exist");
        }
        boolean validCredentials = userDetails.getPassword().equals(password);
        if(!validCredentials) {
            throw new NotAuthorizedException("Invalid credentials");
        }
        Sessions session = new Sessions();
        session.setSessionId(UUID.randomUUID().toString());
        session.setUserId(userDetails.getUserId());
        session.setCreatedAt(System.currentTimeMillis());
        session.setLastAccessedAt(System.currentTimeMillis());
        sessionDao.createSession(session);
        return session;
    }

    public void logout(String sessionId) {
        sessionDao.deleteSession(sessionId);
    }
}
