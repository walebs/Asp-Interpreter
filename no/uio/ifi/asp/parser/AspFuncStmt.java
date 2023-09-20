package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspFuncStmt extends AspCompoundStmt {
    static AspName name = null;
    static AspSuite suite = null;
    ArrayList<AspName> nameList = new ArrayList<>();

    AspFuncStmt(int n) {
        super(n);
    }

    static AspFuncStmt parse(Scanner s) {
        enterParser("fun stmt");

        AspFuncStmt afs = new AspFuncStmt(s.curLineNum());
        skip(s, defToken);
        name = AspName.parse(s);
        skip(s, leftParToken);
        while (s.curToken().kind != rightParToken) {
            afs.nameList.add(AspName.parse(s));
            if (s.curToken().kind == commaToken) {
                skip(s, commaToken);
            }
        }
        skip(s, rightParToken);
        skip(s, colonToken);
        suite = AspSuite.parse(s);

        leaveParser("fun stmt");
        return afs;
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
