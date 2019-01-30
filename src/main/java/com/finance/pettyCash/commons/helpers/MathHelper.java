package com.finance.pettyCash.commons.helpers;

import java.text.DecimalFormat;

public class MathHelper {
    public static double round(double number) {
        DecimalFormat df = new DecimalFormat("#0.#####");
        return Double.parseDouble(df.format(number).replace(",", "."));
    }
}
