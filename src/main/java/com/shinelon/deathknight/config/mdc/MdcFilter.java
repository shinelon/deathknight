package com.shinelon.deathknight.config.mdc;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

/***
 *
 * MdcFilter.java
 *
 * @author syq
 *
 */
@WebFilter(urlPatterns = "/*")
@Order(1)
@Component
public class MdcFilter implements Filter {

    private static final String REQUEST_KEY = "request";

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        insertIntoMDC(request);
        try {
            chain.doFilter(request, response);
        } finally {
            clearMDC();
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private void clearMDC() {
        MDC.remove(REQUEST_KEY);
    }

    private void insertIntoMDC(ServletRequest request) {
        MDC.put(REQUEST_KEY, UUID.randomUUID().toString().replace("-", "").toLowerCase());
    }

}
