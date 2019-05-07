/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package masterschedulecreator;

import java.util.Comparator;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author steve
 */
public class studentComparator implements Comparator<Student>{

    
    @Override
    
    public int compare(Student student1, Student student2)
        {
            
            String first_Str;
            String second_Str;
            first_Str = student1.getName();
            second_Str = student2.getName();
            
            //System.out.println(first_Str + "  " + second_Str);
            
            //add 
            
            return StringUtils.compare(second_Str, first_Str);
        }
    
}
                
    