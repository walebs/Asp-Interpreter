package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;

public class AspExprStmt extends AspSmallStmt {
    static AspExpr expr;

    AspExprStmt(int n) {
        super(n);
    }

    public static AspSmallStmt parse(Scanner s) {
        enterParser("expr stmt");

        AspExprStmt aes = new AspExprStmt(s.curLineNum());
        expr = AspExpr.parse(s);

        leaveParser("expr stmt");
        return aes;
    }

}
