// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
	    // len
        assign("len", new RuntimeFunc("len") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "len", where);
                return actualParams.get(0).evalLen(where);
            }
        });
    
        // print
        assign("print", new RuntimeFunc("print") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                for (int i = 0; i < actualParams.size(); ++i) {
                    if (i > 0) System.out.print(" ");
                    System.out.print(actualParams.get(i).toString());
                }
                System.out.println();
                return new RuntimeNoneValue();
            }
        });
        
        // float
        assign("float", new RuntimeFunc("float") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "float", where);
                if (actualParams.get(0) instanceof RuntimeIntValue) {
                    double val = (double) actualParams.get(0).getIntValue("", where);
                    return new RuntimeFloatValue(val);
                }
                else if (actualParams.get(0) instanceof RuntimeStringValue) {
                    double val = Double.parseDouble(actualParams.get(0).getStringValue("", where));
                    return new RuntimeFloatValue(val);
                }
                else return actualParams.get(0);
            }
        });

        // int
        assign("int", new RuntimeFunc("int") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                //Samme som float
                checkNumParams(actualParams, 1, "int", where);
                if (actualParams.get(0) instanceof RuntimeFloatValue) {
                    long val = (long) actualParams.get(0).getFloatValue("", where);
                    return new RuntimeIntValue(val);
                }
                else if (actualParams.get(0) instanceof RuntimeStringValue) {
                    long val = Long.parseLong(actualParams.get(0).getStringValue("", where));
                    return new RuntimeIntValue(val);
                }
                else return actualParams.get(0);
            }
        });

        // str
        assign("str", new RuntimeFunc("str") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "string", where);
                return new RuntimeStringValue(actualParams.get(0).toString());
            }
        });
        
        // input
        assign("input", new RuntimeFunc("input") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "input", where);
                System.out.println(actualParams.get(0).toString());
                String result = keyboard.nextLine();
                return new RuntimeStringValue(result);
            }
        });

        // range
        assign("range", new RuntimeFunc("range") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                if (actualParams.size() == 1) {
                    ArrayList<RuntimeValue> values = new ArrayList<>();
                    for (long i = 0; i < actualParams.get(0).getIntValue("", where); i++) {
                        values.add(new RuntimeIntValue(i));
                    }
                    return new RuntimeListValue(values);
                }
                else if (actualParams.size() == 2) {
                    ArrayList<RuntimeValue> values = new ArrayList<>();
                    for (long i = actualParams.get(0).getIntValue("", where); i < actualParams.get(1).getIntValue("", where); i++) {
                        values.add(new RuntimeIntValue(i));
                    }
                    return new RuntimeListValue(values);
                }
                else {
                    RuntimeValue.runtimeError("Wrong number of parameters to range!",where);
                }
                return new RuntimeNoneValue();
            }
        });
    }

    private void checkNumParams(ArrayList<RuntimeValue> actArgs, int nCorrect, String id, AspSyntax where) {
        if (actArgs.size() != nCorrect) RuntimeValue.runtimeError("Wrong number of parameters to "+id+"!",where);
    }
}
