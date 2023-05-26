package br.ufsm.csi.pi.niki.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("...auth manager configuration...");
        auth.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        return new AuthenticationFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/usuario").permitAll()
                .antMatchers(HttpMethod.POST, "/cadastrar-usuario").permitAll()
                .antMatchers(HttpMethod.GET, "/lista-receita").permitAll()
                .antMatchers(HttpMethod.GET, "/listagem-receitas").permitAll()

                .antMatchers(HttpMethod.GET, "/lista-usuario").permitAll()
                .antMatchers(HttpMethod.POST, "/lista-usuario").permitAll()
                .antMatchers(HttpMethod.PUT, "/lista-usuario/{id}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/lista-usuario/{id}").permitAll()

                .antMatchers(HttpMethod.POST, "/registrar-receita").hasAuthority("admin")
                .antMatchers(HttpMethod.POST, "/editar-receita").hasAuthority("admin")
                .antMatchers(HttpMethod.PUT, "/editar-receita/{id}").hasAuthority("admin")
                .antMatchers(HttpMethod.DELETE, "/editar-receita/{id}").hasAuthority("admin")
        ;

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(this.authenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }


    @Bean
    public CorsFilter corsFilter() {
        System.out.println("** CORS config **");
        final var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}


