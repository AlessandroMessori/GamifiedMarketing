package it.polimi.db2.controllers;

import fakeEntities.Review;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;
import java.util.ArrayList;

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

    ArrayList<Review> reviewList;


    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        reviewList = new ArrayList<>();
        reviewList.add(new Review("User 1", 4, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel pulvinar erat, et pretium purus. Duis non eleifend nibh, id pulvinar nisl. Nulla porta tempus urna eu interdum. Praesent augue lectus, mollis non aliquam non, viverra eget sapien. Proin suscipit ullamcorper elit, non suscipit sapien ultricies vitae."));
        reviewList.add(new Review("User 2", 3, "Maecenas consectetur sed neque et egestas. In ante leo, consectetur semper laoreet commodo, aliquam eu diam. "));
        reviewList.add(new Review("User 3", 5, "Duis sed accumsan nibh. Donec placerat dui elit, pulvinar varius felis semper non. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Suspendisse vitae metus dolor. Fusce arcu diam, consequat a mattis eu, mollis at elit."));
        reviewList.add(new Review("User 4", 5, "Duis sed accumsan nibh. Donec placerat dui elit, pulvinar varius felis semper non. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Suspendisse vitae metus dolor. Fusce arcu diam, consequat a mattis eu, mollis at elit."));
        reviewList.add(new Review("User 5", 5, "Duis sed accumsan nibh. Donec placerat dui elit, pulvinar varius felis semper non. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Suspendisse vitae metus dolor. Fusce arcu diam, consequat a mattis eu, mollis at elit."));
        reviewList.add(new Review("User 6", 5, "Duis sed accumsan nibh. Donec placerat dui elit, pulvinar varius felis semper non. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Suspendisse vitae metus dolor. Fusce arcu diam, consequat a mattis eu, mollis at elit."));

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("reviewList", reviewList);
        ctx.setVariable("productName", "iPhone 12");
        ctx.setVariable("productImg","https://images.everyeye.it/img-notizie/iphone-12-pro-max-davvero-ultratop-apple-previste-specifiche-esclusive-v3-471318-1280x720.jpg");

        templateEngine.process("/WEB-INF/views/home", ctx, response.getWriter());
        response.setContentType("text/plain");
    }

}