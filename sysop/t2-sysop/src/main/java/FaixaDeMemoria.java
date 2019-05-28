import java.util.ArrayList;
import java.util.List;

public class FaixaDeMemoria {

    private int mi;
    private int mf;
    private List<Bloco> blocos;

    public FaixaDeMemoria(int mi, int mf) {
        this.mi = mi;
        this.mf = mf;
        blocos = new ArrayList<>();
    }

    public int getMi() {
        return mi;
    }

    public void setMi(int mi) {
        this.mi = mi;
    }

    public int getMf() {
        return mf;
    }

    public void setMf(int mf) {
        this.mf = mf;
    }

    public List<Bloco> getBlocos() {
        return blocos;
    }

    public boolean fetchToFaixaDeMemoria(Bloco bloco) {
        if(bloco.getTamanhoBloco() > (mf - mi)){
            System.out.println("Bloco inserido maior que a faixa de memória");
            return false;
        }
        //Insere na lista se a lista estiver vazia e o bloco for menor que que a lista
        if (blocos.isEmpty() && fitsBetween(mi, mf, bloco)) {
            return blocos.add(setupBloco(mi, mi + bloco.getTamanhoBloco(), bloco));
        }
        //Insere na lista se houver um espaço vazio no começo da lista antes de um bloco
        // alocado e o bloco couber neste espaço
        if (fitsBetween(mi, blocos.get(0).getInicioBloco(), bloco)) {
            blocos.add(0, setupBloco(mi, mi + bloco.getTamanhoBloco(), bloco));
            return true;
        }
        //Busca um espaço vazio entre blocos alocados na lista
        //Insere no primeiro que encontrar
        for (int i = 0; i + 1 < blocos.size(); i++) {
            if (fitsBetween(blocos.get(i).getFinalBloco(), blocos.get(i + 1).getInicioBloco(), bloco)) {
                int inicio = blocos.get(i).getFinalBloco();
                blocos.add(i + 1, setupBloco(inicio, inicio + bloco.getTamanhoBloco(), bloco));
                return true;
            }
        }
        //Insere na lista se houver um espaço vazio no final da lista após um bloco
        // alocado e o bloco couber neste espaço
        if (fitsBetween(blocos.get(blocos.size() - 1).getFinalBloco(), mf, bloco)) {
            return blocos.add(setupBloco(blocos.get(blocos.size() - 1).getFinalBloco(),
                    blocos.get(blocos.size() - 1).getFinalBloco() + bloco.getTamanhoBloco(),
                    bloco));
        }
        return false;
    }

    //configura bloco settando seus inicio e fim com base no que recebe do orquestrador
    private Bloco setupBloco(int begin, int end, Bloco bloco) {
        System.out.println("Inserindo bloco na faixa de memória: " + begin + "-" + end);
        return bloco.setInicioBloco(begin)
                .setFinalBloco(end);
    }

    //dado dois valores inteiros e um bloco, verifica se o bloco caberia nesse intervalo
    private boolean fitsBetween(int begin, int end, Bloco bloco) {
        return end - begin >= bloco.getTamanhoBloco();
    }

    //imprime a lista
    public String printList(List<Bloco> list) {
        String result = "";
        if (!list.isEmpty())
            if (list.get(0).getInicioBloco() == mi) {
                result += printBloco(list.get(0));
            } else {
                result += mi + " - " + list.get(0).getInicioBloco()
                        + " livre "
                        + " (tamanho "
                        + (list.get(0).getInicioBloco() - mi) + ")\n";
            }
        else
            result = mi + " - " + mf
                    + " livre "
                    + " (tamanho " + (mf - mi) + ")\n";
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getInicioBloco() == list.get(i - 1).getFinalBloco())
                result += printBloco(list.get(i));
            else
                result += list.get(i - 1).getFinalBloco() + " - "
                        + list.get(i).getInicioBloco()
                        + " livre "
                        + " (tamanho "
                        + (list.get(i).getInicioBloco() - list.get(i - 1).getFinalBloco()) + ")\n"
                        + printBloco(list.get(i));
        }
        if (!list.isEmpty())
            if (list.get(list.size() - 1).getFinalBloco() < mf)
                result += list.get(list.size() - 1).getFinalBloco() + " - " + mf
                        + " livre "
                        + " (tamanho "
                        + (mf - list.get(list.size() - 1).getFinalBloco()) + ")\n";
        return result;
    }

    //dado um bloco imprime este
    private String printBloco(Bloco bloco) {
        return bloco.getInicioBloco()
                + " - " + bloco.getFinalBloco()
                + " bloco " + bloco.getId()
                + "(tamanho " + bloco.getTamanhoBloco() + ")\n";
    }
}