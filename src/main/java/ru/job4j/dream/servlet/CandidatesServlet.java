package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidatesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", Store.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Store.instOf().addCandidates(
                new Candidate(
                        Integer.valueOf(req.getParameter("id")),
                        req.getParameter("name")
                )
        );
        //Store.instOf().addCandidates(new Candidate(0, req.getParameter("name")));
//        resp.sendRedirect(req.getContextPath()+"/candidates.jsp");
        resp.sendRedirect(req.getContextPath()+"/candidate.do");
    }
}






