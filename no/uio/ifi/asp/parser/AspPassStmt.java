package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspPassStmt extends AspSmallStmt {

    AspPassStmt(int n) {
        super(n);
    }

    static AspPassStmt parse(Scanner s) {
        enterParser("pass stmt");
        skip(s, passToken);
        leaveParser("pass stmt");
        return new AspPassStmt(s.curLineNum());
    }
}
