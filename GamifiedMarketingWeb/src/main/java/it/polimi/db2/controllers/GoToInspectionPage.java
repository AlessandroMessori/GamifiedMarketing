package it.polimi.db2.controllers;

import it.polimi.db2.entities.Answer;
import it.polimi.db2.services.AnswerService;
import org.apache.commons.lang3.tuple.Pair;
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


@WebServlet("/admin/inspection")
public class GoToInspectionPage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "AnswerService")
    AnswerService answerService;
    Map<String, Pair<List<Answer>, Integer>> allAnswers, completedAnswers;
    List<String> deletedUsers;

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

        try {
            allAnswers = answerService.findAnswersByDate(new Date());
            deletedUsers = getDeletedUsers();

            completedAnswers = allAnswers
                    .entrySet()
                    .stream()
                    .filter(item -> item.getValue().getRight() > 0)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            ctx.setVariable("answers", completedAnswers);
            ctx.setVariable("deletedUsers", deletedUsers);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.setVariable("error", e.getMessage());
        }

        templateEngine.process("/WEB-INF/views/inspection", ctx, response.getWriter());
        response.setContentType("text/plain");
    }

    List<String> getDeletedUsers() {
        List<String> deletedUsers = new ArrayList<>();
        for (HashMap.Entry<String, Pair<List<Answer>, Integer>> entry : allAnswers.entrySet()) {
            if (entry.getValue().getRight() == 0) {
                deletedUsers.add(entry.getKey());
            }
        }
        return deletedUsers;
    }

}