/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elasticcloudservice.predict;

import java.util.*;

public class Predict {
    
        /**
         * here begins my work
         * the first argument is the train data lines, format is like: 56498c50-84e4	flavor12    2015-01-01 19:03:32, seperated by "\n";
         * the second argument is input lines.
         * @param ecsContent  
         * @param inputContent
         * @return 
         */
	public static String[] predictVm(String[] ecsContent, String[] inputContent) {

            //result
            String result="";
            try{
                //parse input information and store in an instance
                InputInfo inputInfo=new InputInfo(inputContent);
                
                //predict 
                //all the predit period is invoked in this class
                PredictCore core=new PredictCore();
                Map<String,Integer> predict_result=core.predict(ecsContent, inputInfo.getFlavorName(), inputInfo.getPredict_start(), inputInfo.getPredict_end());
                //make the result to string
                int total_flavor=0;
                for(Map.Entry<String,Integer> entry: predict_result.entrySet()){
                    result=result+entry.getKey()+" "+entry.getValue()+"\n";
                    total_flavor=total_flavor+entry.getValue();
                }
                result=total_flavor+"\n"+result;
              
                
                //allocate to physical machine
                //all the allocate mechanic is invoked in Allocate class
                //the warning is the class Allocate is a static class
                //create vm list
                List<Allocate.VirtualMachine> vm_list=new ArrayList<>();
                for(Map.Entry<String,Integer> entry: predict_result.entrySet()){
                    Allocate.VirtualMachine vm=new Allocate.VirtualMachine(entry.getKey(), InputInfo.getCPU(inputInfo.getFlavor_list(), entry.getKey()), InputInfo.getMemory(inputInfo.getFlavor_list(), entry.getKey()), entry.getValue());
                    vm_list.add(vm);
                }               
                
                //allocate this method will instantly return a string result
                String allocate_result=Allocate.allocateAsString(vm_list, inputInfo.getPhysical_cpu(), inputInfo.getPhysical_mem());
                //to a whole string
                result=result+"\n"+allocate_result;
                
                //change the long String to a list String[],each element is a line
                return result.split("\n");
                
            }catch(Exception exc){
                //only used when debug
                exc.printStackTrace();
            }

            //if anything wrong happened, whaterver it is, null will be returned
             return null;
	}

}
