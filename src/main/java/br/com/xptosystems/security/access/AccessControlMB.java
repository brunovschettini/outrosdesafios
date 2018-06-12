package br.com.xptosystems.security.access;

import br.com.xptosystems.dao.Dao;
import br.com.xptosystems.security.UserToken;
import br.com.xptosystems.security.Users;
import br.com.xptosystems.security.ws.UsersWS;
//import br.com.xptosystems.security.ws.UsersWS;
//import br.com.xptosystems.ws.UsersWS;
import br.com.xptosystems.securitys.dao.UsersDao;
import br.com.xptosystems.utils.Defaults;
import br.com.xptosystems.utils.Messages;
import br.com.xptosystems.utils.Sessions;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class AccessControlMB implements Serializable {

    private Users user;
    private String urlDestino;
    private String login = "admin@xptosystems.com.br";
    private String password = "xptosystems";
    private boolean rememberMe;
    private String CONTEXT = "XptoSystems/";

    public AccessControlMB() {
        new Defaults().loadJson();
        user = new Users();
        urlDestino = "";
        login = "admin@xptosystems.com.br";
        password = "xptosystems";
    }

    public synchronized String authUsers() throws NoSuchAlgorithmException, IOException {
        new Defaults().loadJson();
        if (login.isEmpty()) {
            Messages.info("Validação", "DIGITE O E-MAIL - admin@xptosystems.com.br!", true);
            return null;
        }
        user = new Users();
        user.setEmail(login.trim());
        if (password.isEmpty()) {
            Messages.info("Validação", "DIGITE UMA SENHA - xptosystems", true);
            return null;
        }
        String pass = password;
        UsersWS usersWS = new UsersWS();
        UserToken ut = new UserToken();
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(pass.getBytes(), 0, pass.length());
        pass = new BigInteger(1, m.digest()).toString(16);
        ut = usersWS.auth(login, pass);

        if (ut == null) {
            password = "xptosystems";
            user = new Users();
            Messages.warn("Validação", "USUÁRIO / SENHA INVÁLIDOS!", true);
            return null;
        }
        if (!ut.getUsers().getActive()) {
            password = "";
            Messages.warn("Validação", "Seu cadastro encontra-se inátivo!", true);
            Messages.warn("-", "!Solicite uma nova senha e reative seu cadastro.", true);
            return null;
        }
        if (Sessions.exists("linkRedirect")) {
            Dao dao = new Dao();
            Sessions.remove("linkRedirect");

        }
        Sessions.put("sessionUsers", ut.getUsers());
        Sessions.put("sessionUserToken", ut);
        Sessions.put("userName", ut.getUsers().getName());
        Sessions.put("sessionClient", "XptoSystems");
        user = ut.getUsers();
        login = "admin@xptosystems.com.br";
        password = "xptosystems";
        return "dashboard.xhtml?faces-redirect=true";
    }

    public void validSessions() throws IOException {
        if (!Sessions.exists("sessionUsers")) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            try {
                String loginString = "";
                String passwordString = "";
                String active = "";
                if (active != null && !active.equals("") && active.equals("1")) {
                    if ((!loginString.equals("")) && (!passwordString.equals(""))) {
                        Users u = new Users();
                        u.setEmail(loginString);
                        u.setPassword(passwordString);
                        UsersDao userDao = new UsersDao();
                        u = userDao.authUser(u);
                        if (u == null) {
                            user = new Users();
                            Messages.warn("Validação", "Usuário / senha inválidos!");
                            return;
                        }
                        if (!u.getActive()) {
                            Messages.warn("Validação", "Seu cadastro encontra-se inátivo!");
                            Messages.warn("-", "!Solicite uma nova senha e reative seu cadastro.");
                            return;
                        }
                        if (Sessions.exists("linkRedirect")) {
                            Dao dao = new Dao();
                            Sessions.remove("linkRedirect");

                        }
                        Sessions.put("sessionUsers", u);
                        Sessions.put("userName", u.getName());
                        user = u;
                    }
                }
                if (!Sessions.exists("sessionUsers")) {
                    NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                    nh.handleNavigation(facesContext, null, "login");
                }
            } catch (Exception e) {
                NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                nh.handleNavigation(facesContext, null, "login");
            }
        }
    }

    public synchronized String logout() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.invalidate();
        return "login.xhtml?faces-redirect=true";
    }

    public Users getUsers() {
        if (user == null || user.getId() == null) {
            if (Sessions.exists("sessionUsers")) {
                user = (Users) Sessions.getObject("sessionUsers");
            }
        }
        return user;
    }

    public void setUsers(Users user) {
        this.user = user;
    }

    public static Users getStaticSessionUsers() {
        if (!Sessions.exists("sessionUsers")) {
            return (Users) Sessions.getObject("sessionUsers");
        }
        return null;
    }

    public Users getSessionUsers() {
        if (Sessions.exists("sessionUsers")) {
            return (Users) Sessions.getObject("sessionUsers");
        }
        return null;
    }

    private String convertToPage(String currentPage) {
        currentPage = currentPage.substring(currentPage.lastIndexOf("/") + 1, currentPage.lastIndexOf("."));
        return currentPage;
    }

    public String getAllowed() throws IOException {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest hsr = (HttpServletRequest) fc.getExternalContext().getRequest();
            Users currentUser = (Users) Sessions.getObject("sessionUsers");
            if (currentUser == null) {
                redirect("error_401");
                return null;
            }
            String currentPage = convertToPage(hsr.getRequestURI());
            if (currentPage.isEmpty()) {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public void redirect(String page) throws IOException {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/" + CONTEXT + page + ".xhtml");
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            // ec.redirect(ec.getRequestContextPath() + "/" + page + ".jsf");   
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            // response.sendRedirect(ec.getRequestContextPath() + "/" + page + ".jsf");
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getNavigationHandler().handleNavigation(context, null, ec.getRequestContextPath() + "/" + page + ".xhtml");
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            RequestDispatcher dd = request.getRequestDispatcher(page + ".jsf");
            dd.forward(request, response);

        } catch (Exception e) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(page + ".xhtml");
            } catch (IOException e2) {
                return;
            }
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String convertToPageStatic(String currentPage) {
        currentPage = currentPage.substring(currentPage.lastIndexOf("/") + 1, currentPage.lastIndexOf("."));
        return currentPage;
    }
}
