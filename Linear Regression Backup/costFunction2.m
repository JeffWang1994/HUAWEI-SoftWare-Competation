function [ jVal,gradient ] = costFunction2( theta )  
%COSTFUNCTION2 Summary of this function goes here  
%   linear regression -> y=theta0 + theta1*x  
%   parameter: x:m*n  theta:n*1   y:m*1   (m=4,n=1)  
%     
  
%Data  
x=[1;2;3;4];  
y=[1.1;2.2;2.7;3.8];  
m=size(x,1);  
  
hypothesis = h_func(x,theta);  
delta = hypothesis - y;  
jVal=sum(delta.^2);  
  
gradient(1)=sum(delta)/m;  
gradient(2)=sum(delta.*x)/m;  
  
end  