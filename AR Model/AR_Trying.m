%x=load('D:\Program Files\Github\HUAWEI-SoftWare-Competation\AR Model\traffic.txt');
load TrainData_History.mat;

x=History(:,8);

y=aryule(x,3);%����4��ģ�ͣ����  y[n] = a1y[n-1] + a2y[n-2] + .....+ap[yn-p]   ��Ԥ��ϵ��a��p��
                       %��һ����matlab�����ִ�к���ʾ������Ϣ��
                       %y =1.0000   -1.0202    0.0799    0.1174   -0.1629
z=idpoly([y],[]);%�Ѷ���ʽת����ģ��
                       %��һ����matlab�����ִ�к���ʾ������Ϣ��
                       %Discrete-time IDPOLY model: A(q)y(t) = e(t)                    
                       %A(q) = 1 - 1.02 q^-1 + 0.07993 q^-2 + 0.1174 q^-3 - 0.1629 q^-4   
                       %This model was not estimated from data. ��Question1������ʲô��˼���ѵ��ҵ�����û���õ��𣿣�                 
                       %Sampling interval: 1 
m=iddata([x],[]);%Question2����һ����ʲô�ô���
                       %��һ����matlab�����ִ�к���ʾ������Ϣ��
                       %Time domain data set with 101 samples.
                       %Sampling interval: 1                   
                       %                                      
                       %Outputs      Unit (if specified)       
                       %  y1                                  
n=ar(x,3,'yw');%Question3�������Ͱ�ģ�ͽ���������
                       %��һ����matlab�����ִ�к���ʾ������Ϣ��
                       %Discrete-time IDPOLY model: A(q)y(t) = e(t)                    
                       %A(q) = 1 - 1.02 q^-1 + 0.07993 q^-2 + 0.1174 q^-3 - 0.1629 q^-4
                       %                                                               
                       %Estimated using AR ('yw'/'ppw') from data set x                
                       %Loss function 3.08869e+008 and FPE 3.32402e+008                
                       %Sampling interval: 1 
compare(n,m,1);%��ǰԤ��һ��ʱ�䵥λ�����ݡ�