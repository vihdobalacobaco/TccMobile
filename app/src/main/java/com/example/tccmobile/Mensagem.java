package com.example.tccmobile;

public class Mensagem {
    private String emissor;
    private String nome;
    private String email;
    private String texto;
    private String tipo;
    private int usuarioId;

    // Construtor atualizado para incluir todos os campos
    public Mensagem(String emissor, String nome, String email, String texto, String tipo) {
        this.emissor = emissor;
        this.nome = nome;
        this.email = email;
        this.texto = texto;
        this.tipo = tipo; // Inicializando corretamente o campo 'tipo'
        this.usuarioId = 0; // Definindo um valor padrão, caso não tenha um usuário associado
    }

    public String getEmissor() {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}