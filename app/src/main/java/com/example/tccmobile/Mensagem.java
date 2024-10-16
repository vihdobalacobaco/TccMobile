package com.example.tccmobile;

public class Mensagem {
    private String emissor;
    private String nome; // Este campo não está sendo utilizado no construtor original
    private String email;
    private String texto;
    private String tipo;
    private int usuarioId; // Pode ser nulo se for um usuário não cadastrado

    // Construtor atualizado para incluir todos os campos
    public Mensagem(String emissor, String nome, String email, String texto) {
        this.emissor = emissor;
        this.nome = nome; // Inicializando o campo 'nome'
        this.email = email;
        this.texto = texto; // Inicializando o campo 'texto'
        this.tipo = tipo; // Inicializando o campo 'tipo'
        this.usuarioId = 0; // Definindo um valor padrão, caso não tenha um usuário associado
    }
    public String getEmissor() {
        return emissor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

}
