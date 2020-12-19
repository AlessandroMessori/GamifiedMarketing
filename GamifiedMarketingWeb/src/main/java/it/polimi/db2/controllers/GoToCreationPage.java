package it.polimi.db2.controllers;

import it.polimi.db2.entities.Product;
import it.polimi.db2.services.PDayService;
import it.polimi.db2.services.ProductService;
import it.polimi.db2.services.QuestionService;
import it.polimi.db2.utils.AuthUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;
import java.util.*;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.Integer.parseInt;


@WebServlet("/admin/creation")
public class GoToCreationPage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "ProductService")
    ProductService productService;
    @EJB(name = "PDayService")
    PDayService pDayService;
    List<Product> products;


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

        products = productService.getAllProducts();

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("productList", products);
        templateEngine.process("/WEB-INF/views/creation", ctx, response.getWriter());
        response.setContentType("text/plain");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        List<String> questionList = new ArrayList<>();

        // obtain and escape params
        String product = StringEscapeUtils.escapeJava(request.getParameter("product"));
        String day = StringEscapeUtils.escapeJava(request.getParameter("day"));

        if (!AuthUtils.checkAdminPrivilegies(request, response)) return;

        try {
            checkForEmptyCredentials(product, day);
        } catch (Exception e) {
            // for debugging only e.printStackTrace();
            ctx.setVariable("message", "Empty Field");
            ctx.setVariable("productList", products);
            templateEngine.process("/WEB-INF/views/creation", ctx, response.getWriter());
            return;
        }

        // obtain and escape params
        for (String key : request.getParameterMap().keySet()) {
            if (key.contains("question")) {
                questionList.add(StringEscapeUtils.escapeJava(request.getParameter(key)));
            }
        }

        try {
            pDayService.createPday(product, day, questionList);
            ctx.setVariable("message", "PDay Saved Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.setVariable("message", "There was an error in saving the Product of The Day");
        }

        products = productService.getAllProducts();
        ctx.setVariable("productList", products);
        templateEngine.process("/WEB-INF/views/creation", ctx, response.getWriter());
    }

    private void checkForEmptyCredentials(String product, String date) throws Exception {

        if (product == null || date == null || product.isEmpty() || date.isEmpty()) {
            throw new Exception("Missing or empty value");
        }

    }

}