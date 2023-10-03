// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.scanner.*;

abstract class AspStmt extends AspSyntax {
    
    AspStmt(int n) {
	    super(n);
    }

    static AspStmt parse(Scanner s) {
        enterParser("stmt");
        AspStmt as = null;
        switch (s.curToken().kind) {
            case nameToken:
            case globalToken:
            case passToken:
            case returnToken:
            case notToken:
            case plusToken:
            case minusToken:
            case integerToken:
            case floatToken:
            case stringToken:
            case trueToken:
            case falseToken:
            case noneToken:
            case leftParToken:
            case leftBracketToken:
            case leftBraceToken:
                as = AspSmallStmtList.parse(s);
                break;
            case ifToken:
            case defToken:
            case forToken:
            case whileToken:
                as = AspCompoundStmt.parse(s);
                break;
        default:
            parserError("Expected a stmt but found a " + s.curToken().kind + "!", s.curLineNum());
        }
        leaveParser("stmt");
        return as;
    }
}
