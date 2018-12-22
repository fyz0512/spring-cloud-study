package org.finchley.study.query;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数容器。
 * @author John Fang
 */
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    private int offset; //开始记录
    private int limit; //结束记录
    private int page; //页数


    public Query() {}
    
    /**
     * 可以根据 offset和limit查询，也可以根据page和limit查询
     * @param params
     */
    public Query(Map<String, Object> params) {
        this.putAll(params);
        
        if (null != params.get("limit")) {
            this.limit = Integer.parseInt(params.get("limit").toString());
            if (null != params.get("page")) {
                this.page = Integer.parseInt(params.get("page").toString());
                if(limit > 0 && page > 0) {
                	this.offset= (page - 1) * limit;
                	this.put("offset", this.offset);
                	
                }
            }
        }else {
        	if (null != params.get("offset")) 
                  this.offset = Integer.parseInt(params.get("offset").toString());
        	 if (null != params.get("page"))
        		 this.page = Integer.parseInt(params.get("page").toString());
        }
        
        
    }
    
    public Query addParam(String name , Object value) {
    	
    	if("limit".equals(name)) {
    		this.limit = Integer.parseInt(value.toString());
    		this.offset = (page - 1) * limit;
    		this.put("offset", this.offset);
    	}else if("offset".equals(name)) {
    		this.offset = Integer.parseInt(value.toString());
    	}else if("page".equals(name)) {
    		this.page = Integer.parseInt(value.toString());
    		this.offset = (page - 1) * limit;
    		this.put("offset", this.offset);
    	}
    	this.put(name, value);
    	
    	return this;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.put("offset", offset);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
