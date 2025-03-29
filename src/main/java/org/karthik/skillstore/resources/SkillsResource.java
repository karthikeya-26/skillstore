package org.karthik.skillstore.resources;


import org.karthik.skillstore.dao.UserDetailsDao;
import org.karthik.skillstore.exceptions.DataNotFoundException;
import org.karthik.skillstore.models.Skills;
import org.karthik.skillstore.services.SkillsService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/")
public class SkillsResource {

    private final SkillsService skillsService= new SkillsService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Skills> getSkills(@PathParam("userName") String userName) {
        System.out.println("user " + userName);
        return skillsService.getSkillsByUserName(userName);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSkills(@PathParam("userName")String userName, Skills skills) {
        skills.setUserId(new UserDetailsDao().getUserDetails(userName).getUserId());
        Skills addedSkill= skillsService.addSkill(skills);
        if(addedSkill!=null){
            return Response.status(Response.Status.CREATED).entity(addedSkill).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{skillId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Skills getSkillsWithSkillName(@PathParam("userName") String userName,@PathParam("skillId") int skillId) {
        return skillsService.getSkillBySkillId(userName,skillId);
    }

    @PUT
    @Path("/{skillId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSkills(@PathParam("userName")String userName,@PathParam("skillId") int skillId, Skills skills) {
        System.out.println("userName = " + userName + " skillId = " + skillId);
        if(skillsService.getSkillBySkillId(userName,skillId)==null){
            throw new DataNotFoundException("Skill does not exist for user "+userName);
        }
        skills.setUserId(new UserDetailsDao().getUserDetails(userName).getUserId());
        skillsService.updateSkill(skills);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{skillId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteSkills(@PathParam("userName") String userName, @PathParam("skillId") int skillId) {
        if(skillsService.getSkillBySkillId(userName,skillId)==null){
            throw new DataNotFoundException("Skill does not exist for user "+userName);
        }
        skillsService.deleteSkill(skillId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
