package br.ufsm.csi.pi.niki.repository;

import br.ufsm.csi.pi.niki.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioCategoria extends JpaRepository<Categoria, Integer> {
    @Query(value = "SELECT *  FROM  categoria WHERE id = ?1", nativeQuery = true)
    Categoria findCategoriaById(String id);
}
