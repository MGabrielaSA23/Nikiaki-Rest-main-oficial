package br.ufsm.csi.pi.niki.model;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
        @Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;

@Column(name = "username", nullable = false, unique = true)
private String username;

@Column(name = "nome")
private String nome;

@Column(name = "email")
private String email;
@Column(name = "senha")
private String senha;

@Column(name = "token")
public String token;

@Column(name = "is_admin")
public boolean isAdmin;

        public Usuario() {

        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public String getSenha() {
                return senha;
        }

        public void setSenha(String senha) {
                this.senha = senha;
        }

        public String getToken() {
                return token;
        }

        public void setToken(String token) {
                this.token = token;
        }

        public boolean isAdmin() {
                return isAdmin;
        }

        public void setAdmin(boolean admin) {
                isAdmin = admin;
        }

        public Usuario(String username,  String nome, String email,String senha, boolean isAdmin) {
                this.username = username;
                this.nome = nome;
                this.email = email;
                this.senha = senha;
                this.isAdmin = isAdmin;
        }
}

