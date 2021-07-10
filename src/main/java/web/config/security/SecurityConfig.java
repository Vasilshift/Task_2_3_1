package web.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //      .antMatchers("/").permitAll()
                //      .antMatchers("/login").anonymous()
                .antMatchers("/users/**").hasRole("USER")
                .antMatchers("/admin/**").authenticated()
                .anyRequest().authenticated()
                .and().formLogin()
                //.and().logout().logoutSuccessUrl("/")
        ;
    }

//    @Bean
//    public JdbcUserDetailsManager users (DataSource dataSource){
//        dataSource = hibernateConfig.dataSource();
//        UserDetails user = User.builder()
//                .username("bbb")
//                .password("bbb")
//                .roles("ADMIN","USER")
//                .build();
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        jdbcUserDetailsManager.createUser(user);
//        return jdbcUserDetailsManager;
//    }
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("ADMIN").password("1").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("USER").password("1").roles("USER");
//        auth.inMemoryAuthentication().withUser("VIP").password("1").roles("VIP");
//    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
