package com.mbarca89.DenTracker.config;

import com.mbarca89.DenTracker.filter.ClientContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ClientContextFilter> clientContextFilter() {
        FilterRegistrationBean<ClientContextFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ClientContextFilter());
        registrationBean.addUrlPatterns("/api/*"); // Especifica las URLs que deben pasar por este filtro
        registrationBean.setOrder(2); // Define el orden del filtro (1 es alto, se ejecutará primero)
        return registrationBean;
    }
}
