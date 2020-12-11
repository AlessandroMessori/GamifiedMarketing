package it.polimi.db2.controllers;

import entities.PDay;
import entities.Product;
import entities.Review;
import exceptions.NoPDayException;
import services.PDayService;
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
    PDay pDay;
    ReviewService reviewService;
    PDayService pDayService;


    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
        this.reviewService = new ReviewService();
        this.pDayService = new PDayService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String productName = "There is no product of the day yet!";
        String productImg = "https://www.generationsforpeace.org/wp-content/uploads/2018/03/empty.jpg";
        reviewList = new ArrayList<>();

        try {
            pDay = pDayService.getTodayProduct();
            Product product = pDay.getProduct();
            reviewList = reviewService.getReviewsByProductId(product.getId());
            productName = product.getName();
            productImg = product.getImage();
        } catch (NoPDayException e) {
            e.printStackTrace();
        } finally {
            ctx.setVariable("reviewList", reviewList);
            ctx.setVariable("productName", productName);
            ctx.setVariable("productImg", productImg);

            templateEngine.process("/WEB-INF/views/home", ctx, response.getWriter());
            response.setContentType("text/plain");
        }
    }

}