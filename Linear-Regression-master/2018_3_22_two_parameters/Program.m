clear; close all; clc;

% read the training data
load('TrainData_History.mat');
data = History;
y_Prediction=[];
Y_Prediction=[];
Y_real=[];
W=[];
% initialize Matrices and Variables
for i=1:15
    x1=History(3:18,i);
    x2=History(2:17,i);      % 仅考虑单参数情况
    x3=History(1:16,i);
%    x4=History(4:15,i);
%    x5=History(3:14,i);
%    x6=History(2:13,i);
%    x7=History(1:12,i);      % featue matrix

y=History(4:19,i);    % results matrix
m = length(y);      % number of training examples
w = zeros(3, 1);     % initial weights
iterations = 150000;  % Iterations needed for Gradient Descent
alpha = 0.0003;       % Learning Rate

%{ Plot the Data
%plot(X, y, 'rx', 'MarkerSize', 10);
%title('Training Examples');
%xlabel('Population in 10,000');
%ylabel('Profit in $10,000');


% Compute the Cost Function
X=[x1,x2,x3];
J = ComputeCost(X, y, w);

% Run Gradient Descent
[w, Js] = GradientDescent(X, y, w, alpha, iterations);
w
w(isnan(w)) = 0;   % 思考为什么w的值会算出为NaN，过拟合问题？
W=[W;w'];           % 通过增加迭代次数和减小学习率，可以避免w为NaN的情况，猜测是欠拟合

x_test=History(17:19,i);
y=x_test'*w
y_Prediction=[round(y_Prediction);y];
Y_Prediction=[ Y_Prediction;y_Prediction'];
y_real=History(20,:);
Y_real=[Y_real;y_real];
end

% calculate success rate
pingfang=0;
pingfang_real=0;
pingfang_prediction=0;
for i=1:15
    pingfang=pingfang+(y_real(i)-y_Prediction(i))^2;
    pingfang_real=pingfang_real+(y_real(i))^2;
    pingfang_prediction=pingfang_prediction+(y_Prediction(i))^2;
end
fenzi=sqrt(pingfang/15);
fenmu=sqrt(pingfang_real/15)+sqrt(pingfang_prediction/15);
Success_rate=1-fenzi/fenmu


