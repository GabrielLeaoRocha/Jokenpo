package server_side.entidades;

public class Jogo {

    private Jogador jogador1;
    private Jogador jogador2;
    private Jogador jogadorVencedor;
    private int placarJogador1;
    private int placarJogador2;
    private int rodadas;
    private boolean fimDeJogo;

    //contrutor
    public Jogo(Jogador jogador1, Jogador jogador2){
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.jogadorVencedor = null;
        this.rodadas = 0;
        this.fimDeJogo = false;
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

    public void setJogadorVencedor(Jogador jogadorVencedor) {
        this.jogadorVencedor = jogadorVencedor;
    }

    public int getPlacarJogador1() {
        return placarJogador1;
    }

    public void setPlacarJogador1(int placarJogador1) {
        this.placarJogador1 = placarJogador1;
    }

    public int getPlacarJogador2() {
        return placarJogador2;
    }

    public void setPlacarJogador2(int placarJogador2) {
        this.placarJogador2 = placarJogador2;
    }

    public int getRodadas() {
        return rodadas;
    }

    public void setRodadas(int rodadas) {
        this.rodadas = rodadas;
    }

    public boolean isFimDeJogo() {
        return fimDeJogo;
    }

    public void setFimDeJogo(boolean fimDeJogo) {
        this.fimDeJogo = fimDeJogo;
    }

    //metodos
    public void addPonto(Rodada rodada){
        if(rodada.getJogadorVencedor().equals(jogador1)){
            this.placarJogador1 ++;
        }
        else if(rodada.getJogadorVencedor().equals(jogador2)){
            this.placarJogador2++;
        }
    }

    public void daterminaFimDeJogo(){
        if(rodadas > 3){
            this.fimDeJogo = true;
            if(this.placarJogador1 > this.placarJogador2){
                this.jogadorVencedor = this.jogador1;
            }
            else{
                this.jogadorVencedor = this.jogador2;
            }
        }
        if(this.placarJogador1 == 2 && this.placarJogador2 == 0){
            this.fimDeJogo = true;
            this.jogadorVencedor = this.jogador1;
        }
        if(this.placarJogador2 == 2 && this.placarJogador1 == 0){
            this.fimDeJogo = true;
            this.jogadorVencedor = this.jogador2;
        }
    }

    public String toString(){
        return "JOGADOR VENCEDOR:\n" +
               this.jogadorVencedor.getNome();
    }
}
