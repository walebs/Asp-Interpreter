package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;

abstract class AspPrimarySuffix extends AspSyntax {

    AspPrimarySuffix(int n) {
        super(n);
    }

    static AspPrimarySuffix parse(Scanner s) {
        AspPrimarySuffix aps = null;            //TODO: Skal den ikke lagres som en lokal variabel? ^^^ liksom?
        switch (s.curToken().kind) {
            case leftParToken:
                aps = AspArguments.parse(s);    //Flytta denne casen over bare fordi rekkefølge er viktig for meg (ocd-problem asså!)
                break;
            case leftBracketToken:
                aps = AspSubscription.parse(s);
                break;
        default:
            parserError("Expected a primary suffix but found a " + s.curToken().kind + "!", s.curLineNum());
        }
        return aps;
    }
}
