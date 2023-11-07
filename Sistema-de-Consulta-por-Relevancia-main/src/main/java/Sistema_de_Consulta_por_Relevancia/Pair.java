package Sistema_de_Consulta_por_Relevancia;

public class Pair<D, F> {
    private D documento;
    private F value;

    public Pair(D documento, F value) {
        this.documento = documento;
        this.value = value;
    }

    public D getDocID() {
        return documento;
    }

    public F getValue() {
        return value;
    }
}
