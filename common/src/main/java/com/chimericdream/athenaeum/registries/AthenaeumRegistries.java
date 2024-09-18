package com.chimericdream.athenaeum.registries;

public class AthenaeumRegistries {
    public static final BookRegistry BOOKS;

    public static void init() {
    }

    static {
        BOOKS = new BookRegistry();
    }
}
