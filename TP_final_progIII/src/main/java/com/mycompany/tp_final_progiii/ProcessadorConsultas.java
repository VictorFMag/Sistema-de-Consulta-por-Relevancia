/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp_final_progiii;

/**
 *
 * @author Victor
 */

import java.util.*;

public class ProcessadorConsultas {
    private List<String> consulta; // Q
    private List<String> documentos; // R
    
    private Indexador indexador;
    private Map<String, List<Pair<Integer, Integer>>> indiceInvertido;

    public ProcessadorConsultas(List<String> consulta, List<String> documentos, Indexador indexador) {
        this.consulta = consulta;
        this.documentos = documentos;
        this.indexador = indexador;
        this.indiceInvertido = indexador.getIndiceInvertido();
        processarConsultas();
    }

    private void processarConsultas() {
        double[] resultado = new double[documentos.size()];

        for (String termo : consulta) {
            if (!indiceInvertido.containsKey(termo)) {
                continue;
            }

            List<Pair<Integer, Integer>> docs = indiceInvertido.get(termo);
            double idf = Math.log((double) documentos.size() / docs.size());

        // a partir daqui tá dando erro
            for (Pair<Integer, Integer> doc : docs) {
                int docId = doc.getDocumento();
                int tf = doc.getFrequencia();
                double w_td = tf * idf;
                resultado[docId] += w_td;
            }
        }

        for (int d = 0; d < resultado.length; d++) {
            resultado[d] /= indexador.calculaWd(resultado[d]);
        }
        // até aqui tá dando erro

        ordenarEImprimir(resultado);
    }

    private void ordenarEImprimir(double[] resultado) {
        Map<Integer, Double> scoreDocumento = new HashMap<>();
        
        for (int i = 0; i < resultado.length; i++) {
            scoreDocumento.put(i, resultado[i]);
        }

        scoreDocumento.entrySet().stream().sorted(Comparator.comparingDouble(Map.Entry::getValue)).forEach(entry -> {
            int docId = entry.getKey();
            double score = entry.getValue();
            
            System.out.println("Documento: " + docId + "  Escore: " + score);
        });
        
        /* Como é feita a ordenagem por escore:
        
            entrySet() -> retorna um conjunto de entradas do mapa. Cada entrada consiste em uma chave (docId e score).

            stream() -> transforma o conjunto de entradas em uma sequência de elementos.

            sorted(Comparator.comparingDouble(Map.Entry::getValue)) -> classifica os elementos do stream com base no valor do escore (getValue() do objeto Map.Entry). Isso coloca os documentos em ordem crescente de escores.
        */
    }
}
