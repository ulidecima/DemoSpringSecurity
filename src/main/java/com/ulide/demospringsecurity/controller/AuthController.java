package com.ulide.demospringsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author ulide
 */
@RestController
@RequestMapping("/auth")
@PreAuthorize("denyAll()")
public class AuthController {
    @GetMapping("/get")
    @PreAuthorize("hasAuthority('READ')")   // Se usa para aplicar restricciones de acceso a los metodos del controlador
    public String helloGet() {
        return "Hola Mundo - GET";
    }

    @PostMapping("/post")
    @PreAuthorize("hasAuthority('CREATE')")
    public String helloPost() {
        return "Hola Mundo - POST";
    }

    @PutMapping("/put")
    @PreAuthorize("hasAuthority('UPDATE')")
    public String helloPut() {
        return "Hola Mundo - PUT";
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('DELETE')")
    public String helloDelete() {
        return "Hola Mundo - Delete";
    }

    @PatchMapping("/patch")
    @PreAuthorize("hasAuthority('REFACTOR')")
    public String helloPatch() {
        return "Hola mundo - PATCH";
    }
}
