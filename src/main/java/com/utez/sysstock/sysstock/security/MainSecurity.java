package com.utez.sysstock.sysstock.security;


import com.utez.sysstock.sysstock.security.jwt.JwtEntryPoint;
import com.utez.sysstock.sysstock.security.jwt.JwtTokenFilter;
import com.utez.sysstock.sysstock.security.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthService service;
    @Autowired
    private JwtEntryPoint entryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(service).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }


    //antMatchers() sirve para proteger los path de nuestra aplicación

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api-sysstock/auth/login","/api-sysstock/auth/").permitAll()
                .antMatchers("/api-sysstock/contact/**").permitAll()
                .antMatchers("/api-sysstock/recovery/*").permitAll()

                .antMatchers(HttpMethod.GET, "/api-sysstock/user/*","/api-sysstock/user/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api-sysstock/user/*","/api-sysstock/user/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/api-sysstock/user/*","/api-sysstock/user/**").permitAll()
                .antMatchers(HttpMethod.PATCH,"/api-sysstock/user/*","/api-sysstock/user/**").permitAll()

                .antMatchers(HttpMethod.GET, "/api-sysstock/categoria/*","/api-sysstock/categoria/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api-sysstock/categoria/*","/api-sysstock/categoria/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/api-sysstock/categoria/*","/api-sysstock/categoria/**").permitAll()
                .antMatchers(HttpMethod.PATCH,"/api-sysstock/categoria/*","/api-sysstock/categoria/**").permitAll()

                .antMatchers(HttpMethod.GET, "/api-sysstock/equipos/*","/api-sysstock/equipos/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api-sysstock/equipos/*","/api-sysstock/equipos/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/api-sysstock/equipos/*","/api-sysstock/equipos/**").permitAll()
                .antMatchers(HttpMethod.PATCH,"/api-sysstock/equipos/*","/api-sysstock/equipos/**").permitAll()

                .antMatchers(HttpMethod.GET, "/api-sysstock/role/*","/api-sysstock/role/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api-sysstock/role/*","/api-sysstock/role/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/api-sysstock/role/*","/api-sysstock/role/**").permitAll()
                .antMatchers(HttpMethod.PATCH,"/api-sysstock/role/*","/api-sysstock/role/**").permitAll()

                .antMatchers(HttpMethod.GET, "/api-sysstock/access/*","/api-sysstock/access/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api-sysstock/access/*","/api-sysstock/access/**").permitAll()


                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }




}

//Developed by: Jose Eduardo Arroyo Palafox
