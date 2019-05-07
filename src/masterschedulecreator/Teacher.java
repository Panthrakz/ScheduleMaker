package masterschedulecreator;

import java.util.Comparator;
import org.apache.commons.lang3.StringUtils;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author steve
 */


public class Teacher {
    
     
    public String name;
    //TreeSet<Course> rootcourses = new TreeSet<Course>(new courseComparator()); 
    public Course[] courses;
    public Teacher(){}
    
    public Teacher (String name, Course[] allcourses){
        this.name = name;
        this.courses = allcourses;
    }
    
    public void setName(String newname){
        name = newname;
    }
    
    public Course[] getCourses(){
        return courses;
    }
    
    public String getName(){
        return name;
    }
    
    
    public void addBlankCourse(){
        for(int i = 0; i < 6; i++){
            if(courses[i] == null){
                courses[i] = new Course();
                i = 6;
            }
        }
    }
    
    public void nameCourse(int index, String newname){
        courses[index].setName(newname);
    }
    
}
