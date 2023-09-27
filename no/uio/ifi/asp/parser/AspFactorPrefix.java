package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspFactorPrefix extends AspSyntax {
    String value;
    
    AspFactorPrefix(int n) {
        super(n);
    }

    static AspFactorPrefix parse(Scanner s, TokenKind token) {
        enterParser("factor prefix");
        AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());
        afp.value = token.toString();
        skip(s, token);
        leaveParser("factor prefix");
        return afp;
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
