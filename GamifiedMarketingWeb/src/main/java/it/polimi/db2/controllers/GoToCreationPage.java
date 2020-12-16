package it.polimi.db2.controllers;

import it.polimi.db2.entities.Product;
import it.polimi.db2.services.ProductService;
import it.polimi.db2.utils.AuthUtils;
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


@WebServlet("/admin/creation")
public class GoToCreationPage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "ProductService")
    ProductService productService;
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

}