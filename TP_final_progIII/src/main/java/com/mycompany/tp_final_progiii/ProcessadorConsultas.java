/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp_final_progiii;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;

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
    private Map<String, List<Pair<Integer, Integer>>> indiceInvertido;

    private List<Pair<Documento, Double>> ranking = new ArrayList<>();

    public ProcessadorConsultas(List<String> consulta, List<Documento> documentos, Indexador indexador) throws IOException {
        this.consulta = consulta;
        this.listaDocumentos = documentos;
        this.indexador = indexador;
        this.indiceInvertido = indexador.getIndiceInvertido();
        calculaSIM();
        printRanking();
    }

    private void calculaSIM() {
        double sim = 0;
        double Wd = 0;
        Pair<Documento, Double> docRankeado;

        for (Documento documento : listaDocumentos) {
            int w_iq = 0;
            String[] termosDoDocumento = documento.conteudo.replaceAll("\\p{Punct}", " ").toLowerCase().split(" ");
            for (String termo : termosDoDocumento) {
                for (String busca : consulta) {
                    if (busca.equals(termo)) {
                        w_iq = 1;
                        break;
                    }
                }
            }
            
            for (String busca : consulta) {
                List<Pair<Integer, Integer>> ocorrenciasDoTermo = indiceInvertido.get(busca);
                double idf = Math.log((double) listaDocumentos.size() / ocorrenciasDoTermo.size());

                for (Pair<Integer, Integer> ocorrencia : ocorrenciasDoTermo) {
                    double w_td = calculaWtd(ocorrencia, idf);
                    sim += (w_td * w_iq);
                    Wd += Math.pow(w_td, 2);
                }
            }
            sim /= Math.sqrt(Wd);
            docRankeado = new Pair<>(documento, sim);
            insereRanking(docRankeado);
            sim = 0;
            Wd = 0;
        }
    }

    private double calculaWtd(Pair<Integer, Integer> ocorrencia, double idf) {
        int tf = ocorrencia.getValue();
        double w_td = tf * idf;
        return w_td;
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
        int cont = 0;
        for (Pair<Documento, Double> pair : ranking) {
            System.out.println("Documento " + pair.getDocID().docID + ", Similaridade: " + pair.getValue());   
            if (cont==9) {break;}
            cont++;
        }

        ControladorDeArquivos controller = new ControladorDeArquivos();
        controller.escreverSaidas(ranking);
    }
}
