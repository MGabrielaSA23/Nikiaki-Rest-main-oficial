package br.ufsm.csi.pi.niki.controller;

import br.ufsm.csi.pi.niki.model.Categoria;
import br.ufsm.csi.pi.niki.repository.RepositorioCategoria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ControllerCategoria {

    final
    RepositorioCategoria repositorioCategoria;

    public ControllerCategoria(RepositorioCategoria repositorioCategoria) {
        this.repositorioCategoria = repositorioCategoria;
    }

    @GetMapping("api/categoria")
    public ResponseEntity<List<Categoria>> getAll(){
        try{
            List<Categoria> receita = new ArrayList<>(repositorioCategoria.findAll());

            if(receita.isEmpty()){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(receita, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("api/categoria/{id}")
    public ResponseEntity<Categoria> getCategoriaById (@PathVariable("id") int id){
        Optional<Categoria> categoriaData = repositorioCategoria.findById(id);

        return categoriaData.map(categoria -> new ResponseEntity<>(categoria, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("api/categoria")
    public ResponseEntity<Categoria> saveCategoria(@RequestBody Categoria categoria) {
        Categoria _categoria = repositorioCategoria.save(
                new Categoria(
                        categoria.getNome()
        ));
        return new ResponseEntity<>(_categoria, HttpStatus.CREATED);
    }

    @PutMapping("api/categoria/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable("id") int id,
                                                 @RequestBody Categoria categoria) {
        Optional<Categoria> categoriaData = repositorioCategoria.findById(id);

        if (categoriaData.isPresent()) {
            Categoria _categoria = categoriaData.get();
            _categoria.setNome(categoria.getNome());

            return new ResponseEntity<>(repositorioCategoria.save(_categoria), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("api/categoria/{id}")
    public ResponseEntity<Categoria> deleteCategoria(@PathVariable("id") int id) {

        try {
            repositorioCategoria.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
