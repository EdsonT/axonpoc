package com.finance.pettyCash.queryModel;

import org.axonframework.queryhandling.QueryHandler;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public abstract class BaseProjection {

    public final static String OFFSET = "offset";
    public final static String LIMIT= "limit";
    public final static String ORDER_BY = "orderBy";
    public final static String ORDER_CRITERIA = "orderCriteria";



    protected static Set<String> RESERVED_KEYWORDS=new HashSet<String>();

    static{
        RESERVED_KEYWORDS.add(OFFSET);
        RESERVED_KEYWORDS.add(LIMIT);
        RESERVED_KEYWORDS.add(ORDER_BY);
        RESERVED_KEYWORDS.add(ORDER_CRITERIA);

    }
    @QueryHandler
    public abstract Object findOne(String code);
    @QueryHandler
    public abstract List<?> findAll(JSONObject params);

}
