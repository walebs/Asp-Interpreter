package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspForStmt extends AspCompoundStmt {
    static AspName name = null;
    static AspExpr expr = null;
    static AspSuite suite = null;

    AspForStmt(int n) {
        super(n);
        //TODO Auto-generated constructor stub
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

    static AspForStmt parse(Scanner s) {
        enterParser("for stmt");
        AspForStmt afs = new AspForStmt(s.curLineNum());

        skip(s, forToken);
        name = AspName.parse(s);
        skip(s, inToken);
        expr = AspExpr.parse(s);
        skip(s, colonToken);
        suite = AspSuite.parse(s);

        leaveParser("for stmt");
        return afs;
    }
    
}
