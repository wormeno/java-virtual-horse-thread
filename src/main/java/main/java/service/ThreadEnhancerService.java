package main.java.service;

import main.java.config.Config;
import main.java.model.EnhancerZone;

import java.util.*;

public class ThreadEnhancerService extends Thread{

    private Config config = new Config();
    private  Integer metersEnhancer = config.getMetersEnhancer();
    private Integer metersOfRace = config.getMetersOfRace();

    private Map<Integer[], EnhancerZone> enhancerZones = new HashMap<>();


    @Override
    public void run() {
        try {
            while (true){
                createEnhancerZone();
                Thread.sleep(config.getRefresTimeEnhancerZone());
            }
        } catch (InterruptedException e) {
            System.out.println("Exiting Thread Enhacer Service");
        }
    }

    /**
     * Crea zona potenciadora
     */
    private void createEnhancerZone(){
        Integer i = UtilHelper.getRandomNumber(1, metersOfRace - 1);
        Integer[] zone = {i,i+ metersEnhancer};
        enhancerZones.put(zone,new EnhancerZone());
    }

    public EnhancerZone findEnhacerZone(Integer value, String nameHorse){
        for(Map.Entry<Integer[], EnhancerZone> entry: enhancerZones.entrySet()){
            if( value >= entry.getKey()[0] && value <= entry.getKey()[1]){
                String zona=entry.getKey()[0] +"-"+entry.getKey()[1];
                System.out.println("\t\t Horse: "+ nameHorse+" en Zona de Potencia: "+zona);
                EnhancerZone enhancerZone = entry.getValue();
                enhancerZone.waitUntilFree(nameHorse, zona);
                return enhancerZone;
            }
        }
        return null;
    }

}
