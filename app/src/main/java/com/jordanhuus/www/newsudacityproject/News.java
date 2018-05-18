package com.jordanhuus.www.newsudacityproject;

/**
 * Created by jorda on 5/18/2018.
 */

public class News {

    private String headLine;
    private String article;
    private long time;
    private String editor;

    public News(String headLine, String article, long time, String editor) {
        this.headLine = headLine;
        this.article = article;
        this.time = time;
        this.editor = editor;
    }


    public String getHeadLine() {
        return headLine;
    }

    public String getArticle() {
        return article;
    }

    public long getTime() {
        return time;
    }

    public String getEditor() {
        return editor;
    }
}
