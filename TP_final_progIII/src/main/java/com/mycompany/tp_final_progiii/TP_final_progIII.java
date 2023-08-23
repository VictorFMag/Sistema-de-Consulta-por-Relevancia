/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tp_final_progiii;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aluno
 */
public class TP_final_progIII {

    public static void main(String[] args) throws IOException {
        ControladorDeArquivos controller = new ControladorDeArquivos();
        
        List<String> documentos = new ArrayList<>();
        controller.lerDocumentos(documentos);

        List<String> stopwords = new ArrayList<>();
        controller.lerStopwords(stopwords);
        
        Indexador indexador = new Indexador(documentos, stopwords);
        
        
        List<String> Q = new ArrayList<>();
        controller.lerConsultas(Q);
        
        ProcessadorConsultas consultas = new ProcessadorConsultas(Q, documentos, indexador);
        
    }
}
