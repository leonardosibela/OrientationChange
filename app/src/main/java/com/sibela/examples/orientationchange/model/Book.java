package com.sibela.examples.orientationchange.model;

import java.util.Date;

public class Book implements TitleAndDescription {

    private String title;
    private String description;
    private String author;
    private Date publicationDate;
    private int edition;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
