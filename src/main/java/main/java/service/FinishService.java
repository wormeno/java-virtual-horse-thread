package main.java.service;

import main.java.model.Horse;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FinishService {
    Semaphore semCon = new Semaphore(0);
    Semaphore semProd = new Semaphore(3);
    Queue queue = new ArrayDeque();
    Lock lock = new ReentrantLock();

    public Queue<Horse> finishHorse = new LinkedList<>();

    public void get(){
        try {
            semCon.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void put( Horse horse){
        try{
            semProd.acquire();
            finishHorse.add(horse);
            semProd.release();
            semCon.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Queue<Horse> getFinishHorse(){
        return finishHorse;
    }
}
