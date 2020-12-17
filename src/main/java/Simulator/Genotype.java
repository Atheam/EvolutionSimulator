package Simulator;

import java.util.Arrays;
import java.util.Random;

public class Genotype {
    private int geneTotal = 32;
    private final int[] genesCount = {0,0,0,0,0,0,0,0};
    private final int[] genes = new int[geneTotal];


    public Genotype(int geneTotal){
        this.geneTotal = geneTotal;
        Random randomizer = new Random();
        for(int i = 0; i < this.geneTotal;i++){
            genes[i] = randomizer.nextInt(8);
            genesCount[genes[i]]++;
        }
        this.fixGenotype();
    }

    public Genotype(){ }


    public void fixGenotype(){

        Random randomizer = new Random();

        while(Arrays.stream(this.genesCount).anyMatch(x -> x == 0)) {

            int missingGene = 0;
            while (this.genesCount[missingGene] != 0) missingGene++;
            int replacedIndex = randomizer.nextInt(geneTotal);
            int geneReplaced = this.genes[replacedIndex];
            this.genes[replacedIndex] = missingGene;
            this.genesCount[missingGene]++;
            this.genesCount[geneReplaced]--;
        }
        Arrays.sort(this.genes);
    }

    @Override
    public String toString() {
        StringBuilder genesBuilder = new StringBuilder();
        for(Integer gene : this.genes) genesBuilder.append(gene.toString()).append(" ");
        return genesBuilder.toString();
    }

    @Override
    public int hashCode() {
        return java.util.Arrays.hashCode(this.genes);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Genotype)) return false;
        Genotype genotype = (Genotype) obj;
        return Arrays.equals(this.genes,genotype.genes);
    }

    public Genotype combineGenes(Genotype genotype){

        Genotype childGenotype = new Genotype();
        childGenotype.geneTotal = this.geneTotal;
        int[] splits = getSplits(this.geneTotal);

        for(int i = 0; i < splits[0]; i ++) {
            childGenotype.genes[i] = this.genes[i];
            childGenotype.genesCount[childGenotype.genes[i]]++;
        }

        for(int i = splits[0]; i < splits[1]; i ++){
            childGenotype.genes[i] = genotype.genes[i];
            childGenotype.genesCount[childGenotype.genes[i]]++;
        }

        for(int i = splits[1]; i < this.genes.length; i++){
            childGenotype.genes[i] = this.genes[i];
            childGenotype.genesCount[childGenotype.genes[i]]++;
        }


        childGenotype.fixGenotype();

        return childGenotype;
    }

    public int[] getSplits(int geneTotal){
        Random randomizer = new Random();
        int[] splits = new int[2];
        splits[0] = randomizer.nextInt(geneTotal - 2) + 1;
        splits[1] = randomizer.nextInt((geneTotal - splits[0]) + 1 ) + splits[0];
        return splits;
    }

    public MapDirection getDirection(){
        Random randomizer = new Random();
        return MapDirection.values()[genes[randomizer.nextInt(this.geneTotal)]];
    }




}
