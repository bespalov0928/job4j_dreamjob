package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MemStore implements Store{
    private static final Store INST = new MemStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final Map<String, User> users = new ConcurrentHashMap<>();

    private final List<String> citys = new ArrayList<>();

    private static AtomicInteger POST_ID = new AtomicInteger(4);

    private static AtomicInteger CAND_ID = new AtomicInteger(4);

    private static AtomicInteger USER_ID = new AtomicInteger(4);

//    private MemStore() {
//        posts.put(1, new Post(1, "Junior Java Job"));
//        posts.put(2, new Post(2, "Middle Java Job"));
//        posts.put(3, new Post(3, "Senior Java Job"));
//        candidates.put(1, new Candidate(1, "Junior  Java"));
//        candidates.put(2, new Candidate(2, "Middle Java"));
//        candidates.put(3, new Candidate(3, "Senior  Java"));
//    }

    public static Store instOf() {
        return INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> postsList = new ArrayList<>();
        postsList = posts.entrySet().stream()
                .map(e -> e.getValue())
                .collect(Collectors.toList());
        System.out.println(postsList);
        return postsList;
//        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void addCandidates(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CAND_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findByIdCan(int id) {
        return candidates.get(id);
    }

    @Override
    public void delCandidate(int id) {

    }

    @Override
    public User addUser(int id, String name, String email, String password) {
        User user = new User(id, name, email, password);
        if (id == 0) {
            user.setId(USER_ID.incrementAndGet());
        }
        users.put(email, user);
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        return users.get(email);
    }

    @Override
    public Collection<String> findAllCity() {
        return citys;
    }
}
