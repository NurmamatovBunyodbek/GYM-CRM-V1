package abdumalik.dev.gymcrmv1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.UUID;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public AuthenticationProvider authenticationProvider() {
        String password = UUID.randomUUID().toString();
        System.out.println("                User Password -----------------------> " + password);

        UserDetails user = User.builder()
                .username("user")
                .password("{noop}" + password)
                .roles("USER")
                .build();

        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(new InMemoryUserDetailsManager(user));
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    // address class
                    .requestMatchers(HttpMethod.GET, "/address", "/address/*").permitAll()
                    .requestMatchers(HttpMethod.POST, "/address").hasAnyRole("SUPERADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/address/*").hasAnyRole("SUPERADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/address/*").hasAnyRole("SUPERADMIN","ADMIN")
                    // login class
                    .requestMatchers(HttpMethod.GET, "/login", "/login/*").hasAnyRole("SUPERADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/login").hasAnyRole("SUPERADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/login/*").hasAnyRole("SUPERADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/login/*").hasAnyRole("SUPERADMIN","ADMIN")
                    // payment class
                    .requestMatchers(HttpMethod.GET, "/payment", "/payment/*").permitAll()
                    .requestMatchers(HttpMethod.POST, "/payment").hasAnyRole("SUPERADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/payment/*").hasAnyRole("SUPERADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/payment/*").hasAnyRole("SUPERADMIN","ADMIN")
                    // trainer class
                    .requestMatchers(HttpMethod.GET, "/trainer", "/trainer/*").permitAll()
                    .requestMatchers(HttpMethod.POST, "/trainer").hasAnyRole("SUPERADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/trainer/*").hasAnyRole("SUPERADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/trainer/*").hasAnyRole("SUPERADMIN","ADMIN")
                    // user class
                    .requestMatchers(HttpMethod.GET, "/user", "/user/*").permitAll()
                    .requestMatchers(HttpMethod.POST, "/user").hasAnyRole("SUPERADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/user/*").hasAnyRole("SUPERADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/user/*").hasAnyRole("SUPERADMIN","ADMIN")


                    .anyRequest()
                    .authenticated();
        }).formLogin(Customizer.withDefaults());

        http.httpBasic(Customizer.withDefaults());

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));
        http.cors(cors -> cors.configurationSource(request -> {
            var config = new org.springframework.web.cors.CorsConfiguration();
            config.setAllowedOrigins(List.of("https://your-frontend.com")); // frontend domain
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
            config.setAllowedHeaders(List.of("*"));
            return config;
        }));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String md5 = MD5Util.getMD5(rawPassword.toString());
                return md5.equals(encodedPassword);
            }
        };

    }

}