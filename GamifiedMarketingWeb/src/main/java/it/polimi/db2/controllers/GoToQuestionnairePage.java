package it.polimi.db2.controllers;

import it.polimi.db2.entities.Question;
import it.polimi.db2.services.QuestionService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/questionnaire")
public class GoToQuestionnairePage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    List<Question> statisticalQuestions;
    QuestionService questionService;


    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
        this.questionService = new QuestionService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        statisticalQuestions = new ArrayList<>();

        statisticalQuestions = questionService.getStatisticalQuestions();

        ctx.setVariable("statisticalQuestions", statisticalQuestions);

        templateEngine.process("/WEB-INF/views/questionnaire", ctx, response.getWriter());
        response.setContentType("text/plain");
    }
}
