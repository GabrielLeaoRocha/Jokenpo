package client_side.application;

import client_side.UI.InterfaceCliente;
import client_side.conexao.Connection;

import javax.swing.*;
import java.net.Socket;
import java.util.Scanner;

public class Application {

    static Socket socket;
    static Connection connection;
    static Scanner sc = new Scanner(System.in);

    public Application(){
        try{
            socket = new Socket("localhost", 1999);
            System.out.println("Pronto para conectar com servidor");
        } catch (Exception e){
            System.err.println("ERRO: " + e.getMessage());
        }
    }

    public static void application (){
        new Application();
        boolean fimJogo = false;
        int modoJogo = 0;
        int jogada = 0;

        //inicio de um front (terminar)
        JFrame frame01 = new JFrame("Jokenpo");
        JPanel panel01 = new JPanel();
        JButton envianome = new JButton("enviar nome");

        panel01.add(envianome);
        frame01.add(panel01);
        frame01.setSize(400,500);
        frame01.setVisible(true);

        //envia o nome do jogador1
        connection.send(socket, InterfaceCliente.determinaNome(sc).trim());

        //valida e determina o modo de jogo
        boolean modoJogoOK = false;
        while(!modoJogoOK) {
            modoJogo = InterfaceCliente.determinaModoJogo(sc);
            if (modoJogo > 0 || modoJogo <= 2) modoJogoOK = true;
            else System.err.println("OPCAO INVALIDA");
        }

        switch (modoJogo) {

            //modo VScomputador
            case 1:
                connection.send(socket, "1");
                while (!fimJogo) {

                    //recebe e valida jogada
                    boolean jogadaOK = false;
                    while (!jogadaOK) {
                        jogada = InterfaceCliente.rodada(sc);
                        if(jogada > 0 || jogada <= 3) jogadaOK = true;
                        else System.err.println("OPCAO INVALIDA");
                    }

                    //envia jogada
                    connection.send(socket, ("" + jogada).trim());

                    //recebe mensagem das jogadas de cada jogador e divide um dois arrays
                    String[] jogadas = connection.recive(socket).split("/");
                    InterfaceCliente.jogadas(jogadas[0].trim(), jogadas[1].trim());

                    System.out.println();
                    String vencedor = connection.recive(socket);
                    System.out.println(vencedor.trim());

                    connection.send(socket, "ok");

                    if (connection.recive(socket).trim().equals("fimDeJogo")) {
                        fimJogo = true;
                    }

                }
                System.out.println("\nPLACAR FINAL");
                System.out.println(connection.recive(socket).trim());
                break;

            case 2:
                connection.send(socket, "2");

                //cliente fica parado aguardando a sala multiplayer ser criada e o feedback do servidor
                System.out.println("Buscando oponente...");
                String resp = connection.recive(socket);
                if(resp.trim().equals("multiplayer")){
                    System.out.println("Oponente achado!");

                    while (!fimJogo) {

                        //recebe e valida jogada
                        boolean jogadaOK = false;
                        while (!jogadaOK) {
                            jogada = InterfaceCliente.rodada(sc);
                            if(jogada > 0 || jogada <= 3) jogadaOK = true;
                            else System.err.println("OPCAO INVALIDA");
                        }

                        //envia jogada
                        connection.send(socket, ("" + jogada).trim());

                        //recebe mensagem das jogadas de cada jogador e divide um dois arrays
                        String[] jogadas = connection.recive(socket).split("/");
                        System.out.println(jogadas.length + " " + jogadas[0] + "/" + jogadas[1]);
                        InterfaceCliente.jogadas(jogadas[0].trim(), jogadas[1].trim());

                        System.out.println();
                        String vencedor = connection.recive(socket);
                        System.out.println(vencedor.trim());

                        connection.send(socket, "ok");

                        if (connection.recive(socket).trim().equals("fimDeJogo")) {
                            fimJogo = true;
                        }

                    }
                    System.out.println("\nPLACAR FINAL");
                    System.out.println(connection.recive(socket).trim());
                    break;
                }
                break;

            default:
                System.err.println("Opcao não existente");
        }
    }
}
