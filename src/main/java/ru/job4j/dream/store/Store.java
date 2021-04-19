package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    void save(Post post);

    Post findById(int id);

    Collection<Candidate> findAllCandidates();

    void addCandidates(Candidate candidate);

    Candidate findByIdCan(int id);

}
