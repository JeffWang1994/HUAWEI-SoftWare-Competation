function PlotFunc( xstart,xend )  
%PLOTFUNC Summary of this function goes here  
%   draw original data and the fitted   
  
  
  
%===================cost function 2====linear regression  
%original data  
x1=[0;0;0;0;0];
x2=[0;0;0;0;2];
y=[0;0;0;2;1];  
%plot(x1,y1,'ro-','MarkerSize',10);  
plot3(x1,x2,y,'rx','MarkerSize',10);  
hold on;  
  
%fitted line - ????  
x1_co=xstart:0.1:xend;  
x2_co=xstart:0.1:xend;
y_co=0*x1_co+0.5*x2_co;  
%plot(x_co,y_co,'g');  
plot3(x1_co,x2_co,y_co);  
  
hold off;  
end  