function [optTheta,functionVal,exitFlag]=Gradient_descent( )  
%GRADIENT_DESCENT Summary of this function goes here  
%   Detailed explanation goes here  
  
%global history;
%w1=[];
%w2=[];

%for i=1:15
%i=1
%    x1=history(2:6,i);
%    x2=history(1:5,i);
%    y=history(3:7,i); 

  options = optimset('GradObj','on','MaxIter',100);  
  initialw = zeros(7,1);  
  [optTheta,functionVal,exitFlag] = fminunc(@costFunction2,initialw,options);  

%{  
  w1=[w1;optTheta(1)];
  w2=[w2;optTheta(2)];
  w3=[w3;optTheta(3)];
  w4=[w4;optTheta(4)];
  w5=[w5;optTheta(5)];
  w6=[w6;optTheta(6)];
  w7=[w7;optTheta(7)];
%} 
  
  w=[w,optTheta]
  
%end

end  