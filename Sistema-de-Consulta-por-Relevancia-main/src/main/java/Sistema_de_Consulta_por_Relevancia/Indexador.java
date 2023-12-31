package Sistema_de_Consulta_por_Relevancia;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Indexador {
    private List<Documento> documentos;
    private List<String> stopwords;
    private Map<String, List<Pair<Documento, Integer>>> indiceInvertido;

    public Indexador(List<Documento> documentos, List<String> stopwords) {
        this.documentos = documentos;
        this.stopwords = stopwords;
        this.indiceInvertido = new HashMap<>(); // busca e inserção é O(1) no caso médio
        indexarDocumentos();
        printarIndice();
    }

    private void indexarDocumentos() {
        for (Documento documento : documentos) {
            String documentoAnalisado = documento.conteudo.toLowerCase(); // lê a linha do docoumento analisado
            String[] linhaFormatada = documentoAnalisado.replaceAll("\\p{Punct}", " ").split("\\s+"); // formata a linha do documento analisado
            
            Map<String, Integer> vocabulario = new HashMap<>(); // cria o vocabulário

            for (String palavra : linhaFormatada) { // verifica se é stopword e adiciona ao vocabulário se não for
                boolean isStopword = false;
                palavra = palavra.toLowerCase();
                for (String stopword : stopwords){
                    if (palavra.toLowerCase().equals(stopword)) {
                        isStopword = true;
                    }
                }
                if (!isStopword) {
                    if (vocabulario.containsKey(palavra)) {
                        vocabulario.put(palavra, vocabulario.get(palavra) + 1);
                    } else {
                        vocabulario.put(palavra, 1);
                    }
                }
            }
            
            for (Map.Entry<String, Integer> entry : vocabulario.entrySet()) { // cria o indice invertido, transformando de vocabulário para Pair
                String palavra = entry.getKey();
                int frequencia = entry.getValue();
                
                if (!indiceInvertido.containsKey(palavra)) { // verifica se o termo analisado já tem pelo menos um índice invertido
                    indiceInvertido.put(palavra, new LinkedList<>());
                }
                
                indiceInvertido.get(palavra).add(new Pair<>(documento, frequencia)); // Termo, <Documento, Frequencia>
            }
        }
    }

    public Map<String, List<Pair<Documento, Integer>>> getIndiceInvertido() {
        return indiceInvertido;
    }
    
    private void printarIndice() { // apenas para testes
        for (Map.Entry<String, List<Pair<Documento, Integer>>> indiceInvertido : this.getIndiceInvertido().entrySet()) {
            String palavra = indiceInvertido.getKey();
            List<Pair<Documento, Integer>> ocorrências = indiceInvertido.getValue();

            System.out.print("[" + palavra + "] => ");
            for (Pair<Documento, Integer> ocorrência : ocorrências) {
                String documentoAnalisado = ocorrência.getDocID().docID.replace(".txt", "");
                int frequencia = ocorrência.getValue();
                System.out.print("(" + documentoAnalisado + "," + frequencia + ") ");
            }
            System.out.println("");
        }
    }
    
}