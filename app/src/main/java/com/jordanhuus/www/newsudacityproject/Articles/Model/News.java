package com.jordanhuus.www.newsudacityproject.Articles.Model;

/**
 * Created by jorda on 5/18/2018.
 */

public class News {

    private String title;
    private String webUrl;
    private String publicationDate;

    public News(String title, String webUrl, String publicationDate) {
        this.title = title;
        this.webUrl = webUrl;
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getPublicationDate() {
        return publicationDate;
    }
}
