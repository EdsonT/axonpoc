package com.finance.pettyCash.commandModel.commands;

import com.finance.pettyCash.base.command.BaseCommand;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class CloseCommand extends BaseCommand {
    @TargetAggregateIdentifier
    private String id;

    public CloseCommand(Object receivedData, String id) {
        super(receivedData);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
