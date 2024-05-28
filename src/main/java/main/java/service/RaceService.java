package main.java.service;

import main.java.config.Config;
import main.java.model.Horse;
import java.util.ArrayList;
import java.util.List;

public class RaceService extends Thread{

    private  Integer numberOfHorses;

    FinishService finishService = new FinishService();

    ThreadEnhancerService threadEnhancerService = new ThreadEnhancerService();

    List<Thread> horseTreads = new ArrayList<>();
    private List<Horse> horseList = new ArrayList<>();

    public RaceService(Integer numberOfHorses) {
        this.numberOfHorses = numberOfHorses;
    }

    @Override
    public void run(){
        createHorse();
        listHorses();
        initRun();
        while (true){
            finishService.get();

            if( finishService.getFinishHorse().size() == 3){
                endOfRaces();
            }
        }
    }

    public void initRun(){

        System.out.println("INICIANDO LA CARRERA \n");

        horseTreads.stream().forEach(Thread::start);

        //El servicio que genera zonas de potencia no es DAEMON: pues no precisamos que se siga ejecutando una vez que la carrera finalice
        threadEnhancerService.start();
    }

    public void createHorse(){
        for (int i = 0; i < numberOfHorses; i++) {
            Horse horse = new Horse( finishService, threadEnhancerService);
            horseList.add(horse);
            Thread horseThread = null;

            if( Runtime.getRuntime().availableProcessors() == 1 ){
                /*
                 * Al tener un sólo hilo será más performante usar hilos del SO
                 */
                horseThread = new Thread(horse);
            }
            else {
                /**
                 * Se usa hilo virtual:
                 * - La espera es alta: mucho tiempo de descanso en la "etapa de espera" y en la zona de potencia"
                 * - Pueden haber muchos bloqueos al llegar a la zona de potencia:
                 *
                 * El hilo virtual evitaría el cambio de contexto.
                 */
                horseThread = Thread.ofVirtual().unstarted(horse);
            }
            horseTreads.add(horseThread);
        }
    }

    private void listHorses(){
        System.out.println("Participantes:");
        horseList.stream().forEach(horse -> {
            System.out.println("Nombre: "+horse.getName()+" - Velocidad: "+horse.getSpped()+" - Resistencia: "+horse.getResistance());
        });
    }

    private void listWinHorses(){
        System.out.println("Ganadores:");
        final Integer[] puesto = {1};
        finishService.getFinishHorse().forEach(horse -> {
            System.out.println("Puesto: "+ puesto[0] +" - Nombre: "+horse.getName()+" - Velocidad: "+horse.getSpped()+" - Resistencia: "+horse.getResistance());
            puesto[0]++;
        });
    }

    private void endOfRaces(){
        System.out.println("FIN CARRERA");
        listWinHorses();
        System.out.println("");
        horseTreads.forEach(Thread::interrupt);

        threadEnhancerService.interrupt();
        System.exit(0);
    }
}
