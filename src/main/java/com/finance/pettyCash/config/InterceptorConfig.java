package com.finance.pettyCash.config;

import com.finance.pettyCash.commandModel.interceptors.PettyCashInterceptor;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterceptorConfig {
    @Autowired
    public void roleInterceptor(CommandBus commandBus, PettyCashInterceptor pettyCashInterceptor) {
        commandBus.registerHandlerInterceptor(pettyCashInterceptor);
    }
}
