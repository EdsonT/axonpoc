package com.finance.pettyCash.base.process;

public class BaseProcess {

    private final Object processData;
    private final Object baseException;

    public BaseProcess(Object processData, Object baseException) {
        this.processData = processData;
        this.baseException = baseException;
    }

    public Object getProcessData() {
        return processData;
    }

    public Object getBaseException() {
        return baseException;
    }

}
