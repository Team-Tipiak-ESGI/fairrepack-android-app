package com.saglissindustries.fairrepack;

public class Assoc {

    private String name, descr, address, uuid;
    private int coin;

    public Assoc(String name, String descr, String address, int coin, String uuid){
        this.name = name;
        this.descr = descr;
        this.address = address;
        this.coin = coin;
        this.uuid = uuid;
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

    public String getUUID(){
        return uuid;
    }

    public int getCoin(){
        return coin;
    }

    public void setCoin(int coin){
        this.coin = coin;
    }
}
