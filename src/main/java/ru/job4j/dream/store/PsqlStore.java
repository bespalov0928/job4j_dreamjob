package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
        return posts;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
        return post;
    }

    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement pss = cn.prepareStatement("UPDATE post set name = ? where id = ?")) {
            pss.setString(1, post.getName());
            pss.setInt(2, post.getId());
            pss.execute();
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }

    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from post where id=?")) {
            ps.setInt(1, id);
            ps.execute();
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    post = new Post(it.getInt("id"), it.getString("name"));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
        return post;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * from candidate")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name"), it.getString("city")));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
        return candidates;
    }

    @Override
    public void addCandidates(Candidate candidate) {
        if (candidate.getId() == 0) {
            createCandidate(candidate);
        } else {
            updateCandidate(candidate);
        }

    }

    private Candidate createCandidate(Candidate candidate) {
        Candidate cand = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO candidate(name, city) values (?,?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getCity());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
        return cand;
    }

    private void updateCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("update candidate set name  = ?, city = ? where id = ?")) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getCity());
            ps.setInt(3, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
    }

    @Override
    public Candidate findByIdCan(int id) {
        Candidate cand = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * FROM candidate WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cand = new Candidate(rs.getInt("id"), rs.getString("name"), rs.getString("city"));
                }
            }
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
        return cand;
    }

    @Override
    public void delCandidate(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("delete from candidate where id = ?")) {
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
    }

    @Override
    public User addUser(int id, String name, String email, String password) {
        User user = null;
        if (id == 0) {
            user = createUser(name, email, password);
        } else {
            user = updateUser(id, name, email, password);
        }
        return user;

    }

    private User createUser(String name, String email, String password) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO users(name, email, password) values (?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    int idNew = id.getInt(1);
                    user = new User(idNew, name, email, password);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
        return user;
    }

    private User updateUser(int id, String name, String email, String password) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("update user set name = ?, email = ?, password=? where id=?")) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setInt(4, id);
            ps.execute();
            user = new User(id, name, email, password);
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from users where email = ?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
                    System.out.println(user);
                }
            }
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
        return user;
    }

    @Override
    public Collection<String> findAllCity() {
        List<String> arrayList = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from city")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    arrayList.add(rs.getString("name"));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
        return arrayList;
    }
}
