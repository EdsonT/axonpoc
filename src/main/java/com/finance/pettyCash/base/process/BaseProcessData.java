package com.finance.pettyCash.base.process;

import com.finance.pettyCash.base.config.GetBeanService;

public abstract class BaseProcessData {


    public abstract Object process(Object data, GetBeanService beanService, String commandType);


    public BaseProcess processData(Object data, GetBeanService beanService, String commandType) {
        try {
            return new BaseProcess(process(data, beanService, commandType), null);
        } catch (Exception e) {
            return new BaseProcess(null,
                    beanService.getErrorService().getMessageError(e));
        }
    }

}
