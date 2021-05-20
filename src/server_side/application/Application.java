package server_side.application;

import client_side.conexao.Connection;
import server_side.UI.InterfaceCliente;
import server_side.entidades.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Application extends javax.swing.JFrame {
    static ServerSocket socket;
    static Socket clientSocket;
    static Connection connection;
    static Sala sala;
    static boolean salaFormada;

    //criacao de sockets
    public Application() {
        try {
            socket = new ServerSocket(1999);
            System.out.println("socket >ok");
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    //valida conexao entre cliente-servidor
    public static boolean accept() {
        try {
            clientSocket = socket.accept();
            return true;
        } catch (IOException e) {
            System.err.println("ERRO: " + e.getMessage());
            return false;
        }
    }

    //determina int randomico entre 0 e 2
    public static int computadorJoga() {
        Random rd = new Random();
        return rd.nextInt(2);
    }


    //metodo main
    public static void application() throws IOException {

        new Application();

        //looping infinito para manter server vivo
        for (; ; ) {
            if (accept()) {
                new Thread(() -> modoJogo(clientSocket)).start();
            }
        }
    }

    //metodo de jogo VS computador
    public static void singlePlayer(Socket clientSocket, Jogador jog1){

        Jogador jog2 = new Jogador("computer");   //denominacao do jogador2

        //inicio do jogo
        Jogo jogo = new Jogo(jog1, jog2);
        System.err.println("-Novo jogo singlePlayer iniciado-");//notificacao no servidor

        //rodadas
        while (!jogo.isFimDeJogo()) {
            Rodada rodada = new Rodada(jog1, jog2);

            //recebe e realiza a jogada do usuario
            int jogada = Integer.parseInt(connection.recive(clientSocket).trim());
            jog1.fazJogada(Jogada.determinaJogada(jogada - 1));
            jog2.fazJogada(Jogada.determinaJogada(computadorJoga())); //jogada do computador

            //envia string com jogadas de cada jogador
            connection.send(clientSocket, InterfaceCliente.jogadas(jog1, jog2));

            rodada.determinaVitoria();

            //caso tenha um vencedor na rodada
            if (rodada.getJogadorVencedor() != null) {
                connection.send(clientSocket, InterfaceCliente.vitoriaJogador(rodada));
                jogo.addPonto(rodada); //add ponto ao jogo
                jogo.addRodada();
                //notificacao no servidor
                System.err.println("Vencedor rodada " + jogo.getRodadas() + ": " + rodada.getJogadorVencedor().getNome());
            }
            //caso de empate a rodada
            else {
                connection.send(clientSocket, "-Empate-");
            }

            //nao permite que os dois sends seguidos do servidor se concatenem no recive do cliente
            connection.recive(clientSocket);

            jogo.daterminaFimDeJogo(); //determina melhor de 3
            if (jogo.isFimDeJogo()) {
                connection.send(clientSocket, "fimDeJogo");
            } else {
                connection.send(clientSocket, " ");
            }
        }
        //fim do jogo
        connection.send(clientSocket, jogo.toString());
        System.err.println(jogo);

        close(clientSocket);

    }

    //metodo de jogo VS jogador
    public static void multiPlayer(Sala sala1) {

        Jogador jog1 = sala1.getJogador01();
        Jogador jog2 = sala1.getJogador02();
        Socket socket1 = sala.getClientSocket01();
        Socket socket2 = sala.getClientSocket02();

        System.err.println("-Novo jogo multiplayer iniciado-");//notificacao no servidor

        //inicio do jogo
        Jogo jogo = new Jogo(jog1, jog2);
        connection.send(socket1, "multiplayer"); //comunica clientes inicio do jogo
        connection.send(socket2, "multiplayer");

        //rodadas
        while (!jogo.isFimDeJogo()) {
            Rodada rodada = new Rodada(jog1, jog2);

            //recebe e realiza a jogada do usuario
            int jogada1 = Integer.parseInt(connection.recive(socket1).trim());
            int jogada2 = Integer.parseInt(connection.recive(socket2).trim());
            jog1.fazJogada(Jogada.determinaJogada(jogada1 - 1));
            jog1.fazJogada(Jogada.determinaJogada(jogada2 - 1));

            //envia string com jogadas de cada jogador
            connection.send(clientSocket, InterfaceCliente.jogadas(jog1, jog2));

            rodada.determinaVitoria();

            //caso tenha um vencedor na rodada
            if (rodada.getJogadorVencedor() != null) {
                connection.send(socket1, InterfaceCliente.vitoriaJogador(rodada));
                connection.send(socket2, InterfaceCliente.vitoriaJogador(rodada));
                jogo.addPonto(rodada); //add ponto ao jogo
                jogo.addRodada();
                //notificacao no servidor
                System.err.println("Vencedor rodada " + jogo.getRodadas() + ": " + rodada.getJogadorVencedor().getNome());
            }
            //caso de empate a rodada
            else {
                connection.send(socket1, "-Empate-");
                connection.send(socket2, "-Empate-");
            }

            //nao permite que os dois sends seguidos do servidor se concatenem no recive do cliente
            connection.recive(socket1);
            connection.recive(socket2);

            jogo.daterminaFimDeJogo(); //determina melhor de 3
            if (jogo.isFimDeJogo()) {
                connection.send(socket1, "fimDeJogo");
                connection.send(socket2, "fimDeJogo");
            } else {
                connection.send(socket1, " ");
                connection.send(socket2, " ");
            }
        }
        //fim do jogo
        connection.send(socket1, jogo.toString());
        connection.send(socket2, jogo.toString());
        System.err.println(jogo);

    }

    public static void formaSala (Socket clientSocket, Jogador jog){

        //caso não tenha sala criada
        if(sala == null){
            sala = new Sala();
            sala.setClientSocket01(clientSocket);
            sala.setJogador01(jog);

        //caso tenha sala criada com apenas um jogador
        } else {
                sala.setClientSocket02(clientSocket);
                sala.setJogador02(jog);
                salaFormada = true;
        }
    }

    //Determina o modo de jogo e chama o metodo de jogo correspondente
    public static void modoJogo(Socket clientSocket) {

        String nome = connection.recive(clientSocket).trim();
        Jogador jog = new Jogador(nome);
        String option = connection.recive(clientSocket);
        switch (option.trim()) {
            case "1":
                singlePlayer(clientSocket, jog);
                break;
            case "2":
                formaSala(clientSocket, jog);

                //caso a sala tenha sido completamente formada
                if(salaFormada){
                    Sala sala1 = sala;
                    multiPlayer(sala1); //chama metodo de multiplayer passando a sala criada

                    //reseta para a criacao de novas salas
                    sala = null;
                    salaFormada = false;
                }
                // OBS: se o thread do usuario não formar a sala ele não realiza nenhuma ação
                break;
            default:
                System.err.println("Opcao invalida");
        }
    }

    public static void close(Socket clientSocket) {
        try{
            clientSocket.close();
        } catch (IOException e){
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}
