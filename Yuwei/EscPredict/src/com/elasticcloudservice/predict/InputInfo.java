/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elasticcloudservice.predict;
import java.util.*;
import java.text.*;

 /**
    * parse input content,the form is like this
    * 56 128 1200
    * 
    * 5
    * flavor1 1 1024
    * flavor2 1 2048
    * flavor3 1 4096
    * flavor4 2 2048
    * flavor5 2 4096
    *  
    * CPU
    * 
    * 2015-02-20 00:00:00
    * 2015-02-27 00:00:00.
    * 
    * @author yuwei
    */
public class InputInfo {
   
    //the input information is stroed here
    //the physical cpu core
    final private int physical_cpu;
    //the physical memory
    final private int physical_mem;
    //the vm list need predicting
    final private FlavorType[] flavor_list;
    //the allocate resource only cpu or memory :CPU/MEM
    final private String allocate_resource;
    //the start time need predicting
    final private Date predict_start;
    //the end time need predicting
    final private Date predict_end;
            
    /**
     * create function
     * @param input
     * @throws ParseException 
     */        
    public InputInfo(String[] input )throws ParseException{
       /**
        * physical_info store three information about the physical machine 
        * the String[0] is cpu, the String[1] is memory
        */
        String[] physical_info=input[0].split(" ");
        physical_cpu=Integer.parseInt(physical_info[0]);
        physical_mem=Integer.parseInt(physical_info[1]);
        
        /**
         * parse flavor type
         */
        int flavor_num=Integer.parseInt(input[2]);
        this.flavor_list=new FlavorType[flavor_num];
        for(int i=0;i<this.flavor_list.length;i++){
            String line=input[i+3];
            String[] value_flavor=line.split(" ");
            this.flavor_list[i]=new FlavorType(value_flavor[0],Integer.parseInt(value_flavor[1]),Integer.parseInt(value_flavor[2])/1024);
        }
        /**
         * parse allocate resource
         */
        this.allocate_resource=input[4+flavor_num];
        
        /**
         * parse time
         */
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.predict_start=format.parse(input[6+flavor_num]);
        this.predict_end=format.parse(input[7+flavor_num]);
    }
    
    /**
     * class flavor type includes flavor name ,cpu core and memory.
     */
    public static class FlavorType{
        final private String name; //flavor name
        final private int cpu;     //cpu core
        final private int mem;    //memory size
        
        /**
         * create function
         * @param name
         * @param cpu
         * @param mem 
         */
        public FlavorType(String name,int cpu,int mem){
            this.name=name;
            this.cpu=cpu;
            this.mem=mem;
        }
        //method of set and get
        public int getCpu(){
            return this.cpu;
        }
        public int getMemory(){
            return this.mem;
        }
        public String getName(){
            return this.name;
        }
    }

    /**
     * @return the physical_cpu
     */
    public int getPhysical_cpu() {
        return physical_cpu;
    }

    /**
     * @return the physical_mem
     */
    public int getPhysical_mem() {
        return physical_mem;
    }

    /**
     * @return the flavor_list
     */
    public FlavorType[] getFlavor_list() {
        return flavor_list;
    }
    
    /**
     * @return flavor name list
     */
    public String[] getFlavorName(){
        String[] list=new String[this.flavor_list.length];
        for(int i=0;i<list.length;i++){
            list[i]=this.flavor_list[i].getName();
        }
        return list;
    }

    /**
     * @return the allocate_resource
     */
    public String getAllocate_resource() {
        return allocate_resource;
    }

    /**
     * @return the predict_start
     */
    public Date getPredict_start() {
        return predict_start;
    }

    /**
     * @return the prrdict_end
     */
    public Date getPredict_end() {
        return predict_end;
    }
    
    /**
     * get cpu in a list with a flavor name
     * @param list
     * @param flavor
     * @return 
     */
    public static int getCPU(FlavorType[] list,String flavor){
        for(int i=0;i<list.length;i++){
            if(list[i].getName().equals(flavor)){
                return list[i].getCpu();
            }
        }
        return 0;
    }
    /**
     * get memory in a list with a flavor name
     * @param list
     * @param flavor
     * @return 
     */
    public static int getMemory(FlavorType[] list,String flavor){
        for(int i=0;i<list.length;i++){
            if(list[i].getName().equals(flavor)){
                return list[i].getMemory();
            }
        }
        return 0;
    }
    
}
