package com.bookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bookstore.service.UserSecurityService;

/**
 * @author Marieta
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private UserSecurityService userSecurityService;
	
	private PasswordEncoder passwordEncoder() {
		return Utils.passwordEncoder();
	}

	private static final String[] PUBLIC_MATCHERS = {
			"/css/**",
			"/js/**",
			"/image/**",
			"/book/**",
			"/user/**"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.csrf().disable()
         .cors().disable()
         .httpBasic().and().authorizeRequests()
         .antMatchers(PUBLIC_MATCHERS).permitAll()
         .antMatchers("/user/**").hasAnyAuthority("USER")
         .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
         .anyRequest().authenticated()
         .and().exceptionHandling().accessDeniedPage("/error.html");
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
	

	    @Bean
	    public UserAuthenticationFilter userAuthenticationFilter() throws Exception {
	        UserAuthenticationFilter userAuthenticationFilter = new UserAuthenticationFilter();
	        userAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
	        userAuthenticationFilter
	                .setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/bookstore/oauth/token", "POST"));
	        return userAuthenticationFilter;
	    }


	
	    @Bean
	    @Primary
	    @Override
	    protected AuthenticationManager authenticationManager() throws Exception {
	        return super.authenticationManager();
	    }
}
