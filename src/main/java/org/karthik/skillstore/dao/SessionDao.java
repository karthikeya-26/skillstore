package org.karthik.skillstore.dao;

import org.karthik.skillstore.dbutil.Database;
import org.karthik.skillstore.models.Sessions;
import org.karthik.skillstore.querylayer.Condition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDao {

    public Sessions getSession(String sessionId){
        Sessions session = null;
        try(Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("Select * from sessions where session_id = ?;");){
            preparedStatement.setString(1,sessionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                session = new Sessions();
                session.setSessionId(resultSet.getString("session_id"));
                session.setUserId(resultSet.getInt("user_id"));
                session.setCreatedAt(resultSet.getLong("created_at"));
                session.setLastAccessedAt(resultSet.getLong("last_accessed_at"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  session;

    }

    public void createSession(Sessions session){
        try(Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Insert into sessions (session_id, user_id, created_at, last_accessed_at) values (?,?,?,?);");){
            preparedStatement.setString(1,session.getSessionId());
            preparedStatement.setInt(2,session.getUserId());
            preparedStatement.setLong(3,session.getCreatedAt());
            preparedStatement.setLong(4,session.getLastAccessedAt());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateSession(Sessions session){
        try(Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update sessions set last_accessed_at = ? where session_id = ?;");){
            preparedStatement.setLong(1,session.getLastAccessedAt());
            preparedStatement.setString(2,session.getSessionId());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteSession(String sessionId){
        try(Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from sessions where session_id = ?;");){
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
