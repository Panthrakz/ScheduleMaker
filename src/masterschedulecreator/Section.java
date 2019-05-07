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
public class Section {
    
    public String sectionname;
    public Course course;
    public Teacher teacher;
    public Student[] students;
    public int sectionnumber;
    public char period;
    
    public Section(){};
    
    public Section(String name, Course course, Teacher teacher, Student[] students, int sectionnumber, char period){
        this.sectionname = name;
        this.course = course;
        this.teacher = teacher;
        this.students = students;
        this.sectionnumber = sectionnumber;
        this.period = period;
    }
    
    public String getName() {
        return this.sectionname;    
    }
    
    public Student[] getStudents() {
        return students;
    }
    
    public Course getCourse(){
        return course;
    }
    
    public Teacher getTeacher(){
        return teacher;
    }
    
    public void setName(String newname){
        sectionname = newname;
    }
    
    public void addTeacher(){
        teacher = new Teacher();
    }
    
    public void nameTeacher(String teachername){
        teacher.setName(teachername);
    }
    
    public void setStudents(Student[] allstudents){
        students = allstudents;
    }
    
    public void addCourse(Course newcourse){
        course = newcourse;
    }
    
}

