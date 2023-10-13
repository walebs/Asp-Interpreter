package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspComparison extends AspSyntax {
    ArrayList<AspTerm> terms = new ArrayList<>();
    ArrayList<AspCompOpr> compOprs = new ArrayList<>();

    AspComparison(int n) {
        super(n);
    }

    static AspComparison parse(Scanner s) {
        enterParser("comparison");
        AspComparison ac = new AspComparison(s.curLineNum());

        while (true) {
            ac.terms.add(AspTerm.parse(s));
            if (s.isCompOpr()) {
                ac.compOprs.add(AspCompOpr.parse(s, s.curToken().kind));
            } else break;
        }

        leaveParser("comparison");
        return ac;
    }

    @Override
    void prettyPrint() {
        for (int i = 0; i < terms.size(); i++) {
            terms.get(i).prettyPrint();
            if (i+1 < terms.size()) compOprs.get(i).prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = terms.get(0).eval(curScope);
        for (int i = 1; i < terms.size(); i++) {
            String k = compOprs.get(i-1).value;
            switch (k) {
                case "<":
                    v = v.evalLess(terms.get(i).eval(curScope), this); break;
                case ">":
                    v = v.evalGreater(terms.get(i).eval(curScope), this); break;
                case "<=":
                    v = v.evalLessEqual(terms.get(i).eval(curScope), this); break;
                case ">=":
                    v = v.evalGreaterEqual(terms.get(i).eval(curScope), this); break;
                case "==":
                    v = v.evalEqual(terms.get(i).eval(curScope), this); break;
                case "!=":
                    v = v.evalNotEqual(terms.get(i).eval(curScope), this); break;
                default:
                    Main.panic("Illegal term operator: " + k + "!");
            }
        }
        return v;
    }
}
