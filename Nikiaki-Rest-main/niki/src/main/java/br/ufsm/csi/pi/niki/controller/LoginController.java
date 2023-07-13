package br.ufsm.csi.pi.niki.controller;

import br.ufsm.csi.pi.niki.model.Usuario;
import br.ufsm.csi.pi.niki.repository.RepositorioUsuario;
import br.ufsm.csi.pi.niki.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    final
    RepositorioUsuario repositorioUsuario;

    public LoginController(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @PostMapping("api/login")
    public ResponseEntity<Object> auth(@RequestBody Usuario usuario) {
        System.out.println("Username: " + usuario.getUsername());
        System.out.println("Is_Admin:" + usuario.isAdmin());

        try {
            final Authentication authenticaticon = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getSenha()));
            if (authenticaticon.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authenticaticon);

                System.out.println("*** Generating Authorization Token ***");
                String token = new JWTUtil().geraToken(usuario.getUsername());

                new Usuario();
                Usuario loggedUser;
                loggedUser = this.repositorioUsuario.findByUsername(usuario.getUsername());

                usuario.setToken(token);
                usuario.setId(loggedUser.getId());
                usuario.setNome(loggedUser.getNome());
                usuario.setSenha(loggedUser.getSenha());
                usuario.setAdmin(loggedUser.isAdmin());
                usuario.setEmail(loggedUser.getEmail());
                System.out.println("Is_Admin:" + loggedUser.isAdmin());
                System.out.println("ID DO USUÁRIO" + loggedUser.getId());
                System.out.println("Token: " + token);

                return new ResponseEntity<>(usuario, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Usuário ou senha incorretos", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Usuário ou senha incorretos", HttpStatus.BAD_REQUEST);
    }
}
