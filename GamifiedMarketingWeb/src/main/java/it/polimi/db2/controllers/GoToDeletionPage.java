package it.polimi.db2.controllers;

import it.polimi.db2.entities.PDay;
import it.polimi.db2.services.PDayService;
import it.polimi.db2.webUtils.AuthUtils;

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


@WebServlet("/admin/deletion")
public class GoToDeletionPage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "PDayService")
    PDayService pDayService;

    List<PDay> pDayList;
    List<String> dateList;


    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (!AuthUtils.checkAdminPrivilegies(request, response)) return;

        pDayList = pDayService
                .getAllPDays()
                .stream()
                .filter(pDay -> pDay.getDate().before(new Date()))
                .collect(Collectors.toList());

        dateList = pDayList
                .stream()
                .map((pDay) -> DateUtils.getDayString(pDay.getDate()))
                .collect(Collectors.toList());

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("pDayList", pDayList);
        ctx.setVariable("dateList", dateList);
        templateEngine.process("/WEB-INF/views/deletion", ctx, response.getWriter());
        response.setContentType("text/plain");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String dayString = StringEscapeUtils.escapeJava(request.getParameter("product"));
        Date day;

        if (dayString == null) {
            ctx.setVariable("message", "Empty Field");
            templateEngine.process("/WEB-INF/views/deletion", ctx, response.getWriter());
            return;
        }

        try {
            day = new SimpleDateFormat("yyyy-MM-dd").parse(dayString);
        } catch (ParseException e) {
            ctx.setVariable("message", "Error in parsing the date");
            templateEngine.process("/WEB-INF/views/deletion", ctx, response.getWriter());
            return;
        }

        pDayList = pDayService
                .getAllPDays()
                .stream()
                .filter(pDay -> pDay.getDate().before(new Date()))
                .collect(Collectors.toList());

        dateList = pDayList
                .stream()
                .map((pDay) -> DateUtils.getDayString(pDay.getDate()))
                .collect(Collectors.toList());

        ctx.setVariable("pDayList", pDayList);
        ctx.setVariable("dateList", dateList);

        try {
            pDayService.deletePDayData(day);
            ctx.setVariable("message", "Questionnaire Data Deleted Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.setVariable("message", "There was an error in deleting the Questionnaire Data");
        }
        templateEngine.process("/WEB-INF/views/deletion", ctx, response.getWriter());
    }


}