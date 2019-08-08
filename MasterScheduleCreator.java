/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package masterschedulecreator;

import org.uncommons.maths.random.Probability;

import java.util.*;

import org.uncommons.maths.random.MersenneTwisterRNG;

import org.uncommons.watchmaker.framework.islands.*;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.AbstractEvolutionEngine;
import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.operators.*;
import org.uncommons.watchmaker.framework.selection.*;
import org.uncommons.watchmaker.framework.termination.*;


public class MasterScheduleCreator {

          
    private static final String TEACHERPATH = "c:/Users/steve/OneDrive/Desktop/Scheduleproject/Teachers.xlsx";
    private static final String STUDENTPATH = "c:/Users/steve/OneDrive/Desktop/Scheduleproject/Students.xlsx";
    private static final String SECTIONPATH = "c:/Users/steve/OneDrive/Desktop/Scheduleproject/Sections.xlsx";
    
    public static void main(String[] args) {
        
    
        ScheduleFactory factory = new ScheduleFactory();
        
        List<EvolutionaryOperator<Schedule>> operators = new LinkedList<EvolutionaryOperator<Schedule>>();
        operators.add(new ScheduleCrossover());
        operators.add(new ScheduleMutator(new Probability(0.25)));
        //operators.add(new IdentityOperator());

        EvolutionaryOperator<Schedule> pipeline = new EvolutionPipeline<Schedule>(operators);
        
        IslandEvolution<Schedule> engine = new IslandEvolution<Schedule>(5, new RingMigration(),factory, 
                                                                                     pipeline,
                                                                                     new ScheduleEvaluator(),
                                                                                     new StochasticUniversalSampling(),
                                                                                     new MersenneTwisterRNG());
        
        //engine.setSingleThreaded(true);
        
        engine.addEvolutionObserver(new IslandEvolutionObserver<Schedule>()
        
            {
            
                public void islandPopulationUpdate(int islandIndex, PopulationData<? extends Schedule> populationData){
            
                }
            
                public void populationUpdate(PopulationData<? extends Schedule> populationData){
                                    
                    System.out.println();
                    System.out.println("Generation " + populationData.getGenerationNumber() + " has maximum fitness: " + populationData.getBestCandidateFitness());
                    System.out.println();
            
                    if((populationData.getBestCandidateFitness() > 85)||((populationData.getGenerationNumber() % 100) == 0)){   //return schedule if fitness greater than ___ (<131)
                
                        SchedulePrinter(populationData.getBestCandidate());
            
                    }
                }
            }
        );
        
        Schedule result = engine.evolve(20, 1, 20, 1, new TargetFitness(92, true));

    }
    
    public static void SchedulePrinter(Schedule candidate){
        Section[][] allsections = candidate.getAllSections();
            String format = "%-25s%-25s%-25s%-25s%-25s%-25s%-25s%-25s%n";
                String[] s = new String[8];
                    
                    for (int j = 0; j < allsections[0].length; j++){   
                                
                        for(int i = 0; i < 8; i++){ 

                            if(i==0){
                                    s[i] = allsections[i][j].getTeacher().getName();
                            }
                            
                            else if(allsections[i-1][j]!=null){
                                    s[i] = allsections[i-1][j].getName();
                            }
                        }

                    System.out.printf(format, s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7]);
                    
                    }
    }
    
   // public Schedule Optimizer(Schedule candidate){
        
        //goodteachers is the number of teachers with a 'good' mini schedule. Set it to max
        //at first, then -- for each conflict.
        
     //   int goodteachers  = candidate.getAllSections().length;
        
        // get each period from the candidate schedule for easy permutation!
        
       // Section[] A = new Section[goodteachers];
       // Section[] B = new Section[goodteachers];
       // Section[] C = new Section[goodteachers];
       // Section[] D = new Section[goodteachers];
       // Section[] E = new Section[goodteachers];
       // Section[] F = new Section[goodteachers];
       // Section[] G = new Section[goodteachers];
        
       // for(int i = 0; i < goodteachers; i++){ 
                          
       //     A[i] = candidate.getAllSections()[0][i];
       //     B[i] = candidate.getAllSections()[1][i];
       //     C[i] = candidate.getAllSections()[2][i];
       //     D[i] = candidate.getAllSections()[3][i];
       //     E[i] = candidate.getAllSections()[4][i];
       //     F[i] = candidate.getAllSections()[5][i];
       //     G[i] = candidate.getAllSections()[6][i];
                                    
        //}
        
        
        // create all permutations of the schedule (7 factorial)
        
        // test each permutation against rules to be written
        
        // return the permutation with the highest 'goodteachers' score
        
        //return best;
    //}

 

        
    }
    
    


