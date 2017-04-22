package com.ncjavaedu.ediary.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.ServletException;

public class BaseServiceImpl extends RemoteServiceServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        final WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        if (ctx == null) {
            throw new IllegalStateException("No Spring web application context found");
        }

        ctx.getAutowireCapableBeanFactory().autowireBeanProperties(this,
                AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
    }

}
