package com.matt2393.invo.Modelo.TeoriaInventarios;

public class DecInvEOQ {
    private double t1,t2,t3,t4;
    /**
     * c1, Costo de mantenimiento
     * c2, Costo penal
     * c3, Costo fijo
     */
    private double c1,c2,c3;
    /**
     * r, tasa demanda
     * k, tasa producción
     */
    private double r,k;
    /**
     * q, cantidad de reorden o compra
     */
    private double q;

    /**
     * S, nivel maximo de produccion
     * D, nivel maximo de insexistencias
     */
    private double S,D;

    /**
     * Costo total del sistema
     */
    private double CTS;

    public double getT1() {
        return t1;
    }

    public void setT1(double t1) {
        this.t1 = t1;
    }

    public double getT2() {
        return t2;
    }

    public void setT2(double t2) {
        this.t2 = t2;
    }

    public double getT3() {
        return t3;
    }

    public void setT3(double t3) {
        this.t3 = t3;
    }

    public double getT4() {
        return t4;
    }

    public void setT4(double t4) {
        this.t4 = t4;
    }

    public double getC1() {
        return c1;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public double getC2() {
        return c2;
    }

    public void setC2(double c2) {
        this.c2 = c2;
    }

    public double getC3() {
        return c3;
    }

    public void setC3(double c3) {
        this.c3 = c3;
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

    public void setK(double k) {
        this.k = k;
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

    /**
     * inventario de un solo producto, demanda constante, revision continua
     */
    public void modelo(double c1,double c2, double c3, double r,double k){
        this.c1=c1;
        this.c2=c2;
        this.c3=c3;
        this.r=r;
        this.k=k;


        if(Double.isInfinite(c2)){
            t2=Math.sqrt((2d*c3*(1-r/k))/(r*c1));
            t1=(1d/k)*Math.sqrt((2d*c3)/(c1*(1-r/k)));
            t3=0;
            t4=0;
        }
        else {
            t2=Math.sqrt((2d*c2*c3*(1-r/k))/(r*(c1+c2)*c1));
            t1=(r*t2)/(k-r);
            t3 = Math.sqrt((2d * c1 * c3 * (1 - r / k)) / (r * (c1 + c2) * c2));
            t4 = (r * t3) / (k - r);
        }

        S = Double.isInfinite(c2) ? Math.sqrt(2d*r*c1*c3*(1-r/k)) : r*t2;
        D = r*t3;


        q = Double.isInfinite(c2) ? (Math.sqrt((2d*r*c3)/(c1*(1-r/k)))) : r*(t1+t2+t3+t4);
        //q=Math.sqrt((2d*r*c3/c1)*(1/(1-r/k))*((c1+c2)/c2));



        CTS=Double.isInfinite(c2) ? Math.sqrt(2d*r*c1*c3*(1-r/k)) : Math.sqrt((2d*r*c1*c2*c3*(1-r/k))/(c1+c2));
    }

}
