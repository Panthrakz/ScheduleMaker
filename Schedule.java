/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package masterschedulecreator;

/**
 *
 * @author steve
 */
public final class Schedule {
    
    
    public static final int SIZE = 7;
    
    //Each Schedule instance has several components. In addition to a name,
    //it has a 2D array (7 periods x n teachers) where each slot 
    //holds a Section of a Course
    
    private final Section[][] allsections;
     
    private int goodteachers;

    
    public Schedule(Section[][] newsections){
        this.allsections = newsections;
    }
    
    public Section[][] getAllSections(){
        return allsections.clone();
    }
    
    //public void setTeacherNumber(int teachernumber){
    //    this.allsections = new Section[7][teachernumber];
    // }
    
    public void addBlankSection(int period, int teacherindex){
        this.allsections[period][teacherindex] = new Section();
        
    }
    
    public void addSection(int period, int teacherindex, Section section){
        this.allsections[period][teacherindex] = section;
    }
    
    public void replaceColumn(int column, Section[] entries){
        for(int i = 0; i < 7; i++){
            //System.out.println(allsections[i][column].getName() + " becomes "+ entries[i].getName());
            this.allsections[i][column] = entries[i];
            
        }
    }
    
    public Section[] getPeriod(int period){
        Section[] buffer = new Section[allsections[0].length];
        
        for(int i = 0; i < buffer.length; i++){
            buffer[i] = allsections[period][i];
        }
        
        
        return buffer;
    }
    
    public void setGoodTeachers(int goodones){
        this.goodteachers = goodones;
    }
    
}
