package com.ulide.demospringsecurity;

import com.ulide.demospringsecurity.model.PermissionModel;
import com.ulide.demospringsecurity.model.RoleEnum;
import com.ulide.demospringsecurity.model.RoleModel;
import com.ulide.demospringsecurity.model.UserModel;
import com.ulide.demospringsecurity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class DemoSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringSecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            /* Creacion de PERMISSIONS */
            PermissionModel createPermission = PermissionModel.builder()
                    .name("CREATE")
                    .build();

            PermissionModel readPermission = PermissionModel.builder()
                    .name("READ")
                    .build();

            PermissionModel updatePermission = PermissionModel.builder()
                    .name("UPDATE")
                    .build();

            PermissionModel deletePermission = PermissionModel.builder()
                    .name("DELETE")
                    .build();

            PermissionModel refactorPermission = PermissionModel.builder()
                    .name("REFACTOR")
                    .build();

            /* Creacion de ROLES */
            RoleModel roleAdmin = RoleModel.builder()
                    .roleEnum(RoleEnum.ADMIN)
                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                    .build();

            RoleModel roleUser = RoleModel.builder()
                    .roleEnum(RoleEnum.USER)
                    .permissionList(Set.of(createPermission, readPermission))
                    .build();

            RoleModel roleInvited = RoleModel.builder()
                    .roleEnum(RoleEnum.INVITED)
                    .permissionList(Set.of(readPermission))
                    .build();

            RoleModel roleDeveloper = RoleModel.builder()
                    .roleEnum(RoleEnum.DEVELOPER)
                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
                    .build();

            /* Creacion de USUARIOS */
            UserModel user1 = UserModel.builder()
                    .username("dev")
                    .password("$2a$10$KSQv.lrzeSLWvTWRNIC1xeuqPhOznsEi.FjSr1aO6kjntrEiu7AOu")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleDeveloper))
                    .build();

            UserModel user2 = UserModel.builder()
                    .username("admin")
                    .password("$2a$10$KSQv.lrzeSLWvTWRNIC1xeuqPhOznsEi.FjSr1aO6kjntrEiu7AOu")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleAdmin))
                    .build();

            UserModel user3 = UserModel.builder()
                    .username("userDefault")
                    .password("$2a$10$KSQv.lrzeSLWvTWRNIC1xeuqPhOznsEi.FjSr1aO6kjntrEiu7AOu")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleUser))
                    .build();

            UserModel user4 = UserModel.builder()
                    .username("invited")
                    .password("$2a$10$KSQv.lrzeSLWvTWRNIC1xeuqPhOznsEi.FjSr1aO6kjntrEiu7AOu")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleInvited))
                    .build();

            /* Se guardan los usuarios en la BD*/
            userRepository.saveAll(List.of(user1, user2, user3, user4));
        };
    }
}
