/*
 * package com.ecom.config;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfigurerAdapter; import
 * org.springframework.security.core.userdetails.User; import
 * org.springframework.security.provisioning.InMemoryUserDetailsManager; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
 * org.springframework.security.crypto.password.PasswordEncoder;
 * 
 * @Configuration
 * 
 * @EnableWebSecurity public class SecurityConfig extends
 * WebSecurityConfigurerAdapter {
 * 
 * @Override protected void configure(HttpSecurity http) throws Exception { http
 * .authorizeRequests() .antMatchers("/register", "/login").permitAll() // Allow
 * these pages for everyone .antMatchers("/cart").authenticated() // Cart only
 * for authenticated users .anyRequest().authenticated() // Require
 * authentication for all other pages .and() .formLogin() .loginPage("/login")
 * .permitAll() .and() .logout() .permitAll(); }
 * 
 * @Bean public InMemoryUserDetailsManager userDetailsService() { // Define
 * users in-memory InMemoryUserDetailsManager manager = new
 * InMemoryUserDetailsManager();
 * manager.createUser(User.withUsername("user").password(passwordEncoder().
 * encode("password")).roles("USER").build());
 * manager.createUser(User.withUsername("admin").password(passwordEncoder().
 * encode("admin")).roles("ADMIN").build()); return manager; }
 * 
 * @Bean public PasswordEncoder passwordEncoder() { return new
 * BCryptPasswordEncoder(); } }
 */