% Author:Jianli.Panlin
% Matlab version:2009A
%%AR(1)ģ�ͽ�ģʾ��������AR(1)�򵥹�û�и��ӵĽ�ģ���̣����Դ��������˽�
%%AR(1)ģ�͵Ĳο���˳��Ҳ������Ϥһ��ϵͳ��ʶ������
clear
%����ģ������
x=2;
for k=1:199
x(k+1)=0.7*x(k)+3*randn(1,1);
end
clear k
%���ף�����ARģ�ͼ����Զ�������
figure
autocorr(x)
figure
parcorr(x)
%ACF��β��PACF1�׽�β��Ӧ����AR(1)ģ��
y=iddata([x,0,0]'); %ת��Ϊϵͳ��ʶ��������ʶ�����������
% Ԥ��Ĺؼ���Ҫ��ԭ�������ϼ�0����k��������Ԥ��T+1��T+K�Ժ������
% �������������۹���http://www.ilovematlab.cn/thread-23681-1-2.html
%������ʱ����û��˵���
AR1=ar(x,1);%���Ʋ�����AR1ͬyһ����һ���ṹ����
p1=predict(y(1:201),AR1,1);%1��Ԥ��
p2=predict(y(1:202),AR1,2);%2��Ԥ��
%���Ԥ�����ݣ�����iddata���͵����ݸ�ʽ�����Կ�help
xp1=p1.OutputData;
xp2=p2.OutputData;
%��ͼ
figure
plot(x,'g')
hold on
plot(xp1,'r')
hold on
plot(xp2,'black')
%���ƾ��������Կ���2��Ԥ��>1��Ԥ��
figure
plot((xp1(1:200)-x').^2,'m')
hold on
plot((xp2(2:200)-x(2:200)').^2,'b')