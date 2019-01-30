package com.finance.pettyCash.base.config;

import com.finance.pettyCash.services.BusinessErrorService;
import com.finance.pettyCash.services.pettyCash.DailyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetBeanService {

    @Autowired
    private BusinessErrorService errorService;

    @Autowired
    private DailyBookService dailyBookService;

    public BusinessErrorService getErrorService() {
        return errorService;
    }

    public DailyBookService getDailyBookService() {
        return dailyBookService;
    }
}
