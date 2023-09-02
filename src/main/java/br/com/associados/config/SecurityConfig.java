package br.com.associados.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().
                authorizeRequests().
                antMatchers(HttpMethod.GET, "/v1").permitAll().
                antMatchers(HttpMethod.POST, "/v1").permitAll().
                antMatchers(HttpMethod.DELETE, "/v1").permitAll().
                antMatchers(HttpMethod.PUT, "/v1").permitAll().
                anyRequest().permitAll();
    }
}

// public class SecurityConfig {
    
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeHttpRequests((authz) -> authz
//                 .anyRequest().authenticated()
//             )
//             .httpBasic(withDefaults());
//         return http.build();
//     }
    
// }



//https://cursos.alura.com.br/forum/topico-websecurityconfigureradapter-deprecated-222772
//Vamos instalar o 17 na minha máquina ou vamos escrever diferente?
