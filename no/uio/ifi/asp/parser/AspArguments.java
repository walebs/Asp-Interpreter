package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspArguments extends AspPrimarySuffix {
    ArrayList<AspExpr> exprs = new ArrayList<>();

    AspArguments(int n) {
        super(n);
    }
    
    static AspArguments parse(Scanner s) {
        enterParser("arguments");
        AspArguments aa = new AspArguments(s.curLineNum());

        skip(s, leftParToken);
        if (s.curToken().kind != rightParToken) {
            while (true) {
                aa.exprs.add(AspExpr.parse(s));
                if (s.curToken().kind == rightParToken) break;
                skip(s, commaToken);
            }
        }
        skip(s, rightParToken);
        
        leaveParser("arguments");
        return aa;
    }

    @Override
    void prettyPrint() {
        prettyWrite("(");
        if (!exprs.isEmpty()) {
            for (int i = 0; i < exprs.size(); i++) {
                exprs.get(i).prettyPrint();
                if (i+1 < exprs.size()) {
                    prettyWrite(", ");
                }
            }
        }
        prettyWrite(")");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        ArrayList<RuntimeValue> value = new ArrayList<>();
        for (int i = 0; i < exprs.size(); i++) {
            value.add(exprs.get(i).eval(curScope));
        }
        return new RuntimeListValue(value);
    }
}
