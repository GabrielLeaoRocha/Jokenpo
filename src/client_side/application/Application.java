package client_side.application;

import client_side.UI.InterfaceCliente;
import client_side.conexao.Connection;

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

        //envia o nome do jogador1
        connection.send(socket, InterfaceCliente.determinaNome(sc).trim());

        while(!fimJogo) {

            //envia jogada
            int jogada = InterfaceCliente.rodada(sc);
            connection.send(socket, ("" + jogada).trim());

            //recebe mensagem das jogadas de cada jogador e divide um dois arrays
            String [] jogadas = connection.recive(socket).split("/");
            InterfaceCliente.jogadas(jogadas[0].trim(),jogadas[1].trim());

            System.out.println();
            String vencedor = connection.recive(socket);
            System.out.println(vencedor.trim());

            connection.send(socket, "ok");

            if(connection.recive(socket).trim().equals("fimDeJogo")){
                fimJogo = true;
            }

        }

        System.out.println("\nPLACAR FINAL");
        System.out.println(connection.recive(socket).trim());
    }
}
