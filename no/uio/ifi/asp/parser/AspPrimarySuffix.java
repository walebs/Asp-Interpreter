package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;

abstract class AspPrimarySuffix extends AspSyntax {

    AspPrimarySuffix(int n) {
        super(n);
    }

    static AspPrimarySuffix parse(Scanner s) {
        enterParser("primary suffix");
        AspPrimarySuffix aps = null;
        switch (s.curToken().kind) {
            case leftParToken:
                aps = AspArguments.parse(s);
                break;
            case leftBracketToken:
                aps = AspSubscription.parse(s);
                break;
        default:
            parserError("Expected a primary suffix but found a " + s.curToken().kind + "!", s.curLineNum());
        }
        leaveParser("primary suffix");
        return aps;
    }
}
