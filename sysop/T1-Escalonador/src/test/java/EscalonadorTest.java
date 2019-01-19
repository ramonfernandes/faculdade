import escalonador.Escalonador;
import escalonador.Processo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EscalonadorTest {

    private Escalonador escalonador;

    @Before
    public void setup(){
        escalonador = new Escalonador();
    }

    @Test
    public void testeEnunciado(){
       Assert.assertEquals("---C1C222C222C444C222C444C22C444C444C444C11C333C111C333C111C333C1C333C333C55C---C5C555C55", escalonador.escalonadorRun());
    }

}
