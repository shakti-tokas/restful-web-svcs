package com.shaz.rest.svc.restfulwebsvcs.Bean;

public class Posts {

    private Integer post_id;
    private String post;

    @Override
    public String toString() {
        return "Posts{" +
                "post_id=" + post_id +
                ", post='" + post + '\'' +
                '}';
    }

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
