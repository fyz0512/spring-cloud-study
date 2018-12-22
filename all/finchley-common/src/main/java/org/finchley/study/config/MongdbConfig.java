package org.finchley.study.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;

/**
 * Mongodb日志保存设置。
 * @author John Fang
 *
 */
@ConfigurationProperties
public class MongdbConfig{

	
	@Value("${spring.mongo.host}")
	protected String host;
	@Value("${spring.mongo.port}")
	protected int port;
	@Value("${spring.mongo.userName}")
	protected String userName;
	@Value("${spring.mongo.password}")
	protected String password;
	@Value("${spring.mongo.database}")
	protected String database;
	
	
	 @Bean(name = "mongo")
	 public MongoClientFactoryBean mongoClientFactoryBean() {
	        MongoClientOptions.Builder builder = MongoClientOptions.builder();
	        MongoClientOptions build = builder.build();
	        MongoCredential credential = MongoCredential.createCredential(
	               userName,
	               database,
	               password.toCharArray());
	        MongoClientFactoryBean mongoClientFactoryBean = new MongoClientFactoryBean();
	        mongoClientFactoryBean.setHost(host);
	        mongoClientFactoryBean.setPort(port);
	        mongoClientFactoryBean.setCredentials(new MongoCredential[]{credential});
	        mongoClientFactoryBean.setMongoClientOptions(build);
	        
	        return mongoClientFactoryBean;
	  }
	 
	 @Bean
	   public MongoTemplate mongoTemplate() throws Exception{

	       MongoTemplate mongoTemplate = new MongoTemplate(mongoClientFactoryBean().getObject(),database);
	       return mongoTemplate;
	   }


}
