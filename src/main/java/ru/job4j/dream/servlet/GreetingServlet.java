package ru.job4j.dream.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class GreetingServlet extends HttpServlet {

    private static final  Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());

        String text = req.getParameter("text");
        Data data = new Data("Nice to meet you, "+text);
        String json = GSON.toJson(data);
        System.out.println(json);
        writer.println(json);
        writer.flush();
    }
}

class Data{
    private String text;

    public Data(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

     public void setText(String text) {
        this.text = text;
    }
}
