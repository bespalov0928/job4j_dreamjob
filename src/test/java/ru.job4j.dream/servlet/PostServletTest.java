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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class PostServletTest {

    @Test
    public void doGet() throws ServletException, IOException {
        Store mem = new MemStore();
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn((Store) mem);

        PsqlStore.instOf().addUser(0, "root", "root@mail.ru", "root");
        PsqlStore.instOf().save(new Post(0, "post1"));

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        req.setAttribute("posts", PsqlStore.instOf().findAllPosts());

        when(PsqlStore.instOf()).thenReturn(mem);
        when(req.getSession()).thenReturn(session);
        when(req.getRequestDispatcher(any())).thenReturn(dispatcher);

        new PostServlet().doGet(req, resp);

        verify(req).getRequestDispatcher("posts.jsp");
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
