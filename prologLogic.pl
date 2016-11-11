father(a,b).
father(b,a).
father(b,c).
%% father(b,c).
%% father(z,a).
%% father(a,b).
%% father(b,c).
%% father(z,a).
%% father(a,m,i).
%% father(b,a).
%% father(c,b).
%% father(c,c).
%% father(n,m).
%% father(n,i).
%% father(n,v).
%% father(m,o).
%% father(o,p).
%% father(b,c).
%% father(b,z).
%% father(z,a).
%% father(y,c).
grandfather(X,Y):- father(X,Z), father(Z,Y).

