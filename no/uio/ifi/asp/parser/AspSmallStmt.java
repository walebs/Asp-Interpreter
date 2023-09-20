package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspSmallStmt extends AspSyntax {

    AspSmallStmt(int n) {
        super(n);
    }

    static AspSmallStmt parse(Scanner s) {
        AspSmallStmt ass = null;
        switch (s.curToken().kind) {
            case returnToken:
                ass = AspReturnStmt.parse(s);
                break;
            case globalToken:
                ass = AspGlobalStmt.parse(s);
                break;
            case passToken:
                ass = AspPassStmt.parse(s);
                break;

        // TODO må legge til flere, må ha med assignment og expr stmt
        default:
            parserError("Expected an expression atom but found a " + s.curToken().kind + "!", s.curLineNum());
        }

        return ass;
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