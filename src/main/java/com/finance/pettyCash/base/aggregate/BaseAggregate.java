package com.finance.pettyCash.base.aggregate;

import com.finance.pettyCash.base.process.BaseProcessData;
import org.axonframework.commandhandling.model.AggregateIdentifier;

public abstract class BaseAggregate extends BaseProcessData {

    @AggregateIdentifier
    protected String id;

}