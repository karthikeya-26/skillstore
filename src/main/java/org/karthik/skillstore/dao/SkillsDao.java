package org.karthik.skillstore.dao;

import org.karthik.skillstore.dbutil.Database;
import org.karthik.skillstore.models.Skills;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillsDao {

    public List<Skills> getUserSkills(String userName) {
        List<Skills> userSkillsList = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM skills where user_id =(select user_id from user_details where user_name = ?)");) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            prepareSkills(userSkillsList, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userSkillsList;
    }

    public List<Skills> getAllSkills() {
        List<Skills> skills = new ArrayList<>();
        try(Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from skills");
        ResultSet resultSet = preparedStatement.executeQuery();) {
            prepareSkills(skills, resultSet);
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return skills;
    }

    public Skills getSkillBySkillId(String userName,int skillId) {
        System.out.println(userName+" "+skillId);
        List<Skills> skills = new ArrayList<>();
        try(Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from skills where skill_id = ? and user_id =(select user_id from user_details where user_name = ?);");){
            preparedStatement.setInt(1, skillId);
            preparedStatement.setString(2, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            prepareSkills(skills, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skills.isEmpty() ?null:skills.get(0);
    }

    public List<Skills> getSkillBySkillName(String userName, String skillName) {

        List<Skills> skills = new ArrayList<>();
        try(Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from skills where name ILIKE ? and user_id =(select user_id from user_details where user_name = ?)");) {
            preparedStatement.setString(1, "%"+skillName+"%");
            preparedStatement.setString(2, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            prepareSkills(skills, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skills;
    }
    private void prepareSkills(List<Skills> skillsList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Skills skills = new Skills();
            skills.setSkillId(resultSet.getInt("skill_id"));
            skills.setSkillName(resultSet.getString("name"));
            skills.setSkillDescription(resultSet.getString("description"));
            skills.setUserId(resultSet.getInt("user_id"));
            skills.setPrice(resultSet.getBigDecimal("price_per_hour"));
            skills.setCreatedAt(resultSet.getLong("created_at"));
            skills.setUpdatedAt(resultSet.getString("updated_at")!= null ? resultSet.getLong("updated_at"):null);
            skillsList.add(skills);
        }
    }

    public Skills addSkill(Skills skills) {
        try(Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into skills ( user_id, name, price_per_hour, description, created_at) values ( ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)){
            preparedStatement.setInt(1, skills.getUserId());
            preparedStatement.setString(2, skills.getSkillName());
            preparedStatement.setBigDecimal(3,skills.getPrice());
            preparedStatement.setString(4, skills.getSkillDescription());
            preparedStatement.setLong(5, skills.getCreatedAt());
            if(preparedStatement.executeUpdate()>0){
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if(generatedKeys.next()){
                    skills.setSkillId(generatedKeys.getInt(1));
                }
            }else {
                throw new RuntimeException("Error inserting skill");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skills;
    }

    public Skills updateSkill(Skills skills) {
        try(Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update skills set name = ?, price_per_hour =?,description = ?, updated_at = ? where skill_id = ? and user_id = ?")){
            preparedStatement.setString(1, skills.getSkillName());
            preparedStatement.setBigDecimal(2, skills.getPrice());
            preparedStatement.setString(3, skills.getSkillDescription());
            preparedStatement.setLong(4, skills.getUpdatedAt());
            preparedStatement.setInt(5, skills.getSkillId());
            preparedStatement.setInt(6, skills.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skills;
    }

    public void deleteSkill(int skillId) {
        try(Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from skills where skill_id = ?")){
            preparedStatement.setLong(1, skillId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
