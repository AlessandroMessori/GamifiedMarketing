package it.polimi.db2.controllers;

import fakeEntities.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    List<Product> productList;
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
        productList = new ArrayList<>();
        dateList = new ArrayList<>();
        String img = "https://images.everyeye.it/img-notizie/iphone-12-pro-max-davvero-ultratop-apple-previste-specifiche-esclusive-v3-471318-1280x720.jpg";
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < 10; i++) {
            productList.add(new Product("Product " + i, img));
            String stringDate = DateFor.format(new Date());
            dateList.add(stringDate);
        }

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("productList", productList);
        ctx.setVariable("dateList", dateList);
        templateEngine.process("/WEB-INF/views/deletion", ctx, response.getWriter());
        response.setContentType("text/plain");
    }

}