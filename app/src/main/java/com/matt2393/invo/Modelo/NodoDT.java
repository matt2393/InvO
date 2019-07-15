package com.matt2393.invo.Modelo;

import android.view.View;

import java.util.ArrayList;

public class NodoDT {
    public final static int TYPE_ALTER=0;
    public final static int TYPE_PROB=1;

    private String name;
    private double value;
    private double prob;
    private int type;
    private View view;
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private ArrayList<NodoDT> childs;


    public NodoDT() {
        childs=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getProb() {
        return prob;
    }

    public void setProb(double prob) {
        this.prob = prob;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<NodoDT> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<NodoDT> childs) {
        this.childs = childs;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
