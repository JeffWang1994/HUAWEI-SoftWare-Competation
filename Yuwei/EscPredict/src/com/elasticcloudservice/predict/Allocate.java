/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elasticcloudservice.predict;
import java.util.*;
/**
 * need rewrite
 * @author yuwei
 */
public class Allocate {
     public static class PhysicalMachine{
        
        final public int CPU;
        final public int MEM;
    
        final private List<String> vmList;
    
        private int remainCPU;
        private int remainMem;    
        /**
         * creation function
        * @param CPU
        * @param MEM 
        */
        public PhysicalMachine(int CPU,  int MEM){
            this.CPU=CPU;
            this.MEM=MEM;
            this.vmList=new ArrayList<>();
            this.remainCPU=CPU;
            this.remainMem=MEM;
        }
    
    
        public void add(String vm,int cpu, int mem){

            this.vmList.add(vm);
            this.remainCPU=this.remainCPU-cpu;
            this.remainMem=this.remainMem-mem;
        }
    
        public int getRemainCPU(){
            return this.remainCPU;
        }
    
        public int getRemainMem(){
            return this.remainMem;
        }
    
        public List<String> getList(){
            return this.vmList;
        }
    
     }
    
    public static class VirtualMachine {
   
         //
        final public String flavor;
        //the number of cpu
        final public int CPU;
        //the number of memory
        final public int MEM;
    

        private int vm_number;
    
    
    
        /**
         * creation function
         * @param flavor
         * @param CPU
         * @param MEM 
         * @param num 
        */
        public VirtualMachine(String flavor,int CPU, int MEM, int num){
           this.flavor=flavor;
            this.CPU=CPU;
            this.MEM=MEM;
            this.vm_number=num;
        }
    
         /**
          * remove vm only one at a time
        */
        public void removeVM(){
            this.vm_number=this.vm_number-1;
        }
    
         /**
          * get remain number
          * @return 
         */
        public int getRemainVM(){
            return this.vm_number;
        }
    }
    
    public static String allocateAsString(List<VirtualMachine> vm_list, int phy_cpu, int phy_mem){
        //invoke core method to do calculate
        List<Map<String,Integer>> list=Allocate.allocate(vm_list, phy_cpu, phy_mem);
        /**
         *result string 
         * the form is like below
         * 2
         * 1 flavor2 1 flavor1 3
         * 2 flavor3 4 flavor2 2 flavor4 1 flavor1 1.
         **/
        String result=String.valueOf(list.size())+"\n";
        //change list into string
        for(int i=0;i<list.size();i++){
           Map<String,Integer> vm_map=list.get(i);
            result=result+String.valueOf(i+1);
            for(Map.Entry<String,Integer> entry: vm_map.entrySet()){
                result=result+" "+entry.getKey()+" "+entry.getValue();
            }
            result=result+"\n";
        }
        return result;
    }

    
    
    /**
     * allocate the vm into physiacl machine effectively
     * @param vm_list
     * @param phy_cpu
     * @param phy_mem
     * @return 
     */
    public static List<Map<String,Integer>> allocate(List<VirtualMachine> vm_list, int phy_cpu, int phy_mem){
        
         //remove zero
        for(int i=0;i<vm_list.size();i++){
            if(vm_list.get(i).getRemainVM()==0){
                vm_list.remove(i);
            }
        }
        
        
        List<PhysicalMachine> phy_list=new ArrayList<>();
        
       boolean n=true;
        
        while(vm_list.isEmpty()==false){
            if(n==true){
                PhysicalMachine phy_machine=new PhysicalMachine(phy_cpu,phy_mem);
                phy_list.add(phy_machine);
                n=false;
            }
            int i;
            for(i=0;i<vm_list.size();i++){
                VirtualMachine vm=vm_list.get(i);
                //System.out.println(phy_list.get(phy_list.size()-1).getRemainCPU());
                if(vm.CPU<=phy_list.get(phy_list.size()-1).getRemainCPU() && vm.MEM<=phy_list.get(phy_list.size()-1).getRemainMem() ){
                    vm.removeVM();
                    phy_list.get(phy_list.size()-1).add(vm.flavor,vm.CPU,vm.MEM);
                    
                    if(vm.getRemainVM()==0) vm_list.remove(vm);
                    break;
                }
            }
            if(i==vm_list.size()){
        
                n=true;
            }
        }
        return Allocate.statistic(phy_list);
    }
    
    
   
   /**
    * 
    * @param list 
    */ 
    public static void foreach(List<Map<String,Integer>> list){
        System.out.println(list.size());
        for(int i=0;i<list.size();i++){
           Map<String,Integer> vm_map=list.get(i);
            System.out.print(i+1);
            for(Map.Entry<String,Integer> entry: vm_map.entrySet()){
                System.out.print(" "+entry.getKey()+" "+entry.getValue());
            }
            System.out.println();
        }
    }
    
    public static List<Map<String,Integer>> statistic( List<PhysicalMachine> orig){
        List<Map<String,Integer>> list=new ArrayList<>();
        for(int i=0;i<orig.size();i++){
            List<String> vm_list=orig.get(i).getList();
            Map<String,Integer> map=new HashMap<>();
            for(int j=0;j<vm_list.size();j++){
                Integer num=map.get(vm_list.get(j));
                if(num==null){
                    map.put(vm_list.get(j),1);
                }else{
                    map.replace(vm_list.get(j), num, num+1);
                }                
            }
            list.add(map);
        }
        return list;
    }
    
}
