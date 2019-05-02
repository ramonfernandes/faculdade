package escalonador;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

//https://github.com/ramonfernandes/T1-Escalonador
/**
 * Autores: Matheus Ferreira e Ramon Fernandes
 *
 * Objetivo: O algoritmo deve receber um arquivo de texto descrevendo um número n de processos
 * estes processos possuem tempo de chegada, tempo de execução, prioridade e tempos de acesso a operações de E/S.
 * Dado isso, o algoritmo deve simular a execução destes processos ultilizando um algoritmo de Round-Robin
 */
public class App {

    public static void main(String[] args) {

        Escalonador escalonador = new Escalonador();
        String result = escalonador.escalonadorRun();
        System.out.println(result);
    }
}