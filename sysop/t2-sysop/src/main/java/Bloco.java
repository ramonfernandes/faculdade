public class Bloco {

    private static int idCounter = 1;
    private int id;
    private int inicioBloco;
    private int finalBloco;
    private int tamanhoBloco;

    public Bloco(int tamanhoBloco) {
        this.id = idCounter;
        this.tamanhoBloco = tamanhoBloco;
        idCounter++;
    }

    public Bloco(int id, int inicioBloco, int finalBloco, int tamanhoBloco) {
        this.id = id;
        this.inicioBloco = inicioBloco;
        this.finalBloco = finalBloco;
        this.tamanhoBloco = tamanhoBloco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInicioBloco() {
        return inicioBloco;
    }

    public Bloco setInicioBloco(int inicioBloco) {
        this.inicioBloco = inicioBloco;
        return this;
    }

    public int getFinalBloco() {
        return finalBloco;
    }

    public Bloco setFinalBloco(int finalBloco) {
        this.finalBloco = finalBloco;
        return this;
    }

    public int getTamanhoBloco() {
        return tamanhoBloco;
    }

    public Bloco setTamanhoBloco(int tamanhoBloco) {
        this.tamanhoBloco = tamanhoBloco;
        return this;
    }

    public static void zeraCouter() {
        idCounter = 1;
    }

    @Override
    public String toString() {
        return "Bloco id: " + this.id +
                "Tamanho do bloco: " + this.tamanhoBloco;
    }
}
