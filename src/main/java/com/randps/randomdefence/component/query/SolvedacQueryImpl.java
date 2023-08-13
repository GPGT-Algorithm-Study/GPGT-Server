package com.randps.randomdefence.component.query;

import java.util.ArrayList;

public class SolvedacQueryImpl implements Query {
    String domain = "";
    ArrayList<Pair> params = new ArrayList<Pair>();

    @Override
    public Boolean setDomain(String domain) {
        try {
            this.domain = domain;
        } catch (InternalError e) {
            this.domain = "";
            return false;
        }
        return true;
    }

    @Override
    public Boolean setParam(String key, Object value) {
        try {
            this.params.add(new Pair(key, value));
        } catch (InternalError e) {
            return false;
        }
        return true;
    }

    @Override
    public String getQuery() {
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        int paramsLen = params.size();

        sb.append(domain);
        if (params.size() > 0) {
            sb.append("?");
        }
        for (Pair param : params) {
            sb.append(param.key + "=" + param.value);
            cnt++;
            if (cnt < paramsLen)
                sb.append("&");
        }

        return sb.toString();
    }

    public String makeSolvedQuery(String userId, String start, String end) {
        StringBuilder sb = new StringBuilder();

        sb.append("*" + start + ".." + end + " -@" + userId);

        return sb.toString();
    }
}
