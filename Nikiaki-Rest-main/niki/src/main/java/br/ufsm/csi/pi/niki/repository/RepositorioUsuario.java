package br.ufsm.csi.pi.niki.repository;

import br.ufsm.csi.pi.niki.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface RepositorioUsuario extends JpaRepository<Usuario, Integer> {

    @Query(value = "SELECT *  FROM  usuario WHERE username = ?1", nativeQuery = true)
    Usuario findByUsername(String username);


    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}

