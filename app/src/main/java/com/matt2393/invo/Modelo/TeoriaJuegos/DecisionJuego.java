package com.matt2393.invo.Modelo.TeoriaJuegos;

public class DecisionJuego {
    private double valorJuego;
    private int jA,jB;
    private double prob;

    public DecisionJuego() {
    }

    public DecisionJuego(double valorJuego, int jA, int jB, double prob) {
        this.valorJuego = valorJuego;
        this.jA = jA;
        this.jB = jB;
        this.prob = prob;
    }

    public double getValorJuego() {
        return valorJuego;
    }

    public void setValorJuego(double valorJuego) {
        this.valorJuego = valorJuego;
    }

    public int getjA() {
        return jA;
    }

    public void setjA(int jA) {
        this.jA = jA;
    }

    public int getjB() {
        return jB;
    }

    public void setjB(int jB) {
        this.jB = jB;
    }

    public double getProb() {
        return prob;
    }

    public void setProb(double prob) {
        this.prob = prob;
    }
}
