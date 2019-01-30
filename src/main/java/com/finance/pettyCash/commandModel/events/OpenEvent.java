package com.finance.pettyCash.commandModel.events;

import com.finance.pettyCash.base.event.Notification;

import java.util.Date;

public class OpenEvent extends Notification {
    private String id;

    public OpenEvent(Object receivedData, Object processedData, Date initDate, Object error, String fromTopic, String toTopic, String id) {
        super(receivedData, processedData, initDate, error, fromTopic, toTopic);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
