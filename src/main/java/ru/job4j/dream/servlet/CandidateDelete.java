package ru.job4j.dream.servlet;

import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class CandidateDelete extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("id");
        for (File file : new File("c:\\images\\").listFiles()) {
            String fileName = file.getName();
            if (fileName.indexOf(".") > 0) {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            fileName.split(".");
            if (name.equals(fileName)) {
                file.delete();
                PsqlStore.instOf().delCandidate(Integer.valueOf(fileName));
                break;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/candidate.do");
    }
}
