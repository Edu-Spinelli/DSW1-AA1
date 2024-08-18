package br.ufscar.dc.dsw.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.repository.IUsuarioRepository;

import java.util.Collections;

@Service
public class CustomUsuarioDetailsService implements UserDetailsService {

    private final IUsuarioRepository usuarioRepository;

    public CustomUsuarioDetailsService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        
        GrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRole());
        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getSenha(),
                Collections.singletonList(authority)  // Converte a String role em uma lista de GrantedAuthority
        );
    }
}