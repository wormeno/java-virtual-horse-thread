package main.java.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EnhancerZone {

    private Boolean isFree=true;
    private Lock lock = new ReentrantLock();
    private String zona;


    public synchronized  void waitUntilFree(String name, String zona){
        while ( isFree == false ){
            try {
                System.out.println("\t\t Horse: "+name+"en zona de potencia: "+zona+" 'LOCKEADA' - Esprando que se libere");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.zona = zona;
        System.out.println("\t\t Horse: "+name+" 'LOCKEA' Zona de potecia:"+zona);
        isFree = false;
    }

    public synchronized void setIsFree(String name) {
        synchronized (this) {
            isFree = true;
            this.notify();
            System.out.println("\t\t Horse:"+name+" 'LIBERA' Zona de potencia: "+zona);
        }
    }
}
