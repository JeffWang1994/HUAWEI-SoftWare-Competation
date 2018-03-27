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
public class PredictCore {
     
    //model name
    //this must be defined for injecting model!!!!
    final private String modelName="GuessModel";
   
    
    
    // the code below is not recommand to change.....................................................................
    //the package name of model class
    final private String packageName="com.elasticcloudservice.predict.";
    
    /**
     * @since 2018.3.26
     * model interface, all the model can override this interface.
     */
    public static interface ModelInterface{
        /**
         * the core method in this interface, 
         * which means a series is inputed and return the next value
         * @param series_map
         * @return 
         */
        public Map<String,Integer> run(Map<String,Integer[]> series_map);
    }
    
    
    
    /**
     * predict the value, the method is invoked by Predict class, which is unchangable
     * the first agument is train data list, each element is a line in the file, raw without parsed;
     * the second argument is the flavor list need predicting;
     * the last two arguments are the start time and the last time need predicting;
     * this method will return a Map, including the vm type and its predicting number,like below
     * flavor1 1
     * flavor2 2
     * flavor3 0
     * @param train_input the input train data
     * @param flavor_list  the flavor list want to predict
     * @param start 
     * @param end
     * @return 
     * @throws java.lang.Exception 
     */
    public Map<String,Integer> predict(String[] train_input, String[] flavor_list ,Date start,Date end) throws Exception{
        
        //allocate result map space
        Map<String,Integer> predict_map=new HashMap<>();
        
        //calculate the preidct period, probably 7-14 days
        //get the calendar instance
        Calendar calendar=Calendar.getInstance();
        //the start of day in a year
        calendar.setTime(start);
        int start_day_of_year=calendar.get(Calendar.DAY_OF_YEAR);
        //the end of day in a year
        calendar.setTime(end);
        int end_day_of_year=calendar.get(Calendar.DAY_OF_YEAR);
        int predict_period=end_day_of_year-start_day_of_year;

        //parse data record, all the information will be stored in DataRecord instance
       DataRecord[] record_list=DataRecord.getRecordList(train_input);
       
       //get the whole map of history number contains all flavors need predicting
       //the number is within a predict_period 
       Map<String,Integer[]> history_map=new HashMap<>();
       for(int i=0;i<flavor_list.length;i++){
           //get the particial history array list
           //the first index is the most recent data record 
           //while the last index is the most far data record
           Integer[] hist=PredictCore.getHistoryList(record_list, flavor_list[i], predict_period);
           history_map.put(flavor_list[i], hist);
       }
       
       
       /**
        * the code structure is changed since2018.3.25, 
        * all the predict detail is deployed in the interface ModelInterface
        * In this method only data pretreating is done and a time series is passed in the interface .
       */
       String modelPath=this.packageName+this.modelName;
       //inject the model
       PredictCore.ModelInterface model=(PredictCore.ModelInterface)Class.forName(modelPath).newInstance();
       //do predict
       predict_map=model.run(history_map);
       
       /** the code below is abondoned at present........................................
       // which mean each x includes 2 value
       int param_num=3;
       
       //allocate the map store  x and y of all flavors 
       //the structure is like <flavor_name <x , y >>
       Map<String,Map<Integer[],Integer>> x_y_list = new HashMap<>();
       for(int i=0;i<flavor_list.length;i++){
           x_y_list.put(flavor_list[i], PredictCore.getX_Y(history_map.get(flavor_list[i]), param_num));
       }
       
       //the real train and predict begins here
       for(int i=0;i<flavor_list.length;i++){
           //train model
            LinearRegression regressModel=new LinearRegression(param_num);
            regressModel.train(x_y_list.get(flavor_list[i]));
            //regressModel.printModel();
            //predict
            //warning is the input x the last index is the recent date
            Integer[] recent=new Integer[param_num];
            Integer[] history=history_map.get(flavor_list[i]);
            for(int j=0;j<recent.length;j++){
                recent[recent.length-j-1]=history[j];
            }
            //predict
            int result_predict=regressModel.predict(recent);
            //if(result_predict==0) continue;
            predict_map.put(flavor_list[i], result_predict);
            
       }
       * .........................................................................
       */
          //System.out.println(predict_map);
        return predict_map;
        
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
    
    /**
     * get list,core method in data pretreat period;
     * the first index is the most recent data record; 
     * while the last index is the most far data record.
     * @param flavor
     * @return 
     */
    private static Integer[] getHistoryList(DataRecord[] record_list,String flavor,int period){
        
        //allocate a space of history list
        List<Integer> history_list=new ArrayList<>();
        
       //calculate the date period
       Calendar calendar=Calendar.getInstance();
       calendar.setTime(record_list[record_list.length-1].getDate());
       //set the day to end which means 23:59:59
       calendar.set(Calendar.HOUR_OF_DAY, 23);
       calendar.set(Calendar.MINUTE, 59);
       calendar.set(Calendar.SECOND, 59);
       //a perid days ago
       calendar.add(Calendar.DATE, -period+1);
       calendar.set(Calendar.HOUR_OF_DAY, 0);
       calendar.set(Calendar.MINUTE, 0);
       calendar.set(Calendar.SECOND, 0);
       //now is the deadline 
        Date deadline=calendar.getTime();
        
        //statistic number
        int num=0;
        
        //parse every record
        for(int i=record_list.length-1;i>=0;i--){
    
            //deadline statistic
            if(record_list[i].getDate().after(deadline)==true){
                //only special flavor is considered
                if(record_list[i].getFlavor().equals(flavor)==true){
                    num=num+1;
                }
            }else{
                history_list.add(num);
                //a period ago
                calendar.add(Calendar.DATE, -period);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                //now is the new deadline
                deadline=calendar.getTime();
                //update
                num=0;

                 //only special flavor is considered
                if(record_list[i].getFlavor().equals(flavor)==true){
                    num=num+1;
                }
            }
            
            // the earliest time is not considered
            if(i< period-1) break;
        }
        
        //to int array
        return history_list.toArray(new Integer[history_list.size()]);
    }
    
    
    /**
     * separate the data into x and y
     * @param data
     * @param feature_number
     * @return 
     */
    private static Map<Integer[],Integer> getX_Y(Integer[] data, int feature_number){
        //allocate the map
        Map<Integer[],Integer> map=new HashMap<>();
        //separate one by one
        for(int i=data.length-1;i>=feature_number;i--){
            Integer[] x=new Integer[feature_number];
            for(int j=0;j<x.length;j++){
                x[j]=data[i-j];
            }
            map.put(x,data[i-x.length] );
        }
        return map;
        
    }

  
    
}
