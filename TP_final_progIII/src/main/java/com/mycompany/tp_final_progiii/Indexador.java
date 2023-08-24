/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp_final_progiii;

/**
 *
 * @author aluno
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Indexador {
    private List<String> documentos;
    private List<String> stopwords;
    private Map<String, List<Pair<Integer, Integer>>> indiceInvertido;

    public Indexador(List<String> documentos, List<String> stopwords) {
        this.documentos = documentos;
        this.stopwords = stopwords;
        this.indiceInvertido = new HashMap<>(); // busca e inserção é O(1) no caso médio
        indexarDocumentos();
        //printarIndice();
    }

    private void indexarDocumentos() {
        for (int d = 0; d < documentos.size(); d++) {
            String documentoAnalisado = documentos.get(d); // lê a linha do docoumento analisado
            String[] linhaFormatada = documentoAnalisado.replace(",", " ").replace("/", " ")
                    .replace(".", " ").replace("-", " ").replace("_", " ")
                    .replace("[", " ").replace("]", " ").replace("(", " ")
                    .replace(")", " ").split("\\s+"); // formata a linha do documento analisado
            
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
                    indiceInvertido.put(palavra, new ArrayList<>());
                }
                
                indiceInvertido.get(palavra).add(new Pair<>(d, frequencia)); // Termo, <Documento, Frequencia>
            }
        }
    }

    public Map<String, List<Pair<Integer, Integer>>> getIndiceInvertido() {
        return indiceInvertido;
    }
    
    private void printarIndice() { // apenas para testes
        for (Map.Entry<String, List<Pair<Integer, Integer>>> indiceInvertido : this.getIndiceInvertido().entrySet()) {
            String palavra = indiceInvertido.getKey();
            List<Pair<Integer, Integer>> ocorrências = indiceInvertido.getValue();

            System.out.print("[" + palavra + "] => ");
            for (Pair<Integer, Integer> ocorrência : ocorrências) {
                int documentoAnalisado = ocorrência.getDocID();
                int frequencia = ocorrência.getFrequencia();
                System.out.print("(" + documentoAnalisado + "," + frequencia + ") ");
            }
            System.out.println("");
        }
    }
    
}