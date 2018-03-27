% Author:Jianli.Panlin
% Matlab version:2009A
%%AR(1)模型建模示例，由于AR(1)简单故没有复杂的建模流程，所以此例仅做了解
%%AR(1)模型的参考，顺便也可以熟悉一下系统辨识工具箱
clear
%生成模拟数据
x=2;
for k=1:199
x(k+1)=0.7*x(k)+3*randn(1,1);
end
clear k
%定阶，由于AR模型简单所以定阶容易
figure
autocorr(x)
figure
parcorr(x)
%ACF拖尾，PACF1阶截尾，应该用AR(1)模型
y=iddata([x,0,0]'); %转化为系统辨识工具箱能识别的数据类型
% 预测的关键是要在原有数据上加0。加k个，可以预测T+1—T+K以后的数据
% 在这里曾经讨论过，http://www.ilovematlab.cn/thread-23681-1-2.html
%不过当时好像没有说清楚
AR1=ar(x,1);%估计参数，AR1同y一样是一个结构数组
p1=predict(y(1:201),AR1,1);%1步预测
p2=predict(y(1:202),AR1,2);%2步预测
%提出预测数据，关于iddata类型的数据格式，可以看help
xp1=p1.OutputData;
xp2=p2.OutputData;
%绘图
figure
plot(x,'g')
hold on
plot(xp1,'r')
hold on
plot(xp2,'black')
%绘制均方误差，可以看到2步预测>1步预测
figure
plot((xp1(1:200)-x').^2,'m')
hold on
plot((xp2(2:200)-x(2:200)').^2,'b')