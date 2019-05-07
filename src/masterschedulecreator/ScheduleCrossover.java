/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package masterschedulecreator;

import java.util.*;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

public class ScheduleCrossover extends AbstractCrossover<Schedule>{
    
    public ScheduleCrossover(){
        this(1);
    }
    
    public ScheduleCrossover(int crossoverPoints){
        super(crossoverPoints);
    }
    
    public ScheduleCrossover(int crossoverPoints, Probability crossoverProbability){
        super(crossoverPoints, crossoverProbability);
    }
    
    public ScheduleCrossover(NumberGenerator<Integer> crossoverPointsVariable){
        super(crossoverPointsVariable);
    }
    
    public ScheduleCrossover(NumberGenerator<Integer> crossoverPointsVariable, NumberGenerator<Probability> crossoverProbabilityVariable){
        super(crossoverPointsVariable, crossoverProbabilityVariable);
    }

    @Override
    protected List<Schedule> mate(Schedule sch1, Schedule sch2, int numberOfCrossoverPoints, Random r){
        
        int low = 0;
        int high = sch1.getAllSections()[0].length;
        
        //Schedule.Section[][] baby1 = new Schedule.Section[Schedule.SIZE][];
        //Schedule.Section[][] baby2 = new Schedule.Section[Schedule.SIZE][];
        
        Section[][] schedj1 = sch1.getAllSections();
        Section[][] schedj2 = sch2.getAllSections();
        
        
        Schedule baby1 = new Schedule(schedj1);
        Schedule baby2 = new Schedule(schedj2);
        
        
        for(int i = 0; i < numberOfCrossoverPoints; i++){
            
            int result = r.nextInt(high-low) + low;
            SwapColumns(baby1, baby2, result);
            
        }
        
        List<Schedule> babies = new ArrayList<Schedule>(2);
        
        babies.add(baby1);
        babies.add(baby2);
        
        //System.out.println("crossed-over!");
        
        return babies;
    }

    
    public void SwapColumns(Schedule sch1, Schedule sch2, int column){
        
        Section[] hold1 = new Section[7];
        Section[][] sch1sections = sch1.getAllSections();
        
        Section[] hold2 = new Section[7];
        Section[][] sch2sections = sch2.getAllSections();
        
        for(int i = 0; i < 7; i++){
            hold1[i] = sch1sections[i][column];
            hold2[i] = sch2sections[i][column];
        }
        
        sch1.replaceColumn(column, hold2);
        sch2.replaceColumn(column, hold1);
        
    }
 }
