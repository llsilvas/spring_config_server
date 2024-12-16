package br.dev.leandro.spring.cloud.config.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<SimpleGrantedAuthority> authorities = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities);
    }

    private Collection<SimpleGrantedAuthority> extractAuthorities(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        Object roles = realmAccess != null ? realmAccess.get("roles") : Collections.emptyList();
        if (roles instanceof List<?>) {
            return ((List<?>) roles).stream()
                    .filter(role -> role instanceof String)
                    .map(role -> "ROLE_" + role)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
