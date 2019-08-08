/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package masterschedulecreator;

import java.util.Comparator;
import org.apache.commons.lang3.StringUtils;



public class courseComparator  implements Comparator<Course>{
 
    @Override
    
    public int compare(Course course1, Course course2)
        {
            
            String first_Str;
            String second_Str;
            first_Str = course1.getName();
            second_Str = course2.getName();
            
            //System.out.println(first_Str + "  " + second_Str);
            
            //add 
            
            return StringUtils.compare(second_Str, first_Str);
        }
                
    }
    
