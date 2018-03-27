/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escpredict.score;
import java.util.*;
import java.io.*;
/**
 *
 * @author yuwei
 */
public class Score {
    public static Map<String,Integer> loadTestData(String path, String[] flavor_list){
        Map<String,Integer> result=new HashMap<>();
        
        
        try(BufferedReader reader=new BufferedReader(new FileReader(path))){
            String line;
            while((line=reader.readLine())!=null){
                String[] value=line.split("\t");
                if(contain(flavor_list,value[1])==true){
                    Integer num=result.get(value[1]);
                    if(num==null) result.put(value[1], 1);
                    else result.replace(value[1], num,num+1);
                }
            }
        }catch(IOException exc){
            exc.printStackTrace();
        }
        return result;
    }

    public static double score(String[] fla,Map<String,Integer> pre,Map<String,Integer> tst){
        double mse=0;
        double a=0;
        double b=0;
        for(int i=0;i<fla.length;i++){
            Integer y=pre.get(fla[i]);
            Integer y_=tst.get(fla[i]);
            if(y==null) y=0;
            if(y_==null) y_=0;
            mse=mse+(y-y_)*(y-y_);
            a=a+y*y;
            b=b+y_*y_;
        }
        mse = Math.sqrt(mse);
        a =Math.sqrt(a);
        b=Math.sqrt(b);
        return (1-mse/(a+b));
    }
    
     /**
     * if the string is contain in the flavor_list
     * @param flavor_list
     * @param string
     * @return 
     */
    private static boolean contain(String[] flavor_list,String string){
         for(int i=0;i<flavor_list.length;i++){
             if(string.equals(flavor_list[i])) return true;
         }
         return false;
    }
    
}

