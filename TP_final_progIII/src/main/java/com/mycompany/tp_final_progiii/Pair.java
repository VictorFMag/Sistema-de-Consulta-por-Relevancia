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
    private F value;

    public Pair(D documento, F value) {
        this.documento = documento;
        this.value = value;
    }

    public D getDocID() {
        return documento;
    }

    public F getValue() {
        return value;
    }
}
