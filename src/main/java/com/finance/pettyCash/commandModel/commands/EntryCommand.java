package com.finance.pettyCash.commandModel.commands;

import com.finance.pettyCash.base.command.BaseCommand;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class EntryCommand extends BaseCommand {

    @TargetAggregateIdentifier
    private String id;

    public EntryCommand(String id, Object receivedData) {
        super(receivedData);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
