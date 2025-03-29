package org.karthik.skillstore.models;

public class Link {

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    private String url;
    private String rel;

    @Override
    public String toString() {
        return "Link{" +
                "url='" + url + '\'' +
                ", rel='" + rel + '\'' +
                '}';
    }
}
