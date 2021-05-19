package server_side.entidades;

import java.net.Socket;

public class Sala {

    static int idSala;
    static Socket clientSocket01;
    static Socket clientSocket02;
    static Jogador jogador01;
    static Jogador jogador02;

    public Sala(){
    }

    public Sala(Socket clientSocket01, Socket clientSocket02){
        this.clientSocket01 = clientSocket01;
        this.clientSocket02 = clientSocket02;
    }

    public static int getIdSala() {
        return idSala;
    }

    public static void setIdSala(int idSala) {
        Sala.idSala = idSala;
    }

    public static Socket getClientSocket01() {
        return clientSocket01;
    }

    public static void setClientSocket01(Socket clientSocket01) {
        Sala.clientSocket01 = clientSocket01;
    }

    public static Socket getClientSocket02() {
        return clientSocket02;
    }

    public static void setClientSocket02(Socket clientSocket02) {
        Sala.clientSocket02 = clientSocket02;
    }

    public static Jogador getJogador01() {
        return jogador01;
    }

    public static void setJogador01(Jogador jogador01) {
        Sala.jogador01 = jogador01;
    }

    public static Jogador getJogador02() {
        return jogador02;
    }

    public static void setJogador02(Jogador jogador02) {
        Sala.jogador02 = jogador02;
    }
}
