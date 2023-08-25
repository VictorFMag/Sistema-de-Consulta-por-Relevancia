/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp_final_progiii;

import java.io.IOException;

/**
 *
 * @author Victor
 */
import java.util.*;

public class ProcessadorConsultas {

    private List<String> consulta; // Tem os termos que devem ser pesquisados
    private List<Documento> listaDocumentos; // Lista com todos os documentos da pasta

    // Pega o índice invertido
    private Indexador indexador;
    private Map<String, List<Pair<Documento, Integer>>> indiceInvertido;

    private List<Pair<Documento, Double>> ranking = new ArrayList<>();

    public ProcessadorConsultas(List<String> consulta, List<Documento> documentos, Indexador indexador) throws IOException {
        this.consulta = consulta;
        this.listaDocumentos = documentos;
        this.indexador = indexador;
        this.indiceInvertido = indexador.getIndiceInvertido();
        for (Documento documento : listaDocumentos) {
            Pair<Documento, Double> docRankeado = new Pair<>(documento, calculaSIM(documento));
            insereRanking(docRankeado);
        }
        printRanking();
    }

    private double calculaSIM(Documento documento) {
        double soma = 0;
        
        for (String busca : consulta) {
            int w_iq = 0;
            if (indiceInvertido.containsKey(busca)) {
                w_iq = 1;
            }

            double w_td = calculaWtd(busca, documento);
            soma += (w_td * w_iq);
        }
        double Wd = calculaWd(documento);
        double sim = soma / Wd;
        return sim;
    }

    private double calculaWtd(String palavra, Documento documento) {
        List<Pair<Documento, Integer>> indiceInvertidoPalavra = indiceInvertido.get(palavra);
        double peso = 0;
        for(Pair<Documento, Integer> p: indiceInvertidoPalavra){
            if(p.getDocID().docID.equals(documento.docID)){
               int ftd = p.getValue();
               int ft = indiceInvertidoPalavra.size();
               peso = ftd * (Math.log(listaDocumentos.size()/ft)/Math.log(2.71));
            }
        }
        return peso;
    }
    
    private double calculaWd(Documento d){
        Set<String> vocabulario = indiceInvertido.keySet();
        double soma = 0;
        for(String palavra: vocabulario){
            double peso = this.calculaWtd(palavra, d);
            soma+=peso*peso;
        }
        return Math.sqrt(soma);
    }

    private void insereRanking(Pair<Documento, Double> docRankeado) {
        ranking.add(docRankeado);
        // Ordena em ordem decrescente com base nos valores Double
        Collections.sort(ranking, new Comparator<Pair<Documento, Double>>() {
            @Override
            public int compare(Pair<Documento, Double> p1, Pair<Documento, Double> p2) {
                // Ordenar em ordem decrescente
                return Double.compare(p2.getValue(), p1.getValue());
            }
        });
    }

    private void printRanking() throws IOException {
        ControladorDeArquivos controller = new ControladorDeArquivos();
        controller.escreverSaidas(ranking);
    }
}
