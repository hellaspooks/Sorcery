package com.root.sorcery.Items;

public class Geode extends ModItem{
    Geode(String registryName) {
        super(registryName);
    }
    
    
    public static Geode geode;
    
    public static void init(){
        geode = new Geode("geode");
    }
}
