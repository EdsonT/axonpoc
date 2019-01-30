package com.finance.pettyCash.commandModel.interceptors;

import com.finance.pettyCash.base.event.Notification;
import com.finance.pettyCash.commons.enums.NotificationType;
import com.finance.pettyCash.commons.tool.JsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${app.name}")
    private String appName;

    public void send(Object request, Object errors) {
        try {
            String defaultTopic = kafkaTemplate.getDefaultTopic();
            Notification event = new Notification(request, null, new Date(), errors, null, null);

            String json = JsonTool.getString(event, appName, NotificationType.NOTIFICATION.toString());
            kafkaTemplate.send(defaultTopic, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
