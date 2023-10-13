package no.uio.ifi.asp.runtime;

public class RuntimeIntValue extends RuntimeValue {
    long value;
    
    public RuntimeIntValue(long v) {
        value = v;
    }

    @Override
    String typeName() {
        return "int";
    }


}
