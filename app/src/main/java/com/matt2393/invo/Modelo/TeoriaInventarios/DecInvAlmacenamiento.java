package com.matt2393.invo.Modelo.TeoriaInventarios;

import java.util.ArrayList;

public class DecInvAlmacenamiento {

    private ArrayList<Double> c1;
    private ArrayList<Double> c2;
    private ArrayList<Double> c3;

    private ArrayList<Double> v;
    private double vTotal;
    private ArrayList<Double> r;

    private ArrayList<Double> t2;

    private ArrayList<Double> q;

    private double CTS;

    private double vResFinal;

    private double alpha=0d;

    private ArrayList<ArrayList<Double>> qRes;
    private ArrayList<Double> vRes;
    private ArrayList<Double> alphaRes;

    public ArrayList<Double> getC1() {
        return c1;
    }

    public void setC1(ArrayList<Double> c1) {
        this.c1 = c1;
    }

    public ArrayList<Double> getC2() {
        return c2;
    }

    public void setC2(ArrayList<Double> c2) {
        this.c2 = c2;
    }

    public ArrayList<Double> getC3() {
        return c3;
    }

    public void setC3(ArrayList<Double> c3) {
        this.c3 = c3;
    }

    public ArrayList<Double> getV() {
        return v;
    }

    public void setV(ArrayList<Double> v) {
        this.v = v;
    }

    public double getvTotal() {
        return vTotal;
    }

    public void setvTotal(double vTotal) {
        this.vTotal = vTotal;
    }

    public ArrayList<Double> getR() {
        return r;
    }

    public void setR(ArrayList<Double> r) {
        this.r = r;
    }

    public ArrayList<Double> getT2() {
        return t2;
    }

    public void setT2(ArrayList<Double> t2) {
        this.t2 = t2;
    }

    public ArrayList<Double> getQ() {
        return q;
    }

    public void setQ(ArrayList<Double> q) {
        this.q = q;
    }

    public double getCTS() {
        return CTS;
    }

    public void setCTS(double CTS) {
        this.CTS = CTS;
    }

    public double getvResFinal() {
        return vResFinal;
    }

    public void setvResFinal(double vResFinal) {
        this.vResFinal = vResFinal;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public ArrayList<ArrayList<Double>> getqRes() {
        return qRes;
    }

    public void setqRes(ArrayList<ArrayList<Double>> qRes) {
        this.qRes = qRes;
    }

    public ArrayList<Double> getvRes() {
        return vRes;
    }

    public void setvRes(ArrayList<Double> vRes) {
        this.vRes = vRes;
    }

    public ArrayList<Double> getAlphaRes() {
        return alphaRes;
    }

    public void setAlphaRes(ArrayList<Double> alphaRes) {
        this.alphaRes = alphaRes;
    }

    public void modelo(ArrayList<Double> c1, ArrayList<Double> c3, ArrayList<Double> r, ArrayList<Double> v, double vTotal){
        this.c1=c1;
        this.c3=c3;
        this.r=r;
        this.v=v;
        this.vTotal=vTotal;

        qRes=new ArrayList<>();
        vRes=new ArrayList<>();
        this.vTotal=vTotal;
        alphaRes=new ArrayList<>();

        alpha=0.05d;
        double qAux;
        double vAux;
        ArrayList<Double> qResAux;

        do {
            alpha-=0.05d;
            qResAux=new ArrayList<>();
            vAux=0;
            for (int i = 0; i < r.size(); i++) {
                qAux=Math.sqrt((2d*r.get(i)*c3.get(i))/(c1.get(i)-2d*alpha*v.get(i)));
                qResAux.add(qAux);
                vAux+=qAux*v.get(i);
            }
            alphaRes.add(alpha);
            qRes.add(qResAux);
            vRes.add(vAux-vTotal);

        } while (vAux-vTotal > 0);

        vResFinal=vRes.get(vRes.size()-1)+vTotal;
        q=new ArrayList<>(qRes.get(qRes.size()-1));

        CTS=0;

        for (int i = 0; i < q.size(); i++) {
            CTS += (0.5 * c1.get(i) * q.get(i) + c3.get(i)*r.get(i)/q.get(i));
        }



      /*  for (int i = 0; i < q.size(); i++) {
            System.out.print(q.get(i)+"\t");
        }
        System.out.println(vResFinal);
/*
        for (int i = 0; i < qRes.size(); i++) {
            for (int j = 0; j < qRes.get(i).size(); j++) {
                System.out.print(qRes.get(i).get(j)+"  ");

            }
            System.out.print(vRes.get(i));
            System.out.println();
        }
        */
        /*
        qResAux=new ArrayList<>();
        vAux=0;
        for (int i = 0; i < r.size(); i++) {
            qAux=Math.sqrt((2d*r.get(i)*c3.get(i))/(c1.get(i)-2d*alpha*v.get(i)));
            qResAux.add(qAux);
            vAux+=qAux*v.get(i);
        }

        for (int i = 0; i < qResAux.size(); i++) {
            System.out.println(qResAux.get(i));
        }

        System.out.println(vAux);
        System.out.println(vTotal-vAux);
        System.out.println(vAux-vTotal);
        */


    }
}
