package it.polimi.db2.controllers;

import it.polimi.db2.entities.Question;
import it.polimi.db2.services.AnswerService;
import it.polimi.db2.services.QuestionService;
import it.polimi.db2.utils.AuthUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/statistical")
public class GoToStatisticalPage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "AnswerService")
    AnswerService answerService;

    @EJB(name = "QuestionService")
    QuestionService questionService;

    List<Question> statisticalQuestions;



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
        statisticalQuestions = new ArrayList<>();

        if (!AuthUtils.checkAuthentication(request, response)) return;

        statisticalQuestions = questionService.getStatisticalQuestions();

        ctx.setVariable("statisticalQuestions", statisticalQuestions);

        templateEngine.process("/WEB-INF/views/statistical", ctx, response.getWriter());
        response.setContentType("text/plain");
    }
}
