package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspSuite extends AspSyntax {
    static AspSmallStmtList assl;
    ArrayList<AspStmt> stmts = new ArrayList<>();

    AspSuite(int n) {
        super(n);
    }

    static AspSuite parse(Scanner s) {
        enterParser("suite");

        AspSuite as = new AspSuite(s.curLineNum());
        if (s.curToken().kind != newLineToken) {
            assl = new AspSmallStmtList(s.curLineNum());
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
        // TODO Auto-generated method stub
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        return null;
    }
    
}
