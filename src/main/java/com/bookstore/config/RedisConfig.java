package com.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import com.bookstore.models.User;


/**
 * @author Marieta
 *
 */
@Configuration
@EnableRedisHttpSession
public class RedisConfig {

	private static final int PORT = 6379;
	private static final String HOST = "127.0.0.1";

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(new RedisStandaloneConfiguration(HOST, PORT));
	}

	@Bean
	public RedisTemplate<String, User> redisTemplate() {
		RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}
	
	  @Bean
	    public ConfigureRedisAction configureRedisAction() {
	        return ConfigureRedisAction.NO_OP;
	    }
	  
	  @Bean
		public HttpSessionIdResolver httpSessionIdResolver() {
			return HeaderHttpSessionIdResolver.xAuthToken(); 
		}

	
}
