package server_side.servidor;

import client_side.conexao.Connection;
import jdk.swing.interop.SwingInterOpUtils;
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

                Jogador jog1 = new Jogador(nome);
                Jogador jog2 = new Jogador("computer");

                Jogo jogo = new Jogo(jog1,jog2);

                while (!jogo.isFimDeJogo()){
                    System.out.println("\n-NOVA RODADA-");
                    Rodada novaRodada = new Rodada(jog1, jog2);
                    System.out.println("Digite uma jogada");
                    System.out.println("(1)Pedra");
                    System.out.println("(2)Papel");
                    System.out.println("(3)Tesoura");
                    System.out.print("> ");
                    int jogada = sc.nextInt();

                    jog1.fazJogada(Jogada.determinaJogada(jogada -1));
                    System.out.println("Jogada: " + jog1.getJogada().toString());
                    jog2.fazJogada(Jogada.determinaJogada(computadorJoga()));
                    System.out.println("Jogada adv: " + jog2.getJogada().toString());

                    novaRodada.determinaVitoria();

                    if(novaRodada.getJogadorVencedor() != null){
                        System.out.println("Vitoria de: " + novaRodada.getJogadorVencedor().getNome());
                        jogo.addPonto(novaRodada);
                    }
                    else{
                        System.out.println("Empate");
                    }

                    jogo.daterminaFimDeJogo();
                }

                System.out.println("\n-PLACAR FINAL-");
                System.out.println(jogo);

                String resp = "Mensagem recebida!";
                connection.send(clientSocket, resp);
            }
        }
    }
}
