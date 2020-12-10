package it.polimi.db2.controllers;

import entities.Review;
import services.ReviewService;
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


@WebServlet("/home")
public class GoToHomePage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    List<Review> reviewList;
    ReviewService reviewService;


    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
        this.reviewService = new ReviewService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        reviewList = reviewService.getReviewsByProductId(1);
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("reviewList", reviewList);
        ctx.setVariable("productName", "iPhone 12");
        ctx.setVariable("productImg", "https://images.everyeye.it/img-notizie/iphone-12-pro-max-davvero-ultratop-apple-previste-specifiche-esclusive-v3-471318-1280x720.jpg");

        templateEngine.process("/WEB-INF/views/home", ctx, response.getWriter());
        response.setContentType("text/plain");
    }

}