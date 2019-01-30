package com.finance.pettyCash.commandModel.interceptors;

import com.finance.pettyCash.commandModel.commands.CloseCommand;
import com.finance.pettyCash.commandModel.commands.CorrectCommand;
import com.finance.pettyCash.commandModel.commands.EntryCommand;
import com.finance.pettyCash.commandModel.commands.OpenCommand;
import com.finance.pettyCash.commons.exceptions.BusinessErrorException;
import com.finance.pettyCash.commons.helpers.ValidatorHelper;
import com.finance.pettyCash.services.BusinessErrorService;
import com.finance.pettyCash.services.pettyCash.DailyBookService;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PettyCashInterceptor implements MessageHandlerInterceptor<CommandMessage<?>> {
    @Autowired
    DailyBookService dailyBookService;

    @Autowired
    KafkaSender kafkaSender;
    @Autowired
    BusinessErrorService errorService;

    public Object handle(UnitOfWork<? extends CommandMessage<?>> unitOfWork, InterceptorChain interceptorChain) throws Exception {
        CommandMessage<?> message = unitOfWork.getMessage();
        kafkaSender.send(message.getPayload(), null);
        if (message.getCommandName().contains("OpenCommand")) {
            OpenCommand command = (OpenCommand) message.getPayload();
            throwException(command, dailyBookService.validateOpen(command));
        } else if (message.getCommandName().contains("EntryCommand")) {
            EntryCommand command = (EntryCommand) message.getPayload();
            throwException(command, dailyBookService.validateEntry(command));
        } else if (message.getCommandName().contains("CloseCommand")) {
            CloseCommand command = (CloseCommand) message.getPayload();
            throwException(command, dailyBookService.validateClose(command));
        } else if (message.getCommandName().contains("CorrectCommand")) {
            CorrectCommand command = (CorrectCommand) message.getPayload();
            throwException(command, dailyBookService.validateCorrect(command));
        }
        return interceptorChain.proceed();
    }

    private Object throwException(Object command, ValidatorHelper validator) throws Exception {
        if (!validator.isValid()) {
            kafkaSender.send(command, validator.getError());
            throw new BusinessErrorException(errorService.getResponseErrors(validator.getErrors()));
        }
        return null;
    }
}
