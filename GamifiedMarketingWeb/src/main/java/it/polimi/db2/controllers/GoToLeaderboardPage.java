package it.polimi.db2.controllers;

import fakeEntities.Position;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/leaderboard")
public class GoToLeaderboardPage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    List<Position> positionsList;


    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Random rd = new Random();
        List<Position> leaderboard;

        positionsList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            positionsList.add(new Position("User " + i, rd.nextInt(1000)));
        }

        leaderboard = positionsList.stream()
                .sorted((p1, p2) -> p2.getPoints() - p1.getPoints())
                .collect(Collectors.toList());

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("leaderboard", leaderboard);
        templateEngine.process("/WEB-INF/views/leaderboard", ctx, response.getWriter());
        response.setContentType("text/plain");
    }

}