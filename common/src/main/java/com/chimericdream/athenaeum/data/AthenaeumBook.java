package com.chimericdream.athenaeum.data;

import java.util.List;

public class AthenaeumBook {
    public final String title;
    public final String author;
    public final List<String> pages;
    public final String genre;
    public final int edition;

    public AthenaeumBook(String title, String author, List<String> pages) {
        this(title, author, pages, null, 0);
    }

    public AthenaeumBook(String title, String author, List<String> pages, String genre) {
        this(title, author, pages, genre, 0);
    }

    public AthenaeumBook(String title, String author, List<String> pages, String genre, int edition) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.genre = genre;
        this.edition = edition;
    }
}
