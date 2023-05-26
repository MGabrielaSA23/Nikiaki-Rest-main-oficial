package br.ufsm.csi.pi.niki.security;

import br.ufsm.csi.pi.niki.model.Usuario;
import br.ufsm.csi.pi.niki.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Username: "+username);

        Usuario usuario = repositorioUsuario.findByUsername(username);

        System.out.println(usuario);

        if (usuario == null) {
            throw new UsernameNotFoundException("Username não encontrado");
        }
        else {
            String authtority = "";
            if (usuario.isAdmin) {
                System.out.println("ADMIN LOGADO");
                authtority = "admin";
            } else {
                System.out.println("USUARIO LOGADO");
                authtority = "user";
            }
            System.out.println(authtority);

            return User.withUsername(usuario.getUsername())
                    .password(usuario.getSenha())
                    .authorities(authtority)
                    .build();
        }
    }
}
