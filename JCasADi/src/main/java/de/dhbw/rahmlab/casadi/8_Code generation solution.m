import casadi.*

%% Ex 1.1
x = SX.sym('x', 2);
y = SX.sym('y');

f = Function('f', {x, y}, {sqrt(x)-y});

f.generate('fun');

%% Ex 1.2
f.generate('fun2',struct('with_header',true));

%% Ex 1.3
x = MX.sym('x', 2);
y = MX.sym('y');

f = Function('f', {x, y}, {sqrt(x)-y});

f.generate('fun3',struct('with_header',true));

% The function body (casadi_f0) changed a lot
% f_work changed

%% Ex 2.1

qp = struct;
qp.h = Sparsity.dense(2, 2);
qp.a = Sparsity.dense(1,2);
f = conic('f','qrqp',qp);
f.generate('fun21',struct('with_header',true));

f('h',[1,0.3;0.3,7],'g',[2;3],'a',[1;2],'lba',-1,'uba',1)

%% Ex 2.2
f = conic('f','osqp',qp);
f.generate('fun22',struct('with_header',true));

fileID = fopen('common.sh','w');
fprintf(fileID,'LIBDIR=''%s''\n',GlobalOptions.getCasadiPath());
fprintf(fileID,'INCDIR=''%s''\n',GlobalOptions.getCasadiIncludePath());
fclose(fileID);

fileID = fopen('common.bat','w');
fprintf(fileID,'set LIBDIR=%s\n',GlobalOptions.getCasadiPath());
fprintf(fileID,'set INCDIR=%s\n',GlobalOptions.getCasadiIncludePath());
fclose(fileID);

