// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspProgram extends AspSyntax {
    ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
	    super(n);
    }


    public static AspProgram parse(Scanner s) {
        enterParser("program");

        AspProgram ap = new AspProgram(s.curLineNum());
        while (s.curToken().kind != eofToken) {
            ap.stmts.add(AspStmt.parse(s));
        }
        skip(s, eofToken);

        leaveParser("program");
        return ap;
    }


    @Override
    public void prettyPrint() {
	    for (int i = 0; i < stmts.size(); i++) {
            stmts.get(i).prettyPrint();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        for (AspStmt as : stmts) {
            try {
                as.eval(curScope);
            } catch (RuntimeReturnValue rrv) {
                RuntimeValue.runtimeError("Return statements outside function!", rrv.lineNum);
            }
        }
        return null;
    }
}
