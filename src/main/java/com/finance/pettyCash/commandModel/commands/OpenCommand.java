package com.finance.pettyCash.commandModel.commands;

import com.finance.pettyCash.base.command.BaseCommand;

public class OpenCommand extends BaseCommand {
    private String id;

    public OpenCommand(Object receivedData, String id) {
        super(receivedData);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
