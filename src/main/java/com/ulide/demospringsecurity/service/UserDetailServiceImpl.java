package com.ulide.demospringsecurity.service;

import com.ulide.demospringsecurity.model.UserModel;
import com.ulide.demospringsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ulide
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    /*
    Al implementar UserDetailsService, esta clase es capaz de proporcionar
    una forma personalizada de cargar detalles de usuarios que son necesarios
    para la autenticacion */

    @Autowired  /* Inyecta automaticamente una una instancia UserRepository en la clase,
                que se utiliza para realizar operaciones contra la Base de Datos */
    private UserRepository userRepository;

    @Override   /* Busca un usuario por su username y luefo construye un 'UserDetails'
                que Spring Security usa para la autenticacion */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // En la siguiente linea, se busca al usuario, si no se encuentra se lanza una UserNotFoundException
        UserModel userModel = userRepository.findUserModelByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        // Lista de Authorities para almacenar roles y permisos del usuario
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        /* Primero se agregan los roles a la lista, usando el prefijo 'ROLE_' para
        * clarificar que son roles */
        userModel.getRoles()
                .forEach(roleModel -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(roleModel.getRoleEnum().name()))));

        // Luego se procesan los permisos asociados a cada rol
        // y se agregan a la lista
        userModel.getRoles().stream()
                .flatMap(roleModel -> roleModel.getPermissionList().stream())
                .forEach(permissionModel -> authorityList.add(new SimpleGrantedAuthority(permissionModel.getName())));

        /* Se crea una instancia de 'User', que es una implementacion concreta de
        * 'UserDetails' de Spring Scurity. Luego a User se le pasan los parametros necesarios
        * para su construccion, incluida la lista de roles */
        return new User(userModel.getUsername(),
                userModel.getPassword(),
                userModel.isEnabled(),
                userModel.isAccountNoExpired(),
                userModel.isCredentialNoExpired(),
                userModel.isAccountNoLocked(),
                authorityList);
    }
}
