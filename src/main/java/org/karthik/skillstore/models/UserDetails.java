package org.karthik.skillstore.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement
public class UserDetails implements Serializable {
    private Integer userId;
    private String userName;
    private String email;
    private transient String password;
    private String location;
    private Long createdAt;
    private Long updatedAt;
    private List<Link> links = new ArrayList<>();

    public UserDetails() {}

    public UserDetails(Integer userId, String userName, String email, String password, String location) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.location = location;

    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Long getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
    public Long getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(String rel, String url) {
        Link link = new Link();
        link.setRel(rel);
        link.setUrl(url);
        links.add(link);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "updatedAt='" + updatedAt + '\'' +
                ", userName='" + userName + '\'' +
                ", userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
