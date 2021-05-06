package server_side.entidades;

public class Jogador {

    private String nome;
    private Jogada jogada;

    //construtor
    public Jogador(String nome) {
        this.nome = nome;
    }

    //getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Jogada getJogada() {
        return jogada;
    }

    //metodos
    public void fazJogada(Jogada jogada){
        this.jogada = jogada;
    }
}
