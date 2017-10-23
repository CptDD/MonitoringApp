package com.gps.rest.util;

/**
 * This filter enables cross-origin requests (google it).
 * @author radu.miron
 * @since 4/29/14
 */

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {

    public CorsFilter() {
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "x-requested-with, accept, content-type, origin, authorization");
        httpServletResponse.addHeader("Access-Control-Max-Age", "1728000");

        chain.doFilter(request, response);
    }
}
