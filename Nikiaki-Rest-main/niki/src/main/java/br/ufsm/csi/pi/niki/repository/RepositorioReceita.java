package br.ufsm.csi.pi.niki.repository;

import br.ufsm.csi.pi.niki.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioReceita extends JpaRepository<Receita, Integer> {

    @Query(value = "SELECT *  FROM  receita WHERE id = ?1", nativeQuery = true)
    Receita findByReceitaId(String id);
    Receita findByNome(String nome);

}
