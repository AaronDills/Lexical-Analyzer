package lexicalanalyzerimplementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import lexicalanalyzerimplementation.Token;

/**
 *
 * @author aarondills
 */
public class LexicalAnalyzer {

    public Token[] tokenTable;
    private Token[] symbolTable;
    private int statementIndex;
    private File inputFile;

    /**
     *
     * Lexical Analyzer converts a source files into a string, parses the string
     * into lexemes, then creates a table defining the token, type, memory size,
     * and scope of each lexeme. A Table containing all total lexemes and a
     * symbol table containing only lexemes with a type are created.
     *
     * @param inputFile
     * @throws FileNotFoundException if file is not created in caller
     */
    public LexicalAnalyzer(File inputFile) throws FileNotFoundException {

        this.inputFile = inputFile;
        Scanner convertFile = new Scanner(inputFile);
        String program = "";
        while (convertFile.hasNext()) {
            program += convertFile.nextLine();
            program += " ";
        }
        this.tokenTable = lex(program);

        int symbolTableLength = 0;
        int i;
        for (i = 0; tokenTable[i + 1] != null; i++) {
            
                if (!(tokenTable[i].getType().equals("N/A"))) {
                    if (!isInTable(tokenTable, i, tokenTable[i].getLexeme())) {
                        symbolTableLength++;
                    }
                }
            
        }
        int numberOfTokenTableTokens = i;
        this.symbolTable = new Token[symbolTableLength];
        int symbolTableIndex = 0;
        for (int j = 0; j < numberOfTokenTableTokens; j++) {
            if (!(tokenTable[j].getType().equals("N/A"))) {
                if (!isInTable(tokenTable, j, tokenTable[j].getLexeme())) {

                    String lexeme = tokenTable[j].getLexeme();
                    String token = tokenTable[j].getToken();
                    String type = tokenTable[j].getType();
                    String memorySize = tokenTable[j].getMemorySize();
                    String scope = tokenTable[j].getScope();
                    Token nextToken = new Token(lexeme, token, type, memorySize, scope);
                    symbolTable[symbolTableIndex] = nextToken;
                    symbolTableIndex++;
                }

            }
        }
    }

    //Takes as input a string a parses it into token,lexeme pairs
    /**
     *
     * @param program
     * @return
     */
    public static Token[] lex(String program) {

        Token[] lexTable = new Token[500];
        String lexeme = "";
        String token = "";
        int programIndex = 0;
        int tableIndex = 0;

        while (programIndex < program.length() - 1) {

            //removes blank spaces until character is accessed
            while (program.charAt(programIndex) == ' ') {
                if (programIndex > program.length()) {
                    return lexTable;
                } else {
                    programIndex++;

                }
            }

            int charClass = getChar(program.charAt(programIndex));

            //switch statement is done on class of character
            switch (charClass) {

                //If character is alphabetical
                case 0:

                    //retrieves one character at a time until total lexeme is parsed.
                    while ((getChar(program.charAt(programIndex)) == 0)
                            || (getChar(program.charAt(programIndex)) == 1)) {

                        lexeme += program.charAt(programIndex);
                        programIndex++;
                    }

                    token = lookUp(lexeme);
                    break;

                //If character is a number
                case 1:

                    //retrieves one character at a time until total lexeme is parsed.
                    while (getChar(program.charAt(programIndex)) == 1) {

                        lexeme += program.charAt(programIndex);
                        programIndex++;
                    }

                    //check to see if there is a decimal in the number to 
                    //determine if it is a floating point number
                    boolean isFloat = false;
                    for (int i = 0; i < lexeme.length(); i++) {
                        if (lexeme.charAt(i) == '.') {
                            isFloat = true;
                            break;
                        }
                    }

                    if (isFloat == true) {
                        token = "Float";
                    } else {
                        token = "Integer";

                    }
                    break;

                //If character is Delimiter
                case 2:

                    lexeme += program.charAt(programIndex);
                    token = "Delimiter";
                    programIndex++;
                    break;

                //All other cases
                case 3:

                    //Checks for string literals
                    if (program.charAt(programIndex) == '“') {
                        do {
                            lexeme += program.charAt(programIndex);
                            programIndex++;
                        } while (program.charAt(programIndex) != '”');
                        lexeme += program.charAt(programIndex);
                        programIndex++;
                        token = "String Literal";
                    } //Checks for equality operators
                    else if (getChar(program.charAt(programIndex + 1)) == 3) {

                        lexeme += program.charAt(programIndex);
                        lexeme += program.charAt(programIndex + 1);
                        token = lookUp(lexeme);
                        programIndex++;
                        programIndex++;
                        break;

                    } //All other cases
                    else {
                        lexeme += program.charAt(programIndex);
                        token = lookUp(lexeme);
                        programIndex++;
                        break;

                    }

            }

            //stores information and resets values
            String type = typeLookUp(lexeme, token, lexTable, tableIndex);
            String memorySize = memorySizeLookup(lexeme, type);
            String scope = scopeLookUp(lexTable, tableIndex);
            Token nextToken = new Token(lexeme, token, type, memorySize, scope);
            lexTable[tableIndex] = nextToken;
            tableIndex++;
            lexeme = "";
            token = "";

        }

        return lexTable;
    }

    /**
     *
     * @param a
     * @return
     */
    public static int getChar(char a) {

        //Tests if character is alpha
        if ((a >= 'a' && a <= 'z') || (a >= 'A' && a <= 'Z')) {
            return 0;
        }

        //Tests if character is numerical
        if ((a >= '0' && a <= '9') || a == '.') {
            return 1;
        }

        //Tests if character is delimitor
        if ((a == ',' || a == '(' || a == ')' || a == '[' || a == ']'
                || a == '{' || a == '}' || a == ' ' || a == ';')) {
            return 2;
        }

        //default for all other cases
        return 3;

    }

    /**
     *
     * @param lexeme
     * @return
     */
    public static String lookUp(String lexeme) {

        switch (lexeme) {

            case "main":
                return "keyword";
            case "if":
                return "keyword";
            case "then":
                return "keyword";
            case "else":
                return "keyword";
            case "do":
                return "keyword";
            case "while":
                return "keyword";
            case "return":
                return "keyword";
            case "bool":
                return "keyword";
            case "const":
                return "keyword";
            case "int":
                return "keyword";
            case "float":
                return "keyword";
            case "long":
                return "keyword";
            case "char":
                return "keyword";
            case "read":
                return "keyword";
            case "write":
                return "keyword";
            case "begin":
                return "keyword";
            case "end":
                return "keyword";
            case "[]":
                return "array";
            case "+":
                return "Addition_Operator";
            case "-":
                return "Subtraction_Operator";
            case "*":
                return "Multiplication_Operator";
            case "/":
                return "Division_Operator";
            case ":=":
                return "Assignment_Operator";
            case ">":
                return "Greater_Than_Operator";
            case "<":
                return "Less_Than_Operator";
            case "=":
                return "Equality_Operator";
            case "!=":
                return "Non_Equaltiy_Operator";
            case "<=":
                return "Less_Than_Equality_Operator";
            case ">=":
                return "Greater_Than_Equality_Operator";
            case "++":
                return "Incrementation_Operator";
            case "--":
                return "Decrementation_Operator";

        }
        return "identifier";

    }

    /**
     *
     * @param tokenTable
     * @param index
     * @return
     */
    public static String typeLookUp(String lexeme, String token, Token[] tokenTable, int index) {
        switch (token) {
            case "keyword":
                return "N/A";
            case "Delimiter":
                return "N/A";
            case "String Literal":
                return "String";
            case "Integer":
                return "int";
            case "Float":
                return "float";
            case "Character":
                return "char";
            case "identifier":
                return identifierType(lexeme, tokenTable, index);
            default:
                return "N/A";

        }
    }

    /**
     *
     * @param tokenTable
     * @param index
     * @return
     */
    public static String memorySizeLookup(String lexeme, String type) {
        switch (type) {
            case "String":

                //The length of the string multiplied by 2 for each character, minus 2 for the quotations
                String length = (lexeme.length() * 2) - 2 + "";
                return length + " Bytes";
            case "int":
                return "4 Bytes";
            case "float":
                return "8 Bytes";
            case "char":
                return "2 Bytes";

            default:
                return "N/A";

        }
    }

    /**
     *
     * @param tokenTable
     * @param index
     * @return
     */
    public static String scopeLookUp(Token[] tokenTable, int index) {
        int leftParenthesis = 0;
        int rightParenthesis = 0;

        for (int i = 0; i < index; i++) {
            if (tokenTable[i].getLexeme().equals("{")) {
                leftParenthesis++;
            }
            if (tokenTable[i].getLexeme().equals("}")) {
                rightParenthesis++;
            }
        }

        int scopeLevel = leftParenthesis - rightParenthesis;

        if (scopeLevel == 0) {
            return "Global";
        }

        return "Level " + scopeLevel + " Inner Scope";
    }

    /**
     *
     * @param tokenTable
     * @param index
     * @return
     */
    public static String identifierType(String lexeme, Token[] tokenTable, int index) {

        //checks table to see if type for lexeme was previously declared in program
        for (int i = 0; i < index; i++) {
            if (tokenTable[i].getLexeme().equals(lexeme)) {
                return tokenTable[i].getType();
            }
        }

        //checks each previous token until it finds keyword declaring the type
        for (int i = index - 1; i > 0; i--) {
            if (tokenTable[i].getToken().equals("keyword")) {
                return tokenTable[i].getLexeme();
            }
        }

        return "constant";

    }

    /**
     *
     * @param tokenTable
     * @param index
     * @param lexeme
     * @return
     */
    public static boolean isInTable(Token[] tokenTable, int index, String lexeme) {
        for (int j = 0; j < index; j++) {
            if (tokenTable[j].getLexeme().equals(lexeme)) {
                return true;
            }
        }
        return false;
    }

    public Token nextToken() {

        Token token = tokenTable[statementIndex];
        this.statementIndex++;
        return token;
    }

    public boolean pushBack(int index) {
        statementIndex = statementIndex - index;
        return false;
    }

    public String toString() {
        String printedTable = "";
        printedTable += "Lexeme                                            Token                                             Type                                              Memory Size                                       Scope\n";
        printedTable += "------                                            -----                                             ----                                              -----------                                       -----\n";
        for (Token token : symbolTable) {
            printedTable += token.toString() + "\n";

        }
        return printedTable;
    }

}
