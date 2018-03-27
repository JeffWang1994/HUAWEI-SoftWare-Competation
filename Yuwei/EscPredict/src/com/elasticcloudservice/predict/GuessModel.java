/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elasticcloudservice.predict;
import java.util.*;
/**
 *
 * @author yuwei
 */
public class GuessModel implements PredictCore.ModelInterface{
    
    @Override
    public Map<String,Integer> run(Map<String,Integer[]> series){
        
        double[][] w=new double[15][3];
        Map<String,Integer> result=new HashMap<>();
        
        w[0][0]=0.5531;
w[1][0]=0.0094;
w[2][0]=0.2333;
w[3][0]=0.2109;
w[4][0]=0.6612;
w[5][0]=0.4272;
w[6][0]=0.3232;
w[7][0]=0.0617;
w[8][0]=0.4951;
w[9][0]=0.1467;
w[10][0]=0.1986;
w[11][0]=0.2827;
w[12][0]=0.7;
w[13][0]=0.1001;
w[14][0]=0.0068;

w[0][1]=0.1107;
w[1][1]=0.6357;
w[2][1]=0.2087;
w[3][1]=0.1689;
w[4][1]=0.3890;
w[5][1]=0.3807;
w[6][1]=0.4912;
w[7][1]=0.7448;
w[8][1]=0.1660;
w[9][1]=0.1200;
w[10][1]=0.2792;
w[11][1]=-0.0484;
w[12][1]=0.3;
w[13][1]=0.1422;
w[14][1]=-0.0201;

w[0][2]=0.5471;
w[1][2]=0.3685;
w[2][2]=0.3880;
w[3][2]=0.4116;
w[4][2]=0.0023;
w[5][2]=0.0398;
w[6][2]=-0.2321;
w[7][2]=0.3355;
w[8][2]=0.0725;
w[9][2]=0.1467;
w[10][2]=0.4261;
w[11][2]=0.1143;
w[12][2]=0.3;
w[13][2]=0.1586;
w[14][2]=0.0536;

        
              
        for(Map.Entry<String,Integer[]> e:series.entrySet()){

            Integer[] hist=e.getValue();
            String name=e.getKey();
            String[] value=name.split("r");
            
            int i=Integer.valueOf(value[1])-1;
            //System.out.println(i);
            Integer r=(int)(hist[0]*w[i][0]+hist[1]*w[i][1]+hist[2]*w[i][2]);
            result.put(name, r);
        }
        
        
        
        return result;
    }
    
}
