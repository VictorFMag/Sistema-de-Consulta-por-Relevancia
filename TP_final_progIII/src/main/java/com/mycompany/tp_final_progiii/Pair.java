/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp_final_progiii;

/**
 *
 * @author Victor
 */
public class Pair<D, F> {
    private D documento;
    private F frequencia;

    public Pair(D documento, F frequencia) {
        this.documento = documento;
        this.frequencia = frequencia;
    }

    public D getDocumento() {
        return documento;
    }

    public F getFrequencia() {
        return frequencia;
    }
}
