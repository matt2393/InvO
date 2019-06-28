package com.matt2393.invo.Modelo.TeoriaColas;

public class Colas {
    private double u;
    private double a;
    private double p;
    private double p0;

    private double Lq;
    private double Ls;
    private double Wq;
    private double Ws;

    private double aEf;
    private double N;
    private double S;

    public void modeloMM1_FIFOinfinf(double u, double a){
        this.u=u;
        this.a=a;

        p=a/u;
        p0=1d-p;
        Lq=p/(1-p)-p;
        Ls=Lq+p;

        Wq=a/(u*(u-a));
        Ws=Wq+(1d/u);
    }

    /**
     *
     * @param u muertes
     * @param a nacimientos
     * @param N capacidad del sistema
     */
    public void modeloMM1_DGNinf(double u, double a, double N){
        this.u=u;
        this.a=a;
        this.N=N;

        p=a/u;
        p0=(1d-p)/(1d-Math.pow(p,N+1));

        aEf=a*(1d-getPk(p,N,N));

        Ls=((p0*p)/Math.pow(1-p,2d))*(N*Math.pow(p,N+1)-(1+N)*Math.pow(p,N)+1);
        Lq=Ls-p;

        Ws=Ls/aEf;
        Wq=Lq/aEf;
    }

    public void modeloMMS_DGinfinf(double u, double a, double S){
        this.u=u;
        this.a=a;
        this.S=S;

        p=a/u;

        double aux=0;
        for (int k = 0; k < S; k++) {
            aux+=(Math.pow(p,k)/fact(k));
        }

        p0=1d/(aux+(Math.pow(p,S)/(fact(S-1)*(S-p))));

        //aEf=a*(1d-getPk(p,N,N));

        Lq=(Math.pow(p,S+1)*p0)/(fact(S-1)*Math.pow(S-p,2d));
        Ls=Lq+p;

        Ws=Ls/a;
        Wq=Lq/a;
    }

    public void modeloMMS_DGNinf(double u, double a, double S, double N){
        this.u=u;
        this.a=a;
        this.S=S;
        this.N=N;

        p=a/u;

        double aux=0;
        for (int n = 0; n < S; n++) {
            aux+=(Math.pow(p,n)/fact(n));
        }

        p0=1d/(aux+((Math.pow(p,S)*(1d-Math.pow(p/S,N-S+1)))/(fact(S-1)*(S-p))));

        aEf=a*(1d-(Math.pow(p,N)*p0/(fact(S)*Math.pow(S,N))));

        Lq=((Math.pow(p,S+1)*p0)/(fact(S-1)*(Math.pow(S-p,2))))*
                (1d-Math.pow(p/S,N-S)-(N-S)*Math.pow(p/S,N-S)*(1-p/S));
        Ls=Lq+aEf/u;

        Ws=Ls/aEf;
        Wq=Lq/aEf;
    }

    private double getPk(double p, double N, double k){
        return (1d-p)/(1d-Math.pow(p,N+1))*Math.pow(p,k);
    }

    private double fact(double num){
        double aux=1;
        for (int i = 1; i <= num; i++) {
            aux*=i;
        }
        return aux;
    }

}
