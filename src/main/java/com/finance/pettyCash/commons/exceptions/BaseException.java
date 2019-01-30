package com.finance.pettyCash.commons.exceptions;


import com.finance.pettyCash.commons.enums.Errors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mirko Flores
 */
public class BaseException extends RuntimeException {
    private final List<Errors> errorServer;

    public BaseException(List<Errors> errorServer) {
        super();
        this.errorServer = errorServer;
    }

    public List<Errors> getErrorServer() {
        return errorServer;
    }

    public List<String> getErrorList() {
        List<String> errorList = new ArrayList<>();
        for (Errors e : errorServer) {
            errorList.add(e.toString());
        }
        return errorList;
    }

    public static BaseException setErrors(List<Errors> errorServer) {
        return  new BaseException(errorServer);
    }
}
