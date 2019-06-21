import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeMemoriaTest {



    @Before


    @Test
    public void testFirstLine() throws IOException {
        Assert.assertEquals("1", buffer.readLine());
    }

    @Test
    public void testListaAguardando() {
        gerenciador.setupFaixaDeMemoria("100", "400");
        List<String> list = new ArrayList<>();
        List<Bloco> listResult = new ArrayList<>();
        list.add("S 100");
        list.add("S 100");
        list.add("S 100");
        list.add("S 200");
        list.add("L 2");
        list.add("L 3");
        gerenciador.run(list);
        listResult.add(new Bloco(1, 100, 200, 100));
        listResult.add(new Bloco(4, 200, 400, 200));
        List<Bloco> faixaDeMemoria = gerenciador.getFaixaDeMemoria().getBlocos();
        Assert.assertTrue(compareTwoArrays(listResult, faixaDeMemoria));
    }

    @Test
    public void testSemEspaçoParaBloco() {
        gerenciador.setupFaixaDeMemoria("100", "500");
        List<String> list = new ArrayList<>();
        List<Bloco> listResult = new ArrayList<>();
        list.add("S 200");
        list.add("S 200");
        list.add("S 100");
        gerenciador.run(list);
        listResult.add(new Bloco(1, 100, 300, 200));
        listResult.add(new Bloco(2, 300, 500, 200));
        List<Bloco> faixaDeMemoria = gerenciador.getFaixaDeMemoria().getBlocos();
        Assert.assertTrue(compareTwoArrays(listResult, faixaDeMemoria));
    }

    private boolean compareTwoArrays(List<Bloco> listOne, List<Bloco> listTwo) {
        if (!(listOne.size() == listTwo.size()))
            return false;
        for (int i = 0; i < listOne.size(); i++) {
            if ((listOne.get(i).getId() != listTwo.get(i).getId())
                    || (listOne.get(i).getInicioBloco() != listTwo.get(i).getInicioBloco())
                    || (listOne.get(i).getFinalBloco() != listTwo.get(i).getFinalBloco())
                    || (listOne.get(i).getTamanhoBloco() != listTwo.get(i).getTamanhoBloco())) {
                return false;
            }
        }
        return true;
    }
}