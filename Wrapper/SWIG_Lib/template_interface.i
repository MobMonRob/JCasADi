%include <swiginterface.i> 

/*
// Use after the defintion of the template and before dependencies.
// Example:

%include "template_interface.i"

%{
template <typename T>
class Asdf {
	public:
	T hello() {return (T) 5;}
};

template class Asdf<int>;

template <typename K>
class Xyz : public Asdf<K> {

};

%}

template <typename T>
class Asdf {
	public:
	T hello();
};

template <typename K>
class Xyz : public Asdf<K> {

};

%template_interface("Asdf", Asdf<int>)

%template(Xyz) Xyz<int>;
*/

%define %template_interface(NAME, CTYPE...)
%feature("interface", name="I"##NAME) CTYPE;
INTERFACE_TYPEMAPS(CTYPE)
%template(NAME) CTYPE;
%enddef
