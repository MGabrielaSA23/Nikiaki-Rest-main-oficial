package br.ufsm.csi.pi.niki.model;

import javax.persistence.*;

@Entity
@Table(name = "favorito")
public class Favorito {


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "receita_id")
    private int receita;
    @Column (name = "usuario_id")
    private int usuario;
    public Favorito(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceita() {
        return receita;
    }

    public void setReceita(int receita) {
        this.receita = receita;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }
    public Favorito(int receita, int usuario) {
        this.receita = receita;
        this.usuario = usuario;
    }
}
