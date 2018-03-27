% This program test the degree of fitting of test data curve and predicted data.
%function degree_of_fitting_test(W,History)

k=15;

w=W(k,:);
data=History(:,k)
result=data(1:3);

for i=1:17
    prediction=round(w(1)*data(i)+w(2)*data(i+1)+w(3)*data(i+2));
    result=[result;prediction];
end
result
plot(data)
hold on
plot(result)

