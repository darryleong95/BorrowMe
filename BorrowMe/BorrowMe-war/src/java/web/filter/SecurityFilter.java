package web.filter;

import entity.CustomerEntity;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})

public class SecurityFilter implements Filter {

    FilterConfig filterConfig;

    private static final String CONTEXT_ROOT = "/BorrowMe-war";

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession(true);
        String requestServletPath = httpServletRequest.getServletPath();

        if (httpSession.getAttribute("isLogin") == null) {
            httpSession.setAttribute("isLogin", false);
        }

        Boolean isLogin = (Boolean) httpSession.getAttribute("isLogin");

        if (!excludeLoginCheck(requestServletPath)) {
            if (isLogin == true) {
                CustomerEntity currentCustomer = (CustomerEntity) httpSession.getAttribute("currentCustomerEntity");
                chain.doFilter(request, response);
            } else {
                //chain.doFilter(request, response);
                httpServletResponse.sendRedirect(CONTEXT_ROOT + "/Error.xhtml");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }

    private Boolean excludeLoginCheck(String path) {
        if (path.equals("/index.xhtml")
                || path.equals("/Error.xhtml")
                || path.equals("/Login.xhtml")
                || path.equals("/ViewAllListings.xhtml")
                || path.equals("/ViewListing.xhtml")
                || path.equals("/ViewProfile.xhtml")
                || path.startsWith("/images")
                || path.startsWith("/javax.faces.resource")
                || path.startsWith("/Resources")) {
            return true;
        } else {
            return false;
        }
    }

}
