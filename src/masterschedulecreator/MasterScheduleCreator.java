/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package masterschedulecreator;

import org.uncommons.maths.random.Probability;

import java.util.*;

import org.uncommons.maths.random.MersenneTwisterRNG;

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
       // operators.add(new ScheduleCrossover());
        //operators.add(new ScheduleMutator(new Probability(0.01)));
       operators.add(new IdentityOperator());

        EvolutionaryOperator<Schedule> pipeline = new EvolutionPipeline<Schedule>(operators);
        
        GenerationalEvolutionEngine<Schedule> engine = new GenerationalEvolutionEngine<Schedule>(factory, 
                                                                                     pipeline,
                                                                                     new ScheduleEvaluator(),
                                                                                     new StochasticUniversalSampling(),
                                                                                     new MersenneTwisterRNG());
        
        //engine.setSingleThreaded(true);
        
        engine.addEvolutionObserver(new EvolutionObserver<Schedule>(){
        
            public void populationUpdate(PopulationData<? extends Schedule> data){
                
                System.out.println();
                System.out.println("Generation " + data.getGenerationNumber() + " has maximum fitness: " + data.getBestCandidateFitness());
                System.out.println();
                
                if(data.getBestCandidateFitness() > 90){
                    
                    Section[][] allsections = data.getBestCandidate().getAllSections();
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
            }
        });
        
        Schedule result = engine.evolve(1, 0, new TargetFitness(130, true));

    }
}

