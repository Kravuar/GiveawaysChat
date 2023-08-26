package net.kravuar.giveaways.domain.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import net.kravuar.giveaways.domain.model.user.PrincipalWithId;
import net.kravuar.jwtauth.components.HttpProps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.function.Function;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public Function<DecodedJWT, Object> principalExtractor() {
        return decodedJWT -> new PrincipalWithId(
                decodedJWT.getClaim("userId").asString(),
                decodedJWT.getSubject()
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, HttpProps httpProps) throws Exception {
        var unauthenticatedMatcher = new OrRequestMatcher(httpProps
                .getUnauthenticatedEndpoints().stream()
                .map(AntPathRequestMatcher::new)
                .map(RequestMatcher.class::cast)
                .toList()
        );
        return httpSecurity
                .authorizeHttpRequests(registry ->
                    registry
                        .requestMatchers(unauthenticatedMatcher)
                            .permitAll()
                        .anyRequest()
                            .authenticated()
                )
                .build();
    }
}