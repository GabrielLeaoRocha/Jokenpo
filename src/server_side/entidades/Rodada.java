package server_side.entidades;

import java.util.ArrayList;

public class Rodada {

    private Jogador jogador1;
    private Jogador jogador2;
    private Jogador jogadorVencedor;

    //construtor
    public Rodada(Jogador jogador1, Jogador jogador2) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.jogadorVencedor = null;
    }

    //get e set
    public Jogador getJogador1() {
        return jogador1;
    }

    public void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }

    public Jogador getJogadorVencedor() {
        return jogadorVencedor;
    }

    //motodos
    public void determinaVitoria(){
        int jogada1 = jogador1.getJogada().ordinal();
        int jogada2 = jogador2.getJogada().ordinal();

        switch (jogada1){
            case 0:
                switch (jogada2){
                    case 0: //empate
                        break;
                    case 1: //vitoria jogador2
                        this.jogadorVencedor = this.jogador2;
                        break;
                    case 2: //vitoria jogador1
                        this.jogadorVencedor = this.jogador1;
                        break;
                    default:
                        this.jogadorVencedor = null;
                }
            break;
            case 1:
                switch (jogada2){
                    case 0: //vitoria jogador1
                        this.jogadorVencedor = this.jogador1;
                        break;
                    case 1: //empate
                        break;
                    case 2: //vitoria jogador2
                        this.jogadorVencedor = this.jogador2;
                        break;
                    default:
                        this.jogadorVencedor = null;
                }
                break;
            case 2:
                switch (jogada2){
                    case 0: //vitoria jogador2
                        this.jogadorVencedor = this.jogador2;
                        break;
                    case 1: //vitoria jogador1
                        this.jogadorVencedor = this.jogador1;
                        break;
                    case 2: //empate
                        break;
                    default:
                        this.jogadorVencedor = null;
                }
                break;
        }
    }

}
