package com.mbarca89.DenTracker.filter;

import com.mbarca89.DenTracker.context.TenantContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TenantFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;

        String tenantHeader = httpReq.getHeader("X-Tenant-ID");

        if (tenantHeader != null) {
            TenantContext.setTenantId(Long.parseLong(tenantHeader));
        }

        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
