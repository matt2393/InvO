package com.matt2393.invo.Modelo.TomaDecision;

public class TDModelos {
    double[][] matriz_pagos,apriori,posteriori;
    double[] prob,probExp;
    int n, m;
    boolean maxim;

    public double[][] getMatriz_pagos() {
        return matriz_pagos;
    }

    public void setMatriz_pagos(double[][] matriz_pagos) {
        this.matriz_pagos = matriz_pagos;
    }

    public double[] getProb() {
        return prob;
    }

    public void setProb(double[] prob) {
        this.prob = prob;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public boolean isMaxim() {
        return maxim;
    }

    public void setMaxim(boolean maxim) {
        this.maxim = maxim;
    }

    public double[][] getApriori() {
        return apriori;
    }

    public void setApriori(double[][] apriori) {
        this.apriori = apriori;
    }

    public double[] getProbExp() {
        return probExp;
    }

    public void setProbExp(double[] probExp) {
        this.probExp = probExp;
    }

    /**
    Models
     */

    public Decision MaxiMax() {
        int i, j;
        Decision decision = new Decision();
        //decision.setValor(-100000000d);
        for (i = 0; i < n; i++)
            for (j = 0; j < m; j++) {
                if ((i == 0 && j == 0) || matriz_pagos[i][j] > decision.getValor()) {
                    decision.setValor(matriz_pagos[i][j]);
                    decision.setI(i);
                    decision.setJ(j);
                }
               /* if(i==0 && j==0) {
                    decision.setValor(matriz_pagos[i][j]);
                    decision.setI(i);
                    decision.setJ(j);
                }
                else if(matriz_pagos[i][j]>decision.getValor()){
                    decision.setValor(matriz_pagos[i][j]);
                    decision.setI(i);
                    decision.setJ(j);
                }*/
            }
        return decision;
    }

    public Decision MaxiMin() {
        Decision decision = new Decision();
        int i, j;
        Decision mins[] = new Decision[n];

        for (i = 0; i < n; i++) {
            Decision d = new Decision();
            for (j = 0; j < m; j++) {
                if (j == 0 || matriz_pagos[i][j] < d.getValor()) {
                    d.setValor(matriz_pagos[i][j]);
                    d.setI(i);
                    d.setJ(j);
                }
            }
            mins[i] = d;
        }
        for (i = 0; i < n; i++) {
            if (i == 0 || mins[i].getValor() > decision.getValor()) {
                decision.setValor(mins[i].getValor());
                decision.setI(mins[i].getI());
                decision.setJ(mins[i].getJ());
            }
        }
        return decision;
    }

    public Decision MaxProbPesimista() {
        Decision decision = new Decision();
        int i, j;
        for (j = 0; j < m; j++)
            if (j == 0 || prob[j] > decision.getProbabilidad()) {
                decision.setProbabilidad(prob[j]);
                decision.setJ(j);
            }
        j = decision.getJ();
        for (i = 0; i < n; i++)
            if (i == 0 || matriz_pagos[i][j] > decision.getValor()) {
                decision.setValor(matriz_pagos[i][j]);
                decision.setI(i);
            }
        return decision;
    }

    public Decision Hurwicz(double alfa) {
        Decision decision = new Decision();
        //Decision ganPes[]=new Decision[n];
        int i, j;
        double may, men, ganPes;
        for (i = 0; i < n; i++) {
            may = men = 0;
            for (j = 0; j < m; j++) {
                if (j == 0 || matriz_pagos[i][j] > may) {
                    may = matriz_pagos[i][j];
                }
                if (j == 0 || matriz_pagos[i][j] < men) {
                    men = matriz_pagos[i][j];
                }
            }
            ganPes = alfa * may + (1d - alfa) * men;
            if (i == 0 || ganPes>decision.getValor()){
                decision.setValor(ganPes);
                decision.setI(i);
            }

        }
        return decision;
    }

    public Decision MiniMax(){
        Decision decision=new Decision();
        double matriz_arrep[][]=new double[n][m];
        int i,j;
        double may,men;
        for(j=0;j<m;j++){
            may=0;
            for (i=0;i<n;i++)
                if(i==0 || matriz_pagos[i][j]>may)
                    may=matriz_pagos[i][j];
            for (i=0;i<n;i++)
                matriz_arrep[i][j]=may-matriz_pagos[i][j];

        }
        may=0;

        for (i=0;i<n;i++){
            for (j=0;j<m;j++){
                if(j==0 || matriz_arrep[i][j]>may){
                    may=matriz_arrep[i][j];
                }
            }
            if(i==0 || may<decision.getValor()){
                decision.setValor(may);
                decision.setI(i);
            }
        }
        return decision;
    }

    /**
     * Sin experimentcion
     */
    public Decision BayesSinExp(){
        Decision decision=new Decision();
        int i,j;
        double vE;
        for(i=0;i<n;i++){
            vE=0;
            for(j=0;j<m;j++)
                vE+=matriz_pagos[i][j]*prob[j];
            if(i==0 || vE>decision.getValor()){
                decision.setValor(vE);
                decision.setI(i);
            }
        }
      /*  double pagMax;
        double pagoEIP=0;
        for(j=0;j<m;j++){
            pagMax=0;
            for (i=0;i<n;i++){
                if(i==0 || matriz_pagos[i][j]>pagMax)
                    pagMax=matriz_pagos[i][j];
            }
            pagoEIP+=prob[j]*pagMax;
        }
        decision.setVEIP(pagoEIP-decision.getValor());
*/
        return decision;
    }
    /**
     * Con Experimentacion
     */
    /*public Decision BayesConExp(double[] progExp){
        this.probExp=progExp;
        int i,j;
        for(i=0;i<m;i++)
            for(j=0;j<m;j++)
                apriori[i][j]=

    }*/
    public Decision[] BayesConExp(double[][] apriori, double valorExp){
        this.apriori=apriori;
        Decision decision[]=new Decision[apriori.length];
        double[][] conjuntas=new double[apriori.length][m];
        double[] sumConj=new double[apriori.length];

        int i,j;
        for (i=0;i<apriori.length;i++){
            sumConj[i]=0;
            for (j=0;j<m;j++){
                conjuntas[i][j]=apriori[i][j]*prob[j];
                sumConj[i]+=conjuntas[i][j];
            }
        }
        probExp=sumConj;
        posteriori=new double[apriori.length][apriori[0].length];
        for(i=0;i<apriori.length;i++)
            for(j=0;j<apriori[0].length;j++)
                posteriori[i][j]=conjuntas[i][j]/sumConj[i];

        int k;
        double vE;
        for (k=0;k<apriori.length;k++){
            Decision d1=new Decision();
            for(i=0;i<n;i++){
                vE=0;
                for(j=0;j<m;j++)
                    vE+=matriz_pagos[i][j]*posteriori[k][j];

                if(i==0 || vE>d1.getValor()){
                    d1.setValor(vE);
                    d1.setI(i);
                }
            }
            d1.setValor(d1.getValor()-valorExp);
            decision[k]=d1;
        }


        return decision;
    }

    public double VEIP(Decision decSinExp){
        double pagMax;
        double pagoEIP=0;
        int i,j;
        for(j=0;j<m;j++){
            pagMax=0;
            for (i=0;i<n;i++){
                if(i==0 || matriz_pagos[i][j]>pagMax)
                    pagMax=matriz_pagos[i][j];
            }
            pagoEIP+=prob[j]*pagMax;
        }
        return pagoEIP-decSinExp.getValor();
    }

    public double VEE(Decision decSinExp, Decision[] decConExp){
        double pagoE=0,vE;
        int i,j;
        for (i=0;i<posteriori.length;i++){
            vE=0;
            for(j=0;j<posteriori[0].length;j++) {
                vE += matriz_pagos[decConExp[i].getI()][j] * posteriori[i][j];
            }
            pagoE+=vE*probExp[i];
        }
        return pagoE-decSinExp.getValor();
    }
}
