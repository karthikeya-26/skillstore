package org.karthik.skillstore.dao;

import org.karthik.skillstore.dbutil.Database;
import org.karthik.skillstore.models.UserDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDetailsDao {

    public List<UserDetails> getUserDetails() {
        List<UserDetails> userDetailsList = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user_details");
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                UserDetails userDetails = new UserDetails();
                prepareUserDetails(userDetails, resultSet);
                userDetailsList.add(userDetails);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userDetailsList;
    }

    public UserDetails getUserDetails(long userId) {
        UserDetails userDetails = null;
        try (Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("Select * from user_details where user_id =?")){
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userDetails = new UserDetails();
                prepareUserDetails(userDetails, resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userDetails;
    }
    public UserDetails getUserDetails(String userName) {
        UserDetails userDetails = null;
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Select * from user_details where user_name =?")){
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userDetails = new UserDetails();
                prepareUserDetails(userDetails, resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userDetails;
    }

    private void prepareUserDetails(UserDetails userDetails, ResultSet resultSet) throws SQLException {
        userDetails.setUserId(resultSet.getInt("user_id"));
        userDetails.setUserName(resultSet.getString("user_name"));
        userDetails.setPassword(resultSet.getString("password"));
        userDetails.setEmail(resultSet.getString("email"));
        userDetails.setLocation(resultSet.getString("location"));
        userDetails.setCreatedAt(resultSet.getLong("created_at"));
        userDetails.setUpdatedAt(resultSet.getString("updated_at")==null?0L:resultSet.getLong("updated_at"));
    }

    public UserDetails addUserDetails(UserDetails userDetails) {
        try(Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into user_details (user_name,email,password,location,created_at) values (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, userDetails.getUserName());
            preparedStatement.setString(2, userDetails.getEmail());
            preparedStatement.setString(3, userDetails.getPassword());
            preparedStatement.setString(4, userDetails.getLocation());
            preparedStatement.setLong(5, userDetails.getCreatedAt());
            if(preparedStatement.executeUpdate()>0){
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if(generatedKeys.next()){
                    userDetails.setUserId(generatedKeys.getInt(1));
                }
            }else {
                throw new RuntimeException("Error adding user details");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userDetails;
    }

    public UserDetails updateUserDetails(UserDetails userDetails) {
        try(Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("udpate user_details set user_name= ?, email=?, password=?, location=?, updated_at=? where user_id=?")) {
            preparedStatement.setString(1, userDetails.getUserName());
            preparedStatement.setString(2, userDetails.getEmail());
            preparedStatement.setString(3, userDetails.getPassword());
            preparedStatement.setString(4, userDetails.getLocation());
            preparedStatement.setLong(5, userDetails.getUpdatedAt());
            preparedStatement.setLong(6, userDetails.getUserId());
            preparedStatement.executeUpdate();
            return userDetails;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUserDetails(String userName) {
        try(Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from user_details where user_name=?")) {
            preparedStatement.setString(1, userName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        UserDetailsDao dao = new UserDetailsDao();
        List<UserDetails> userDetailsList = dao.getUserDetails();
        System.out.println(userDetailsList);
    }
}
