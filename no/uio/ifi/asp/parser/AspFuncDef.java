package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspFuncDef extends AspCompoundStmt {
    AspName name;
    AspSuite suite;
    ArrayList<AspName> nameList = new ArrayList<>();

    AspFuncDef(int n) {
        super(n);
    }

    static AspFuncDef parse(Scanner s) {
        enterParser("func def");

        AspFuncDef afs = new AspFuncDef(s.curLineNum());
        skip(s, defToken);
        afs.name = AspName.parse(s);
        skip(s, leftParToken);
        while (s.curToken().kind != rightParToken) {
            afs.nameList.add(AspName.parse(s));
            if (s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }
        skip(s, rightParToken);
        skip(s, colonToken);
        afs.suite = AspSuite.parse(s);

        leaveParser("func def");
        return afs;
    }

    @Override
    void prettyPrint() {
        prettyWrite("def ");
        name.prettyPrint();
        prettyWrite(" (");
        if (!nameList.isEmpty()) {
           for (int i = 0; i < nameList.size(); i++) {
            nameList.get(i).prettyPrint();

            if (i+1 < nameList.size()) prettyWrite(", ");
           }
        }
        prettyWrite(")");
        prettyWrite(": ");
        suite.prettyPrint();
        prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        return null;
    }
}
