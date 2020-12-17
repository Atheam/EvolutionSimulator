package Simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class StatTrack {
    private final GrassMap map;
    private long deadNum = 0;
    private long lifeSpanSum = 0;
    private final Map<Genotype,Integer> genotypes;
    private Genotype bestGenotype;
    private String EMPTY_GENOTYPE = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";

    public StatTrack(GrassMap map)
    {
        this.genotypes = new HashMap<>();
        this.map = map;

    }

    public int getAnimalsCount(){
        return this.map.getAnimalsList().size();
    }
    public int getGrassCount(){
        return this.map.getGrassPositions().size();
    }

    public void animalDeathUpdate(Animal animal){
        this.deadNum+=1;
        this.lifeSpanSum += animal.getDaysAlive();
        this.removeGenes(animal.getGenotype());
    }


    public String getBestGenotype(){
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
        this.genotypes.remove(genotype);
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
            totalNumOfChildren += animal.getNumOfChildren();
        }
        return totalNumOfChildren/animals.size();
    }


}
