package br.senai.sp.jandira.tabuada.model;

import java.util.Scanner;

public class TabuadaApp {
    public int multiplicando;
    public int multiplicadorInicial;
    public int multiplicadorFinal;
    public String[] tabuada;

    public void obterDados(){

        Scanner leitor = new Scanner(System.in);
        System.out.println("Digite o multiplicando: ");
        multiplicando = leitor.nextInt();

        System.out.println("Digite o multiplicador inicial: ");
        multiplicadorInicial = leitor.nextInt();

        System.out.println("Digite o multiplicador final: ");
        multiplicadorFinal = leitor.nextInt();

        calcularTabuada();

    }

    public void calcularTabuada(){
        int apoio = 0;
        if (multiplicadorFinal < multiplicadorInicial){
            apoio = multiplicadorFinal;
            multiplicadorFinal = multiplicadorInicial;
            multiplicadorInicial = apoio;
        }
        int tamanho  = multiplicadorFinal - multiplicadorInicial + 1;
        tabuada = new String[tamanho];

        int i = 0;
        while (i < tamanho){

            int produto = multiplicando * multiplicadorInicial;
            tabuada[i] = multiplicando + " X " + multiplicadorInicial + " = " + produto;
            multiplicadorInicial = multiplicadorInicial + 1;
            i = i + 1;
        }

        exibirTabuada();
    }

    public void  exibirTabuada(){
        System.out.println("Resultado da sua tabuada");

        int i = 0;

        while (i < tabuada.length){
            System.out.println(tabuada[i]);
            i++;
        }
    }
}
