package server_side.servidor;

import client_side.conexao.Connection;
import server_side.entidades.Jogada;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

    //metodo main
    public static void main(String[]args){

        new MainServer();

        for(;;){
            if(accept()){
                String txt = connection.recive(clientSocket); //clientSocket ja vinculado ao socket de conexao
                System.out.println(txt.trim());

                String resp = "Mensagem recebida!";

                connection.send(clientSocket, resp);
            }
        }
    }
}
