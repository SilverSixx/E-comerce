package com.example.ecommerce.security;


import com.example.ecommerce.customer.CustomerService;
import com.example.ecommerce.security.filter.MyAuthenticationFilter;
import com.example.ecommerce.security.filter.MyAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityFilterChain extends WebSecurityConfigurerAdapter {
    private final CustomerService customerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        MyAuthenticationFilter authenticationFilter = new MyAuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/api/login");
        http
            .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers("/api/login/**").permitAll()
                    .antMatchers("/api/v*/customers/**", "/api/v*/products/**").hasAuthority( "ROLE_ADMIN")
                    .anyRequest().authenticated()
                        .and()
                .addFilter(authenticationFilter)
                .addFilterBefore(new MyAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
            .formLogin()
                .loginPage("/index.html") // set the login page URL
                .permitAll();
        http.httpBasic();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(5242880); // 5MB
        return resolver;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

