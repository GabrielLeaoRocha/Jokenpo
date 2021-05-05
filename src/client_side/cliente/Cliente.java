package client_side.cliente;

import client_side.conexao.Connection;

import java.net.Socket;

public class Cliente {

    static Socket socket;
    static Connection connection;

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
        String txt = "Troca de mensagens ok";

        connection.send(socket, txt);

        String resp = connection.recive(socket);
        System.out.println(resp.trim());

    }
}
