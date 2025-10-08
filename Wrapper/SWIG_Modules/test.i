%module test__;

%include <swiginterface.i>

%interface(Base)

%inline %{

class Base
{
public:
    static int baseFunc() {return 42;}
};

class Derived : public Base
{
};

%}

