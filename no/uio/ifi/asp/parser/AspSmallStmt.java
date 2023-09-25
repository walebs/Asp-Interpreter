package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.*;

abstract class AspSmallStmt extends AspSyntax {

    AspSmallStmt(int n) {
        super(n);
    }

    static AspSmallStmt parse(Scanner s) {
        enterParser("small stmt");
        AspSmallStmt ass = null;
        switch (s.curToken().kind) {
            case notToken:
            case plusToken:
            case minusToken:
            // TODO hvordan funker det når både assignment og expr har name token i seg?
            case integerToken:
            case floatToken:
            case stringToken:
            case falseToken:
            case trueToken:
            case noneToken:
            case leftBraceToken:
            case leftBracketToken:
            case leftParToken:
            case nameToken:
                ass = AspExprStmt.parse(s);
                break;
            case returnToken:
                ass = AspReturnStmt.parse(s);
                break;
            case globalToken:
                ass = AspGlobalStmt.parse(s);
                break;
            case passToken:
                ass = AspPassStmt.parse(s);
                break;
            /* case nameToken:
                ass = AspAssignment.parse(s);
                break; */
        default:
            parserError("Expected a small stmt but found a " + s.curToken().kind + "!", s.curLineNum());
        }
        leaveParser("small stmt");
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
