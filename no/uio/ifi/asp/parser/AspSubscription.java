package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSubscription extends AspPrimarySuffix {
    static AspExpr expr;

    AspSubscription(int n) {
        super(n);
    }

    static AspSubscription parse(Scanner s) {
        enterParser("subscription");
        AspSubscription as = new AspSubscription(s.curLineNum());
        skip(s, leftBracketToken);
        expr = AspExpr.parse(s);
        skip(s, rightBracketToken);
        leaveParser("subscription");
        return as;
    }

    @Override
    void prettyPrint() {
        // TODO Auto-generated method stub
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        return null;
    }

}
