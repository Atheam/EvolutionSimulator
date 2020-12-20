# EvolutionSimulator
## Simple simulation of evolution on a 2D plane

![alt text](https://github.com/Atheam/EvolutionSimulator/blob/master/simulation_screen.gif)


EvolutionSimulator is a simple take at simulating evolution and its principles with a very basic rules.
The map consists of animals and grass, animals roam around the map based on particular genotype each animal has.
When an animal enters a square with a grass it eats it and gains some amount of energy, when two animals
enter the same square at the same time, and both of these animals have the right amount of energy they breed, producing 
a new animal which is spawn next to them. Genotype of a child is based on both genotypes of its parents, gene sequances of
both parents are split in two places and the resulting genotype of the child is the merged chunks (2 from first parent 1 from the second)
of genes. 

Every animal's move is determined by using this animal's genotype, also each day every animal loses some amount of energy.
This way only animals with the best genotype will survive, just how we wanted it to be simulating a real evolution!

![alt text](https://github.com/Atheam/EvolutionSimulator/blob/master/simulation_features.gif)

The simulation also provides tracking of a selected animal providing a deeper insight of current state of simulation.


