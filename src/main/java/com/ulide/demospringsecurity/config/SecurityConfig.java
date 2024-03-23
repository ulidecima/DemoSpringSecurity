package com.ulide.demospringsecurity.config;

import com.ulide.demospringsecurity.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author ulide
 */
@Configuration          // Indica que esta clase proporciona configuraciones de beans para el contenedor de Spring
@EnableWebSecurity      // Habilita la seguridad web en la app
@EnableMethodSecurity   // Permite la seguridad a nivel de método, es decir
                        // que se pueden aplicar anotaciones de seguridad en métodos particulares
public class SecurityConfig {
    /* Este metodo es importante para la configuracion de la cadena de filtros
    * de seguridad de Spring. Aqui se configuran diferentes aspectos de securidad
    * como CSRF, basic auth y gestion de sesiones */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // Deshabilita la proteccion Cross Site Request Forgery para las solicitudes HTTP
                .httpBasic(Customizer.withDefaults())
                /* Se establece una politica de sesion STATELESS, que hace que la app no mantenga las sesiones de los usuarios
                * La autenticacion se realiza en cada solicitud sin mantener un estado de sesion en el servidor */
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    // Crea y devuelve un bean utilizando la configuración de autenticación
    // proporcionada por 'AuthenticationConfiguration'. Es necesario para
    // la autenticacion en Spring Security
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Crea y devuelve un bean 'AuthenticationProvider utilizando 'DaoAuthenticationProvider'
    // que es un proveedor de autenticacion que utiliza 'UserDetailsService' y un PasswordEncoder
    // para autenticar usuarios
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);

        return provider;
    }

    // Crea y devuelve un bean 'PasswordEncoder', utilizando 'BCryptPasswordEncoder',
    // que es un codificador de contraseñas que utiliza bcrypt para hashear las
    // contraseñas de los usuarios antes de almacenarlas en la BD
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
