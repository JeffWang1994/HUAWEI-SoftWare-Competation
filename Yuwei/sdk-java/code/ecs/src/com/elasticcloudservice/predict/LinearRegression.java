/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elasticcloudservice.predict;
import java.util.*;
/**
 * 
 * need rewrite....
 * @since 2018.3.26
 * @author yuwei
 */
public class LinearRegression implements PredictCore.ModelInterface{
    
    final private LinearModel model;
    
    /**
     * create function
     * @param param_num 
     */
    public LinearRegression(int param_num){
        model=new LinearModel(param_num);
    }
    
     public LinearRegression(){
        model=new LinearModel(3);
    }
    
     @Override
    public Map<String,Integer> run(Map<String,Integer[]> series_map){
        
        double[] w=new double[this.model.weight.length];
        w[0]=0.3;
        w[1]=0.3;
        w[2]=0.7;
        double b=0;
          //initial model
        this.model.setParam(w, b);
        
        Map<String,Integer> result=new HashMap<>();
        for(Map.Entry<String,Integer[]> e:series_map.entrySet()){
            Integer[] history=e.getValue();
            Integer[] recent=new Integer[this.model.weight.length];
            
            for(int j=0;j<recent.length;j++){
                recent[recent.length-j-1]=history[j];
            }
            int y=this.predict(recent);
            result.put(e.getKey(), y);
        }
        
        
        return result;
    }
    
    
    
    
    
    /**
     * train the model with gradient depress
     * @param x_y
     */
    public void train(Map<Integer[],Integer> x_y){
        
        //initial weight and b;
        double[] w=new double[this.model.weight.length];

        w[0]=0.3;
        w[1]=0.3;
        w[2]=0.7;
        double b=0;
          //initial model
        this.model.setParam(w, b);
        this.printModel();
        /*
        
        for(int i=0;i<w.length;i++){
            w[i]=Math.random()*i;
        }
        double b= Math.random()*3;
        
        //gradient to accurate the weight
        // at most 10000 rounds
        // the difference is less than 0.01 and the train is ok;
        int max_round=10000;
        double max_error = 0.0001;
        double step = 0.0001;
        
      
        //start start
        for(int i=0;i<max_round;i++){
            //train the weight one by one
            for(int j=0;j<w.length;j++){
                w[j]=w[j] - step * this.model.diff(x_y, j+1);
            }
            b = b -step*this.model.diff(x_y, 0);
            
            //if each of the weight error changes no more than 0.1 and train is ok
            boolean finish=true;
            for(int j=0;j<w.length;j++){
                if((w[j] - this.model.weight[j])>=max_error &&(w[j] - this.model.weight[j])<=-max_error){
                    finish=false;
                }
            }
           // System.out.println(b+" "+model.b+" "+(b-this.model.b));
            if(finish==true && (b-this.model.b)<max_error && (b-this.model.b)>-max_error) break ;
            
            //update the weight
            this.model.setParam(w, b);
            
            //print
            
            for(int j=0;j<w.length;j++){
                System.out.print("w"+(j+1)+"="+w[j]+", ");
            }
            System.out.print("b="+b+" ");
            System.out.println("mse="+this.model.mse(x_y));
            
            
        }

        */
         
    }
    
    /**
     * predict
     * @param x
     * @return 
     */
    public int predict(Integer[] x){
        double result=this.model.getY(x);
        if(((result*10)%10)<5) return (int)result;
        else return (int)(result+1);
    }
    
    public void printModel(){
        String f="y=";
        for(int i=0;i<this.model.weight.length;i++){
            f=f+this.model.weight[i]+"x"+(i+1)+"+";
        }
        f=f+this.model.b;
        System.out.println(f);
    }
    
    
    
    /**
     * the linear model class,the model is like y = w1x1+w2x2+...+b.
     */
    public static class LinearModel{
        final private double[] weight;
        private double b;
        /**
         * create function
         * @param weight_num 
         */
        public LinearModel(int weight_num){
            this.weight=new double[weight_num];
            b=0;
        }
        
        /**
         * set param
         * @param w
         * @param b 
         */
        public void setParam(double[] w, double b){
            this.b=b;
            System.arraycopy(w, 0, this.weight, 0, this.weight.length);
        }
        
        /**
         * get y
         * y = w1x1+w2x2+...+b
         * @param x
         * @return 
         */
        public double getY(Integer[] x){
            double y=0;
            for(int i=0;i<this.weight.length;i++){
                y = y + x[i] * this.weight[i];
            }
            y= y + b;
            return y;
        }
        
        /**
         * minmum square error
         * @param x_y
         * @return 
         */
        public double mse(Map<Integer[],Integer> x_y){
            double e=0;
            for(Map.Entry<Integer[],Integer> entry: x_y.entrySet()){
                Integer[] x=entry.getKey();
                double e_each=this.getY(x)-entry.getValue();
                e = e+e_each * e_each;
            }
            return e/(2 * x_y.size());
        }
        
        /**
         * differentiation
         * @param x_y
         * @param loc y =w1x1 +w2x2+...+b ,the loc =0 is the b; 
         * @return 
         */
        public double diff(Map<Integer[],Integer> x_y, int loc){
            double diff=0;
            for(Map.Entry<Integer[],Integer> entry: x_y.entrySet()){
                Integer[] x=entry.getKey();
                if(loc==0){
                    diff=diff+(this.getY(x)-entry.getValue());
                }else{
                    diff=diff+(this.getY(x)-entry.getValue())*x[loc-1];
                }
            }
            return diff;
        }
        
    }
}
