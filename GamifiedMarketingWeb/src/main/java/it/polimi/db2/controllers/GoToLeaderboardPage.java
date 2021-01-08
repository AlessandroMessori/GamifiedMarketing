package it.polimi.db2.controllers;

import it.polimi.db2.entities.Points;
import it.polimi.db2.exceptions.NoPDayException;
import it.polimi.db2.exceptions.NoPointsException;
import it.polimi.db2.services.LeaderboardService;
import it.polimi.db2.services.PDayService;
import it.polimi.db2.utils.AuthUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.ejb.EJB;
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

    @EJB(name = "LeaderboardService")
    LeaderboardService leaderboardService;


    @EJB(name = "PDayService")
    PDayService pDayService;

    List<Points> leaderboard;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        if (!AuthUtils.checkAuthentication(request, response)) return;


        //checks if a pDay for today as been added
        try {
            pDayService.getTodayProduct();
        } catch (NoPDayException e) {
            e.printStackTrace();
            response.sendRedirect("/pday");
            return;
        }

        leaderboard = null;
        try {
            leaderboard = leaderboardService.getTodayLeaderboard();
        } catch (NoPointsException e) {
            e.printStackTrace();

        }

        leaderboard = leaderboard == null ? null : leaderboard.stream()
                .filter(points -> points.getVal() > 0)
                .sorted((p1, p2) -> p2.getVal() - p1.getVal())
                .collect(Collectors.toList());

        ctx.setVariable("leaderboard", leaderboard);
        templateEngine.process("/WEB-INF/views/leaderboard", ctx, response.getWriter());
        response.setContentType("text/plain");
    }

}