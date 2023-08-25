/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp_final_progiii;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author aluno
 */
public class ControladorDeArquivos {

    public void lerDocumentos(List<Documento> documentos) throws FileNotFoundException, IOException {
        String pastaPath = "corpus"; // Caminho da pasta
        File pasta = new File(pastaPath);
        for (File arquivo : pasta.listFiles()) {
            Documento documento = new Documento(arquivo.getName(), lerConteudoDocumento(arquivo, documentos));
            documentos.add(documento);
        }
    }

    public String lerConteudoDocumento(File arquivo, List<Documento> documentos) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        String linha = br.readLine();
        return linha;
    }

    public void lerStopwords(List<String> stopwords) throws FileNotFoundException, IOException {
        String docPath = "stopwords.txt"; // Caminho do arquivo
        BufferedReader br = new BufferedReader(new FileReader(docPath));
        String linha;
        while ((linha = br.readLine()) != null) {
            stopwords.add(linha);
        }
    }

    public void lerConsultas(List<String> Q) throws FileNotFoundException, IOException {
        String docPath = "consulta1.txt"; // Caminho do arquivo
        BufferedReader br = new BufferedReader(new FileReader(docPath));

        String linha = br.readLine();
        String[] formatedData = linha.split(" ");
        for (String termo : formatedData) {
            Q.add(termo);
        }
    }

    public void escreverSaidas(List<Pair<Documento, Double>> ranking) throws FileNotFoundException, IOException {
        String docPath = "rankingFinal.txt"; // Caminho do arquivo
        FileWriter FW = new FileWriter(docPath, false);

        int cont = 0;
        for (Pair<Documento, Double> par : ranking) {
            String docIdFormatado = par.getDocID().docID.replace(".txt", " ");
            if (par.getValue()>0) {
                FW.write(docIdFormatado);   
            }
            if (cont==9) {break;}
            cont++;
        }
        FW.close();
    }
}
