package it.polimi.db2.controllers;

import it.polimi.db2.entities.Answer;
import it.polimi.db2.services.AnswerService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    List<Answer> answers;
    HashMap<String, List<Answer>> answerGroups;


    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        answers = answerService.findAnswersByDate(new Date());
        answerGroups = groupAnswersByUser(answers);
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("answers", answerGroups);
        templateEngine.process("/WEB-INF/views/inspection", ctx, response.getWriter());
        response.setContentType("text/plain");
    }

    HashMap<String, List<Answer>> groupAnswersByUser(List<Answer> answers) {
        HashMap<String, List<Answer>> hashMap = new HashMap<>();

        for (Answer a : answers) {
            String currentUserEmail = a.getUserEmail();
            if (!hashMap.containsKey(currentUserEmail)) {
                List<Answer> list = new ArrayList<Answer>();
                list.add(a);

                hashMap.put(currentUserEmail, list);
            } else {
                hashMap.get(currentUserEmail).add(a);
            }
        }

        return hashMap;
    }


}