package server_side.application;

import client_side.conexao.Connection;
import server_side.UI.InterfaceCliente;
import server_side.entidades.Jogada;
import server_side.entidades.Jogador;
import server_side.entidades.Jogo;
import server_side.entidades.Rodada;
import server_side.servidor.MainServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Application {
    static ServerSocket socket;
    static Socket clientSocket;
    static Connection connection;

    //criacao de sockets
    public Application(){
        try{
            socket = new ServerSocket(1999);
            System.out.println("socket >ok");
        } catch (Exception e){
            System.err.println("ERROR: " +  e.getMessage());
        }
    }

    //valida conexao entre cliente-servidor
    public static boolean accept(){
        try{
            clientSocket = socket.accept();
            return true;
        } catch (IOException e){
            System.err.println("ERRO: " + e.getMessage());
            return false;
        }
    }

    public static int computadorJoga(){
        Random rd = new Random();
        return rd.nextInt(2);
    }


    //metodo main
    public static void application(){

        Scanner sc = new Scanner(System.in);
        new Application();

        //looping infinito para manter server vivo
        for(;;){
            if(accept()){
                String nome = connection.recive(clientSocket).trim(); //clientSocket ja vinculado ao socket de conexao

                Jogador jog1 = new Jogador(nome);               //denominacao do jogador1
                Jogador jog2 = new Jogador("computer");   //denominacao do jogador2

                //inicio do jogo
                Jogo jogo = new Jogo(jog1,jog2);
                System.err.println("-Novo jogo iniciado-");//notificacao no servidor

                //rodadas
                while (!jogo.isFimDeJogo()){
                    Rodada rodada = new Rodada(jog1,jog2);

                    //recebe e realiza a jogada do usuario
                    int jogada = Integer.parseInt(connection.recive(clientSocket).trim());
                    jog1.fazJogada(Jogada.determinaJogada(jogada -1));
                    jog2.fazJogada(Jogada.determinaJogada(computadorJoga()));

                    //envia string com jogadas de cada jogador
                    connection.send(clientSocket,InterfaceCliente.jogadas(jog1,jog2));

                    rodada.determinaVitoria();

                    //caso tenha um vencedor na rodada
                    if(rodada.getJogadorVencedor() != null){
                        connection.send(clientSocket,InterfaceCliente.vitoriaJogador(rodada));
                        jogo.addPonto(rodada); //add ponto ao jogo
                        jogo.addRodada();
                        //notificacao no servidor
                        System.err.println("Vencedor rodada " + jogo.getRodadas() + ": " + rodada.getJogadorVencedor().getNome());
                    }
                    //caso de empate a rodada
                    else{
                        connection.send(clientSocket,"-Empate-");
                    }

                    //nao permite que os dois sends seguidos do servidor se concatenem no recive do cliente
                    connection.recive(clientSocket);

                    jogo.daterminaFimDeJogo(); //determina melhor de 3
                    if(jogo.isFimDeJogo()){
                        connection.send(clientSocket,"fimDeJogo");
                    }
                    else{
                        connection.send(clientSocket," ");
                    }
                }
                //fim do jogo
                connection.send(clientSocket, jogo.toString());
                System.err.println(jogo);
            }
        }
    }
}
