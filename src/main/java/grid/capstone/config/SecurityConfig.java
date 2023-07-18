package grid.capstone.config;

import grid.capstone.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author Javaughn Stephenson
 * @since 18/07/2023
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final String ROLE_DOCTOR = "DOCTOR";
    public static final String ROLE_PATIENT = "PATIENT";


    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("api/v1/auth/login","api/v1/doctors", "api/v1/patients")

                )
                .cors(Customizer.withDefaults())

                .authorizeHttpRequests(request -> request
                        //Public Api's
                        .requestMatchers("api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"api/v1/doctors").permitAll()
                        .requestMatchers(HttpMethod.POST,"api/v1/patients").permitAll()

                        //Private Api's
                        .requestMatchers("api/v1/patients", "api/v1/patients/**").hasAnyRole(ROLE_DOCTOR, ROLE_PATIENT)

                        .requestMatchers(HttpMethod.POST,"api/v1/expenses/**").hasRole(ROLE_DOCTOR)
                        .requestMatchers(HttpMethod.PUT,"api/v1/expenses/**").hasRole(ROLE_DOCTOR)
                        .requestMatchers("api/v1/expenses","api/v1/expenses/**").hasRole(ROLE_PATIENT)

                        .requestMatchers(HttpMethod.POST,"/api/v1/medical-record/**").hasRole(ROLE_DOCTOR)


                        .anyRequest().authenticated()
                )


                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
