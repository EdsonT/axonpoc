
package com.finance.pettyCash.queryModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

public class BaseEntity {
    @Id
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    protected String limit;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String offset;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    protected String orderCriteria;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    protected String orderBy;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getOrderCriteria() {
        return orderCriteria;
    }

    public void setOrderCriteria(String orderCriteria) {
        this.orderCriteria = orderCriteria;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
