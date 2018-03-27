%x=load('D:\Program Files\Github\HUAWEI-SoftWare-Competation\AR Model\traffic.txt');
load TrainData_History.mat;

x=History(:,8);

y=aryule(x,3);%建立4阶模型，求出  y[n] = a1y[n-1] + a2y[n-2] + .....+ap[yn-p]   的预测系数a（p）
                       %这一步在matlab命令窗口执行后显示如下信息：
                       %y =1.0000   -1.0202    0.0799    0.1174   -0.1629
z=idpoly([y],[]);%把多项式转换成模型
                       %这一步在matlab命令窗口执行后显示如下信息：
                       %Discrete-time IDPOLY model: A(q)y(t) = e(t)                    
                       %A(q) = 1 - 1.02 q^-1 + 0.07993 q^-2 + 0.1174 q^-3 - 0.1629 q^-4   
                       %This model was not estimated from data. （Question1：这是什么意思？难道我的数据没有用到吗？）                 
                       %Sampling interval: 1 
m=iddata([x],[]);%Question2：这一行是什么用处？
                       %这一步在matlab命令窗口执行后显示如下信息：
                       %Time domain data set with 101 samples.
                       %Sampling interval: 1                   
                       %                                      
                       %Outputs      Unit (if specified)       
                       %  y1                                  
n=ar(x,3,'yw');%Question3：这样就把模型建立好了吗？
                       %这一步在matlab命令窗口执行后显示如下信息：
                       %Discrete-time IDPOLY model: A(q)y(t) = e(t)                    
                       %A(q) = 1 - 1.02 q^-1 + 0.07993 q^-2 + 0.1174 q^-3 - 0.1629 q^-4
                       %                                                               
                       %Estimated using AR ('yw'/'ppw') from data set x                
                       %Loss function 3.08869e+008 and FPE 3.32402e+008                
                       %Sampling interval: 1 
compare(n,m,1);%向前预测一个时间单位的数据。