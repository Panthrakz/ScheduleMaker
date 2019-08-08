package masterschedulecreator;



public class Student {
    
    public String name;
    public Course[] courses = new Course[7];
    public Section[] studentschedule;
    
    public Student(){}
    
    public Student(String name, Course[] classes, Section[] studentschedule){
        this.name = name;
        this.courses = classes;
        this.studentschedule = studentschedule;
    }

    
    public void addBlankCourse(){
        for(int i = 0; i < 7; i++){
            if(courses[i] == null){
                courses[i] = new Course();
                i = 7;
            }
        }
    }
    
    public void addAllChores(){
        for(int i = 0; i < 7; i++){
            if(courses[i] == null){
                courses[i] = new Course("Chores");
                
            }
        }
    }
    
    public void nameCourse(int index, String newname){
        courses[index].setName(newname);
    }
    
    public Course[] getCourseList(){
        return courses;
    }
    
    public void setName(String newname){
        name = newname;
    }
    
    public String getName(){
        return name;
    }
    
}
