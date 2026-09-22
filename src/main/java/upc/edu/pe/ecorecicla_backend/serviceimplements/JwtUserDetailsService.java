package upc.edu.pe.ecorecicla_backend.serviceimplements;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecorecicla_backend.entities.Usuarios;
import upc.edu.pe.ecorecicla_backend.repositories.IUsuarioRepository;

import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final IUsuarioRepository usuarioRepository;

    public JwtUserDetailsService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Usuarios usuario = usuarioRepository.findByNombre(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuario no encontrado: " + username
                        )
                );

        List<GrantedAuthority> authorities =
                List.of(
                        new SimpleGrantedAuthority(
                                usuario.getRol().getNombre()
                        )
                );
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getContrasenia())
                .authorities(authorities)
                .disabled(!Boolean.TRUE.equals(usuario.getEnabled()))
                .build();
    }
}
