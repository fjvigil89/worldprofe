package es.enxenio.sife1701.config;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import es.enxenio.sife1701.config.jwt.JWTConfigurer;
import es.enxenio.sife1701.config.jwt.TokenProvider;
import es.enxenio.sife1701.model.usuario.Rol;
import es.enxenio.sife1701.model.usuario.UsuarioService;

/**
 * Created by crodriguez on 18/05/2016.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private TokenProvider tokenProvider;

    @Bean
    public ShaPasswordEncoder passwordEncoder() {
        return new ShaPasswordEncoder(256);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/client/**")
            .antMatchers("/app/**")
            .antMatchers("/i18n/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off

        http
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)

            .and().csrf().disable().headers().frameOptions().disable()

            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and().authorizeRequests()
            .antMatchers("/api/public/**").permitAll()
            .antMatchers("/api/admin/**").hasAuthority(Rol.ROLE_ADMIN.name())
            .antMatchers("/api/account/account").authenticated()
            .antMatchers("/api/account/**").permitAll()

            .antMatchers("/upload/**").permitAll()

            .and().apply(securityConfigurerAdapter()).and();

        // @formatter:on
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

}
