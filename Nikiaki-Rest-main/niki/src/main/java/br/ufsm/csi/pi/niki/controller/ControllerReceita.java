package br.ufsm.csi.pi.niki.controller;

import br.ufsm.csi.pi.niki.model.Receita;
import br.ufsm.csi.pi.niki.repository.RepositorioReceita;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController

public class ControllerReceita {

    final
    RepositorioReceita repositorioReceita;

    public ControllerReceita(RepositorioReceita repositorioReceita) {
        this.repositorioReceita = repositorioReceita;
    }

    @GetMapping("api/receita")
    public ResponseEntity<List<Receita>> getAll(){
        try{
            List<Receita> receita = new ArrayList<>(repositorioReceita.findAll());

            if(receita.isEmpty()){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(receita, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("api/receita/{id}")
    public ResponseEntity<Receita> getReceitaById (@PathVariable("id") int id){
        Optional<Receita> receitaData = repositorioReceita.findById(id);

        return receitaData.map(receita -> new ResponseEntity<>(receita, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("api/receita")
    public ResponseEntity<Receita> saveReceita(@RequestBody Receita receita) {
        Receita _receita = repositorioReceita.save(new Receita(
                receita.getNome(),
                receita.getCategoria(),
                receita.getIngredientes(),
                receita.getMododepreparo(),
                receita.getPorcoes(),
                receita.getTempodepreparo())
        );
        return new ResponseEntity<>(_receita, HttpStatus.CREATED);
    }

    @PutMapping("api/receita/{id}")
    public ResponseEntity<Receita> updateReceita(@PathVariable("id") int id,
                                                    @RequestBody Receita receita) {
        Optional<Receita> receitaData = repositorioReceita.findById(id);

        if (receitaData.isPresent()) {
            Receita _receita = receitaData.get();
            _receita.setNome(receita.getNome());
            _receita.setCategoria(receita.getCategoria());
            _receita.setIngredientes(receita.getIngredientes());
            _receita.setMododepreparo(receita.getMododepreparo());
            _receita.setPorcoes(receita.getPorcoes());
            _receita.setTempodepreparo(receita.getTempodepreparo());

            return new ResponseEntity<>(repositorioReceita.save(_receita), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("api/receita/{id}")
    public ResponseEntity<Receita> deleteReceita(@PathVariable("id") int id) {

        try {
            repositorioReceita.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
