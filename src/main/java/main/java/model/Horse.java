package main.java.model;

import main.java.config.Config;
import main.java.service.FinishService;
import main.java.service.ThreadEnhancerService;
import main.java.service.UtilHelper;
import org.apache.commons.lang3.RandomStringUtils;


public class Horse implements Runnable{

    private Config config = new Config();

    private Integer minSpeed = config.getMinSpeedHorse();
    private Integer maxSpeed = config.getMaxSpeedHorse();

    private Integer minResistance = config.getMinResistanceHorse();
    private Integer maxResistance = config.getMaxResistanceHorse();

    private Integer metersRun = 0;

    private String name;
    private Integer spped;
    private Integer resistance;

    private Integer metersOfRace = config.getMetersOfRace();

    private Boolean finishRace = false;

    FinishService finishService;
    ThreadEnhancerService threadEnhancerService;

    public  Horse( FinishService finishService, ThreadEnhancerService threadEnhancerService){
        setNombre();
        setResistencia();
        setVelocidad();
        this.finishService = finishService;
        this.threadEnhancerService = threadEnhancerService;
    }

    @Override
    public void run() {
        while (!finishRace){
            runnig();
        }
        finishService.put(this);
        System.out.println(this.getName()+" LLEGUE A LA META!! \n" );
    }

    /**
     * Correr consiste en:
     *  1.- Avanzar
     *  2.- Está en un área potenciada?
     *      Si => aplica potencia
     *      No => ingresa en etapa potenciada
     *  3.- Actualiza el estado de finalización de carrera
     *
     */
    private void runnig(){
        advancementStage();
        if( !finishRace ){
            afterAdvance();
            waitingStage();
        }
    }

    /**
     * Avanza una distancia, en metros, igual a la velocidad multiplicado por un número
     * aleatorio entre 1 y 10
     */
    private void advancementStage(){
        addMetersRun( spped + UtilHelper.getRandomNumber(1,10) );
    }

    /**
     * Luego de avanzar verifica si está en zona potenciada
     *  Si => espera 7 segundos y avanza 100 metros
     *  No=> ingresa en etapa de espera clásic
     */
    private void afterAdvance(){
        EnhancerZone enhancerZone = threadEnhancerService.findEnhacerZone(this.metersRun, this.name);
        if(  enhancerZone != null){
            sleeping(config.getSleepEnhancerZone());
            enhancerZone.setIsFree(this.name);
            addMetersRun(config.getPlusEnhancerZone());
        }
        else{
            waitingStage();
        }
    }

    /**
     * Suspende el avance por un tiempo determinado - resistencia del caballo
     *  tiempoDescanso: está en área potenciada:
     *      si => 7 segundos (tiempo aplicado por estar en área potenciada
     *      no => tiempo aleatorio entre 1 y 5 menos la resistencia
     *
     */
    private void waitingStage(){
        Integer oneTime = UtilHelper.getRandomNumber(1,5);
        Integer timeSleep = oneTime > resistance ? (oneTime - resistance) : oneTime;
        sleeping(timeSleep*1000);
    }

    private void sleeping(Integer timeSleep){
        try {
            System.out.println("\t Etapa de espera.: "+this.name+". Tiempo (ms):"+timeSleep);
            Thread.sleep(timeSleep);
        } catch (InterruptedException e) {
            System.out.println("Exiting Thread Horse Service");
        }
    }

    void setFinishedRace(){
        this.finishRace = metersOfRace <= metersRun;
    }

    private void printMetersRun(){
        //logger.info("Horse {} - Meters advanced: ", getName(), metersRun);
        System.out.println("Horse: "+ getName()+"  - Metros avanzados: "+ metersRun);
    }

    private void setNombre(){
        name = UtilHelper.randomAlphabetic(6);
    }

    private void setVelocidad(){
        spped = UtilHelper.getRandomNumber(minSpeed, maxSpeed);
    }

    private void setResistencia(){
        resistance = UtilHelper.getRandomNumber(minResistance, maxResistance);
    }

    /**
     * Actualiza la distancia corrida e imprime dicho valor
     * @param meters
     */
    private void addMetersRun(Integer meters){
        metersRun += meters;
        setFinishedRace();
        printMetersRun();
    }

    public String getName() {
        return this.name;
    }

    public Integer getSpped() {
        return spped;
    }

    public Integer getResistance() {
        return resistance;
    }
}
