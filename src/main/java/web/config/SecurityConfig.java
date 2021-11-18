package web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import web.Model.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /* Старый способ добавления юзеров в спринг секьюрити

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser(userBuilder.username("Farid").password("Farid").roles("ADMIN"))
                .withUser(userBuilder.username("Timur").password("Timur").roles("USER"));
    }
    */

    //Настройка прав доступа к страницам

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/add-new-user").hasRole(Role.ADMIN.name())
                .antMatchers("/save-user/**").hasRole(Role.ADMIN.name())
                .antMatchers("/update-user/**").hasRole(Role.ADMIN.name())
                .antMatchers("/user-delete/**").hasRole(Role.ADMIN.name())
                .and()
                .formLogin()
                .permitAll();
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessUrl("/spring_mvc_war_exploded/login");

    }


    // Новый способ добавления юзеров в спринг секьюрити

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password("{noop}admin")
                        .roles(Role.ADMIN.name())
                        .build(),
                User.builder()
                        .username("user")
                        .password("{noop}user")
                        .roles(Role.USER.name())
                        .build()
        );
    }
}
