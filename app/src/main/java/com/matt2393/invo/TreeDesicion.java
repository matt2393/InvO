package com.matt2393.invo;

import com.matt2393.invo.Modelo.NodoDT;

public class TreeDesicion {
    private NodoDT raiz;

    public TreeDesicion() {
    }

    public TreeDesicion(NodoDT raiz) {
        this.raiz = raiz;
    }

    public NodoDT getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoDT raiz) {
        this.raiz = raiz;
    }

    public void add(NodoDT parent, NodoDT child){
        parent.getChilds().add(child);
    }
}
