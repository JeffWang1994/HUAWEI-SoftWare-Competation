function [ jVal,gradient ] = costFunction2( w )  
%COSTFUNCTION2 Summary of this function goes here  
%   linear regression -> y=theta0 + theta1*x  
%   parameter: x:m*n  theta:n*1   y:m*1   (m=4,n=1)  
%     
  
%Data  

global History;

i=1;
x1=History(7:18,i);
x2=History(6:17,i);
x3=History(5:16,i);
x4=History(4:15,i);
x5=History(3:14,i);
x6=History(2:13,i);
x7=History(1:12,i);
y=History(8:19,i); 
m=size(x1,1);  
  
hypothesis = h_func(x1,x2,x3,x4,x5,x6,x7,w);  
delta = hypothesis - y;  
jVal=sum(delta.^2);  
  
gradient(1)=sum(delta.*x1)/m;  
gradient(2)=sum(delta.*x2)/m;  
 
end  