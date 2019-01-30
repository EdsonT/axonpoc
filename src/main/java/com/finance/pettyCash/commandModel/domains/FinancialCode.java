package com.finance.pettyCash.commandModel.domains;

import java.util.Calendar;
import java.util.Date;

public class FinancialCode {
    private String code;

    public void generateCode(String code, String auto) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH);
        String aux = year + month.toString() + code + "-" + auto;
        this.code = aux.toUpperCase();
    }

    public String getCode() {
        return code;
    }
}
