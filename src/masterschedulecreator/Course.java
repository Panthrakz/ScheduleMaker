package masterschedulecreator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author steve
 */
public class Course {
    
    public String name;
    Teacher[] teachers = new Teacher[3];
    Student[] students = new Student[50];
    public int sections;
    
    public String getName() {
        return this.name;    
    }
    
   public void changeSectionNumber(int totalsections){
       sections = totalsections;
   }
    
    public int getLength() {
        return students.length;
    }
    
    public Course(){}
    
    public Course(String name){
        this.name = name;
    }
    
    public Course(String name, Teacher[] teachers, Student[] students, int sections){
        this.name = name;
        this.teachers = teachers;
        this.students = students;
        this.sections = sections;
    }
    
    public void setName(String newname){
        name = newname;
    }
    
    public void addTeacher(Teacher newteacher){
        for(int i = 0; i < teachers.length; i++){
            if(teachers[i] == null){
                teachers[i] = newteacher;
                i = teachers.length;
            }
        }
    }
 
    public void addBlankTeacher(){
        for(int i = 0; i < teachers.length; i++){
            if(teachers[i] == null){
                teachers[i] = new Teacher();
                i = teachers.length;
            }
        }
    }
    
    public void setNewTeacherName(String newname){
        for(int i = 0; i < 2; i++){
            if(teachers[i].getName() == null){
                teachers[i].setName(newname);
                i = 2;
            }
        }
    }
    
    public Teacher[] getTeacherList(){
        return teachers;
    }
    
    public int getNumberOfTeachers(){
        return teachers.length;
    }
 
    public void addBlankStudent(){
        for(int i = 0; i < students.length; i++){
            if(students[i] == null){
                students[i] = new Student();
                i = students.length;
            }
        }
    }
    
    public void setNewStudentName(String newname){
        for(int i = 0; i < students.length; i++){
            
        //    System.out.println(i);
            
            if(students[i].getName() == null){
                students[i].setName(newname);
                i = students.length;
            }
        }
    }
    
    public Student[] getStudentList(){
        return students;
    }
    
}
