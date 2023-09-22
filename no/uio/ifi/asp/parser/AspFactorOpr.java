package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspFactorOpr extends AspSyntax {
    //TODO lagre hvilken factorOpr som blir brukt?

    AspFactorOpr(int n) {
        super(n);
    }

    static AspFactorOpr parse(Scanner s, TokenKind token) {
        enterParser("factor opr");
        AspFactorOpr afo = new AspFactorOpr(s.curLineNum());
        skip(s, token);
        leaveParser("factor opr");
        return afo;
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
