package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspReturnStmt extends AspSmallStmt {
    static AspExpr ae = null;

    AspReturnStmt(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspReturnStmt parse(Scanner s) {
        enterParser("return stmt");
        AspReturnStmt ars = new AspReturnStmt(s.curLineNum());
        skip(s, returnToken);
        // TODO gir dette mening?
        ae = AspExpr.parse(s);
        leaveParser("return stmt");
        return ars;
    }
}
