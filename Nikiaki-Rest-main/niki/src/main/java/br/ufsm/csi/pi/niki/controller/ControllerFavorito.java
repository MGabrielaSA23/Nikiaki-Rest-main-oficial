package br.ufsm.csi.pi.niki.controller;

import br.ufsm.csi.pi.niki.model.Favorito;
import br.ufsm.csi.pi.niki.model.Usuario;
import br.ufsm.csi.pi.niki.repository.RepositorioFavorito;
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
public class ControllerFavorito {
    final
    RepositorioFavorito repositorioFavorito;

    public ControllerFavorito(RepositorioFavorito repositorioFavorito) {
        this.repositorioFavorito = repositorioFavorito;
    }

    @GetMapping("api/favorito")
    public ResponseEntity<List<Favorito>> getAllUsers() {
        try {
            List<Favorito> favoritos = new ArrayList<Favorito>(repositorioFavorito.findAll());

            if (favoritos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(favoritos, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("api/favorito/{id}")
    public ResponseEntity<Favorito> getFavoritoById(@PathVariable("id") int id) {
        Optional<Favorito> favoritoData = repositorioFavorito.findById(id);

        return favoritoData.map(favorito -> new ResponseEntity<>(favorito, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("api/favorito")
    public ResponseEntity<Favorito> addFavorito(@RequestBody Favorito favorito) {
        Favorito _Favorito = repositorioFavorito.save(
                new Favorito(
                        favorito.getReceita(),
                        favorito.getUsuario()
              )
        );
        return new ResponseEntity<>(_Favorito, HttpStatus.CREATED);
    }

    @PutMapping("api/favorito/{id}")
    public ResponseEntity<Favorito> editFavorito(@PathVariable("id") int _id,
                                               @RequestBody Favorito favorito) {
        Optional<Favorito> favoritoData = repositorioFavorito.findById(_id);

        if (favoritoData.isPresent()) {
            Favorito _Favorito = favoritoData.get();
            _Favorito.setReceita(favorito.getReceita());
            _Favorito.setUsuario(favorito.getUsuario());
            return new ResponseEntity<>(repositorioFavorito.save(_Favorito), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("api/favorito/{id}")
    public ResponseEntity<Favorito> deleteFavoritoById(@PathVariable("id") int id) {
        try {
            repositorioFavorito.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

