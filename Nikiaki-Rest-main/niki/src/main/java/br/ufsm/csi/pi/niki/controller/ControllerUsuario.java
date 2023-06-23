package br.ufsm.csi.pi.niki.controller;

import br.ufsm.csi.pi.niki.model.Usuario;
import br.ufsm.csi.pi.niki.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ControllerUsuario {

    @Autowired
    private PasswordEncoder passwordEncoder;

    final
    RepositorioUsuario repositorioUsuario;

    public ControllerUsuario(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @GetMapping("api/usuario")
    public ResponseEntity<List<Usuario>> getAllUsers() {
        try {
            List<Usuario> users = new ArrayList<Usuario>(repositorioUsuario.findAll());

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("api/usuario/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable("id") int id) {
        Optional<Usuario> userData = repositorioUsuario.findById(id);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("api/usuario")
    public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) {
        Usuario _Usuario = repositorioUsuario.save(
                new Usuario(
                usuario.getUsername(),
                usuario.getNome(),
                usuario.getEmail(),
                passwordEncoder.encode(usuario.getSenha()),
                usuario.isAdmin()
              )
        );
        return new ResponseEntity<>(_Usuario, HttpStatus.CREATED);
    }

    @PutMapping("api/usuario/{id}")
    public ResponseEntity<Usuario> editUsuario(@PathVariable("id") int _id,
                                               @RequestBody Usuario usuario) {
        Optional<Usuario> userData = repositorioUsuario.findById(_id);

        if (userData.isPresent()) {
            Usuario _Usuario = userData.get();
            _Usuario.setUsername(usuario.getUsername());
            _Usuario.setNome(usuario.getNome());
            _Usuario.setEmail(usuario.getEmail());
            String encodedPassword = passwordEncoder.encode(usuario.getSenha());
            _Usuario.setSenha(encodedPassword);
            return new ResponseEntity<>(repositorioUsuario.save(_Usuario), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("api/usuario/{id}")
    public ResponseEntity<Usuario> deleteUserById(@PathVariable("id") int id) {
        try {
            repositorioUsuario.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

