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
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

/**
 *
 * @author steve
 */
public class ScheduleEvaluator implements FitnessEvaluator<Schedule>{
    
    private static final String TEACHERPATH = "c:/Users/steve/OneDrive/Desktop/Scheduleproject/Teachers.xlsx";
    private static final String STUDENTPATH = "c:/Users/steve/OneDrive/Desktop/Scheduleproject/Students.xlsx";
    private static final String SECTIONPATH = "c:/Users/steve/OneDrive/Desktop/Scheduleproject/Sections.xlsx";
    
    Student[] studentlist = getMasterStudentList();
    
    
    public double getFitness(Schedule candidate, List<? extends Schedule> population){
        
        double fitness = studentlist.length;
        //System.out.println(studentlist.length);
        
        String[] conflictedstudents = new String[studentlist.length];  

        //Check for students class conflicts requires building each student's
        //mini-schedule from the available Master schedule, and then looking for conflicts:
        
        for(int i = 0; i < studentlist.length; i++){
            
                if(doesInsurmountableConflictExist(studentlist[i], candidate) == 1){
                    fitness--;
                    conflictedstudents[i] = studentlist[i].getName();
                }
        }

       // if(fitness > 115){
            
       //     System.out.println("These are the conflicted students: ");
            
       //     for(int j = 0; j < studentlist.length; j++){
       //         if(conflictedstudents[j] !=null){
       //             System.out.println(conflictedstudents[j]+ " ");
       //         }
       //     }
       // }
        
        return fitness;
    }
    
    public boolean isNatural()
    {
        return true;
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
            
            studentlist = new Student[rows + 1];  
            
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
                               // System.out.println(studentlist[i].getCourseList()[z].getName());
                                z = 7;
                            }
                        }
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
        
        Arrays.sort(studentlist, new studentComparator());
        
        return studentlist;
     
    }
    
    public int doesInsurmountableConflictExist(Student student, Schedule schedule){
        
        //This method makes a mini schedule for a given student that is an array with
        //1 (or more!) in every period and section that a student may take, and a 0
        //elsewhere. Returns conflicts, which is a 0 if there are none, and 1 if there
        //are any. Doen't actually make a student schedule - just checks if it's possible!
        
        int conflicts;
        
        String studentname = student.getName();
        Course[] studentcourses = student.getCourseList();

        //If a student has no courses, pass them through with no conflicts.
        
        if(studentcourses.length == 0){
            conflicts = 0;
        }
        
        //Otherwise, go through the whole rigamarole:
        
        else{
        
            //done and possiblesections check to make sure everything in the excel is spelled right.
            //If there are zero sections for a course in a student's list of courses,
            //then something is mistyped in the excel and needs to be fixed.
            
            boolean[] done = new boolean[7];
            int possiblesections = 0;
            
            //baseschedule is a 7 x (numberofcoursestakenbystudent) array of sections;
            //rows are periods, and columns are sections of a course.
            
            int[][] baseschedule = new int[7][7];
            Section[][] allsections = schedule.getAllSections();
        
            //Then, look in allsections to find out how many sections there
            //are of each such course, and create a baseschedule that lists 
            //only those sections the student could possibly take. 
            
            for (int q = 0; q < 7; q++){
            
                //We have the courses the student is taking - let's build the baseschedule:
                // Baseschedule is an array of 0s and ns - 0s mean the course is not offered
                //during the given period, and an n means there are n such instances
                //that period.
            
                //i is periods, j is teachers
                
                for(int i = 0; i < allsections[0].length; i++){

                    for (int j = 0; j < 7; j++){

                        if(allsections[j][i].getCourse().getName().equals(studentcourses[q].getName())){

                            done[q] = true;
                            baseschedule[j][q]++;
                            possiblesections++;
                            
                        }
                    }
                }
            }

            //If done isn't full of 'true,' that means there's a typo in the excel files.
            
            for(int z = 0; z < 6; z++){
                
                if(done[z] == false){
                                                
                    System.out.println(studentname + " has a typo in their courses. " +  z);
                        
                }
            }
                
            conflicts = pathfinder(baseschedule, 1);

        }
        
        return conflicts;
        
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
   
    public static int pathfinder(int[][] sections, int path){
        
      //  System.out.println("");
        
        //for(int z = 0; z < sections.length; z++){
        //    for(int y = 0; y < sections[0].length; y++){
        //        System.out.print(sections[z][y]);
        //    }
        //    System.out.println();
        // }
        
        
        if(path == 0){
            //System.out.println("ding ding dingggg!");
            return path;
        }
        
        else{
        
            if((sections[0][0] > 0)&&(sections.length==1)&&(sections[0].length==1)){
                path = 0;   
                return path;
            }   
        
            else if((sections[0][0] == 0)&&(sections.length==1)&&(sections[0].length==1)){
                path = 1;
                return path;
            }
        
            else{
                for(int i = 0; (i < sections.length); i++){
                
                        if(sections[i][0] > 0){
                            
                            int[][] buffer = arrayShrinker(sections, i, 0);
                            path = pathfinder(buffer, path);
                        }
                        
                }
            }
        }

        return path;

    }
    
    public static int[][] arrayShrinker(int[][] sections, int row, int column){
        
        int[][] buffer = new int[sections.length - 1][sections[0].length - 1];
        
        for(int i = 0; i < sections.length-1; i++){
            for(int j = 0; j < sections[0].length-1; j++){
                
                if((i < row) && (j<column))  {
                    buffer[i][j] = sections[i][j];
                }
                
                else if ((i < row) && (j >= column)){
                    buffer[i][j] = sections[i][j+1];
                }
                
                else if ((i >= row) && (j < column)){
                    buffer[i][j] = sections[i+1][j];
                }
                
                else if ((i >= row) && (j >= column)){
                    buffer[i][j] = sections[i+1][j+1];
                }
            }
        }
         
        return buffer;

    }
}

   
