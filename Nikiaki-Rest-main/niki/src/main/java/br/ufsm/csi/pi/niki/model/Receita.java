/*
Poderá Adicionar e esse são os campos:
- Nome;
- Imagem;
- Categoria (que será definida pelo programador já que será uma
tabela a parte, o administrador apenas poderá por qual o tipo da categoria e
não cadastrar uma categoria nova);
- Ingredientes;
- Modo de Preparo;
- Porções;
- Tempo de Preparo;
- Poderá Editar (poderá alterar todos os dados acima)
- Poderá Excluir;
 */

package br.ufsm.csi.pi.niki.model;

import javax.persistence.*;

@Entity
@Table(name = "receita")
public class Receita{

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;


    @Column (name = "nome")
    private String nome;

    @Column (name = "categoria")
    private String categoria;

    @Column (name = "ingredientes")
    private String ingredientes;

    @Column (name = "mododepreparo")
    private String mododepreparo;

    @Column (name = "porcoes")
    private String porcoes;

    @Column (name = "tempodepreparo")
    private String tempodepreparo;

    @Column (name = "observacao")
    private String observacao;


    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }


    public Receita(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getMododepreparo() {
        return mododepreparo;
    }

    public void setMododepreparo(String mododepreparo) {
        this.mododepreparo = mododepreparo;
    }

    public String getPorcoes() {
        return porcoes;
    }

    public void setPorcoes(String porcoes) {
        this.porcoes = porcoes;
    }

    public String getTempodepreparo() {
        return tempodepreparo;
    }

    public void setTempodepreparo(String tempodepreparo) {
        this.tempodepreparo = tempodepreparo;
    }

    public Receita(String nome, String categoria, String ingredientes,
                   String mododepreparo, String porcoes, String tempodepreparo, String observacao) {
        this.nome = nome;
        this.categoria = categoria;
        this.ingredientes = ingredientes;
        this.mododepreparo = mododepreparo;
        this.porcoes = porcoes;
        this.tempodepreparo = tempodepreparo;
        this.observacao = observacao;
    }

}
