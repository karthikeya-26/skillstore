package org.karthik.skillstore.services;

import org.karthik.skillstore.dao.SkillsDao;
import org.karthik.skillstore.exceptions.BadRequestException;
import org.karthik.skillstore.exceptions.DataNotFoundException;
import org.karthik.skillstore.models.Skills;

import java.util.List;


public class SkillsService {
    private SkillsDao skillsDao = new SkillsDao();

    public List<Skills> getSkills() {
        return skillsDao.getAllSkills();
    }

//    public Skills getSkillById(int id) {
//        if(id<=0 ){
//            throw new IllegalArgumentException("Skill id must be greater than 0");
//        }
//        Skills skill =  skillsDao.getSkillById(id);
//        if(skill==null){
//            throw new DataNotFoundException("Skill with id "+id+" not found");
//        }
//        return skill;
//    }

    public List<Skills> getSkillBySkillName(String userName, String skillName) {
        if((skillName==null || skillName.isEmpty())&&(userName==null || userName.isEmpty())){
            throw new IllegalArgumentException("Skill name and user name cannot be null or empty");
        }
        List<Skills> skills = skillsDao.getSkillBySkillName(userName,skillName);
        if(skills==null || skills.isEmpty()){
            throw new DataNotFoundException("Skill with name "+skillName+" not found for user "+userName+".");
        }
        return skills;

    }

    public Skills getSkillBySkillId(String userName, int skillId) {
        if(skillId<=0 ){
            throw new IllegalArgumentException("Skill id must be greater than 0");
        }
        System.out.println("hi from skill service");
        Skills skill =  skillsDao.getSkillBySkillId(userName,skillId);
        System.out.println("skill in skill service after dao:"+skill);
        if(skill==null){
            throw new DataNotFoundException("Skill with id "+skillId+" for user "+userName+"  not found");
        }
        return skill;
    }

    public List<Skills> getSkillsByUserName(String userName) {
        if(userName==null || userName.isEmpty()){
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        List<Skills> skills = skillsDao.getUserSkills(userName);
        if(skills==null || skills.isEmpty()){
            throw new DataNotFoundException("Skills for user "+userName+" not found");
        }
        return skills;
    }
    public Skills addSkill(Skills skills) {

        if(skills.getSkillName()==null || skills.getSkillName().isEmpty()|| skills.getSkillDescription()==null || skills.getSkillDescription().isEmpty()|| skills.getPrice()==null|| skills.getPrice().doubleValue()<=0){
            throw new BadRequestException(
                    "Skill name, skill description and price must be provided and valid");
        }
        skills.setCreatedAt(System.currentTimeMillis());
        return skillsDao.addSkill(skills);
    }

    public void updateSkill(Skills skills) {
        if(skills.getSkillName()==null || skills.getSkillName().isEmpty()|| skills.getSkillDescription()==null || skills.getSkillDescription().isEmpty()|| skills.getPrice()==null|| skills.getPrice().doubleValue()<=0){
            throw new BadRequestException(
                    "Skill name, skill description and price must be provided and valid");
        }
        skills.setUpdatedAt(System.currentTimeMillis());
        skillsDao.updateSkill(skills);
    }

    public void deleteSkill(int skillId) {
        skillsDao.deleteSkill(skillId);
    }




}
