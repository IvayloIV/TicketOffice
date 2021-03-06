package bg.tuvarna.ticketoffice.config;

import bg.tuvarna.ticketoffice.domain.enums.Role;
import bg.tuvarna.ticketoffice.jwtauth.JwtAuthenticationEntryPoint;
import bg.tuvarna.ticketoffice.jwtauth.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             JwtRequestFilter jwtRequestFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
            .authorizeRequests()
                .antMatchers("/user/login", "/swagger-ui/**", "/v3/api-docs/**", "/docs").permitAll()
                .antMatchers("/user/register").hasAuthority(Role.ADMIN.name())
                .antMatchers("/user/profile", "/user/details/*", "/event/list", "/notification/count", "/notification/list").hasAnyAuthority(Role.ORGANISER.name(), Role.DISTRIBUTOR.name())
                .antMatchers("/event/create", "/event/edit", "/event/details/*", "/user/rate", "/distributor/list").hasAnyAuthority(Role.ORGANISER.name())
                .antMatchers("/ticket/create", "/event/distributor").hasAnyAuthority(Role.DISTRIBUTOR.name())
                .anyRequest().authenticated()
            .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
