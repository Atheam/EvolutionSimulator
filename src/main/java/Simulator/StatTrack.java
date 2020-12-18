package Simulator;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class StatTrack {
    private final GrassMap map;
    private long deadNum = 0;
    private long lifeSpanSum = 0;
    private final Map<Genotype,Integer> genotypes;
    private Genotype bestGenotype;
    private Animal trackedAnimal;
    private int daysCount = 0;

    private String EMPTY_GENOTYPE = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";

    public StatTrack(GrassMap map)
    {
        this.genotypes = new HashMap<>();
        this.map = map;

    }
    public Genotype getBestGenotype(){
        return this.bestGenotype;
    }
    public int getAnimalsCount(){
        return this.map.getAnimalsList().size();
    }
    public int getGrassCount(){
        return this.map.getGrassPositions().size();
    }
    public void daysUpdate(int days){
        this.daysCount = days;
    }

    public void animalDeathUpdate(Animal animal){
        this.deadNum+=1;
        this.lifeSpanSum += animal.getDaysAlive();
        animal.setDeathDay(this.daysCount);
        this.removeGenes(animal.getGenotype());
    }


    public String getBestGenes(){
        if(genotypes.size() == 0) return EMPTY_GENOTYPE;
        if(genotypes.size() == 1) this.bestGenotype = this.genotypes.keySet().iterator().next();
        else this.bestGenotype = this.genotypes.entrySet().stream().max((g1, g2) -> g1.getValue().compareTo(g2.getValue()) ).get().getKey();
        return this.bestGenotype.toString();
    }

    public void addGenes(Genotype genotype){
        if(this.genotypes.containsKey(genotype)) this.genotypes.put(genotype,this.genotypes.get(genotype)+1);
        else this.genotypes.put(genotype,1);
    }

    public void removeGenes(Genotype genotype){
        int n = this.genotypes.get(genotype);
        if(n > 1) this.genotypes.put(genotype,n-1);
        else this.genotypes.remove(genotype);
    }

    public float getAverageEnergy(){
        ArrayList<Animal> animals =  this.map.getAnimalsList();
        float totalEnergy = 0;
        if(animals.size() == 0) return 0;
        for(Animal animal:animals) totalEnergy+=animal.getEnergy();
        return totalEnergy/animals.size();

    }
    public float getLifeSpan(){
        if (this.deadNum !=0) return (float) this.lifeSpanSum/this.deadNum;
        else return 0;

    }
    public float getChildrenCount(){
        float totalNumOfChildren = 0;
        ArrayList<Animal> animals = map.getAnimalsList();
        if(animals.size() == 0) return 0;
        for(Animal animal : animals){
            totalNumOfChildren += animal.getChildren().size();
        }
        return totalNumOfChildren/animals.size();
    }

    public void addTracking(Vector2d position){
        this.trackedAnimal =  map.getAnimals().get(position).get(0);
    }

    public void showTrackingInfo(Stage stage){
        Text trackingInfo = new Text();
        if(this.trackedAnimal == null) trackingInfo.setText("No animal being tracked");
        else if(this.trackedAnimal.getDeathDay() == 0) trackingInfo.setText("Animal is alive. \n this animal has "+ this.trackedAnimal.getChildren().size() + " children");
        else trackingInfo.setText("Animal had been alive for " + trackedAnimal.getDaysAlive() + " days\n" + "Animal had died in day number " + trackedAnimal.getDeathDay() +
                                "\nThis animal had " + trackedAnimal.getChildren().size() + " children in total");
        Stage dialog = new Stage();
        dialog.initModality(Modality.NONE);
        dialog.initOwner(stage);
        dialog.setTitle("Animal tracking info");
        HBox dialogHBox = new HBox(20);
        dialogHBox.setAlignment(Pos.CENTER);
        dialogHBox.getChildren().addAll(trackingInfo);
        Scene dialogScene = new Scene(dialogHBox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setAlwaysOnTop(true);
    }
    public void saveToFile(ActionEvent event){


        try {
            FileWriter fileWriter = new FileWriter(".\\src\\main\\java\\Simulator\\params\\stat.txt");
            fileWriter.write(String.format("Statistics at day: %d \n", this.daysCount));
            fileWriter.write(String.format("Animals alive: %d \n", this.map.getAnimalsList().size()));
            fileWriter.write(String.format("Grass number: %d \n", this.map.getGrassPositions().size()));
            fileWriter.write(String.format("Average animal energy: %f \n", this.getAverageEnergy()));
            fileWriter.write(String.format("Dominating genotype : %s \n", this.getBestGenes()));
            fileWriter.write(String.format("Average animal life span: %f \n", this.getLifeSpan()));
            fileWriter.write(String.format("Average animal energy: %f \n", this.getAverageEnergy()));
            fileWriter.write(String.format("Average animal energy: %f \n", this.getChildrenCount()));
            fileWriter.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }


    }




}
