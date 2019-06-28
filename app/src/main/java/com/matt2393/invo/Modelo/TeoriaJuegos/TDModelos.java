package com.matt2393.invo.Modelo.TeoriaJuegos;


public class TDModelos {

    private double[][] matrizPagos;
    private int n, m;

    public double[][] getMatrizPagos() {
        return matrizPagos;
    }

    public void setMatrizPagos(double[][] matrizPagos) {
        this.matrizPagos = matrizPagos;
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

    /**
     * Modelos
     */
    public void Dominacion() {
        double[][] auxMatriz;
        double[] auxVec;
        int i, j, k, h, l;
        boolean b, b1;
        b = b1 = true;
        while (b && b1) {
            for (i = 0; i < m; i++) {
                for (j = 0; j < m; j++) {
                    if (i != j) {
                        b = true;
                        for (k = 0; k < n; k++) {
                            if (matrizPagos[k][i] > matrizPagos[k][j]) {
                                b = false;
                                break;
                            }
                        }
                        if (b) {
                            l = 0;
                            auxMatriz = new double[n][m - 1];
                            auxVec = new double[n * m - n];
                            for (k = 0; k < m; k++)
                                if (k != j) {
                                    for (h = 0; h < n; h++) {
                                        auxVec[l] = matrizPagos[h][k];
                                        l++;
                                    }
                                }

                            l = 0;
                            m--;
                            for (k = 0; k < m; k++)
                                for (h = 0; h < n; h++) {
                                    auxMatriz[h][k] = auxVec[l];
                                    l++;
                                }

                            matrizPagos = auxMatriz;

                            break;
                        }
                    }
                }
                if (b)
                    break;
            }

            if (n == 2 && m == 2)
                break;

            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    if (i != j) {
                        b1 = true;
                        for (k = 0; k < m; k++) {
                            if (matrizPagos[i][k] < matrizPagos[j][k]) {
                                b1 = false;
                                break;
                            }
                        }
                        if (b1) {
                            l = 0;
                            auxMatriz = new double[n - 1][m];
                            auxVec = new double[n * m - m];
                            for (k = 0; k < n; k++)
                                if (k != j) {
                                    for (h = 0; h < m; h++) {
                                        auxVec[l] = matrizPagos[k][h];
                                        l++;
                                    }
                                }
                            l = 0;
                            n--;
                            for (k = 0; k < n; k++)
                                for (h = 0; h < m; h++) {
                                    auxMatriz[k][h] = auxVec[l];
                                    l++;
                                }


                            matrizPagos = auxMatriz;

                            break;
                        }
                    }
                }
                if (b1)
                    break;
            }

            if (n == 2 && m == 2)
                break;
        }


    }

    public DecisionJuego puntoSilla() {
        DecisionJuego decisionJuego = new DecisionJuego();
        decisionJuego.setValorJuego(-100000000);

        DecisionJuego fil[], col[];
        fil = new DecisionJuego[n];
        col = new DecisionJuego[m];
        DecisionJuego maxF, minC, max, min;

        int i, j;
        for (i = 0; i < n; i++) {
            minC = new DecisionJuego();
            for (j = 0; j < m; j++) {
                if (j == 0 || matrizPagos[i][j] < minC.getValorJuego()) {
                    minC.setValorJuego(matrizPagos[i][j]);
                    minC.setjA(i);
                    minC.setjB(j);
                }
            }
            fil[i] = minC;
        }

        for (j = 0; j < m; j++) {
            maxF = new DecisionJuego();
            for (i = 0; i < n; i++) {
                if (i == 0 || matrizPagos[i][j] > maxF.getValorJuego()) {
                    maxF.setValorJuego(matrizPagos[i][j]);
                    maxF.setjA(i);
                    maxF.setjB(j);
                }
            }
            col[j] = maxF;
        }

        max = new DecisionJuego();
        min = new DecisionJuego();
        for (i = 0; i < fil.length; i++)
            if (i == 0 || fil[i].getValorJuego() > max.getValorJuego()) {
                max = fil[i];
            }


        for (i = 0; i < col.length; i++)
            if (i == 0 || col[i].getValorJuego() < min.getValorJuego()) {
                min = col[i];
            }


        if (max.getValorJuego() == min.getValorJuego())
            decisionJuego = max;
        return decisionJuego;
    }

    public DecisionJuego[] algebraico() {
        DecisionJuego d[] = new DecisionJuego[2];
        double p1, p2, q1, q2;
        int i, j;
        DecisionJuego dA, dB;
        dA = new DecisionJuego();
        dB = new DecisionJuego();

        if (n == 2 && m == 2) {
            p1 = (matrizPagos[1][1] - matrizPagos[1][0])
                    / (matrizPagos[0][0] - matrizPagos[1][0] - matrizPagos[0][1] + matrizPagos[1][1]);
            p2 = 1 - p1;
            q1 = (matrizPagos[1][1] - matrizPagos[0][1])
                    / (matrizPagos[0][0] - matrizPagos[0][1] - matrizPagos[1][0] + matrizPagos[1][1]);
            q2 = 1 - q1;


            dA.setProb(p1 > p2 ? p1 : p2);
            dB.setProb(q1 > q2 ? q1 : q2);
            dA.setjA(p1>p2?0:1);
            dB.setjB(q1>q2?0:1);
        }
        d[0] = dA;
        d[1] = dB;

        return d;
    }


    public void mostrarMatriz() {
        int i, j;
        System.out.println();
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                System.out.print(matrizPagos[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
