/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package masterschedulecreator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;


public class ScheduleFactory extends AbstractCandidateFactory<Schedule>{
    
    private static final String TEACHERPATH = "c:/Users/steve/OneDrive/Desktop/Scheduleproject/Teachers.xlsx";
    private static final String STUDENTPATH = "c:/Users/steve/OneDrive/Desktop/Scheduleproject/Students.xlsx";
    private static final String SECTIONPATH = "c:/Users/steve/OneDrive/Desktop/Scheduleproject/Sections.xlsx";
    
    
    
    public ScheduleFactory(){}
    
    public Schedule generateRandomCandidate(Random rng){
    
        Schedule schedule = makeRandomSchedule(rng);
       
     //  System.out.println(schedule.getAllSections().length + " " + schedule.getAllSections()[0].length);
        
        
       // for(int i = 0; i < schedule.getAllSections()[0].length; i++){
       //     for(int j = 0; j < 7; j++){
       //         if(schedule.getAllSections()[j][i] != null){
       //             System.out.println(j + " " + i + " " +schedule.getAllSections()[j][i].getName());
       //         }

       //     }    
        //}
        
         return schedule;
        
    }
    
    public static Teacher[] getAllTeachers(){
        
        Teacher[] allteachers = null;
        FileInputStream fis;
        int numberofteachers = 0;   
        
        try{

            fis = new FileInputStream(TEACHERPATH);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            numberofteachers = sheet.getLastRowNum() + 1; 
            
        //    System.out.println(numberofteachers);
            
            allteachers = new Teacher[numberofteachers];
            
            for(int i = 0; i < numberofteachers; i++){
                allteachers[i] = new Teacher();
                allteachers[i].setName(sheet.getRow(i).getCell(0).getStringCellValue());
            }
            
        }

        catch (FileNotFoundException e){

            e.printStackTrace();

        }

        catch (IOException e) {

            e.printStackTrace();

        }
        
        return allteachers;

    }
    
    public static int getNumberOfSectionsByTeacher(Teacher teacher){
        
        int numberofsections = 0;
        FileInputStream fis;
        
        try{
            
            fis = new FileInputStream(SECTIONPATH);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum() + 1;
        
            for(int i = 0; i < rows; i++){
                if(teacher.getName().equals(sheet.getRow(i).getCell(1).getStringCellValue())){
                    numberofsections = numberofsections + (int)sheet.getRow(i).getCell(2).getNumericCellValue();
                }
            }
            
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
       
        return numberofsections;
    }
    
    public static int getNumberOfSectionsByCourse(Course course){
        
        int numberofsections = 0;
        FileInputStream fis;
        
        try{
            
            fis = new FileInputStream(SECTIONPATH);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum() + 1;
        
            for(int i = 0; i < rows; i++){
                if(course.getName().equals(sheet.getRow(i).getCell(0).getStringCellValue())){
                    numberofsections = numberofsections + (int)sheet.getRow(i).getCell(2).getNumericCellValue();
                }
            }
            
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
       
        return numberofsections;
    }
    
    public static Course[] getMasterCourseList(){
        
        Course[] courselist = null;
        
        FileInputStream fis;
        int y = 0;
        
        try{
            fis = new FileInputStream(TEACHERPATH);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum();
                     
            //We need to count the number of courses and teachers in the excel file, 
            //then create the arrays of appropriate size
            
            for ( int i = 0; i < rows + 1; i++){
                Row row = sheet.getRow(i);
                y = y + row.getLastCellNum() - 1;
            }
                        
            courselist = new Course[y];  
            
            Course course = new Course();
            Teacher teacher = new Teacher();
          
            Cell cell1;
            Cell cell2;
            
            //k counts the number of elements going into courselist
            
            int k = 0;
            
            for ( int i = 0; i < rows + 1; i++){
                Row row = sheet.getRow(i);
                int cells = row.getLastCellNum();
                                
                for (int j = 1; j<cells; j++){
                    cell1 = row.getCell(j);
                    cell2 = row.getCell(0);
                    
                    if(cell1 == null){
                        i++;
                    }
                
                    else{
                        
                        teacher.setName(cell2.getStringCellValue());
                        course.setName(cell1.getStringCellValue());
                        
                        course.addTeacher(teacher);
                        
                        courselist[k] = new Course();
                        courselist[k].setName(course.getName());
                        courselist[k].addBlankTeacher();
                        courselist[k].setNewTeacherName(teacher.getName());
                        
                       k++;
                        
                    }      
                }
            }
             
            fis.close();    
            
        }
            
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
         
        Arrays.sort(courselist, new courseComparator());
        
        // for(int n = 0; n < courselist.length; n++){
        //        System.out.println("In courselist, the " + n + "th course is " +courselist[n].getName() +" taught by " + courselist[n].getTeacherList()[0].getName());
        //        }
        
      
        return courselist;
     
        
    }
    
    public static Student[] getMasterStudentList(){

       Student[] studentlist = null;
        
        FileInputStream fis;
        int y = 0;
        
        try{
            fis = new FileInputStream(STUDENTPATH);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum();
                     
            //We need to count the number of courses and teachers in the excel file, 
            //then create the arrays of appropriate size
            
            studentlist = new Student[rows+1];  
            
            Student student = new Student();
            Course course = new Course();
          
            Cell cell1;
            Cell cell2;
            
            for ( int i = 0; i < rows +1; i++){
                
                Row row = sheet.getRow(i);
                int cells = row.getLastCellNum();
                      
                studentlist[i] = new Student();
                
                cell2 = row.getCell(0);
                student.setName(cell2.getStringCellValue());
                studentlist[i].setName(student.getName());
                
                
                for (int j = 1; j<cells; j++){
                    cell1 = row.getCell(j);
                    
                    
                    int numberofcourses;
                    Course[] courselist; 
                    
                    if(cell1 == null){
                        j++;
                    }
                    
                    else{
                        
                        course.setName(cell1.getStringCellValue());
                        
                        studentlist[i].addBlankCourse();
                        courselist = studentlist[i].getCourseList();
                      
                        //This next loop looks into the outputted courselist for a student,
                        //where the newest element should have the name 'null.' Then, it
                        //renames that element in the real courselist stored in Student
                        //to match what's in the excel (stored in 'course')
                       
                        for(int z = 0; z < 7; z++){
                            
                            if(courselist[z].getName() == null){
                                studentlist[i].nameCourse(z, course.getName());
                                System.out.println(studentlist[i].getCourseList()[z].getName());
                                z = 7;
                            }
                        }
                    }      
                }
                
                studentlist[i].addAllChores();
                
            }
                            
            fis.close();    
            
        }
            
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        Arrays.sort(studentlist, new studentComparator());
        
        return studentlist;
     
    }
  
    public static Course[] putStudentsInCourses(){
        
        Course[] allcourses = getMasterCourseList();
        String[] allcoursenames = new String[allcourses.length];
       
        for (int i = 0; i < allcourses.length; i++){
           allcoursenames[i] = allcourses[i].getName();

        }
       
        Student[] allstudents = getMasterStudentList();
   
        String studentname = new String();
        String coursename = new String();
        Course[] studentcourses;
        
    for(int i = 0; i < allstudents.length; i++){
        
        studentname = allstudents[i].getName();
        studentcourses = allstudents[i].getCourseList();
        int numberofcourses = allstudents[i].getCourseList().length;
        
        //System.out.println(studentname);
        
        for(int j = 0; j < numberofcourses; j++){
            if(studentcourses[j]==null){
                j++;
            }
            else{
                
                coursename = studentcourses[j].getName();
                
                for (int k = 0; k < allcourses.length; k++){
                    
                    if(allcoursenames[k].equals(coursename)){
                        allcourses[k].addBlankStudent();
                        allcourses[k].setNewStudentName(studentname);
                        //System.out.println(" is in " + allcourses[k].getName());
                    }
                     
                }
                
            }
                
        }
    }
        
    Student[] studentlist;
    
    //for(int x = 0; x < allcourses.length; x++){
        
    //    studentlist = allcourses[x].getStudentList();
    //    System.out.println("In "+ allcourses[x].getName() + " there are the following students:");
        
    //    for ( int y = 0; y < studentlist.length; y++){
            
    //        if(studentlist[y] == null){
    //            y = studentlist.length;
    //       }
    //        else{
            
    //            System.out.println(studentlist[y].getName());
    //        }
            
    //    }
        
    //}
    
    return allcourses;
    
    }
    
    public static Section[] makeSections(){
        
        Section[] allsections = null;
        FileInputStream fis;
        
        try{
            
            fis = new FileInputStream(SECTIONPATH);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum() + 1;
            
            int y = 0;
            
            for(int i = 0; i < rows; i++){
                y = y + (int)sheet.getRow(i).getCell(2).getNumericCellValue();
            }
            
            allsections = new Section[y];
            
            String sectionteacher = new String();
            String sectionname = new String();
            Teacher teacher = new Teacher();
            int sections;
            
            Cell cellCourse;
            Cell cellTeacher;
            Cell cellSections;
                
            int k = 0;
            
                for ( int i = 0; i < rows; i++){
                
                    Row row = sheet.getRow(i);
                    
                    cellCourse = row.getCell(0);
                    cellTeacher = row.getCell(1);
                    cellSections = row.getCell(2);
                    
                    sectionname = cellCourse.getStringCellValue();
                    sectionteacher = cellTeacher.getStringCellValue();
                    sections = (int)cellSections.getNumericCellValue();
                    
                    Course sectioncourse = new Course();
                    
                        for ( int j = 0; j < sections; j++){
                            
                            sectioncourse.setName(sectionname);
                            
                            allsections[k] = new Section();
                            allsections[k].addCourse(sectioncourse);
                            allsections[k].setName(sectionname + (sections - j));
                            allsections[k].addTeacher();
                            allsections[k].nameTeacher(sectionteacher);
                          //  System.out.println(allsections[k].getTeacher().getName() + " teaches " + allsections[k].getName() +", an instance of " + allsections[k].getCourse().getName());
                            k++;
                            
                            
                            
                        }
                }
        }
    
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return allsections;
        
}
    
    public static Section[] putCoursesAndStudentsInSections(){
        
        //This first part makes a list of all courses, who teaches them,
        //then populates the courses with every student who has them.
        
        //Course[] mastercourses = getMasterCourseList();
        
        Course[] mastercourses = putStudentsInCourses();
        
        //Then, we make all the sections for each course, and add the course
        //properties (teacher, all students, course name) to each section
        
        Section[] mastersections = makeSections();
     
        //Now that everything is loaded, add stose student, teacher, and 
        //coursename properties to each section
        
        String coursename = new String();
            
        for(int i = 0; i < mastersections.length; i++){
            
            coursename = mastersections[i].getCourse().getName();  
            Student[] coursestudents;
            
            
            for(int j = 0; j < mastercourses.length; j++){
                    
                if(coursename.equals(mastercourses[j].getName())){
                        
                    mastersections[i].setStudents(mastercourses[j].getStudentList());
                                        
                }
            }
        }
        
        return mastersections;
        
    } 
   
    public static Schedule makeRandomSchedule(Random rng){
        
        //makeSchedule takes in the Excel files, and spits out a BASIC
        //schedule where every section oges in order, 1 - 7, into schedule
        //for each teacher.
        
        
        
      //  Schedule schedule = new Schedule();
       // char period;
        Teacher teacher;
        Teacher[] allteachers = getAllTeachers();
        int numberofteachers = getAllTeachers().length;
        
        Section[][] schedule = new Section[7][numberofteachers];
        
        //Put that number into the schedule, so it knows how big to be:
        
        //schedule.setTeacherNumber(numberofteachers);
        
        int numberofsections;
        
        //Section[] allsections = putCoursesAndStudentsInSections();
        
        Section[] allsections = makeSections();
        
        //Student[] studentsincourse;
        //for(int i = 0; i < allsections.length; i++){
        //    System.out.println(allsections[i].getName() + " is taught by " + allsections[i].getTeacher().getName() + " and has the following students: ");
        //    studentsincourse = allsections[i].getStudents();
        //    for(int k = 0; k < studentsincourse.length; k++){
        //        if(studentsincourse[0] == null){
        //            System.out.println(" none yet!");
        //            k = studentsincourse.length;
        //        }
        //        else if(studentsincourse[k] == null){
        //            k = studentsincourse.length;
        //        }
        //        else{
        //            System.out.println(studentsincourse[k].getName());
        //        }
        //    }
        //}
    
        //We need to iterate over every section to give them a home in the schedule.
        //To do that, we need to make a mini-array of randomized sections for each teacher.
        
        Section[] teachersections = new Section[7];
        Section thissection;
        
        
        for(int i = 0; i < numberofteachers; i++){
            
            teacher = allteachers[i];
            numberofsections = getNumberOfSectionsByTeacher(teacher);
            int z = 0;
            
            for(int j = 0; j < allsections.length; j++){
                    
                if(teacher.getName().equals(allsections[j].getTeacher().getName())){
                        
                    teachersections[z] = allsections[j];
                    z++;
                    
                }
            }
            
            //Let's add free periods to the blank remaining spots so there are no null elements:
            
            Course FreePeriod = new Course();
            FreePeriod.setName("FreePeriod");
            
            while(z<7){
                
                teachersections[z] = new Section();
                teachersections[z].setName("FreePeriod");
                teachersections[z].addCourse(FreePeriod);
                teachersections[z].addTeacher();
                teachersections[z].nameTeacher(teacher.getName());
                z++;
            }
            
            
            //Now, teachersections has all of the sections taught by the given
            //teacher. Let's randomize, and insert into schedule:
            
            shuffleArray(teachersections, rng);
            
            for(int k = 0; k < 7; k++){
                
               // System.out.println(teacher.getName() + " teaches " + numberofsections + " sections.");
               // i is the teacher index
               // k is the index of the section taught by that teacher
               
               
                thissection = teachersections[k];
                schedule[k][i] = thissection;
                
                // System.out.println(schedule.getAllSections()[k][i].getName() +" is taught by "+schedule.getAllSections()[k][i].getTeacher().getName());
                
            }
            
        }

        Schedule buffer = new Schedule(schedule);
        
    return buffer;
    
    }
    
    private static void shuffleArray(Section[] array, Random rng) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rng.nextInt(i + 1);
            Section a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    
}
    

