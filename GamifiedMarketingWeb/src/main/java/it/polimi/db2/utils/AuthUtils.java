package it.polimi.db2.utils;

import it.polimi.db2.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthUtils {

    public static boolean checkAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }

    public static boolean checkAdminPrivilegies(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("user") == null || !((User) session.getAttribute("user")).getIsAdmin()) {
            response.sendRedirect("/pday");
            return false;
        }

        return true;
    }

    public static boolean checkUserBan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("user") == null || ((User) session.getAttribute("user")).getIsBanned()) {
            response.sendRedirect("/pday");
            return true;
        }

        return false;
    }

}
