import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Gerenciador {

    private FaixaDeMemoria faixaDeMemoria;
    private List<Bloco> listAguardando;

    /*
     Autor: Ramon Fernandes
     Data: 22 de Novembro

     O problema consiste em receber uma faixa de memória com blocos a serem adionados na mesma
    * o algoritmo deve fazer a gestão destes blocos, inserindo-os, removendo-os e guardando os que não conseguirem ser
    * alocados para sua inserção caso memória seja liberada*/

    /*Observações sobre o uso do algoritmo:
     *       - O arquivo de arquivo que será lido deve ser configurado passando o nome do mesmo
     *           no método setup
     *       - Para rodar o código execute o test TestRun na classe GerenciadorDeMemoriaTest
     */

    // Configura a faixa de memória, recebendo seu inicio e final
    public void setupFaixaDeMemoria(String mi, String mf) {
        this.faixaDeMemoria = new FaixaDeMemoria(Integer.valueOf(mi.replaceAll("\\s+", "")),
                                                Integer.valueOf(mf.replaceAll("\\s+", "")));
    }

    public List<Bloco> run(List<String> blocos) {
        Scanner sc = new Scanner(System.in);
        listAguardando = new ArrayList<>();
        if (isNull(faixaDeMemoria))
            System.out.println("faixa de memória não configurada");
        else {
            for (int i = 0; i < blocos.size(); i++) {
                String[] bloco = blocos.get(i).split(" ");
                bloco[1].replaceAll("\\s+", "");

                if (bloco[0].equals("S")) {
                    System.out.println("Inserindo em memória bloco de tamanho " + bloco[1]);
                    Bloco blocoAtual = new Bloco(Integer.valueOf(bloco[1]));
                    //Tenta inserir o bloco na faixa de memória
                    //caso não consiga loga a falha e adiciona bloco a listade aguarde
                    if (!faixaDeMemoria.fetchToFaixaDeMemoria(blocoAtual)) {
                        System.out.println("Não foi possivel alocar o bloco em memória");
                        System.out.println("Adicionando bloco a lista de aguarde");
                        System.out.println(faixaDeMemoria.printList(faixaDeMemoria.getBlocos()));
                        sc.next();
                        if (getEspacoLivreTotal() > blocoAtual.getTamanhoBloco())
                            System.out.println("DESFRAGMENTAÇÃO EXTERNA: Espaço livre fragmentado pela memória");
                        listAguardando.add(blocoAtual);
                    }
                }
                if (bloco[0].equals("L")) {
                    try {
                        System.out.println("Removendo bloco id:" + bloco[1]);
                        //recebe um id de bloco e tenta remove-lo da lista
                        faixaDeMemoria.getBlocos().remove(faixaDeMemoria.getBlocos().stream()
                                .filter(bloco1 -> bloco1.getId() == Integer.parseInt(bloco[1]))
                                .findFirst()
                                .get());
                        System.out.println("Faixa de memóra liberada");
                    } catch (NoSuchElementException e) {
                        //caso o bloco não exist é logado e continua o ciclo
                        System.out.println("Bloco inexistente");
                    }
                    //caso um espaço de memória seja liberado e a listaAguardando não esteja vazia
                    //perceorre a listaAguardando e verifica se consegue inserir um bloco na memória
                    //alocará mais de um se possível
                    if (!listAguardando.isEmpty()) {
                        System.out.println("Verificando lista de aguarde para inserir outro bloco na memória");
                        for (int index = 0; index < listAguardando.size(); index++) {
                            Bloco blocoAguardando = listAguardando.get(index);
                            if (faixaDeMemoria.fetchToFaixaDeMemoria(blocoAguardando)) {
                                System.out.println("Adicionando bloco à memória: " + blocoAguardando.toString());
                                listAguardando.remove(blocoAguardando);
                            }
                        }
                    }
                }
            }
            if (!listAguardando.isEmpty()) {
                listAguardando.forEach(bloco -> faixaDeMemoria.fetchToFaixaDeMemoria(bloco));
            }
        }
        return faixaDeMemoria.getBlocos();
    }

    private boolean isNull(Object object) {
        if (object != null)
            return false;
        return true;
    }

    private int getEspacoLivreTotal(){
        int result = 0;
        if(faixaDeMemoria.getMi() < faixaDeMemoria.getBlocos().get(0).getInicioBloco()){
            result += faixaDeMemoria.getBlocos().get(0).getInicioBloco() - faixaDeMemoria.getMi();
        }
        for(int i = 1; i < faixaDeMemoria.getBlocos().size(); i++){
            if(faixaDeMemoria.getBlocos().get(i).getInicioBloco() > faixaDeMemoria.getBlocos().get(i-1).getFinalBloco())
                result += faixaDeMemoria.getBlocos().get(i).getInicioBloco() - faixaDeMemoria.getBlocos().get(i-1).getFinalBloco();
        }
        return result;
    }

    public FaixaDeMemoria getFaixaDeMemoria() {
        return faixaDeMemoria;
    }

}
