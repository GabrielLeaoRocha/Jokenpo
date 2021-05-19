package server_side.entidades;

import java.net.Socket;

public class Sala {

    static Socket clientSocket01;
    static Socket clientSocket02;

    public Sala(){
    }

    public Sala(Socket clientSocket01, Socket clientSocket02){
        this.clientSocket01 = clientSocket01;
        this.clientSocket02 = clientSocket02;
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
}
