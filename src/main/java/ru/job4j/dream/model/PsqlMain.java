package ru.job4j.dream.model;

import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        store.save(new Post(1, "Java midl Job"));
        Post post = store.findById(7);
        System.out.println(post.getName());

    }

}
