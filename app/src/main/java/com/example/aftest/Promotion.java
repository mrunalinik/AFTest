package com.example.aftest;

import java.io.Serializable;

public class Promotion implements Serializable{

    private String buttonTitle;
    private String buttonTarget;
    private String description;
    private String image;
    private String footer;
    private String title;

    public String getButtonTitle() {
        return buttonTitle;
    }

    public void setButtonTitle(String buttonTitle) {
        this.buttonTitle = buttonTitle;
    }

    public String getButtonTarget() {
        return buttonTarget;
    }

    public void setButtonTarget(String buttonTarget) {
        this.buttonTarget = buttonTarget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return  "Title       :" + title +"<br/>"+
                "Description :" + description +"<br/>"+
                "Footer      :" + footer +"<br/>";
    }
}
