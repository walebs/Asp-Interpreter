package no.uio.ifi.asp.runtime;

public class RuntimeFloatValue extends RuntimeValue {
    Double value;

    public RuntimeFloatValue(Double v) {
        value = v;
    }
    
    @Override
    String typeName() {
        return "float";
    }
}
