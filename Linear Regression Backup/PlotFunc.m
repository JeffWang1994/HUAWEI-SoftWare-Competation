function PlotFunc( xstart,xend )  
%PLOTFUNC Summary of this function goes here  
%   draw original data and the fitted   
  
  
  
%===================cost function 2====linear regression  
%original data  
x1=[1;2;3;4];  
y1=[1.1;2.2;2.7;3.8];  
%plot(x1,y1,'ro-','MarkerSize',10);  
plot(x1,y1,'rx','MarkerSize',10);  
hold on;  
  
%fitted line - ????  
x_co=xstart:0.1:xend;  
y_co=0.3+0.86*x_co;  
%plot(x_co,y_co,'g');  
plot(x_co,y_co);  
  
hold off;  
end  