// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private Stack<Integer> indents = new Stack<>();
    private final int TABDIST = 4;


    public Scanner(String fileName) {
		curFileName = fileName;
		indents.push(0);

		try {
			sourceFile = new LineNumberReader(
					new InputStreamReader(
					new FileInputStream(fileName),
					"UTF-8"));
		} catch (IOException e) {
			scannerError("Cannot read " + fileName + "!");
		}
    }


    private void scannerError(String message) {
		String m = "Asp scanner error";
		if (curLineNum() > 0)
			m += " on line " + curLineNum();
		m += ": " + message;

		Main.error(m);
    }


    public Token curToken() {
		while (curLineTokens.isEmpty()) {
			readNextLine();
		}
		return curLineTokens.get(0);
    }


    public void readNextToken() {
		if (! curLineTokens.isEmpty())
			curLineTokens.remove(0);
    }


    private void readNextLine() {
		curLineTokens.clear();

		// Read the next line:
		String line = null;
		try {
			line = sourceFile.readLine();
			if (line == null) {
			sourceFile.close();
			sourceFile = null;
			} else {
			Main.log.noteSourceLine(curLineNum(), line);
			}
		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");
		}

		//-- Must be changed in part 1:

		//Innledende TAB-er oversettes til blanke.
		int numberOfSpacesAtStart = tabToBlanks(line);
		boolean isEmpty = false;			//antar at hver linje inneholder noe men endrer hvis ikke
		int lastLineNumOfIndents = 0;		//legge til en variabel som holder på forrige linjes antall indents

		//Hvis linjen er tom (eventuelt blanke), ignoreres den.
		if (line.length() <= numberOfSpacesAtStart) {
			isEmpty = true;
		}
		
		//Hvis linjen bare inneholder en kommentar (dvs førsteikke-blanke tegn er en ’#’), ignoreres den.
		if (line.charAt(numberOfSpacesAtStart - 1) == '#') {
			isEmpty = true;
		}
		
		//Indentering beregnes, og INDENT/DEDENT-er legges i curLineTokens.
		int indents = findIndent(line);
		if (indents > lastLineNumOfIndents) {
			//hvis linjen har mer indent enn forrige
			curLineTokens.add(new Token(indentToken, curLineNum()));
		} else if (indents > lastLineNumOfIndents) {
			//hvis det er siste linje i filen
		} else {
			//hvis linjen har færre indent enn forrige
			curLineTokens.add(new Token(dedentToken, curLineNum()));
		}

		lastLineNumOfIndents = indents;

		//Gå gjennom linjen:
			//Blanke tegn og TAB-er ignoreres.
			//En ’#’ angir at resten av linjen skal ignoreres.
			//Andre tegn angir starten på et nytt symbol. Finn ut hvor mange tegn som inngår i symbolet. Lag et Token-objekt og legg det i curLineTokens.

		// Terminate line:
		curLineTokens.add(new Token(newLineToken,curLineNum()));
		isEmpty = false;

		for (Token t: curLineTokens) 
			Main.log.noteToken(t);
    }

	public int tabToBlanks(String s) {
		int n = 0;

		while (n < s.length() && (s.charAt(n) == ' ' || s.charAt(n) == '\t')) {
			if (s.charAt(n) == '\t') {
				int pluss = 4 - (n % 4);
				n = pluss;
				System.out.println("pluss " + pluss);
			} else {
				n++;
			}
		}
		return n;
	}

    public int curLineNum() {
		return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
		int indent = 0;

		while (indent<s.length() && s.charAt(indent)==' ') indent++;
		return indent;
    }

    private String expandLeadingTabs(String s) {
		//-- Must be changed in part 1:
		return null;
    }


    private boolean isLetterAZ(char c) {
		return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
		return '0'<=c && c<='9';
    }


    public boolean isCompOpr() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }


    public boolean isFactorPrefix() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }


    public boolean isFactorOpr() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }
	

    public boolean isTermOpr() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }


    public boolean anyEqualToken() {
		for (Token t: curLineTokens) {
			if (t.kind == equalToken) return true;
			if (t.kind == semicolonToken) return false;
		}
		return false;
    }
}
