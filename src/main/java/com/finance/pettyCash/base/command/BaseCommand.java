package com.finance.pettyCash.base.command;

import java.util.Date;

public class BaseCommand {
    private final Object receivedData;
    private final Date initDate;

    public BaseCommand(Object receivedData) {
        this.receivedData = receivedData;
        this.initDate = new Date();
    }

    public Object getReceivedData() {
        return receivedData;
    }

    public Date getInitDate() {
        return initDate;
    }
}
