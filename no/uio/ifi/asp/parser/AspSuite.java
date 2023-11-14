package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspSuite extends AspSyntax {
    AspSmallStmtList assl;
    ArrayList<AspStmt> stmts = new ArrayList<>();

    AspSuite(int n) {
        super(n);
    }

    static AspSuite parse(Scanner s) {
        enterParser("suite");

        AspSuite as = new AspSuite(s.curLineNum());
        if (s.curToken().kind != newLineToken) {
            as.assl = AspSmallStmtList.parse(s);
        } else {
            skip(s, newLineToken);
            skip(s, indentToken);
            while (s.curToken().kind != dedentToken) {
                as.stmts.add(AspStmt.parse(s));
            }
            skip(s, dedentToken);
        }

        leaveParser("suite");
        return as;
    }

    @Override
    void prettyPrint() {
        if (!stmts.isEmpty()) {
            prettyWriteLn();
            prettyIndent();
            for (int i = 0; i < stmts.size(); i++) {
                stmts.get(i).prettyPrint();
            }
            prettyDedent();
        } else assl.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        if (stmts.isEmpty()) assl.eval(curScope);
        else {
            for (AspStmt as : stmts) as.eval(curScope);
        }
        trace("suite");
        return null;
    }
    
}
