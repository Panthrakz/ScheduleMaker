/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package masterschedulecreator;

import java.util.Comparator;
import org.apache.commons.lang3.StringUtils;

public class stringComparator implements Comparator<String> {

@Override
  
    public int compare(String string1, String string2){
        
        return StringUtils.compare(string1, string2);        
    }

}
