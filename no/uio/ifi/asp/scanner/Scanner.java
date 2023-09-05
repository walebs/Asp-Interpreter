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

		boolean ignoreLine = false;			//velger om man skal ignorere eller ikke

		//Innledende TAB-er oversettes til blanke.
		String spacesAtStart = expandLeadingTabs(line);

		//Hvis linjen er tom (eventuelt blanke), ignoreres den.
		if (line.length() == spacesAtStart.length()) {
			ignoreLine = true;
		}
		
		//Hvis linjen bare inneholder en kommentar (dvs førsteikke-blanke tegn er en ’#’), ignoreres den.
		if (!ignoreLine && line.strip().charAt(0) == '#') {
			ignoreLine = true;
		}

		//Indentering beregnes, og INDENT/DEDENT-er legges i curLineTokens.
		int indentsOnLine = findIndent(spacesAtStart);

		if (indentsOnLine > indents.peek()) {
			//hvis linjen har mer indent
			indents.push(indents.peek() + 1);
			curLineTokens.add(new Token(indentToken, curLineNum()));
		} else if (indentsOnLine < indents.peek()) {
			//hvis linjen har færre indents
			while (indentsOnLine < indents.peek()) {
				indents.pop();
				curLineTokens.add(new Token(dedentToken, curLineNum()));
			}
		} else if (indentsOnLine != indents.peek()) {
			//TODO error handling 
		}

		//Gå gjennom linjen:		
		int pos = 0;
		while (!ignoreLine && pos < line.length()) {
			char c = line.charAt(pos);
			char cNext = line.charAt(pos++);

			//Blanke tegn og TAB-er ignoreres.
			if (Character.isWhitespace(c) || c == '\t') {
				//Dont do shit
				pos++;
			}
			
			//En ’#’ angir at resten av linjen skal ignoreres.
			else if (c == '#') {
				break;
			} 

			//Andre tegn angir starten på et nytt symbol. Finn ut hvor mange tegn som inngår i symbolet. Lag et Token-objekt og legg det i curLineTokens.
			else if (isDigit(c)) {
				// setter opp en string for å sjekke om det er int eller float
				// les kompendiumet om dette er greit
				String str = "";
				boolean isFloat = false;
				while (isDigit(line.charAt(pos)) || line.charAt(pos) == '.') {
					//siden floats har en . som skiller desimalene så kan vi sjekke om vi finner et ., 
					//og da skille om vi trenger floatToken eller integerToken

					//TODO error handling for å si ifra om det er et ulovlig flyttall
					//TODO må være tall både før og etter .
					if (line.charAt(pos) == '.'){
						isFloat = true;
					}
					
					str += line.charAt(pos);
					pos++;
				}
				
				//sjekker om det er float eller int
				if (isFloat) {
					Token t = new Token(floatToken, curLineNum());
					t.floatLit = Float.parseFloat(str);
					curLineTokens.add(t);
				} else {
					Token t = new Token(integerToken, curLineNum());
					t.integerLit = Integer.parseInt(str);
					curLineTokens.add(t);
				}

			} else if (isLetterAZ(c)) {
				//setter opp for å sjekke en string mot alle mulige keywords
				String str = "";
				while (isLetterAZ(line.charAt(pos))) {
					if (line.charAt(pos) == '$') {
						//TODO error handling ved dette tegnet
					}
					str += line.charAt(pos);
					pos++;
				}

				// Finner riktig keyword til stringen
				if (str.equals("and")) {
					curLineTokens.add(new Token(andToken, curLineNum()));
				} else if (str.equals("def")) {
					curLineTokens.add(new Token(defToken, curLineNum()));
				} else if (str.equals("elif")) {
					curLineTokens.add(new Token(elifToken, curLineNum()));
				} else if (str.equals("else")) {
					curLineTokens.add(new Token(elseToken, curLineNum()));
				} else if (str.equals("False")) {
					curLineTokens.add(new Token(falseToken, curLineNum()));
				} else if (str.equals("for")) {
					curLineTokens.add(new Token(forToken, curLineNum()));
				} else if (str.equals("global")) {
					curLineTokens.add(new Token(globalToken, curLineNum()));
				} else if (str.equals("if")) {
					curLineTokens.add(new Token(ifToken, curLineNum()));
				} else if (str.equals("in")) {
					curLineTokens.add(new Token(inToken, curLineNum()));
				} else if (str.equals("None")) {
					curLineTokens.add(new Token(noneToken, curLineNum()));
				} else if (str.equals("not")) {
					curLineTokens.add(new Token(notToken, curLineNum()));
				} else if (str.equals("or")) {
					curLineTokens.add(new Token(orToken, curLineNum()));
				} else if (str.equals("pass")) {
					curLineTokens.add(new Token(passToken, curLineNum()));
				} else if (str.equals("return")) {
					curLineTokens.add(new Token(returnToken, curLineNum()));
				} else if (str.equals("True")) {
					curLineTokens.add(new Token(trueToken, curLineNum()));
				} else if (str.equals("while")) {
					curLineTokens.add(new Token(whileToken, curLineNum()));
				} else {
					//hvis name legge til riktig verdier til Token
					Token t = new Token(nameToken, curLineNum());
					t.name = str;
					curLineTokens.add(t);
				}
			} else if (c == '"' || c == '\'') {
				//TODO error handling hvis det ikke kommer en slutt på stringen
				//TODO sjekk filen test-uten-slutt.asp i feil-mappen
				
				//finner stringen og legger den til som et Token
				String str = "";
				while (line.charAt(pos) != '"' || line.charAt(pos) != '\'') {
					str += line.charAt(pos);
					pos++;
				}

				Token t = new Token(stringToken, curLineNum());
				t.stringLit = str;
				curLineTokens.add(t);

			} else if (c == '+') {
				curLineTokens.add(new Token(plusToken, curLineNum()));
				pos++;
			} else if (c == '-') {
				curLineTokens.add(new Token(minusToken, curLineNum()));
				pos++;
			} else if (c == '*') {
				curLineTokens.add(new Token(astToken, curLineNum()));
				pos++;
			} else if (c == '/') {
				if (cNext == '/') {
					curLineTokens.add(new Token(doubleSlashToken, curLineNum()));
					pos++;
				} else {
					curLineTokens.add(new Token(slashToken, curLineNum()));
					pos++;
				}
			} else if (c == '=') {
				if (cNext == '=') {
					curLineTokens.add(new Token(doubleEqualToken, curLineNum()));
					pos++;
				} else {
					curLineTokens.add(new Token(equalToken, curLineNum()));
					pos++;
				}
			} else if (c == '>') {
				if (cNext == '=') {
					curLineTokens.add(new Token(greaterEqualToken, curLineNum()));
					pos++;
				} else {
					curLineTokens.add(new Token(greaterToken, curLineNum()));
					pos++;
				}
			} else if (c == '<') {
				if (cNext == '=') {
					curLineTokens.add(new Token(lessEqualToken, curLineNum()));
					pos++;
				} else {
					curLineTokens.add(new Token(lessToken, curLineNum()));
					pos++;
				}
			} else if (c == '!') {
				if (cNext == '=') {
					curLineTokens.add(new Token(notEqualToken, curLineNum()));
					pos++;
				}
			} else if (c == '%') {
				curLineTokens.add(new Token(percentToken, curLineNum()));
				pos++;
			} else if (c == ':') {
				curLineTokens.add(new Token(colonToken, curLineNum()));
				pos++;
			} else if (c == ',') {
				curLineTokens.add(new Token(commaToken, curLineNum()));
				pos++;
			} else if (c == '{') {
				curLineTokens.add(new Token(leftBraceToken, curLineNum()));
				pos++;
			} else if (c == '}') {
				curLineTokens.add(new Token(rightBraceToken, curLineNum()));
				pos++;
			} else if (c == '[') {
				curLineTokens.add(new Token(leftBracketToken, curLineNum()));
				pos++;
			} else if (c == ']') {
				curLineTokens.add(new Token(rightBracketToken, curLineNum()));
				pos++;
			} else if (c == '(') {
				curLineTokens.add(new Token(leftParToken, curLineNum()));
				pos++;
			} else if (c == ')') {
				curLineTokens.add(new Token(rightParToken, curLineNum()));
				pos++;
			} else if (c == ';') {
				curLineTokens.add(new Token(semicolonToken, curLineNum()));
				pos++;
			}
		}

		// Terminate line:
		//hvis sourceFile blir null er vi på slutten av filen
		//må legge til nødvendige dedentoken og E-o-f token
		if (sourceFile == null) {
			while (indents != null) {
				indents.pop();
				curLineTokens.add(new Token(dedentToken, curLineNum()));
			}
			curLineTokens.add(new Token(newLineToken,curLineNum()));
			curLineTokens.add(new Token(eofToken, curLineNum()));
		} else {
			curLineTokens.add(new Token(newLineToken,curLineNum()));
		}

		for (Token t: curLineTokens) 
			Main.log.noteToken(t);
    }

    public int curLineNum() {
		return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
		int indent = 0;

		while (indent<s.length() && s.charAt(indent)==' ') indent++;
		return indent;
    }

    /*
	 * Om former TAB til antall mellomrom sånn at det er lett for findIndent() å telle antall indent
	 */
	private String expandLeadingTabs(String s) {
		int n = 0;
		String text = s.stripLeading();
		String line = "";

		while (n < s.length() && (s.charAt(n) == ' ' || s.charAt(n) == '\t')) {
			if (s.charAt(n) == '\t') {
				for (int i = 0; i <  TABDIST - (n % TABDIST); i++) {
					line += ' ';
					n++;
				}
			} else {
				line += ' ';
				n++;
			}
		}
		line += text;
		return line;
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
