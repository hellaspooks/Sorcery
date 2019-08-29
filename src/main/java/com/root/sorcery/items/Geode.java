package com.root.sorcery.items;

public class Geode extends ModItem {
    Geode(String registryName) {
        super(registryName);
    }
    
    
    public static Geode geode;
    
    public static void init(){
        geode = new Geode("geode");
    }
}
