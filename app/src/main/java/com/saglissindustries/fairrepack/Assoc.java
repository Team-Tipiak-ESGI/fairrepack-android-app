package com.saglissindustries.fairrepack;

public class Assoc {

    private String name, descr, address;
    private int coin;

    public Assoc(String name, String descr, String address, int coin){

    }

    public String getName(){
        return name;
    }

    public String getDescr(){
        return descr;
    }

    public String getAddress(){
        return address;
    }

    public int getCoin(){
        return coin;
    }

    public void setCoin(int coin){
        this.coin = coin;
    }
}
