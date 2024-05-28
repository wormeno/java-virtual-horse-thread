package main.java;

import main.java.service.RaceService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad de caballos: ");
        Integer horses= scanner.nextInt();

        RaceService raceService = new RaceService(horses);
        raceService.start();
    }
}
