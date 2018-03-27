/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elasticcloudservice.predict;
import java.util.*;
import java.text.SimpleDateFormat;
/**
 *
 * @author yuwei
 */
public class DataRecord implements Comparable<DataRecord>{
    final private String flavor;
    
    final private Date date;
    
    /**
     * create function
     * @param line
     * @throws Exception 
     */
    public DataRecord(String line) throws Exception{
        String[] value=line.split("\t");
        this.flavor=value[1];
        this.date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value[2]);
    }

    /**
     * @return the flavor
     */
    public String getFlavor() {
        return flavor;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }
    
    @Override
    public int compareTo(DataRecord r){
        return this.date.compareTo(r.date);
    }
    
    
    public static DataRecord[] getRecordList(String[] data_input){
         try{
            //parse data record into list
            List<DataRecord> record_list=new ArrayList<>();
            for(int i=0;i<data_input.length;i++){
                record_list.add(new DataRecord(data_input[i]));
            }
            
            //sort
            //sort at present,invoke official sort method
            Collections.sort(record_list);
            
            //to array
            return record_list.toArray(new DataRecord[record_list.size()]);
        }catch(Exception exc){
            exc.printStackTrace();
        }
         return null;
    }
    
}
