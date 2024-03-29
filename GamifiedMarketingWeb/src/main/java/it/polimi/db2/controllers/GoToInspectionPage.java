package it.polimi.db2.controllers;

import it.polimi.db2.entities.Answer;
import it.polimi.db2.pairs.AnswersPoints;
import it.polimi.db2.services.AnswerService;

import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    
    Map<String, AnswersPoints> allAnswers, completedAnswers;
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
        String dayString = StringEscapeUtils.escapeJava(request.getParameter("day"));
        Date day = null;

        if (dayString == null) {
            dayString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }


        try {
            day = new SimpleDateFormat("yyyy-MM-dd").parse(dayString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        ctx.setVariable("day", dayString);

        try {
            allAnswers = answerService.findAnswersByDate(day);
            deletedUsers = getDeletedUsers();

            completedAnswers = allAnswers
                    .entrySet()
                    .stream()
                    .filter(item -> item.getValue().getPoints() > 0)
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
        for (HashMap.Entry<String, AnswersPoints> entry : allAnswers.entrySet()) {
            if (entry.getValue().getPoints() == 0) {
                deletedUsers.add(entry.getKey());
            }
        }
        return deletedUsers;
    }

}