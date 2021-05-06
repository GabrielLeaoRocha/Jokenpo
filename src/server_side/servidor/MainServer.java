package server_side.servidor;

import client_side.conexao.Connection;
import jdk.swing.interop.SwingInterOpUtils;
import server_side.UI.InterfaceCliente;
import server_side.entidades.Jogada;
import server_side.entidades.Jogador;
import server_side.entidades.Jogo;
import server_side.entidades.Rodada;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class MainServer {

    static ServerSocket socket;
    static Socket clientSocket;
    static Connection connection;

    //criacao de sockets
    public MainServer(){
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
    public static void main(String[]args){

        Scanner sc = new Scanner(System.in);

        new MainServer();

        for(;;){
            if(accept()){
                String txt = connection.recive(clientSocket); //clientSocket ja vinculado ao socket de conexao
                System.out.println(txt.trim());

                System.out.print("Digite seu nome: ");
                String nome = sc.nextLine();

                Jogador jog1 = new Jogador(nome);               //denominacao do jogador1
                Jogador jog2 = new Jogador("computer");   //denominacao do jogador2

                //inicio do jogo
                Jogo jogo = new Jogo(jog1,jog2);

                //rodadas
                while (!jogo.isFimDeJogo()){
                    Rodada novaRodada = new Rodada(jog1,jog2);

                    int jogada = InterfaceCliente.rodada(sc);
                    jog1.fazJogada(Jogada.determinaJogada(jogada -1));
                    jog2.fazJogada(Jogada.determinaJogada(computadorJoga()));

                    InterfaceCliente.jogadas(jog1,jog2);

                    novaRodada.determinaVitoria();
                    //caso tenha um vencedor na rodada
                    if(novaRodada.getJogadorVencedor() != null){
                        InterfaceCliente.vitoriaJogador(novaRodada); //print vencedor da rodada
                        jogo.addPonto(novaRodada); //add ponto ao jogo
                    }
                    //caso de empate a rodada
                    else{
                        System.out.println("-Empate-");
                    }

                    jogo.daterminaFimDeJogo(); //determina melhor de 3
                }
                //fim do jogo

                System.out.println("\n-PLACAR FINAL-");
                System.out.println(jogo);

                String resp = "Mensagem recebida!";
                connection.send(clientSocket, resp);
            }
        }
    }
}
