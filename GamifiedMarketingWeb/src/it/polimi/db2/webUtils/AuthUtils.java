package it.polimi.db2.webUtils;

import it.polimi.db2.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthUtils {

    public static boolean checkAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("user") == null) {
            response.sendRedirect("/GamifiedMarketingWeb/");
            return false;
        }

        return true;
    }

    public static boolean checkAdminPrivilegies(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("user") == null || !((User) session.getAttribute("user")).getIsAdmin()) {
            response.sendRedirect("/GamifiedMarketingWeb/pday");
            return false;
        }

        return true;
    }

    public static boolean checkUserBan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("user") == null || ((User) session.getAttribute("user")).getIsBanned()) {
            response.sendRedirect("/GamifiedMarketingWeb/pday");
            return true;
        }

        return false;
    }

}
