package ru.job4j.dream.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CityServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());

        ArrayList<String> listCity = (ArrayList<String>) PsqlStore.instOf().findAllCity();
        System.out.println(listCity);
//        String text = req.getParameter("text");
        String json = GSON.toJson(listCity.toArray());
        System.out.println(json);
        writer.println(json);
        writer.flush();
    }
}
