package com.finance.pettyCash.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class AxonConfiguration {
    @Value("${axon.kafka.host}")
    private String host;
    @Value("${axon.kafka.port}")
    private int port;
    @Value("${axon.kafka.database}")
    private String dataBase;
    @Value("${axon.kafka.username}")
    private String userName;
    @Value("${axon.kafka.password}")
    private String password;

    @Bean
    public EventStorageEngine eventStorageEngine(MongoClient client) {
        client = mongo();
        return new MongoEventStorageEngine(new DefaultMongoTemplate(client, this.dataBase));
    }

    public MongoClient mongo() {
        return new MongoClient(Collections.singletonList(new ServerAddress(host, port)),
                Collections.singletonList(MongoCredential
                        .createCredential(userName, dataBase, password.toCharArray())));
    }
}
