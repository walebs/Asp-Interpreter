package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspCompOpr extends AspSyntax {
    String value;

    AspCompOpr(int n) {
        super(n);
    }

    // sender inn hvilket token som er compOpr for å slippe å sjekke igjen
    static AspCompOpr parse(Scanner s, TokenKind token) {
        enterParser("comp opr");
        AspCompOpr aco = new AspCompOpr(s.curLineNum());
        aco.value = token.toString();
        skip(s, token);
        leaveParser("comp opr");
        return aco;
    }

    @Override
    void prettyPrint() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prettyPrint'");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }

}
