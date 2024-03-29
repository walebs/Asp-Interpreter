package no.uio.ifi.asp.parser;

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
            case integerToken:
            case floatToken:
            case stringToken:
            case falseToken:
            case trueToken:
            case noneToken:
            case leftBraceToken:
            case leftBracketToken:
            case leftParToken:
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
            case nameToken:
                if (s.anyEqualToken()) ass = AspAssignment.parse(s);
                else ass = AspExprStmt.parse(s);
                break;
        default:
            parserError("Expected a small stmt but found a " + s.curToken().kind + "!", s.curLineNum());
        }
        leaveParser("small stmt");
        return ass;
    }
}
