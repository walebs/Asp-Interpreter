package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspName extends AspAtom {
    String value;

    AspName(int s) {
        super(s);
        // TODO: Auto-generated 
    }

    static AspName parse(Scanner s) {
        enterParser("name");
        AspName an = new AspName(s.curLineNum());
        an.value = s.curToken().name;
        skip(s, nameToken);
        leaveParser("name");
        return an;
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
