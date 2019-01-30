package com.finance.pettyCash.base.event;

import java.util.Date;

public class Notification {

    private final Object receivedData;
    private final Object processedData;
    private final Date initDate;
    private final Date finishDate;
    private final Object error;
    private final String fromTopic;
    private final String toTopic;

    public Notification(Object receivedData, Object processedData,
                        Date initDate,
                        Object error, String fromTopic, String toTopic) {
        this.receivedData = receivedData;
        this.processedData = processedData;
        this.initDate = initDate;
        this.finishDate = new Date();
        this.error = error;
        this.fromTopic = fromTopic;
        this.toTopic = toTopic;
    }

    public Object getReceivedData() {
        return receivedData;
    }

    public Object getProcessedData() {
        return processedData;
    }

    public Date getInitDate() {
        return initDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public Object getError() {
        return error;
    }

    public String getFromTopic() {
        return fromTopic;
    }

    public String getToTopic() {
        return toTopic;
    }
}
