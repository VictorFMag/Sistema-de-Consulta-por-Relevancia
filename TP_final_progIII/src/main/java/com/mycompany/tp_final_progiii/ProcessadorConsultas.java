/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp_final_progiii;

import java.lang.ModuleLayer.Controller;

/**
 *
 * @author Victor
 */

import java.util.*;

public class ProcessadorConsultas {
    private List<String> consulta; // Tem os termos que devem ser pesquisados
    private List<String> listaDocumentos; // Lista com todos os documentos da pasta

    // Pega o índice invertido
    private Indexador indexador;
    private Map<String, List<Pair<Integer, Integer>>> indiceInvertido;

    private List<Pair<Integer, Double>> ranking = new ArrayList<>();

    public ProcessadorConsultas(List<String> consulta, List<String> documentos, Indexador indexador) {
        this.consulta = consulta;
        this.listaDocumentos = documentos;
        this.indexador = indexador;
        this.indiceInvertido = indexador.getIndiceInvertido();
        calculaSIM();
        //printRanking();
    }

    private void calculaSIM() {
        double sim = 0;
        double Wd = 0;
        Pair<Integer, Double> docRankeado;
        
        for (String documento : listaDocumentos) {
            for (Map<String, List<Pair<Integer, Integer>>> palavras : indiceInvertido) {
                //se o documento tiver a palavra, wiq é 1, senão é 0
            }
            for (String termo : consulta) {
                List<Pair<Integer, Integer>> ocorrenciasDoTermo = indiceInvertido.get(termo);
                double idf = Math.log((double) listaDocumentos.size() / ocorrenciasDoTermo.size());

                for (Pair<Integer, Integer> ocorrencia : ocorrenciasDoTermo) {
                    sim += (calculaWtd(ocorrencia, idf));
                    Wd += Math.pow(calculaWtd(ocorrencia, idf), 2);
                }
                System.out.println(Math.sqrt(Wd)); // o Wd está sempre sendo o mesmo
            }
            sim /= Math.sqrt(Wd);
            docRankeado = new Pair<Integer, Double>(listaDocumentos.indexOf(documento), sim);
            insereRanking(docRankeado);
            sim = 0;
            Wd = 0;
        }
    }

    private double calculaWtd(Pair<Integer, Integer> ocorrencia, double idf) {
        int tf = ocorrencia.getFrequencia();
        double w_td = tf * idf;
        return w_td;
    }

    private void insereRanking(Pair<Integer, Double> docRankeado) {
        ranking.add(docRankeado);
        // Ordenar em ordem decrescente com base nos valores Double
        Collections.sort(ranking, new Comparator<Pair<Integer, Double>>() {
            @Override
            public int compare(Pair<Integer, Double> p1, Pair<Integer, Double> p2) {
                // Ordenar em ordem decrescente
                return Double.compare(p2.getFrequencia(), p1.getFrequencia());
            }
        });
    }

    private void printRanking() {
        for (Pair<Integer, Double> pair : ranking) {
            System.out.println("Documento "+pair.getDocID() + ", Similaridade: " + pair.getFrequencia());
        }
    }
}
