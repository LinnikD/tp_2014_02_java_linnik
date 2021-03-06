package frontend;


import db.AccountService;
import db.AccountServiceMessages;
import exceptions.AccountServiceException;
import exceptions.EmptyDataException;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by uzzz on 19.02.14.
 */

public class Frontend extends HttpServlet {
    private AccountService ac;

    public Frontend(AccountService ac) {
        this.ac = ac;
    }

    private void sendResponse(HttpServletResponse resp, String resultPage, Map<String, Object> variables) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(PageGenerator.getPage(resultPage, variables));
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        HttpSession userSession = request.getSession();
        Long userId = (Long) userSession.getAttribute("userId");
        switch (request.getPathInfo()) {
            case Pages.MAIN_PAGE:
                if (userId != null) {
                    pageVariables.put("userId", userId);
                    sendResponse(response, Templates.USER_TPL, pageVariables);
                } else {
                    sendResponse(response, Templates.MAIN_TPL, pageVariables);
                }
                break;

            case Pages.AUTH_PAGE:
                sendResponse(response, Templates.AUTH_TPL, pageVariables);
                break;

            case Pages.REG_PAGE:
                sendResponse(response, Templates.REGISTER_TPL, pageVariables);
                break;

            case Pages.TIMER_PAGE:
                if (userId != null) {
                    pageVariables.put("time", new Date().toString());
                    pageVariables.put("userId", userId);
                    pageVariables.put("refreshPeriod", "1000");
                    sendResponse(response, Templates.TIMER_TPL, pageVariables);
                } else {
                    response.sendRedirect(Pages.MAIN_PAGE);
                }
                break;

            case Pages.QUIT_PAGE:
                ac.logout();
                userSession.invalidate();
                response.sendRedirect(Pages.MAIN_PAGE);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }

    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        switch (request.getPathInfo()) {
            case Pages.AUTH_PAGE:
                doAuthenticate(request, response);
                break;
            case Pages.REG_PAGE:
                doRegister(request, response);
                break;
            default:
                response.sendRedirect(Pages.MAIN_PAGE);
                break;
        }
    }

    private void doAuthenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            long userId = ac.authenticate(username, password);
            request.getSession().setAttribute("userId", userId);
            response.sendRedirect(Pages.TIMER_PAGE);
        } catch (EmptyDataException | AccountServiceException e) {
            messageToPage(response, e.getMessage(), Templates.AUTH_TPL, Templates.MessageType.ERROR);
        }
    }

    private void doRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            ac.register(username, password);
            messageToPage(response, AccountServiceMessages.USER_ADDED, Templates.REGISTER_TPL, Templates.MessageType.INFO);
        } catch (EmptyDataException | AccountServiceException e) {
            messageToPage(response, e.getMessage(), Templates.REGISTER_TPL, Templates.MessageType.ERROR);
        }
    }

    private void messageToPage(HttpServletResponse response, String message, String pageTemplate, Templates.MessageType type) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        switch (type) {
            case ERROR:
                pageVariables.put("errorMsg", message);
                break;
            case INFO:
                pageVariables.put("infoMsg", message);
                break;
        }
        sendResponse(response, pageTemplate, pageVariables);
    }
}