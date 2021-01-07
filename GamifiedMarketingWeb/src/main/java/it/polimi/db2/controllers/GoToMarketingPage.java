package it.polimi.db2.controllers;

import it.polimi.db2.entities.Question;
import it.polimi.db2.entities.User;
import it.polimi.db2.exceptions.BannedWordException;
import it.polimi.db2.exceptions.NoPDayException;
import it.polimi.db2.services.AnswerService;
import it.polimi.db2.services.PDayService;
import it.polimi.db2.services.QuestionService;
import it.polimi.db2.utils.AuthUtils;
import org.apache.commons.text.StringEscapeUtils;
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

import static java.lang.Integer.parseInt;


@WebServlet("/marketing")
public class GoToMarketingPage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "AnswerService")
    AnswerService answerService;

    @EJB(name = "QuestionService")
    QuestionService questionService;

    @EJB(name = "PDayService")
    PDayService pDayService;

    List<Question> marketingQuestions, statisticalQuestions;


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
        marketingQuestions = new ArrayList<>();
        statisticalQuestions = new ArrayList<>();

        if (!AuthUtils.checkAuthentication(request, response) || AuthUtils.checkUserBan(request, response)) return;

        //checks if a pDay for today as been added
        try {
            pDayService.getTodayProduct();
        } catch (NoPDayException e) {
            e.printStackTrace();
            response.sendRedirect("/pday");
            return;
        }

        marketingQuestions = questionService.getTodayMarketingQuestions();
        statisticalQuestions = questionService.getTodayStatisticalQuestions();

        ctx.setVariable("marketingQuestions", marketingQuestions);
        ctx.setVariable("statisticalQuestions", statisticalQuestions);

        templateEngine.process("/WEB-INF/views/marketing", ctx, response.getWriter());
        response.setContentType("text/plain");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        List<Integer> idsList = new ArrayList<>();
        List<String> answerList = new ArrayList<>();
        User user = (User) request.getSession().getAttribute("user");

        if (!AuthUtils.checkAuthentication(request, response) || AuthUtils.checkUserBan(request, response)) return;

        // obtain and escape params
        for (String key : request.getParameterMap().keySet()) {
            idsList.add(parseInt(key.split("-")[1]));
            answerList.add(StringEscapeUtils.escapeJava(request.getParameter(key)));
        }


        try {
            answerService.saveUserAnswers(user.getEmail(), idsList, answerList);
            ctx.setVariable("message", "Questions saved successfully!");
            response.sendRedirect("/greetings");
        } catch (BannedWordException e) {
            response.sendRedirect("/pday");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.setVariable("message", "There was an error in saving the marketing questions");
        }
    }
}
