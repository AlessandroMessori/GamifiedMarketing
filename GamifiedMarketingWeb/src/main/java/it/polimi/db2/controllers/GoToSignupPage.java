package it.polimi.db2.controllers;

import it.polimi.db2.services.UserService;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/signup")
public class GoToSignupPage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "UserService")
    UserService userService;

    public GoToSignupPage() {
        super();
    }

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        templateEngine.process("/WEB-INF/views/signup", ctx, response.getWriter());
        response.setContentType("text/plain");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path;
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        // obtain and escape params
        String email = StringEscapeUtils.escapeJava(request.getParameter("email"));
        String uname = StringEscapeUtils.escapeJava(request.getParameter("username"));
        String pwd = StringEscapeUtils.escapeJava(request.getParameter("password"));

        try {
            checkForEmptyCredentials(email, uname, pwd);
        } catch (Exception e) {
            // for debugging only e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
            return;
        }

        try {
            userService.registerUser(email, uname, pwd);
            path = "/WEB-INF/views/login";
            ctx.setVariable("successMsg", "User Registered Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.setVariable("errorMsg", e.getMessage());
            path = "/WEB-INF/views/signup";
        }

        templateEngine.process(path, ctx, response.getWriter());
    }

    private void checkForEmptyCredentials(String email, String uname, String pwd) throws Exception {

        if (email == null || uname == null || pwd == null || email.isEmpty() || uname.isEmpty() || pwd.isEmpty()) {
            throw new Exception("Missing or empty credential value");
        }

    }

}