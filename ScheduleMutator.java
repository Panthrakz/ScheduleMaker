/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package masterschedulecreator;

import java.util.*;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.Probability;

public class ScheduleMutator implements EvolutionaryOperator<Schedule>{
    
        private final NumberGenerator<Probability> mutationProbability;
    
        public ScheduleMutator(Probability mutationProbability){
            this.mutationProbability = new ConstantGenerator<Probability>(mutationProbability);
        }
        
        public List<Schedule> apply(List<Schedule> selectedCandidates, Random rng){
            
            List<Schedule> mutatedPopulation = new ArrayList<Schedule>(selectedCandidates.size());
            for(Schedule s : selectedCandidates){
                
                 
                Schedule mutato = mutateSchedule(new Schedule(s.getAllSections()), rng);
                mutatedPopulation.add(mutato);
                
            }
          
            return mutatedPopulation;
            
        }
    
        private Schedule mutateSchedule(Schedule sch1, Random rng){
            
            int high = sch1.getAllSections()[0].length;
            int column;
            
            Section[][] buffer = new Section[7][high];
            
            for(int j = 0; j < 7; j++){

                System.arraycopy(sch1.getPeriod(j), 0, buffer[j], 0, high);
                
            }
            
            for(int i = 0; i < high; i++){
                
                if(mutationProbability.nextValue().nextEvent(rng)){
                
                    column = rng.nextInt(high-1);
                               
                    int index1 = rng.nextInt(6);
                    int index2 = rng.nextInt(6);
                    Section buffer1 = buffer[index1][column];
                    buffer[index1][column] = buffer[index2][column];
                    buffer[index2][column] = buffer1;
            
                    i = high;
            
                    
                }    
            }
            
            return new Schedule(buffer);
        }
    
        public void shuffleColumn(Section[][] schedule, int column){
        
        Section[] array = new Section[7];
        
        for(int k = 0; k < 7; k++){
            array[k] =  schedule[k][column];
        }
        
        for (int i = array.length - 1; i > 0; i--) {
            int j = (int)Math.floor(Math.random() * (i + 1));
            Section temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        
        for(int q = 0; q < 7; q++){
            schedule[q][column] = array[q];
        }
        
    }
        
        public void twoSwap(Section[][] schedule, int column, Random rng){
        
            //int index1 = rng.nextInt(7);
            //int index2 = rng.nextInt(7);
            //Section buffer1 = schedule[index1][column];
            //schedule[index1][column] = schedule[index2]column];
            //schedule[index2][column] = buffer1;
            
            
            Section[] array = new Section[7];
        
            for(int i = 0; i < 7; i++){
                array[i] =  schedule[i][column];
            }
            
            int index1 = rng.nextInt(7);
            Section buffer1 = array[index1];
            int index2 = rng.nextInt(7);
            Section buffer2 = array[index2];
            
            array[index1] = buffer2;
            array[index2] = buffer1;
            
            //for(int j = 0; j < 7; j++){
                
            //    schedule[j][column]
          //  }
            
        //    schedule.replaceColumn(column, array);
        
        }
        
}
