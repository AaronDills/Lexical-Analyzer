package lexicalanalyzerimplementation;

public class Token {

    private String lexeme;
    private String token;
    private String type;
    private String memorySize;
    private String scope;

    public Token(String lexeme, String token, String type, String memorySize, String scope) {
        this.lexeme = lexeme;
        this.token = token;
        this.type = type;
        this.memorySize = memorySize;
        this.scope = scope;
    }

    public boolean isMain() {
        return this.lexeme.equals("main");
    }

    public boolean isBegin() {
        return this.lexeme.equals("begin");
    }

    public boolean isEnd() {
        return this.lexeme.equals("end");
    }

    public boolean isIdentifier() {
        return this.token.equals("identifier");
    }

    public boolean isAssignmentOperator() {
        return this.lexeme.equals(":=");
    }

    public boolean isOperator() {
        return this.token.equals("Multiplication_Operator") || this.token.equals("Division_Operator") || this.token.equals("Addition_Operator") || this.token.equals("Subtraction_Operator");

    }

    public boolean isNumber() {
        return this.type.equals("int") && this.token.equals("Integer");
    }

    public boolean isArithmeticOperator() {
        return this.token.equals("Addition_Operator") || this.token.equals("Subtraction_Operator");

    }

    public boolean isMultiplyingOperator() {
        return this.token.equals("Multiplication_Operator") || this.token.equals("Division_Operator");
    }

    public boolean isIf() {
        return this.lexeme.equals("if");
    }

    public boolean isElse() {
        return this.lexeme.equals("else");
    }

    public boolean isThen() {
        return this.lexeme.equals("then");
    }

    public boolean isWhile() {
        return this.lexeme.equals("while");
    }

    public boolean isDo() {
        return this.lexeme.equals("do");
    }

    public boolean isType() {
        return this.lexeme.equals("int") || this.lexeme.equals("char") || this.lexeme.equals("String") || this.lexeme.equals("float");
    }

    public boolean isArray() {
        return this.token.equals("array");
    }

    public boolean isRead() {
        return this.lexeme.equals("read");
    }

    public boolean isWrite() {
        return this.lexeme.equals("write");
    }

    public boolean isParenthesis() {
        return this.lexeme.equals("(") || this.lexeme.equals(")");
    }

    public boolean isBracket() {
        return this.lexeme.equals("{") || this.lexeme.equals("}");

    }
    
    public boolean isSemiColon(){
        return this.lexeme.equals(";");
    }

    public boolean isLiteral() {
        return this.token.equals("String Literal") || this.token.equals("Integer") || this.token.equals("Float");
    }

    public boolean isBooleanTerm() {
        return this.lexeme.equals("true") || this.lexeme.equals("false");
    }

    public boolean isRelationalOperator() {
        return this.token.equals("Greater_Than_Operator") || this.token.equals("Less_Than_Operator") || this.token.equals("Equality_Operator") || this.token.equals("Non_Equaltiy_Operator")
                || this.token.equals("Less_Than_Equality_Operator") || this.token.equals("Greater_Than_Equality_Operator");
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public String getToken() {
        return this.token;
    }

    public String getType() {
        return this.type;
    }

    public String getMemorySize() {
        return this.memorySize;
    }

    public String getScope() {
        return this.scope;
    }

    public String toString() {
        return String.format("%-50s", lexeme) + String.format("%-50s", token) + String.format("%-50s", type) + String.format("%-50s", memorySize) + String.format("%-50s", scope);

    }

}
