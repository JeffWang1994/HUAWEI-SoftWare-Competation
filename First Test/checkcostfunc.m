function [ parameter ] = checkcostfunc(  )  
%CHECKC2 Summary of this function goes here  
%   check if the cost function works well  
%   check with the matlab fit function as standard  
  
%check cost function 2  
x=[2;1];
y=[0;0];  
  
EXPR= {'x','1'};  
p=fittype(EXPR);  
parameter=fit(x,y,p);  
  
end  