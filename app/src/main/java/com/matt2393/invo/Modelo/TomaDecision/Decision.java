package com.matt2393.invo.Modelo.TomaDecision;

public class Decision {
    private double valor,probabilidad;
    private int i,j;
    private double VEIP,VEE;

    public Decision() {
    }

    public Decision(double valor, double probabilidad, int i, int j) {
        this.valor = valor;
        this.probabilidad = probabilidad;
        this.i = i;
        this.j = j;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public double getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(double probabilidad) {
        this.probabilidad = probabilidad;
    }

    public double getVEIP() {
        return VEIP;
    }

    public void setVEIP(double VEIP) {
        this.VEIP = VEIP;
    }

    public double getVEE() {
        return VEE;
    }

    public void setVEE(double VEE) {
        this.VEE = VEE;
    }
}
