father(a,b).
father(b,c).
grandfather(X,Y):- father(X,Z), father(Z,Y).

