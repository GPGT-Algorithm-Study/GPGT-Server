package com.randps.randomdefence.component.query;

public interface Query {
    public Boolean setDomain(String domain);
    public Boolean setParam(String key, Object value);
    public String getQuery();
}
