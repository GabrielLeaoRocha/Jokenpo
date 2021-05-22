package client_side.UI;

import server_side.entidades.Jogador;
import server_side.entidades.Rodada;

import java.util.Scanner;

public class InterfaceCliente {

    public static int rodada(Scanner sc){
        try {
            System.out.println("-----------------");
            System.out.println("-NOVA RODADA-");
            System.out.println("Digite uma jogada");
            System.out.println("(1)Pedra");
            System.out.println("(2)Papel");
            System.out.println("(3)Tesoura");
            System.out.print("> ");
            int jogada = sc.nextInt();
            return jogada;

        } catch (Exception e){
            return 0;
        }
    }

    public static void jogadas(String jog1, String jog2){
        System.out.println("Jogada: " + jog1.trim());
        System.out.println("Jogada adv: " + jog2.trim());
    }

    public static String determinaNome(Scanner sc){
        System.out.print("Digite seu nome:" );
        String nome = sc.nextLine();
        return nome;
    }

    public static int determinaModoJogo(Scanner sc){
        try {
            System.out.println("-Selecione o modo de jogo:");
            System.out.println("(1) vs.computador");
            System.out.println("(2) jogadorVSjogador");
            int escolha = sc.nextInt();
            return escolha;

        }catch(Exception e){
            return 0;
        }
    }
}

