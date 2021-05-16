package ru.job4j.dream.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GreetingServlet extends HttpServlet {

    private static final  Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());

        Data data = new Data("Nice to meet you, "+name);
        String json = GSON.toJson(data);
        System.out.println(json);
        //writer.println("Nice to meet you, "+name);
        //writer.println(name);
        writer.println(json);
        writer.flush();
    }
}

class Data{
    private String text;

    public Data(String text) {
        this.text = text;
    }

    public String getName() {
        return text;
    }

    public void setName(String text) {
        this.text = text;
    }
}
