package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspTerm extends AspSyntax {
    ArrayList<AspFactor> factors = new ArrayList<>();
    ArrayList<AspTermOpr> termOpr = new ArrayList<>();

    AspTerm(int n) {
        super(n);
    }

    static AspTerm parse(Scanner s) {
        enterParser("term");
        AspTerm at = new AspTerm(s.curLineNum());
        
        while (true) {
            at.factors.add(AspFactor.parse(s));
            if (s.isTermOpr()) {
                at.termOpr.add(AspTermOpr.parse(s, s.curToken().kind));
            } else break;
        }

        leaveParser("term");
        return at;
    }

    @Override
    void prettyPrint() {
        for (int i = 0; i < factors.size(); i++) {
            factors.get(i).prettyPrint();
            if (i+1 < factors.size()) termOpr.get(i).prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = factors.get(0).eval(curScope);
        for (int i = 1; i < factors.size(); i++) {
            String k = termOpr.get(i-1).value;   //TODO: Usikkert om dette er et problem
            switch (k) {
                case "-":
                    v = v.evalSubtract(factors.get(i).eval(curScope), this); break;
                case "+":
                    v = v.evalAdd(factors.get(i).eval(curScope), this); break;
                default:
                    Main.panic("Illegal term operator: " + k + "!");
            }
        }
        return v;
    }
}
