package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    void save(Post post);

    Post findById(int id);

    Collection<Candidate> findAllCandidates();

    void addCandidates(Candidate candidate);

    Candidate findByIdCan(int id);

    void delCandidate(int id);

    User addUser(int id, String name, String email, String password);

    User findUserById(String email, String password);

    User findUserByEmail(String email);
}
