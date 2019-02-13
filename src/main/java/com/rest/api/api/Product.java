package com.rest.api.api;


public class Product {
    String name;
    String createdBy;
    String type;
    String question;

    public Product(String name,String createdBy,String type,String question){
        this.name=name;
        this.createdBy=createdBy;
        this.type=type;
        this.question=question;
    }

    public String getName() {
        return name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getByType() {
        return type;
    }

    public String getQuestion(){
     return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
