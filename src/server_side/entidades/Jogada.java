package server_side.entidades;

public enum Jogada {

    PEDRA, PAPEL, TESOURA;

    public static Jogada determinaJogada(int num){
        switch (num){
            case 0:
                return PEDRA;
            case 1:
                return PAPEL;
            case 2:
                return TESOURA;
            default:
                System.err.println("ERRO");
                return null;
        }
    }
}
