package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspAndTest extends AspSyntax {
    ArrayList<AspNotTest> notTests = new ArrayList<>();

    AspAndTest(int n) {
        super(n);
    }

    static AspAndTest parse(Scanner s) {
        enterParser("and test");
        AspAndTest aat = new AspAndTest(s.curLineNum());
        
        while (true) {
            aat.notTests.add(AspNotTest.parse(s));
            if (s.curToken().kind != andToken) break;
            skip(s, andToken);
        }

        leaveParser("and test");
        return aat;
    }
    
    @Override
    public void prettyPrint() {
        // TODO riktig?
        /* int nPrinted = 0;
        for (AspNotTest ant : notTests) {
            if (nPrinted > 0) prettyWrite("and");
            ant.prettyPrint();
            ++nPrinted;
        } */
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO do
        return null;
    }
}