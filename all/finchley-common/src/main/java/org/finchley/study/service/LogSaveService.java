package org.finchley.study.service;

import org.finchley.study.dto.LogDO;
import org.finchley.study.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogSaveService {
    
	
	@Autowired
	MongoTemplate mongoTemplate;
	
    public ResponseData save(LogDO logDO) {
    	
    	mongoTemplate.insert(logDO);
    	
    	return ResponseData.ok();
    }
}