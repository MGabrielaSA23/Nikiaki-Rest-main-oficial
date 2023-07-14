package br.ufsm.csi.pi.niki.repository;

import br.ufsm.csi.pi.niki.model.Favorito;
import br.ufsm.csi.pi.niki.model.Receita;
import br.ufsm.csi.pi.niki.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface RepositorioFavorito extends JpaRepository<Favorito, Integer> {
    @Modifying
    @Query(value = "INSERT INTO favorito (receita_id, usuario_id) VALUES (?, ?);", nativeQuery = true)
    void insertFavorito(int receita_id, int usuario_id);

    @Modifying
    @Query(value = "DELETE FROM favorito WHERE receita_id = ? AND usuario_id = ?;", nativeQuery = true)
    void dislikeReceita(int receita_id, int usuario_id);

    @Query(value = "SELECT CASE WHEN EXISTS(SELECT 1 FROM favorito WHERE receita_id = ? AND usuario_id = ?) THEN TRUE ELSE FALSE END;", nativeQuery = true)
    boolean checkIfReceitaLiked(int receita_id, int usuario_id);

}

