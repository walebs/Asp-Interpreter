package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspFactor extends AspSyntax {
    ArrayList<AspFactorPrefix> factorPrefs = new ArrayList<>();
    ArrayList<AspPrimary> primary = new ArrayList<>();
    ArrayList<AspFactorOpr> factorOprs = new ArrayList<>();

    AspFactor(int n) {
        super(n);
    }

    static AspFactor parse(Scanner s) {
        enterParser("factor");
        AspFactor af = new AspFactor(s.curLineNum());

        while (true) {
            if (s.isFactorPrefix()) af.factorPrefs.add(AspFactorPrefix.parse(s, s.curToken().kind));
            else af.factorPrefs.add(null);
            af.primary.add(AspPrimary.parse(s));
            if (s.isFactorOpr()) af.factorOprs.add(AspFactorOpr.parse(s, s.curToken().kind));
            else {
                af.factorOprs.add(null); 
                break;
            }
        }

        leaveParser("factor");
        return af;
    }

    @Override
    void prettyPrint() {
        for (int i = 0; i < primary.size(); i++) {
            if (factorPrefs.get(i) != null) factorPrefs.get(i).prettyPrint();
            primary.get(i).prettyPrint();
            if (factorOprs.get(i) != null) factorOprs.get(i).prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = primary.get(0).eval(curScope);
        if (factorPrefs.get(0) != null) {
            String ss = factorPrefs.get(0).value;
            switch(ss) {
                case "+":
                    v = v.evalPositive(primary.get(0)); break;
                case "-":
                    v = v.evalNegate(primary.get(0)); break;
                default:
                    Main.panic("Illegal factor prefix, 1: " + ss + "!");
            }
        }

        for (int i = 1; i < primary.size(); i++) {
            RuntimeValue v2 = primary.get(i).eval(curScope);
            if (factorPrefs.get(i) != null) {
                String k = factorPrefs.get(i).value;
                switch(k) {
                    case "+":
                        v2 = v2.evalPositive(this); break;
                    case "-":
                        v2 = v2.evalNegate(this); break;
                    default:
                        Main.panic("Illegal factor prefix, 2: " + k + "!");
                }
            }

            if (factorOprs.get(i-1) != null) {
                String s = factorOprs.get(i-1).value;
                switch(s) {
                    case "*":
                        v = v.evalMultiply(v2, this); break;
                    case "//":
                        v = v.evalIntDivide(v2, this); break;
                    case "/":
                        v = v.evalDivide(v2, this); break;
                    case "%":
                        v = v.evalModulo(v2, this); break;
                    default:
                        Main.panic("Illegal factor operator: " + s + "!");
                }
            }            
        }
        return v;
    }
}
