package client_side.UI;

import server_side.entidades.Jogador;
import server_side.entidades.Rodada;

import java.util.Scanner;

public class InterfaceCliente {

    public static int rodada(Scanner sc){
        System.out.println("\n-NOVA RODADA-");
        System.out.println("Digite uma jogada");
        System.out.println("(1)Pedra");
        System.out.println("(2)Papel");
        System.out.println("(3)Tesoura");
        System.out.print("> ");
        int jogada = sc.nextInt();
        return jogada;
    }

    public static void jogadas(String jog1, String jog2){
        System.out.println("Jogada: " + jog1);
        System.out.println("Jogada adv: " + jog2);
    }

}
