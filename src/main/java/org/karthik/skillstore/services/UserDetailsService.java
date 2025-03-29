package org.karthik.skillstore.services;

import org.karthik.skillstore.dao.UserDetailsDao;
import org.karthik.skillstore.exceptions.BadRequestException;
import org.karthik.skillstore.models.UserDetails;

import java.util.List;

public class UserDetailsService {
    private final UserDetailsDao userDetailsDao = new UserDetailsDao();
    public UserDetailsService(){
//        this.userDetails.put("karthik", new UserDetails(1L,"karthik","karthik@gmail","password","chennai"));
    }
    public List<UserDetails> getAllUserDetails() {
        return userDetailsDao.getUserDetails();
    }

    public UserDetails getUserDetails(String username) {
        return userDetailsDao.getUserDetails(username);
    }

    public UserDetails addUserDetails(UserDetails userDetails) {
        if(userDetails.getUserName() == null || userDetails.getUserName().trim().isEmpty() || userDetails.getPassword() == null || userDetails.getPassword().isEmpty() || userDetails.getEmail() == null || userDetails.getEmail().isEmpty() || userDetails.getLocation() == null || userDetails.getLocation().isEmpty()) {
            throw new BadRequestException("User details are missing. Please provide all the details");
        }
        userDetails.setCreatedAt(System.currentTimeMillis());
        userDetailsDao.addUserDetails(userDetails);
        return userDetails;
    }

    public UserDetails updateUserDetails(UserDetails userDetails) {
        if(userDetails.getUserName() == null || userDetails.getUserName().isEmpty()) {
            return null;
        }
        userDetailsDao.updateUserDetails(userDetails);
        return userDetails;
    }

    public void removeUserDetails(String userName) {
        userDetailsDao.deleteUserDetails(userName);
    }

}
