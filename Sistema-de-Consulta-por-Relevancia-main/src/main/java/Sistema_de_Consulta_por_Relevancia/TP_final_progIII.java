package Sistema_de_Consulta_por_Relevancia;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TP_final_progIII {

    public static void main(String[] args) throws IOException {
        ControladorDeArquivos controller = new ControladorDeArquivos();
        
        List<Documento> documentos = new LinkedList<>(); // inserção O(1)
        controller.lerDocumentos(documentos);

        List<String> stopwords = new LinkedList<>(); // inserção O(1)
        controller.lerStopwords(stopwords);
        
        Indexador indexador = new Indexador(documentos, stopwords);
        
        List<String> Q = new LinkedList<>();  // inserção O(1)
        controller.lerConsultas(Q);
        
        ProcessadorConsultas consultas = new ProcessadorConsultas(Q, documentos, indexador);
    }
}
