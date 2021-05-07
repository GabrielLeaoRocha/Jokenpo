package client_side.cliente;

import client_side.UI.InterfaceCliente;
import client_side.conexao.Connection;

import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    static Socket socket;
    static Connection connection;
    static Scanner sc = new Scanner(System.in);

    public Cliente(){
        try{
            socket = new Socket("localhost", 1999);
            System.out.println("Pronto para conectar com servidor");
        } catch (Exception e){
            System.err.println("ERRO: " + e.getMessage());
        }
    }

    public static void main (String[]args){
        new Cliente();
        boolean fimJogo = false;

        //envia o nome do jogador1
        System.out.print("Digite seu nome:" );
        String nome = sc.nextLine();
        connection.send(socket, nome.trim());

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
