
new_flavor=flavor(1331:1461);
History2=[];
for i=1:15
    [m,n]=size(find(new_flavor==i));
    History2=[History2,m];
end
History=[History;History2]
