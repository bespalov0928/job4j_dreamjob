package ru.job4j.dream.servlet;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class PostServletTest {

    @Test
    public void doGet() throws ServletException, IOException {
        MemStore mem = new MemStore();
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn((Store) mem);

        PsqlStore.instOf().addUser(0, "root", "root@mail.ru", "root");
        PsqlStore.instOf().save(new Post(0, "post1"));

        assertThat(mem.findAllPosts().iterator().next().getName(), is("post1"));

        User user = PsqlStore.instOf().findUserByEmail("root@mail.ru");
        assertThat(user.getName(), is("root"));

    }

    @Test
    public void doPost() throws ServletException, IOException {
        MemStore mem = new MemStore();
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn((Store) mem);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("name")).thenReturn("Petr Arsentev");
        new PostServlet().doPost(req, resp);
        assertThat(mem.findAllPosts().iterator().next().getName(), is("Petr Arsentev"));

    }
}
