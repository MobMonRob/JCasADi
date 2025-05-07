package de.dhbw.rahmlab.casadi.api.core.wrapper.generictype;

import de.dhbw.rahmlab.casadi.api.core.wrapper.bool.BooleanVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.DoubleVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.DoubleVectorCollection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.integer.IntegerVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.numeric.NumberWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.str.StringVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.casadi.TypeID;

public class CasADiGenericWrapper {

    private final GenericType genericType;

    public CasADiGenericWrapper() {
        this.genericType = new GenericType();
    }

    public CasADiGenericWrapper(GenericType other) {
        this.genericType = new GenericType(other);
    }

    public CasADiGenericWrapper(CasADiGenericWrapper other) {
        this.genericType = new GenericType(other.getCasADiObject());
    }

    public CasADiGenericWrapper(boolean b) {
        this.genericType = new GenericType(b);
    }

    public CasADiGenericWrapper(long i) {
        this.genericType = new GenericType(i);
    }

    public CasADiGenericWrapper(int i) {
        this.genericType = new GenericType(i);
    }

    public CasADiGenericWrapper(double d) {
        this.genericType = new GenericType(d);
    }

    public CasADiGenericWrapper(String s) {
        this.genericType = new GenericType(s);
    }

    public CasADiGenericWrapper(BooleanVector iv) {
        this.genericType = new GenericType(iv.getCasADiObject());
    }

    public CasADiGenericWrapper(IntegerVector iv) {
        this.genericType = new GenericType(iv.getCasADiObject());
    }

    public CasADiGenericWrapper(DoubleVector dv) {
        this.genericType = new GenericType();
    }

    public CasADiGenericWrapper(DoubleVectorCollection dv) {
        this.genericType = new GenericType(dv.getCasADiObject());
    }

    public CasADiGenericWrapper(StringVector sv) {
        this.genericType = new GenericType(sv.getCasADiObject());
    }

    public CasADiGenericWrapper(FunctionWrapper f) {
        this.genericType = new GenericType(f.getCasADiObject());
    }

    public CasADiGenericWrapper(FunctionVector f) {
        this.genericType = new GenericType(f.getCasADiObject());
    }

    public CasADiGenericWrapper(Dictionary dict) {
        this.genericType = new GenericType(dict.getCasADiObject());
    }

    /**
     * Constructor for NumberWrapper Objects. Takes the double value of the wrapper
     *
     * @param wrapper
     */
    public CasADiGenericWrapper(NumberWrapper wrapper) {
        this.genericType = new GenericType(wrapper.getDoubleValue());
    }


    public CasADiGenericWrapper(NumberWrapper wrapper, boolean isInteger) {
        if (isInteger) {
            this.genericType = new GenericType(wrapper.getIntValue());
        } else {
            this.genericType = new GenericType(wrapper.getLongValue());
        }
    }

    public String getDescription() {
        return this.genericType.get_description();
    }

    public boolean canCastTo(TypeID other) {
        return this.genericType.can_cast_to(other);
    }

    public boolean canCastTo(CasADiGenericWrapper other) {
        return this.genericType.can_cast_to(other.getCasADiObject());
    }

    public TypeID getType() {
        return this.genericType.getType();
    }

    public boolean isBoolean() {
        return this.genericType.is_bool();
    }

    public boolean isInteger() {
        return this.genericType.is_int();
    }

    public boolean isDouble() {
        return this.genericType.is_double();
    }

    public boolean isString() {
        return this.genericType.is_string();
    }

    public boolean isEmptyVector() {
        return this.genericType.is_empty_vector();
    }

    public boolean isIntegerVector() {
        return this.genericType.is_int_vector();
    }

    public boolean isIntegerVectorCollection() {
        return this.genericType.is_int_vector_vector();
    }

    public boolean isDoubleVector() {
        return this.genericType.is_double_vector();
    }

    public boolean isDoubleVectorCollection() {
        return this.genericType.is_double_vector_vector();
    }

    public boolean isBooleanVector() {
        return this.genericType.is_bool_vector();
    }

    public boolean isStringVector() {
        return this.genericType.is_string_vector();
    }

    public boolean isDict() {
        return this.genericType.is_dict();
    }

    public boolean isFunction() {
        return this.genericType.is_function();
    }

    public boolean isFunctionVector() {
        return this.genericType.is_function_vector();
    }

    public boolean isVoidPointer() {
        return this.genericType.is_void_pointer();
    }

    public boolean castToInternalTypeBoolean() {
        return this.genericType.as_bool();
    }

    public long castToInternalTypeInteger() {
        return this.genericType.as_int();
    }

    public double castToInternalTypeDouble() {
        return this.genericType.as_double();
    }

    public NumberWrapper castToInternalTypeNumberWrapper() {
        return new NumberWrapper(this.genericType.as_double());
    }

    public String castToInternalTypeString() {
        return this.genericType.as_string();
    }

    public IntegerVector castToInternalTypeIntegerVector() {
        return new IntegerVector(this.genericType.as_int_vector());
    }

    public IntegerVector castToInternalTypeBooleanVector() {
        return new IntegerVector(this.genericType.as_bool_vector());
    }

    public DoubleVector castToInternalTypeDoubleVector() {
        return new DoubleVector(this.genericType.as_double_vector());
    }

    public DoubleVectorCollection castToInternalTypeDoubleVectorCollection() {
        return new DoubleVectorCollection(this.genericType.as_double_vector_vector());
    }

    public StringVector castToInternalTypeStringVector() {
        return new StringVector(this.genericType.as_string_vector());
    }

    public Dictionary castToInternalTypeDict() {
        return new Dictionary(this.genericType.as_dict());
    }

    public FunctionWrapper castToInternalTypeFunction() {
        return new FunctionWrapper(this.genericType.as_function());
    }

    public FunctionVector castToInternalTypeFunctionVector() {
        return new FunctionVector(this.genericType.as_function_vector());
    }

    public boolean convertToBoolean() {
        return this.genericType.to_bool();
    }

    public long convertToInteger() {
        return this.genericType.to_int();
    }

    public double convertToDouble() {
        return this.genericType.to_double();
    }

    public NumberWrapper convertToNumberWrapper() {
        return new NumberWrapper(this.genericType.to_double());
    }

    public String convertToString() {
        return this.genericType.to_string();
    }

    public IntegerVector convertToIntegerVector() {
        return new IntegerVector(this.genericType.to_int_vector());
    }

    public BooleanVector convertToBooleanVector() {
        return new BooleanVector(this.genericType.to_bool_vector());
    }

    public DoubleVector convertToDoubleVector() {
        return new DoubleVector(this.genericType.to_double_vector());
    }

    public DoubleVectorCollection convertToBooleanVectorCollection() {
        return new DoubleVectorCollection(this.genericType.to_double_vector_vector());
    }

    public StringVector convertToStringVector() {
        return new StringVector(this.genericType.to_string_vector());
    }

    public Dictionary convertToDict() {
        return new Dictionary(this.genericType.to_dict());
    }

    public FunctionWrapper convertToFunctionWrapper() {
        return new FunctionWrapper(this.genericType.to_function());
    }

    public FunctionVector convertToFunctionVector() {
        return new FunctionVector(this.genericType.to_function_vector());
    }

    public DMWrapper convertToDMWrapper() {
        return new DMWrapper(this.genericType.as_double());
    }

    public MXWrapper convertToMXWrapper() {
        return new MXWrapper(this.genericType.as_double());
    }

    public SXWrapper convertToSXWrapper() {
        return new SXWrapper(this.genericType.as_double());
    }

    public String getClassName() {
        return this.genericType.class_name();
    }

    public String toString(boolean more) {
        return this.genericType.toString(more);
    }

    public String toString() {
        return this.genericType.toString();
    }

    public boolean isNull() {
        return this.genericType.is_null();
    }

    public long getHash() {
        return this.genericType.__hash__();
    }

    public GenericType getCasADiObject() {
        return this.genericType;
    }

    public GenericTypeVector toVector() {
        return new GenericTypeVector(this);
    }

}
