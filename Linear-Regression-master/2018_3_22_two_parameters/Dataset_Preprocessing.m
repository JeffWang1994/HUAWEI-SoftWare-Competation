% Dataset Preprocessing

load('TrainData_History.mat');
Data_Preprocessed=[];

for k=1:15
Data=History(:,k);
[m,n]=size(Data);

% achieve average = 0
average=sum(Data)/m;
Data=Data-average;

% achieve variance = 1
variance=var(Data);
Data=Data/variance;

Data_Preprocessed=[Data_Preprocessed,Data]

end
