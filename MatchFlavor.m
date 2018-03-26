%function [history_flavor_format]=MatchFlavor(flavor_format,Flavor)

[m,n]=size(Flavor);
cpu_history=[];

for i=1:m
    num=find(flavor_format(:,1)==Flavor(i));
    history_flavor_format=[Flavor(i),flavor_format(num,2),flavor_format(num,3)];
end

