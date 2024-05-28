package main.java.service;

public class UtilHelper {

    public static Integer getRandomNumber(Integer min, Integer max){
        return min + (int) (Math.random() * (max - min + 1));
    }
}
