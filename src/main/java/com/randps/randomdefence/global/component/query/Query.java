package com.randps.randomdefence.global.component.query;

public interface Query {
    public Boolean setDomain(String domain);
    public Boolean setParam(String key, Object value);
    public String getQuery();
}
