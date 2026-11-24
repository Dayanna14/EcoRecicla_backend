package upc.edu.pe.ecorecicla_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upc.edu.pe.ecorecicla_backend.dtos.LoginRequestDTO;
import upc.edu.pe.ecorecicla_backend.dtos.LoginResponseDTO;
import upc.edu.pe.ecorecicla_backend.entities.Usuarios;
import upc.edu.pe.ecorecicla_backend.securities.JwtTokenService;
import upc.edu.pe.ecorecicla_backend.serviceinterfaces.IUsuarioService;

import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    private final IUsuarioService uS;

    public LoginController(
            AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService,
            IUsuarioService uS) {

        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.uS = uS;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token =
                jwtTokenService.generateToken(userDetails);
        // Obtener TODOS los roles del usuario
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // Datos del usuario (el username del token es su email)
        Usuarios usuario = uS.buscarPorEmail(userDetails.getUsername()).orElse(null);

        return ResponseEntity.ok(
                new LoginResponseDTO(
                        token,
                        userDetails.getUsername(),
                        roles,
                        usuario != null ? usuario.getId_usuario() : null,
                        usuario != null ? usuario.getNombre() : null,
                        usuario != null && Boolean.TRUE.equals(usuario.getDebeCambiarContrasenia())
                )
        );
    }
}
