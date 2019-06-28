package com.matt2393.invo.Modelo.TeoriaInventarios;

import java.util.ArrayList;

public class DecInvDescuentos {

    private double t2;

    /**
     * c1, Costo de mantenimiento
     * c2, Costo penal infinito
     * c3, Costo fijo
     */
    private double c1,c3;
    private final double c2=Double.POSITIVE_INFINITY;

    /**
     * r, tasa demanda
     * k, tasa producción Infito
     */
    private double r;
    private final double k=Double.POSITIVE_INFINITY;
    /**
     * q, cantidad de reorden o compra
     */
    private double q;

    /**
     * S, nivel maximo de produccion
     * D, nivel maximo de insexistencias 0, no se permite inexistencias
     */
    private double S,D;

    /**
     * Costo total del sistema
     */
    private double CTS;

    /**
     * ki, cantidad para descuentos
     * pi, precio de descuento para cada cantidad ki
     */
    private ArrayList<Double> ki;
    private ArrayList<Double> pi;


    public void modelo(double c1, double c3, double r, ArrayList<Double> ki, ArrayList<Double> pi){
        this.c1=c1;
        this.c3=c3;
        this.r=r;
        this.ki=ki;
        this.pi=pi;
        q = Math.sqrt(2d*c3*r/c1);
        int index = 0;

        for (int i = 0; i < ki.size()-1; i++) {
            if ( ki.get(i) <= q && q < ki.get(i+1)) {
                index=i;
            }
            else if( ki.get(i+1)<=q)
                index=i+1;
        }
        double cAux=Double.MAX_VALUE;
        double qAux=q;
        if(index<ki.size()-1){
            qAux=ki.get(index+1);
            cAux=pi.get(index+1)*r+(c3*r/qAux)+0.5d*c1*qAux;
        }
        CTS=pi.get(index)*r+(c3*r/q)+0.5d*c1*q;

        if(CTS>cAux) {
            CTS = cAux;
            q=qAux;
        }
    }


    public double getT2() {
        return t2;
    }

    public void setT2(double t2) {
        this.t2 = t2;
    }

    public double getC1() {
        return c1;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public double getC3() {
        return c3;
    }

    public void setC3(double c3) {
        this.c3 = c3;
    }

    public double getC2() {
        return c2;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getK() {
        return k;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    public double getS() {
        return S;
    }

    public void setS(double s) {
        S = s;
    }

    public double getD() {
        return D;
    }

    public void setD(double d) {
        D = d;
    }

    public double getCTS() {
        return CTS;
    }

    public void setCTS(double CTS) {
        this.CTS = CTS;
    }

    public ArrayList<Double> getKi() {
        return ki;
    }

    public void setKi(ArrayList<Double> ki) {
        this.ki = ki;
    }

    public ArrayList<Double> getPi() {
        return pi;
    }

    public void setPi(ArrayList<Double> pi) {
        this.pi = pi;
    }
}
