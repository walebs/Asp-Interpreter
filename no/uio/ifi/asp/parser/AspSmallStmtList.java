package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspSmallStmtList extends AspStmt {
    ArrayList<AspSmallStmt> smallStmts = new ArrayList<>();

    AspSmallStmtList(int n) {
        super(n);
    }

    static AspSmallStmtList parse(Scanner s) {
        enterParser("small stmt list");
        AspSmallStmtList assl = new AspSmallStmtList(s.curLineNum());

        while (true) {
            assl.smallStmts.add(AspSmallStmt.parse(s));
            if (s.curToken().kind == newLineToken) break;
            skip(s, semicolonToken);
            if (s.curToken().kind == newLineToken) break;
        }
        skip(s, newLineToken);

        leaveParser("small stmt list");
        return assl;
    }

    @Override
    void prettyPrint() {
        for (int i = 0; i < smallStmts.size(); i++) {
            smallStmts.get(i).prettyPrint();
            if (i+1 < smallStmts.size()) prettyWrite("; ");
        }
        prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        for (AspSmallStmt ass : smallStmts) ass.eval(curScope);
        //trace("small stmt list");
        return null;
    }
}
