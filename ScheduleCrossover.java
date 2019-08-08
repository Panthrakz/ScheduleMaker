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
        
        int high = sch1.getAllSections()[0].length;
        
        Section[][] baby1sections = new Section[7][high];
        Section[][] baby2sections = new Section[7][high];
        
        for(int j = 0; j < 7; j++){

            System.arraycopy(sch1.getPeriod(j), 0, baby1sections[j], 0, high);
            System.arraycopy(sch2.getPeriod(j), 0, baby2sections[j], 0, high);    
        }
        
        Schedule baby1 = new Schedule(baby1sections);
        Schedule baby2 = new Schedule(baby2sections);
        
        for(int i = 0; i < numberOfCrossoverPoints; i++){
            
            int result = r.nextInt(high-1);
            SwapColumns(baby1, baby2, result);
            
        }
        
        List<Schedule> babies = new ArrayList<Schedule>(2);
        
        babies.add(baby1);
        babies.add(baby2);
        
        return babies;
    }

    
    public void SwapColumns(Schedule sch3, Schedule sch4, int column){
        
        Section[] hold3 = new Section[7];
        Section[][] sch3sections = sch3.getAllSections();
        
        Section[] hold4 = new Section[7];
        Section[][] sch4sections = sch4.getAllSections();
        
        for(int i = 0; i < 7; i++){
            hold3[i] = sch3sections[i][column];
            hold4[i] = sch4sections[i][column];
        }
        
        sch3.replaceColumn(column, hold4);
        sch4.replaceColumn(column, hold3);
        
    }
 }
