package org.cloudsmith.geppetto.pp.dsl.lexer;

// Use our own Lexer superclass by means of import. 
import org.eclipse.xtext.parser.antlr.Lexer;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class PPLexer extends Lexer {
    public static final int KW_NOT_EQ=101;
    public static final int KW_COLON=138;
    public static final int KW_EQUALS=112;
    public static final int KW_PLUS=133;
    public static final int RULE_ANY_OTHER=83;
    public static final int RULE_RE_FLAGS=78;
    public static final int KEYWORD_56=15;
    public static final int KW_DEFAULT=85;
    public static final int KEYWORD_55=14;
    public static final int KEYWORD_54=13;
    public static final int KW_DEFINE=86;
    public static final int RULE_RE_FOLLOW_CHAR=81;
    public static final int KEYWORD_53=20;
    public static final int KEYWORD_52=19;
    public static final int KEYWORD_51=18;
    public static final int KEYWORD_50=17;
    public static final int KW_ELSIF=90;
    public static final int KW_IN_EDGE_SUB=111;
    public static final int EOF=-1;
    public static final int KW_ESC_DLR=118;
    public static final int KEYWORD_59=10;
    public static final int KEYWORD_58=9;
    public static final int KEYWORD_57=16;
    public static final int KW_DQ=127;
    public static final int KW_RPAR=131;
    public static final int KW_IN_EDGE=107;
    public static final int KEYWORD_65=5;
    public static final int KEYWORD_64=8;
    public static final int KEYWORD_66=4;
    public static final int KEYWORD_61=12;
    public static final int KEYWORD_60=11;
    public static final int KEYWORD_63=7;
    public static final int RULE_RE_FIRST_CHAR=80;
    public static final int KEYWORD_62=6;
    public static final int KW_EQ=141;
    public static final int KW_RARR=106;
    public static final int KW_SLASH=137;
    public static final int KW_ESC_DLR_BRACE=98;
    public static final int KW_PIPE=148;
    public static final int KW_RSHIFT=116;
    public static final int KW_LT=140;
    public static final int KEYWORD_30=26;
    public static final int KW_SQ=129;
    public static final int KEYWORD_34=30;
    public static final int KEYWORD_33=29;
    public static final int KEYWORD_32=28;
    public static final int KW_IMPORT=87;
    public static final int NUMERIC=151;
    public static final int KW_RBRACK=146;
    public static final int KEYWORD_31=27;
    public static final int KEYWORD_38=34;
    public static final int KW_RBRACE=149;
    public static final int KW_ESC_ESC=120;
    public static final int KEYWORD_37=33;
    public static final int KEYWORD_36=32;
    public static final int KEYWORD_35=31;
    public static final int KW_PLUS_EQ=104;
    public static final int RULE_ML_COMMENT=70;
    public static final int KEYWORD_39=35;
    public static final int KW_APPEND=105;
    public static final int RULE_NUMERIC=75;
    public static final int KW_MODULO=128;
    public static final int KW_LCOLLECT=110;
    public static final int KEYWORD_41=37;
    public static final int KEYWORD_40=36;
    public static final int KEYWORD_43=39;
    public static final int KEYWORD_42=38;
    public static final int KW_LBRACE=147;
    public static final int KEYWORD_45=41;
    public static final int KEYWORD_44=40;
    public static final int KEYWORD_47=43;
    public static final int KW_SEMI=139;
    public static final int KEYWORD_46=42;
    public static final int KEYWORD_49=45;
    public static final int KEYWORD_48=44;
    public static final int KW_NOT=126;
    public static final int RULE_DOLLAR_VAR=73;
    public static final int KW_ELSE=94;
    public static final int KW_MUL=132;
    public static final int RULE_REGULAR_EXPRESSION=79;
    public static final int KW_LBRACK=145;
    public static final int KW_AND=99;
    public static final int KEYWORD_19=64;
    public static final int KW_UNLESS=88;
    public static final int KW_DOT=136;
    public static final int KEYWORD_17=62;
    public static final int KW_LSHIFT=108;
    public static final int KEYWORD_18=63;
    public static final int KEYWORD_15=60;
    public static final int KEYWORD_16=61;
    public static final int KW_TRUE=96;
    public static final int KEYWORD_13=58;
    public static final int KEYWORD_14=59;
    public static final int KEYWORD_11=56;
    public static final int KEYWORD_12=57;
    public static final int KEYWORD_10=55;
    public static final int KW_MINUS=135;
    public static final int KW_NODE=95;
    public static final int KW_GT_EQ=115;
    public static final int RULE_NUMBER=76;
    public static final int KW_LT_EQ=109;
    public static final int KW_ESC_SQ=119;
    public static final int KEYWORD_6=51;
    public static final int KEYWORD_7=52;
    public static final int KEYWORD_8=53;
    public static final int KEYWORD_9=54;
    public static final int KEYWORD_28=24;
    public static final int KW_INHERITS=84;
    public static final int KW_IN=122;
    public static final int KEYWORD_29=25;
    public static final int KEYWORD_24=69;
    public static final int KEYWORD_25=21;
    public static final int KEYWORD_26=22;
    public static final int KEYWORD_27=23;
    public static final int KEYWORD_20=65;
    public static final int KW_IF=121;
    public static final int KEYWORD_21=66;
    public static final int KW_RCOLLECT=124;
    public static final int KEYWORD_22=67;
    public static final int KEYWORD_23=68;
    public static final int KW_QMARK=143;
    public static final int KW_MATCHES=114;
    public static final int KW_LLCOLLECT=97;
    public static final int RULE_RE_BACKSLASH_SEQUENCE=82;
    public static final int KW_FALSE=91;
    public static final int KW_COMMA=134;
    public static final int KW_GT=142;
    public static final int KW_NOT_MATCHES=102;
    public static final int KEYWORD_1=46;
    public static final int KEYWORD_5=50;
    public static final int RULE_WORD_CHARS=74;
    public static final int KEYWORD_4=49;
    public static final int KEYWORD_3=48;
    public static final int KEYWORD_2=47;
    public static final int RULE_NS=150;
    public static final int RULE_RE_BODY=77;
    public static final int RULE_SL_COMMENT=71;
    public static final int KW_CASE=93;
    public static final int KW_DLR_BRACE=103;
    public static final int KW_OUT_EDGE_SUB=125;
    public static final int KW_ESC_DQ=117;
    public static final int KW_LPAR=130;
    public static final int KW_OR=123;
    public static final int KW_UNDEF=92;
    public static final int KW_CLASS=89;
    public static final int KW_FARROW=113;
    public static final int KW_AT=144;
    public static final int KW_RRCOLLECT=100;
    public static final int RULE_WS=72;

      private Map<String, Integer> literals = getLiteralsMap();

      private static Map<String, Integer> getLiteralsMap() {
     	Map<String, Integer> result = new HashMap<String, Integer>();  
      	result.put("inherits", KW_INHERITS );
    	result.put("default", KW_DEFAULT );
    	result.put("unless", KW_UNLESS );
    	result.put("import", KW_IMPORT );
    	result.put("define", KW_DEFINE );
    	result.put("undef", KW_UNDEF );
    	result.put("false", KW_FALSE );
    	result.put("elsif", KW_ELSIF );
    	result.put("class", KW_CLASS );
    	result.put("true", KW_TRUE );
    	result.put("node", KW_NODE );
    	result.put("else", KW_ELSE );
    	result.put("case", KW_CASE );
    	result.put("and", KW_AND );
    	result.put("if", KW_IF );
    	result.put("in", KW_IN );
    	result.put("or", KW_OR );
    	return result;
      }
      private boolean isReAcceptable() {
      	if(singleQuotedString || doubleQuotedString)
      		return false;
      	// accept after ',' 'node', '{','}, '=~', '!~'
      	switch(lastSignificantToken) {
      		// NOTE: Must manually make sure these refer to the correct KEYWORD numbers
      		case KW_COMMA       : // ','
      		case KW_NODE        : // 'node'
      		case KW_LBRACE      : // '{'
      		case KW_RBRACE      : // '}'
      		case KW_MATCHES     : // '=~'
      		case KW_NOT_MATCHES : // '!~'
      		case KW_INHERITS    : // 'inherits'
      		case 0 : // nothing seen before, used when serializing
      			return true;
      		default:
      			return false;
      		}
      }
    	private boolean singleQuotedString = false;

    	private boolean doubleQuotedString = false;

    	protected int lastSignificantToken = 0;

    	private int dqIndex = 0;

    	private boolean dqStack[] = new boolean[10];

    	private int braceNesting = 0;

    	private void enterBrace() {
    		if(!isInterpolating())
    			return;
    		braceNesting++;
    	}

    	private void exitBrace() {
    		if(!isInterpolating())
    			return;
    		braceNesting--;
    		if(braceNesting == 0)
    			popDq();
    	}

    	private boolean isInDqString() {
    		return doubleQuotedString;
    	}

    	private boolean isInSqString() {
    		return singleQuotedString;
    	}

    	private boolean isInterpolating() {
    		return dqIndex > 0;
    	}

    	private boolean isNotInString() {
    		return !singleQuotedString && !doubleQuotedString;
    	}

    	private void popDq() {
    		if(dqIndex == 0)
    			doubleQuotedString = false; // bad state, but stay alive
    		else
    			doubleQuotedString = dqStack[--dqIndex];
    	}

    	private void pushDq() {
    		if(dqIndex >= dqStack.length)
    			dqStack = Arrays.copyOf(dqStack, dqStack.length + 10);

    		dqStack[dqIndex++] = doubleQuotedString;
    		doubleQuotedString = false;
    	}
    	private int replaceLiteral(int originalType, String text) {
    		// replace originalType with keyword if string completely matches a keyword and
    		// we are not in a string...
    	    Integer t = originalType;
    		if(isNotInString()) {
    			Integer t2 = literals.get(text);
    			if(t2 != null)
    			   t = t2;
    		}
    		return t;
    	}


    // delegates
    // delegators

    public PPLexer() {;} 
    public PPLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public PPLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g"; }

    // $ANTLR start "KW_INHERITS"
    public final void mKW_INHERITS() throws RecognitionException {
        try {
            int _type = KW_INHERITS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:128:13: ({...}? => 'inherits' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:128:15: {...}? => 'inherits'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_INHERITS", "isNotInString()");
            }
            match("inherits"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_INHERITS"

    // $ANTLR start "KW_DEFAULT"
    public final void mKW_DEFAULT() throws RecognitionException {
        try {
            int _type = KW_DEFAULT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:130:13: ({...}? => 'default' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:130:15: {...}? => 'default'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_DEFAULT", "isNotInString()");
            }
            match("default"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_DEFAULT"

    // $ANTLR start "KW_DEFINE"
    public final void mKW_DEFINE() throws RecognitionException {
        try {
            int _type = KW_DEFINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:132:13: ({...}? => 'define' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:132:15: {...}? => 'define'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_DEFINE", "isNotInString()");
            }
            match("define"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_DEFINE"

    // $ANTLR start "KW_IMPORT"
    public final void mKW_IMPORT() throws RecognitionException {
        try {
            int _type = KW_IMPORT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:134:13: ({...}? => 'import' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:134:15: {...}? => 'import'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_IMPORT", "isNotInString()");
            }
            match("import"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_IMPORT"

    // $ANTLR start "KW_UNLESS"
    public final void mKW_UNLESS() throws RecognitionException {
        try {
            int _type = KW_UNLESS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:136:13: ({...}? => 'unless' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:136:15: {...}? => 'unless'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_UNLESS", "isNotInString()");
            }
            match("unless"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_UNLESS"

    // $ANTLR start "KW_CLASS"
    public final void mKW_CLASS() throws RecognitionException {
        try {
            int _type = KW_CLASS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:138:13: ({...}? => 'class' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:138:15: {...}? => 'class'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_CLASS", "isNotInString()");
            }
            match("class"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_CLASS"

    // $ANTLR start "KW_ELSIF"
    public final void mKW_ELSIF() throws RecognitionException {
        try {
            int _type = KW_ELSIF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:140:13: ({...}? => 'elsif' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:140:15: {...}? => 'elsif'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_ELSIF", "isNotInString()");
            }
            match("elsif"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_ELSIF"

    // $ANTLR start "KW_FALSE"
    public final void mKW_FALSE() throws RecognitionException {
        try {
            int _type = KW_FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:142:13: ({...}? => 'false' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:142:15: {...}? => 'false'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_FALSE", "isNotInString()");
            }
            match("false"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_FALSE"

    // $ANTLR start "KW_UNDEF"
    public final void mKW_UNDEF() throws RecognitionException {
        try {
            int _type = KW_UNDEF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:144:13: ({...}? => 'undef' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:144:15: {...}? => 'undef'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_UNDEF", "isNotInString()");
            }
            match("undef"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_UNDEF"

    // $ANTLR start "KW_CASE"
    public final void mKW_CASE() throws RecognitionException {
        try {
            int _type = KW_CASE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:146:16: ({...}? => 'case' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:146:18: {...}? => 'case'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_CASE", "isNotInString()");
            }
            match("case"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_CASE"

    // $ANTLR start "KW_ELSE"
    public final void mKW_ELSE() throws RecognitionException {
        try {
            int _type = KW_ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:148:16: ({...}? => 'else' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:148:18: {...}? => 'else'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_ELSE", "isNotInString()");
            }
            match("else"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_ELSE"

    // $ANTLR start "KW_NODE"
    public final void mKW_NODE() throws RecognitionException {
        try {
            int _type = KW_NODE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:150:16: ({...}? => 'node' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:150:18: {...}? => 'node'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_NODE", "isNotInString()");
            }
            match("node"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_NODE"

    // $ANTLR start "KW_TRUE"
    public final void mKW_TRUE() throws RecognitionException {
        try {
            int _type = KW_TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:152:16: ({...}? => 'true' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:152:18: {...}? => 'true'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_TRUE", "isNotInString()");
            }
            match("true"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_TRUE"

    // $ANTLR start "KW_LLCOLLECT"
    public final void mKW_LLCOLLECT() throws RecognitionException {
        try {
            int _type = KW_LLCOLLECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:154:16: ({...}? => '<<|' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:154:18: {...}? => '<<|'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_LLCOLLECT", "isNotInString()");
            }
            match("<<|"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_LLCOLLECT"

    // $ANTLR start "KW_ESC_DLR_BRACE"
    public final void mKW_ESC_DLR_BRACE() throws RecognitionException {
        try {
            int _type = KW_ESC_DLR_BRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:156:18: ( '\\\\${' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:156:20: '\\\\${'
            {
            match("\\${"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_ESC_DLR_BRACE"

    // $ANTLR start "KW_AND"
    public final void mKW_AND() throws RecognitionException {
        try {
            int _type = KW_AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:158:16: ({...}? => 'and' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:158:18: {...}? => 'and'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_AND", "isNotInString()");
            }
            match("and"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_AND"

    // $ANTLR start "KW_RRCOLLECT"
    public final void mKW_RRCOLLECT() throws RecognitionException {
        try {
            int _type = KW_RRCOLLECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:160:16: ({...}? => '|>>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:160:18: {...}? => '|>>'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_RRCOLLECT", "isNotInString()");
            }
            match("|>>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_RRCOLLECT"

    // $ANTLR start "KW_NOT_EQ"
    public final void mKW_NOT_EQ() throws RecognitionException {
        try {
            int _type = KW_NOT_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:162:16: ({...}? => '!=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:162:18: {...}? => '!='
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_NOT_EQ", "isNotInString()");
            }
            match("!="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_NOT_EQ"

    // $ANTLR start "KW_NOT_MATCHES"
    public final void mKW_NOT_MATCHES() throws RecognitionException {
        try {
            int _type = KW_NOT_MATCHES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:164:16: ({...}? => '!~' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:164:18: {...}? => '!~'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_NOT_MATCHES", "isNotInString()");
            }
            match("!~"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_NOT_MATCHES"

    // $ANTLR start "KW_DLR_BRACE"
    public final void mKW_DLR_BRACE() throws RecognitionException {
        try {
            int _type = KW_DLR_BRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:166:14: ( '${' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:166:16: '${'
            {
            match("${"); if (state.failed) return ;

            if ( state.backtracking==0 ) {

              	if(doubleQuotedString) {
              		// in string expression interpolation mode
              		pushDq();
              		enterBrace();
              	} else if(singleQuotedString) {
              		_type = RULE_ANY_OTHER;
              	}

            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_DLR_BRACE"

    // $ANTLR start "KW_PLUS_EQ"
    public final void mKW_PLUS_EQ() throws RecognitionException {
        try {
            int _type = KW_PLUS_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:176:14: ({...}? => '+=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:176:16: {...}? => '+='
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_PLUS_EQ", "isNotInString()");
            }
            match("+="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_PLUS_EQ"

    // $ANTLR start "KW_APPEND"
    public final void mKW_APPEND() throws RecognitionException {
        try {
            int _type = KW_APPEND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:178:14: ({...}? => '+>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:178:16: {...}? => '+>'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_APPEND", "isNotInString()");
            }
            match("+>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_APPEND"

    // $ANTLR start "KW_RARR"
    public final void mKW_RARR() throws RecognitionException {
        try {
            int _type = KW_RARR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:180:14: ({...}? => '->' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:180:16: {...}? => '->'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_RARR", "isNotInString()");
            }
            match("->"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_RARR"

    // $ANTLR start "KW_IN_EDGE"
    public final void mKW_IN_EDGE() throws RecognitionException {
        try {
            int _type = KW_IN_EDGE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:182:14: ({...}? => '<-' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:182:16: {...}? => '<-'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_IN_EDGE", "isNotInString()");
            }
            match("<-"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_IN_EDGE"

    // $ANTLR start "KW_LSHIFT"
    public final void mKW_LSHIFT() throws RecognitionException {
        try {
            int _type = KW_LSHIFT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:184:14: ({...}? => '<<' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:184:16: {...}? => '<<'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_LSHIFT", "isNotInString()");
            }
            match("<<"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_LSHIFT"

    // $ANTLR start "KW_LT_EQ"
    public final void mKW_LT_EQ() throws RecognitionException {
        try {
            int _type = KW_LT_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:186:15: ({...}? => '<=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:186:17: {...}? => '<='
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_LT_EQ", "isNotInString()");
            }
            match("<="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_LT_EQ"

    // $ANTLR start "KW_LCOLLECT"
    public final void mKW_LCOLLECT() throws RecognitionException {
        try {
            int _type = KW_LCOLLECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:188:14: ({...}? => '<|' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:188:16: {...}? => '<|'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_LCOLLECT", "isNotInString()");
            }
            match("<|"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_LCOLLECT"

    // $ANTLR start "KW_IN_EDGE_SUB"
    public final void mKW_IN_EDGE_SUB() throws RecognitionException {
        try {
            int _type = KW_IN_EDGE_SUB;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:190:16: ({...}? => '<~' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:190:18: {...}? => '<~'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_IN_EDGE_SUB", "isNotInString()");
            }
            match("<~"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_IN_EDGE_SUB"

    // $ANTLR start "KW_EQUALS"
    public final void mKW_EQUALS() throws RecognitionException {
        try {
            int _type = KW_EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:192:14: ({...}? => '==' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:192:16: {...}? => '=='
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_EQUALS", "isNotInString()");
            }
            match("=="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_EQUALS"

    // $ANTLR start "KW_FARROW"
    public final void mKW_FARROW() throws RecognitionException {
        try {
            int _type = KW_FARROW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:194:14: ({...}? => '=>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:194:16: {...}? => '=>'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_FARROW", "isNotInString()");
            }
            match("=>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_FARROW"

    // $ANTLR start "KW_MATCHES"
    public final void mKW_MATCHES() throws RecognitionException {
        try {
            int _type = KW_MATCHES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:196:14: ({...}? => '=~' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:196:16: {...}? => '=~'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_MATCHES", "isNotInString()");
            }
            match("=~"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_MATCHES"

    // $ANTLR start "KW_GT_EQ"
    public final void mKW_GT_EQ() throws RecognitionException {
        try {
            int _type = KW_GT_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:198:15: ({...}? => '>=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:198:17: {...}? => '>='
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_GT_EQ", "isNotInString()");
            }
            match(">="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_GT_EQ"

    // $ANTLR start "KW_RSHIFT"
    public final void mKW_RSHIFT() throws RecognitionException {
        try {
            int _type = KW_RSHIFT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:200:14: ({...}? => '>>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:200:16: {...}? => '>>'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_RSHIFT", "isNotInString()");
            }
            match(">>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_RSHIFT"

    // $ANTLR start "KW_ESC_DQ"
    public final void mKW_ESC_DQ() throws RecognitionException {
        try {
            int _type = KW_ESC_DQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:202:14: ( '\\\\\"' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:202:16: '\\\\\"'
            {
            match("\\\""); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_ESC_DQ"

    // $ANTLR start "KW_ESC_DLR"
    public final void mKW_ESC_DLR() throws RecognitionException {
        try {
            int _type = KW_ESC_DLR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:204:14: ( '\\\\$' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:204:16: '\\\\$'
            {
            match("\\$"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_ESC_DLR"

    // $ANTLR start "KW_ESC_SQ"
    public final void mKW_ESC_SQ() throws RecognitionException {
        try {
            int _type = KW_ESC_SQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:206:14: ( '\\\\\\'' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:206:16: '\\\\\\''
            {
            match("\\'"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_ESC_SQ"

    // $ANTLR start "KW_ESC_ESC"
    public final void mKW_ESC_ESC() throws RecognitionException {
        try {
            int _type = KW_ESC_ESC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:208:14: ( '\\\\\\\\' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:208:16: '\\\\\\\\'
            {
            match("\\\\"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_ESC_ESC"

    // $ANTLR start "KW_IF"
    public final void mKW_IF() throws RecognitionException {
        try {
            int _type = KW_IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:210:14: ({...}? => 'if' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:210:16: {...}? => 'if'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_IF", "isNotInString()");
            }
            match("if"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_IF"

    // $ANTLR start "KW_IN"
    public final void mKW_IN() throws RecognitionException {
        try {
            int _type = KW_IN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:212:14: ({...}? => 'in' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:212:16: {...}? => 'in'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_IN", "isNotInString()");
            }
            match("in"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_IN"

    // $ANTLR start "KW_OR"
    public final void mKW_OR() throws RecognitionException {
        try {
            int _type = KW_OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:214:14: ({...}? => 'or' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:214:16: {...}? => 'or'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_OR", "isNotInString()");
            }
            match("or"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_OR"

    // $ANTLR start "KW_RCOLLECT"
    public final void mKW_RCOLLECT() throws RecognitionException {
        try {
            int _type = KW_RCOLLECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:216:14: ({...}? => '|>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:216:16: {...}? => '|>'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_RCOLLECT", "isNotInString()");
            }
            match("|>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_RCOLLECT"

    // $ANTLR start "KW_OUT_EDGE_SUB"
    public final void mKW_OUT_EDGE_SUB() throws RecognitionException {
        try {
            int _type = KW_OUT_EDGE_SUB;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:218:17: ({...}? => '~>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:218:19: {...}? => '~>'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_OUT_EDGE_SUB", "isNotInString()");
            }
            match("~>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_OUT_EDGE_SUB"

    // $ANTLR start "KW_NOT"
    public final void mKW_NOT() throws RecognitionException {
        try {
            int _type = KW_NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:220:14: ({...}? => '!' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:220:16: {...}? => '!'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_NOT", "isNotInString()");
            }
            match('!'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_NOT"

    // $ANTLR start "KW_DQ"
    public final void mKW_DQ() throws RecognitionException {
        try {
            int _type = KW_DQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:222:14: ({...}? => '\"' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:222:16: {...}? => '\"'
            {
            if ( !((!singleQuotedString)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_DQ", "!singleQuotedString");
            }
            match('\"'); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               	// flip if in dq string or not
              		doubleQuotedString = !doubleQuotedString;
              	
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_DQ"

    // $ANTLR start "KW_MODULO"
    public final void mKW_MODULO() throws RecognitionException {
        try {
            int _type = KW_MODULO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:227:14: ({...}? => '%' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:227:16: {...}? => '%'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_MODULO", "isNotInString()");
            }
            match('%'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_MODULO"

    // $ANTLR start "KW_SQ"
    public final void mKW_SQ() throws RecognitionException {
        try {
            int _type = KW_SQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:229:14: ({...}? => '\\'' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:229:16: {...}? => '\\''
            {
            if ( !((!doubleQuotedString)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_SQ", "!doubleQuotedString");
            }
            match('\''); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               	// flip if in sq string or not
              		singleQuotedString = !singleQuotedString;
              	
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_SQ"

    // $ANTLR start "KW_LPAR"
    public final void mKW_LPAR() throws RecognitionException {
        try {
            int _type = KW_LPAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:234:14: ({...}? => '(' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:234:16: {...}? => '('
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_LPAR", "isNotInString()");
            }
            match('('); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_LPAR"

    // $ANTLR start "KW_RPAR"
    public final void mKW_RPAR() throws RecognitionException {
        try {
            int _type = KW_RPAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:236:14: ({...}? => ')' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:236:16: {...}? => ')'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_RPAR", "isNotInString()");
            }
            match(')'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_RPAR"

    // $ANTLR start "KW_MUL"
    public final void mKW_MUL() throws RecognitionException {
        try {
            int _type = KW_MUL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:238:14: ({...}? => '*' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:238:16: {...}? => '*'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_MUL", "isNotInString()");
            }
            match('*'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_MUL"

    // $ANTLR start "KW_PLUS"
    public final void mKW_PLUS() throws RecognitionException {
        try {
            int _type = KW_PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:240:14: ({...}? => '+' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:240:16: {...}? => '+'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_PLUS", "isNotInString()");
            }
            match('+'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_PLUS"

    // $ANTLR start "KW_COMMA"
    public final void mKW_COMMA() throws RecognitionException {
        try {
            int _type = KW_COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:242:14: ({...}? => ',' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:242:16: {...}? => ','
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_COMMA", "isNotInString()");
            }
            match(','); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_COMMA"

    // $ANTLR start "KW_MINUS"
    public final void mKW_MINUS() throws RecognitionException {
        try {
            int _type = KW_MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:244:14: ( '-' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:244:16: '-'
            {
            match('-'); if (state.failed) return ;
            if ( state.backtracking==0 ) {

              	if(singleQuotedString || doubleQuotedString)
              		_type = RULE_ANY_OTHER;

            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_MINUS"

    // $ANTLR start "KW_DOT"
    public final void mKW_DOT() throws RecognitionException {
        try {
            int _type = KW_DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:249:14: ( '.' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:249:16: '.'
            {
            match('.'); if (state.failed) return ;
            if ( state.backtracking==0 ) {

              	if(singleQuotedString || doubleQuotedString)
              		_type = RULE_ANY_OTHER;

            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_DOT"

    // $ANTLR start "KW_SLASH"
    public final void mKW_SLASH() throws RecognitionException {
        try {
            int _type = KW_SLASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:255:14: ({...}? => '/' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:255:16: {...}? => '/'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_SLASH", "isNotInString()");
            }
            match('/'); if (state.failed) return ;
            if ( state.backtracking==0 ) {

              	if(isReAcceptable()) {
              		mRULE_REGULAR_EXPRESSION();
              		return;
              	}

            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_SLASH"

    // $ANTLR start "KW_COLON"
    public final void mKW_COLON() throws RecognitionException {
        try {
            int _type = KW_COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:262:14: ({...}? => ':' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:262:16: {...}? => ':'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_COLON", "isNotInString()");
            }
            match(':'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_COLON"

    // $ANTLR start "KW_SEMI"
    public final void mKW_SEMI() throws RecognitionException {
        try {
            int _type = KW_SEMI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:264:14: ({...}? => ';' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:264:16: {...}? => ';'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_SEMI", "isNotInString()");
            }
            match(';'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_SEMI"

    // $ANTLR start "KW_LT"
    public final void mKW_LT() throws RecognitionException {
        try {
            int _type = KW_LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:266:14: ({...}? => '<' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:266:16: {...}? => '<'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_LT", "isNotInString()");
            }
            match('<'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_LT"

    // $ANTLR start "KW_EQ"
    public final void mKW_EQ() throws RecognitionException {
        try {
            int _type = KW_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:268:14: ({...}? => '=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:268:16: {...}? => '='
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_EQ", "isNotInString()");
            }
            match('='); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_EQ"

    // $ANTLR start "KW_GT"
    public final void mKW_GT() throws RecognitionException {
        try {
            int _type = KW_GT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:270:14: ({...}? => '>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:270:16: {...}? => '>'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_GT", "isNotInString()");
            }
            match('>'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_GT"

    // $ANTLR start "KW_QMARK"
    public final void mKW_QMARK() throws RecognitionException {
        try {
            int _type = KW_QMARK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:272:14: ({...}? => '?' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:272:16: {...}? => '?'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_QMARK", "isNotInString()");
            }
            match('?'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_QMARK"

    // $ANTLR start "KW_AT"
    public final void mKW_AT() throws RecognitionException {
        try {
            int _type = KW_AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:274:14: ({...}? => '@' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:274:16: {...}? => '@'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_AT", "isNotInString()");
            }
            match('@'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_AT"

    // $ANTLR start "KW_LBRACK"
    public final void mKW_LBRACK() throws RecognitionException {
        try {
            int _type = KW_LBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:14: ({...}? => '[' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:16: {...}? => '['
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_LBRACK", "isNotInString()");
            }
            match('['); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_LBRACK"

    // $ANTLR start "KW_RBRACK"
    public final void mKW_RBRACK() throws RecognitionException {
        try {
            int _type = KW_RBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:278:14: ({...}? => ']' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:278:16: {...}? => ']'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_RBRACK", "isNotInString()");
            }
            match(']'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_RBRACK"

    // $ANTLR start "KW_LBRACE"
    public final void mKW_LBRACE() throws RecognitionException {
        try {
            int _type = KW_LBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:280:14: ({...}? => '{' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:280:16: {...}? => '{'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_LBRACE", "isNotInString()");
            }
            match('{'); if (state.failed) return ;
            if ( state.backtracking==0 ) {
              enterBrace();
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_LBRACE"

    // $ANTLR start "KW_PIPE"
    public final void mKW_PIPE() throws RecognitionException {
        try {
            int _type = KW_PIPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:282:14: ({...}? => '|' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:282:16: {...}? => '|'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_PIPE", "isNotInString()");
            }
            match('|'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_PIPE"

    // $ANTLR start "KW_RBRACE"
    public final void mKW_RBRACE() throws RecognitionException {
        try {
            int _type = KW_RBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:284:14: ({...}? => '}' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:284:16: {...}? => '}'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KW_RBRACE", "isNotInString()");
            }
            match('}'); if (state.failed) return ;
            if ( state.backtracking==0 ) {
              exitBrace();
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KW_RBRACE"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:287:17: ({...}? => ( ( '/*' ( options {greedy=false; } : . )* '*/' ) ( ' ' | '\\u00A0' | '\\t' )* ( ( '\\r' )? '\\n' )? ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:287:19: {...}? => ( ( '/*' ( options {greedy=false; } : . )* '*/' ) ( ' ' | '\\u00A0' | '\\t' )* ( ( '\\r' )? '\\n' )? )
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "RULE_ML_COMMENT", "isNotInString()");
            }
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:287:40: ( ( '/*' ( options {greedy=false; } : . )* '*/' ) ( ' ' | '\\u00A0' | '\\t' )* ( ( '\\r' )? '\\n' )? )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:287:41: ( '/*' ( options {greedy=false; } : . )* '*/' ) ( ' ' | '\\u00A0' | '\\t' )* ( ( '\\r' )? '\\n' )?
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:287:41: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:287:42: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); if (state.failed) return ;

            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:287:47: ( options {greedy=false; } : . )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='*') ) {
                    int LA1_1 = input.LA(2);

                    if ( (LA1_1=='/') ) {
                        alt1=2;
                    }
                    else if ( ((LA1_1>='\u0000' && LA1_1<='.')||(LA1_1>='0' && LA1_1<='\uFFFF')) ) {
                        alt1=1;
                    }


                }
                else if ( ((LA1_0>='\u0000' && LA1_0<=')')||(LA1_0>='+' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:287:75: .
            	    {
            	    matchAny(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match("*/"); if (state.failed) return ;


            }

            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:287:85: ( ' ' | '\\u00A0' | '\\t' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='\t'||LA2_0==' '||LA2_0=='\u00A0') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' '||input.LA(1)=='\u00A0' ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:287:106: ( ( '\\r' )? '\\n' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\n'||LA4_0=='\r') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:287:107: ( '\\r' )? '\\n'
                    {
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:287:107: ( '\\r' )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0=='\r') ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:287:107: '\\r'
                            {
                            match('\r'); if (state.failed) return ;

                            }
                            break;

                    }

                    match('\n'); if (state.failed) return ;

                    }
                    break;

            }


            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:291:17: ({...}? => '#' (~ ( ( '\\r' | '\\n' ) ) )* ( ( '\\r' )? '\\n' )? )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:291:19: {...}? => '#' (~ ( ( '\\r' | '\\n' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "RULE_SL_COMMENT", "isNotInString()");
            }
            match('#'); if (state.failed) return ;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:291:44: (~ ( ( '\\r' | '\\n' ) ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='\u0000' && LA5_0<='\t')||(LA5_0>='\u000B' && LA5_0<='\f')||(LA5_0>='\u000E' && LA5_0<='\uFFFF')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:291:44: ~ ( ( '\\r' | '\\n' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:291:60: ( ( '\\r' )? '\\n' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='\n'||LA7_0=='\r') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:291:61: ( '\\r' )? '\\n'
                    {
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:291:61: ( '\\r' )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0=='\r') ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:291:61: '\\r'
                            {
                            match('\r'); if (state.failed) return ;

                            }
                            break;

                    }

                    match('\n'); if (state.failed) return ;

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:293:9: ( ( ' ' | '\\u00A0' | '\\t' | '\\r' | '\\n' )+ )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:293:11: ( ' ' | '\\u00A0' | '\\t' | '\\r' | '\\n' )+
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:293:11: ( ' ' | '\\u00A0' | '\\t' | '\\r' | '\\n' )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='\t' && LA8_0<='\n')||LA8_0=='\r'||LA8_0==' '||LA8_0=='\u00A0') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' '||input.LA(1)=='\u00A0' ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);

            if ( state.backtracking==0 ) {

              	if(doubleQuotedString || singleQuotedString) {
              		_type = RULE_ANY_OTHER;
              	}

            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_DOLLAR_VAR"
    public final void mRULE_DOLLAR_VAR() throws RecognitionException {
        try {
            int _type = RULE_DOLLAR_VAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:300:17: ( '$' ( ( ':' ':' )=> RULE_NS )? ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+ ( ( ':' ':' )=> RULE_NS ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+ )* )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:300:19: '$' ( ( ':' ':' )=> RULE_NS )? ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+ ( ( ':' ':' )=> RULE_NS ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+ )*
            {
            match('$'); if (state.failed) return ;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:301:2: ( ( ':' ':' )=> RULE_NS )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==':') && (synpred1_PPLexer())) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:301:3: ( ':' ':' )=> RULE_NS
                    {
                    mRULE_NS(); if (state.failed) return ;

                    }
                    break;

            }

            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:301:24: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='0' && LA10_0<='9')||(LA10_0>='A' && LA10_0<='Z')||LA10_0=='_'||(LA10_0>='a' && LA10_0<='z')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);

            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:302:2: ( ( ':' ':' )=> RULE_NS ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+ )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==':') && (synpred2_PPLexer())) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:302:3: ( ':' ':' )=> RULE_NS ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+
            	    {
            	    mRULE_NS(); if (state.failed) return ;
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:302:22: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+
            	    int cnt11=0;
            	    loop11:
            	    do {
            	        int alt11=2;
            	        int LA11_0 = input.LA(1);

            	        if ( ((LA11_0>='0' && LA11_0<='9')||(LA11_0>='A' && LA11_0<='Z')||LA11_0=='_'||(LA11_0>='a' && LA11_0<='z')) ) {
            	            alt11=1;
            	        }


            	        switch (alt11) {
            	    	case 1 :
            	    	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:
            	    	    {
            	    	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	    	        input.consume();
            	    	    state.failed=false;
            	    	    }
            	    	    else {
            	    	        if (state.backtracking>0) {state.failed=true; return ;}
            	    	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	    	        recover(mse);
            	    	        throw mse;}


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt11 >= 1 ) break loop11;
            	    	    if (state.backtracking>0) {state.failed=true; return ;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(11, input);
            	                throw eee;
            	        }
            	        cnt11++;
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_DOLLAR_VAR"

    // $ANTLR start "RULE_WORD_CHARS"
    public final void mRULE_WORD_CHARS() throws RecognitionException {
        try {
            int _type = RULE_WORD_CHARS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:17: ( ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | ( ':' ':' )=> RULE_NS ) ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | ( ':' ':' )=> RULE_NS )* )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:19: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | ( ':' ':' )=> RULE_NS ) ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | ( ':' ':' )=> RULE_NS )*
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:19: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | ( ':' ':' )=> RULE_NS )
            int alt13=5;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>='0' && LA13_0<='9')) ) {
                alt13=1;
            }
            else if ( ((LA13_0>='a' && LA13_0<='z')) ) {
                alt13=2;
            }
            else if ( ((LA13_0>='A' && LA13_0<='Z')) ) {
                alt13=3;
            }
            else if ( (LA13_0=='_') ) {
                alt13=4;
            }
            else if ( (LA13_0==':') && (synpred3_PPLexer())) {
                alt13=5;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:20: '0' .. '9'
                    {
                    matchRange('0','9'); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:29: 'a' .. 'z'
                    {
                    matchRange('a','z'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:38: 'A' .. 'Z'
                    {
                    matchRange('A','Z'); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:47: '_'
                    {
                    match('_'); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:51: ( ':' ':' )=> RULE_NS
                    {
                    mRULE_NS(); if (state.failed) return ;

                    }
                    break;

            }

            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:71: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | ( ':' ':' )=> RULE_NS )*
            loop14:
            do {
                int alt14=7;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>='0' && LA14_0<='9')) ) {
                    alt14=1;
                }
                else if ( ((LA14_0>='a' && LA14_0<='z')) ) {
                    alt14=2;
                }
                else if ( ((LA14_0>='A' && LA14_0<='Z')) ) {
                    alt14=3;
                }
                else if ( (LA14_0=='_') ) {
                    alt14=4;
                }
                else if ( (LA14_0=='-') ) {
                    alt14=5;
                }
                else if ( (LA14_0==':') && (synpred4_PPLexer())) {
                    alt14=6;
                }


                switch (alt14) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:72: '0' .. '9'
            	    {
            	    matchRange('0','9'); if (state.failed) return ;

            	    }
            	    break;
            	case 2 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:81: 'a' .. 'z'
            	    {
            	    matchRange('a','z'); if (state.failed) return ;

            	    }
            	    break;
            	case 3 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:90: 'A' .. 'Z'
            	    {
            	    matchRange('A','Z'); if (state.failed) return ;

            	    }
            	    break;
            	case 4 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:99: '_'
            	    {
            	    match('_'); if (state.failed) return ;

            	    }
            	    break;
            	case 5 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:103: '-'
            	    {
            	    match('-'); if (state.failed) return ;

            	    }
            	    break;
            	case 6 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:107: ( ':' ':' )=> RULE_NS
            	    {
            	    mRULE_NS(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            if ( state.backtracking==0 ) {
              	// check if what was matched is a keyword - emit that instead
              	_type = replaceLiteral(_type, getText());

            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WORD_CHARS"

    // $ANTLR start "RULE_NUMBER"
    public final void mRULE_NUMBER() throws RecognitionException {
        try {
            int _type = RULE_NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:311:13: ( ( NUMERIC )=> NUMERIC )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:311:15: ( NUMERIC )=> NUMERIC
            {
            mNUMERIC(); if (state.failed) return ;
            if ( state.backtracking==0 ) {

              	_type = RULE_WORD_CHARS;

            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_NUMBER"

    // $ANTLR start "RULE_REGULAR_EXPRESSION"
    public final void mRULE_REGULAR_EXPRESSION() throws RecognitionException {
        try {
            int _type = RULE_REGULAR_EXPRESSION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:315:25: ({...}? => '/' RULE_RE_BODY '/' ( RULE_RE_FLAGS )? )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:315:27: {...}? => '/' RULE_RE_BODY '/' ( RULE_RE_FLAGS )?
            {
            if ( !((isReAcceptable())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "RULE_REGULAR_EXPRESSION", "isReAcceptable()");
            }
            match('/'); if (state.failed) return ;
            mRULE_RE_BODY(); if (state.failed) return ;
            match('/'); if (state.failed) return ;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:315:69: ( RULE_RE_FLAGS )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( ((LA15_0>='a' && LA15_0<='z')) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:315:69: RULE_RE_FLAGS
                    {
                    mRULE_RE_FLAGS(); if (state.failed) return ;

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_REGULAR_EXPRESSION"

    // $ANTLR start "NUMERIC"
    public final void mNUMERIC() throws RecognitionException {
        try {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:318:3: ( ( '0' ( 'x' | 'X' ) )=> ( '0' ( 'x' | 'X' ) ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )+ ) | ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )? ( ( 'e' | 'E' ) ( '-' )? ( '0' .. '9' )+ )? )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0=='0') ) {
                int LA23_1 = input.LA(2);

                if ( (LA23_1=='X'||LA23_1=='x') && (synpred6_PPLexer())) {
                    alt23=1;
                }
                else {
                    alt23=2;}
            }
            else if ( ((LA23_0>='1' && LA23_0<='9')) ) {
                alt23=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:318:5: ( '0' ( 'x' | 'X' ) )=> ( '0' ( 'x' | 'X' ) ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )+ )
                    {
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:318:22: ( '0' ( 'x' | 'X' ) ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )+ )
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:318:23: '0' ( 'x' | 'X' ) ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )+
                    {
                    match('0'); if (state.failed) return ;
                    if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                        input.consume();
                    state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:318:37: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )+
                    int cnt16=0;
                    loop16:
                    do {
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( ((LA16_0>='0' && LA16_0<='9')||(LA16_0>='A' && LA16_0<='F')||(LA16_0>='a' && LA16_0<='f')) ) {
                            alt16=1;
                        }


                        switch (alt16) {
                    	case 1 :
                    	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:
                    	    {
                    	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                    	        input.consume();
                    	    state.failed=false;
                    	    }
                    	    else {
                    	        if (state.backtracking>0) {state.failed=true; return ;}
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt16 >= 1 ) break loop16;
                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(16, input);
                                throw eee;
                        }
                        cnt16++;
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:319:4: ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )? ( ( 'e' | 'E' ) ( '-' )? ( '0' .. '9' )+ )?
                    {
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:319:4: ( '0' .. '9' )+
                    int cnt17=0;
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( ((LA17_0>='0' && LA17_0<='9')) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:319:5: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt17 >= 1 ) break loop17;
                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(17, input);
                                throw eee;
                        }
                        cnt17++;
                    } while (true);

                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:319:16: ( '.' ( '0' .. '9' )+ )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0=='.') ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:319:17: '.' ( '0' .. '9' )+
                            {
                            match('.'); if (state.failed) return ;
                            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:319:21: ( '0' .. '9' )+
                            int cnt18=0;
                            loop18:
                            do {
                                int alt18=2;
                                int LA18_0 = input.LA(1);

                                if ( ((LA18_0>='0' && LA18_0<='9')) ) {
                                    alt18=1;
                                }


                                switch (alt18) {
                            	case 1 :
                            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:319:22: '0' .. '9'
                            	    {
                            	    matchRange('0','9'); if (state.failed) return ;

                            	    }
                            	    break;

                            	default :
                            	    if ( cnt18 >= 1 ) break loop18;
                            	    if (state.backtracking>0) {state.failed=true; return ;}
                                        EarlyExitException eee =
                                            new EarlyExitException(18, input);
                                        throw eee;
                                }
                                cnt18++;
                            } while (true);


                            }
                            break;

                    }

                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:319:35: ( ( 'e' | 'E' ) ( '-' )? ( '0' .. '9' )+ )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0=='E'||LA22_0=='e') ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:319:36: ( 'e' | 'E' ) ( '-' )? ( '0' .. '9' )+
                            {
                            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                                input.consume();
                            state.failed=false;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return ;}
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}

                            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:319:46: ( '-' )?
                            int alt20=2;
                            int LA20_0 = input.LA(1);

                            if ( (LA20_0=='-') ) {
                                alt20=1;
                            }
                            switch (alt20) {
                                case 1 :
                                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:319:46: '-'
                                    {
                                    match('-'); if (state.failed) return ;

                                    }
                                    break;

                            }

                            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:319:51: ( '0' .. '9' )+
                            int cnt21=0;
                            loop21:
                            do {
                                int alt21=2;
                                int LA21_0 = input.LA(1);

                                if ( ((LA21_0>='0' && LA21_0<='9')) ) {
                                    alt21=1;
                                }


                                switch (alt21) {
                            	case 1 :
                            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:319:52: '0' .. '9'
                            	    {
                            	    matchRange('0','9'); if (state.failed) return ;

                            	    }
                            	    break;

                            	default :
                            	    if ( cnt21 >= 1 ) break loop21;
                            	    if (state.backtracking>0) {state.failed=true; return ;}
                                        EarlyExitException eee =
                                            new EarlyExitException(21, input);
                                        throw eee;
                                }
                                cnt21++;
                            } while (true);


                            }
                            break;

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "NUMERIC"

    // $ANTLR start "RULE_NS"
    public final void mRULE_NS() throws RecognitionException {
        try {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:322:18: ( '::' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:322:20: '::'
            {
            match("::"); if (state.failed) return ;


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_NS"

    // $ANTLR start "RULE_RE_BODY"
    public final void mRULE_RE_BODY() throws RecognitionException {
        try {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:324:23: ( ( RULE_RE_FIRST_CHAR ( RULE_RE_FOLLOW_CHAR )* ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:324:25: ( RULE_RE_FIRST_CHAR ( RULE_RE_FOLLOW_CHAR )* )
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:324:25: ( RULE_RE_FIRST_CHAR ( RULE_RE_FOLLOW_CHAR )* )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:324:26: RULE_RE_FIRST_CHAR ( RULE_RE_FOLLOW_CHAR )*
            {
            mRULE_RE_FIRST_CHAR(); if (state.failed) return ;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:324:45: ( RULE_RE_FOLLOW_CHAR )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( ((LA24_0>='\u0000' && LA24_0<='\t')||(LA24_0>='\u000B' && LA24_0<='.')||(LA24_0>='0' && LA24_0<='\uFFFF')) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:324:45: RULE_RE_FOLLOW_CHAR
            	    {
            	    mRULE_RE_FOLLOW_CHAR(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_RE_BODY"

    // $ANTLR start "RULE_RE_FIRST_CHAR"
    public final void mRULE_RE_FIRST_CHAR() throws RecognitionException {
        try {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:326:29: ( (~ ( ( '\\n' | '*' | '/' | '\\\\' ) ) | RULE_RE_BACKSLASH_SEQUENCE ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:326:31: (~ ( ( '\\n' | '*' | '/' | '\\\\' ) ) | RULE_RE_BACKSLASH_SEQUENCE )
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:326:31: (~ ( ( '\\n' | '*' | '/' | '\\\\' ) ) | RULE_RE_BACKSLASH_SEQUENCE )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( ((LA25_0>='\u0000' && LA25_0<='\t')||(LA25_0>='\u000B' && LA25_0<=')')||(LA25_0>='+' && LA25_0<='.')||(LA25_0>='0' && LA25_0<='[')||(LA25_0>=']' && LA25_0<='\uFFFF')) ) {
                alt25=1;
            }
            else if ( (LA25_0=='\\') ) {
                alt25=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:326:32: ~ ( ( '\\n' | '*' | '/' | '\\\\' ) )
                    {
                    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<=')')||(input.LA(1)>='+' && input.LA(1)<='.')||(input.LA(1)>='0' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                        input.consume();
                    state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 2 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:326:55: RULE_RE_BACKSLASH_SEQUENCE
                    {
                    mRULE_RE_BACKSLASH_SEQUENCE(); if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_RE_FIRST_CHAR"

    // $ANTLR start "RULE_RE_FOLLOW_CHAR"
    public final void mRULE_RE_FOLLOW_CHAR() throws RecognitionException {
        try {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:328:30: ( ( RULE_RE_FIRST_CHAR | '*' ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:328:32: ( RULE_RE_FIRST_CHAR | '*' )
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:328:32: ( RULE_RE_FIRST_CHAR | '*' )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( ((LA26_0>='\u0000' && LA26_0<='\t')||(LA26_0>='\u000B' && LA26_0<=')')||(LA26_0>='+' && LA26_0<='.')||(LA26_0>='0' && LA26_0<='\uFFFF')) ) {
                alt26=1;
            }
            else if ( (LA26_0=='*') ) {
                alt26=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:328:33: RULE_RE_FIRST_CHAR
                    {
                    mRULE_RE_FIRST_CHAR(); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:328:52: '*'
                    {
                    match('*'); if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_RE_FOLLOW_CHAR"

    // $ANTLR start "RULE_RE_BACKSLASH_SEQUENCE"
    public final void mRULE_RE_BACKSLASH_SEQUENCE() throws RecognitionException {
        try {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:330:37: ( ( '\\\\' ~ ( '\\n' ) ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:330:39: ( '\\\\' ~ ( '\\n' ) )
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:330:39: ( '\\\\' ~ ( '\\n' ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:330:40: '\\\\' ~ ( '\\n' )
            {
            match('\\'); if (state.failed) return ;
            if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\uFFFF') ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_RE_BACKSLASH_SEQUENCE"

    // $ANTLR start "RULE_RE_FLAGS"
    public final void mRULE_RE_FLAGS() throws RecognitionException {
        try {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:332:24: ( ( 'a' .. 'z' )+ )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:332:26: ( 'a' .. 'z' )+
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:332:26: ( 'a' .. 'z' )+
            int cnt27=0;
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( ((LA27_0>='a' && LA27_0<='z')) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:332:27: 'a' .. 'z'
            	    {
            	    matchRange('a','z'); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt27 >= 1 ) break loop27;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(27, input);
                        throw eee;
                }
                cnt27++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_RE_FLAGS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:334:16: ( . )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:334:18: .
            {
            matchAny(); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:8: ( KW_INHERITS | KW_DEFAULT | KW_DEFINE | KW_IMPORT | KW_UNLESS | KW_CLASS | KW_ELSIF | KW_FALSE | KW_UNDEF | KW_CASE | KW_ELSE | KW_NODE | KW_TRUE | KW_LLCOLLECT | KW_ESC_DLR_BRACE | KW_AND | KW_RRCOLLECT | KW_NOT_EQ | KW_NOT_MATCHES | KW_DLR_BRACE | KW_PLUS_EQ | KW_APPEND | KW_RARR | KW_IN_EDGE | KW_LSHIFT | KW_LT_EQ | KW_LCOLLECT | KW_IN_EDGE_SUB | KW_EQUALS | KW_FARROW | KW_MATCHES | KW_GT_EQ | KW_RSHIFT | KW_ESC_DQ | KW_ESC_DLR | KW_ESC_SQ | KW_ESC_ESC | KW_IF | KW_IN | KW_OR | KW_RCOLLECT | KW_OUT_EDGE_SUB | KW_NOT | KW_DQ | KW_MODULO | KW_SQ | KW_LPAR | KW_RPAR | KW_MUL | KW_PLUS | KW_COMMA | KW_MINUS | KW_DOT | KW_SLASH | KW_COLON | KW_SEMI | KW_LT | KW_EQ | KW_GT | KW_QMARK | KW_AT | KW_LBRACK | KW_RBRACK | KW_LBRACE | KW_PIPE | KW_RBRACE | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_DOLLAR_VAR | RULE_WORD_CHARS | RULE_NUMBER | RULE_REGULAR_EXPRESSION | RULE_ANY_OTHER )
        int alt28=74;
        alt28 = dfa28.predict(input);
        switch (alt28) {
            case 1 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:10: KW_INHERITS
                {
                mKW_INHERITS(); if (state.failed) return ;

                }
                break;
            case 2 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:22: KW_DEFAULT
                {
                mKW_DEFAULT(); if (state.failed) return ;

                }
                break;
            case 3 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:33: KW_DEFINE
                {
                mKW_DEFINE(); if (state.failed) return ;

                }
                break;
            case 4 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:43: KW_IMPORT
                {
                mKW_IMPORT(); if (state.failed) return ;

                }
                break;
            case 5 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:53: KW_UNLESS
                {
                mKW_UNLESS(); if (state.failed) return ;

                }
                break;
            case 6 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:63: KW_CLASS
                {
                mKW_CLASS(); if (state.failed) return ;

                }
                break;
            case 7 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:72: KW_ELSIF
                {
                mKW_ELSIF(); if (state.failed) return ;

                }
                break;
            case 8 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:81: KW_FALSE
                {
                mKW_FALSE(); if (state.failed) return ;

                }
                break;
            case 9 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:90: KW_UNDEF
                {
                mKW_UNDEF(); if (state.failed) return ;

                }
                break;
            case 10 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:99: KW_CASE
                {
                mKW_CASE(); if (state.failed) return ;

                }
                break;
            case 11 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:107: KW_ELSE
                {
                mKW_ELSE(); if (state.failed) return ;

                }
                break;
            case 12 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:115: KW_NODE
                {
                mKW_NODE(); if (state.failed) return ;

                }
                break;
            case 13 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:123: KW_TRUE
                {
                mKW_TRUE(); if (state.failed) return ;

                }
                break;
            case 14 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:131: KW_LLCOLLECT
                {
                mKW_LLCOLLECT(); if (state.failed) return ;

                }
                break;
            case 15 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:144: KW_ESC_DLR_BRACE
                {
                mKW_ESC_DLR_BRACE(); if (state.failed) return ;

                }
                break;
            case 16 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:161: KW_AND
                {
                mKW_AND(); if (state.failed) return ;

                }
                break;
            case 17 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:168: KW_RRCOLLECT
                {
                mKW_RRCOLLECT(); if (state.failed) return ;

                }
                break;
            case 18 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:181: KW_NOT_EQ
                {
                mKW_NOT_EQ(); if (state.failed) return ;

                }
                break;
            case 19 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:191: KW_NOT_MATCHES
                {
                mKW_NOT_MATCHES(); if (state.failed) return ;

                }
                break;
            case 20 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:206: KW_DLR_BRACE
                {
                mKW_DLR_BRACE(); if (state.failed) return ;

                }
                break;
            case 21 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:219: KW_PLUS_EQ
                {
                mKW_PLUS_EQ(); if (state.failed) return ;

                }
                break;
            case 22 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:230: KW_APPEND
                {
                mKW_APPEND(); if (state.failed) return ;

                }
                break;
            case 23 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:240: KW_RARR
                {
                mKW_RARR(); if (state.failed) return ;

                }
                break;
            case 24 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:248: KW_IN_EDGE
                {
                mKW_IN_EDGE(); if (state.failed) return ;

                }
                break;
            case 25 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:259: KW_LSHIFT
                {
                mKW_LSHIFT(); if (state.failed) return ;

                }
                break;
            case 26 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:269: KW_LT_EQ
                {
                mKW_LT_EQ(); if (state.failed) return ;

                }
                break;
            case 27 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:278: KW_LCOLLECT
                {
                mKW_LCOLLECT(); if (state.failed) return ;

                }
                break;
            case 28 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:290: KW_IN_EDGE_SUB
                {
                mKW_IN_EDGE_SUB(); if (state.failed) return ;

                }
                break;
            case 29 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:305: KW_EQUALS
                {
                mKW_EQUALS(); if (state.failed) return ;

                }
                break;
            case 30 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:315: KW_FARROW
                {
                mKW_FARROW(); if (state.failed) return ;

                }
                break;
            case 31 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:325: KW_MATCHES
                {
                mKW_MATCHES(); if (state.failed) return ;

                }
                break;
            case 32 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:336: KW_GT_EQ
                {
                mKW_GT_EQ(); if (state.failed) return ;

                }
                break;
            case 33 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:345: KW_RSHIFT
                {
                mKW_RSHIFT(); if (state.failed) return ;

                }
                break;
            case 34 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:355: KW_ESC_DQ
                {
                mKW_ESC_DQ(); if (state.failed) return ;

                }
                break;
            case 35 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:365: KW_ESC_DLR
                {
                mKW_ESC_DLR(); if (state.failed) return ;

                }
                break;
            case 36 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:376: KW_ESC_SQ
                {
                mKW_ESC_SQ(); if (state.failed) return ;

                }
                break;
            case 37 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:386: KW_ESC_ESC
                {
                mKW_ESC_ESC(); if (state.failed) return ;

                }
                break;
            case 38 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:397: KW_IF
                {
                mKW_IF(); if (state.failed) return ;

                }
                break;
            case 39 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:403: KW_IN
                {
                mKW_IN(); if (state.failed) return ;

                }
                break;
            case 40 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:409: KW_OR
                {
                mKW_OR(); if (state.failed) return ;

                }
                break;
            case 41 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:415: KW_RCOLLECT
                {
                mKW_RCOLLECT(); if (state.failed) return ;

                }
                break;
            case 42 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:427: KW_OUT_EDGE_SUB
                {
                mKW_OUT_EDGE_SUB(); if (state.failed) return ;

                }
                break;
            case 43 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:443: KW_NOT
                {
                mKW_NOT(); if (state.failed) return ;

                }
                break;
            case 44 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:450: KW_DQ
                {
                mKW_DQ(); if (state.failed) return ;

                }
                break;
            case 45 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:456: KW_MODULO
                {
                mKW_MODULO(); if (state.failed) return ;

                }
                break;
            case 46 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:466: KW_SQ
                {
                mKW_SQ(); if (state.failed) return ;

                }
                break;
            case 47 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:472: KW_LPAR
                {
                mKW_LPAR(); if (state.failed) return ;

                }
                break;
            case 48 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:480: KW_RPAR
                {
                mKW_RPAR(); if (state.failed) return ;

                }
                break;
            case 49 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:488: KW_MUL
                {
                mKW_MUL(); if (state.failed) return ;

                }
                break;
            case 50 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:495: KW_PLUS
                {
                mKW_PLUS(); if (state.failed) return ;

                }
                break;
            case 51 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:503: KW_COMMA
                {
                mKW_COMMA(); if (state.failed) return ;

                }
                break;
            case 52 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:512: KW_MINUS
                {
                mKW_MINUS(); if (state.failed) return ;

                }
                break;
            case 53 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:521: KW_DOT
                {
                mKW_DOT(); if (state.failed) return ;

                }
                break;
            case 54 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:528: KW_SLASH
                {
                mKW_SLASH(); if (state.failed) return ;

                }
                break;
            case 55 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:537: KW_COLON
                {
                mKW_COLON(); if (state.failed) return ;

                }
                break;
            case 56 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:546: KW_SEMI
                {
                mKW_SEMI(); if (state.failed) return ;

                }
                break;
            case 57 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:554: KW_LT
                {
                mKW_LT(); if (state.failed) return ;

                }
                break;
            case 58 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:560: KW_EQ
                {
                mKW_EQ(); if (state.failed) return ;

                }
                break;
            case 59 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:566: KW_GT
                {
                mKW_GT(); if (state.failed) return ;

                }
                break;
            case 60 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:572: KW_QMARK
                {
                mKW_QMARK(); if (state.failed) return ;

                }
                break;
            case 61 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:581: KW_AT
                {
                mKW_AT(); if (state.failed) return ;

                }
                break;
            case 62 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:587: KW_LBRACK
                {
                mKW_LBRACK(); if (state.failed) return ;

                }
                break;
            case 63 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:597: KW_RBRACK
                {
                mKW_RBRACK(); if (state.failed) return ;

                }
                break;
            case 64 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:607: KW_LBRACE
                {
                mKW_LBRACE(); if (state.failed) return ;

                }
                break;
            case 65 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:617: KW_PIPE
                {
                mKW_PIPE(); if (state.failed) return ;

                }
                break;
            case 66 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:625: KW_RBRACE
                {
                mKW_RBRACE(); if (state.failed) return ;

                }
                break;
            case 67 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:635: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); if (state.failed) return ;

                }
                break;
            case 68 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:651: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); if (state.failed) return ;

                }
                break;
            case 69 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:667: RULE_WS
                {
                mRULE_WS(); if (state.failed) return ;

                }
                break;
            case 70 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:675: RULE_DOLLAR_VAR
                {
                mRULE_DOLLAR_VAR(); if (state.failed) return ;

                }
                break;
            case 71 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:691: RULE_WORD_CHARS
                {
                mRULE_WORD_CHARS(); if (state.failed) return ;

                }
                break;
            case 72 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:707: RULE_NUMBER
                {
                mRULE_NUMBER(); if (state.failed) return ;

                }
                break;
            case 73 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:719: RULE_REGULAR_EXPRESSION
                {
                mRULE_REGULAR_EXPRESSION(); if (state.failed) return ;

                }
                break;
            case 74 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:743: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); if (state.failed) return ;

                }
                break;

        }

    }

    // $ANTLR start synpred1_PPLexer
    public final void synpred1_PPLexer_fragment() throws RecognitionException {   
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:301:3: ( ':' ':' )
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:301:4: ':' ':'
        {
        match(':'); if (state.failed) return ;
        match(':'); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_PPLexer

    // $ANTLR start synpred2_PPLexer
    public final void synpred2_PPLexer_fragment() throws RecognitionException {   
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:302:3: ( ':' ':' )
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:302:4: ':' ':'
        {
        match(':'); if (state.failed) return ;
        match(':'); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_PPLexer

    // $ANTLR start synpred3_PPLexer
    public final void synpred3_PPLexer_fragment() throws RecognitionException {   
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:51: ( ':' ':' )
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:52: ':' ':'
        {
        match(':'); if (state.failed) return ;
        match(':'); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_PPLexer

    // $ANTLR start synpred4_PPLexer
    public final void synpred4_PPLexer_fragment() throws RecognitionException {   
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:107: ( ':' ':' )
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:305:108: ':' ':'
        {
        match(':'); if (state.failed) return ;
        match(':'); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_PPLexer

    // $ANTLR start synpred6_PPLexer
    public final void synpred6_PPLexer_fragment() throws RecognitionException {   
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:318:5: ( '0' ( 'x' | 'X' ) )
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:318:6: '0' ( 'x' | 'X' )
        {
        match('0'); if (state.failed) return ;
        if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
            input.consume();
        state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            recover(mse);
            throw mse;}


        }
    }
    // $ANTLR end synpred6_PPLexer

    public final boolean synpred2_PPLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_PPLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_PPLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_PPLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_PPLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_PPLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_PPLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_PPLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_PPLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_PPLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA28 dfa28 = new DFA28(this);
    static final String DFA28_eotS =
        "\1\uffff\10\61\1\77\1\55\1\61\1\106\1\111\1\55\1\116\1\120\1\124"+
        "\1\127\1\61\1\55\1\132\1\133\1\134\1\135\1\136\1\137\1\140\1\uffff"+
        "\1\143\1\145\1\146\1\147\1\150\1\151\1\152\1\153\1\154\1\156\1\uffff"+
        "\1\61\3\uffff\1\61\1\uffff\1\167\1\61\1\171\1\uffff\10\61\1\u0084"+
        "\5\uffff\1\u0087\3\uffff\1\61\1\u008a\22\uffff\1\u0090\27\uffff"+
        "\5\61\1\uffff\1\61\1\uffff\1\61\1\uffff\11\61\5\uffff\1\u00b5\30"+
        "\uffff\6\61\1\uffff\1\61\1\uffff\5\61\1\u00be\1\61\1\u00c0\1\61"+
        "\1\u00c2\1\u00c3\2\uffff\5\61\1\u00ca\1\u00cb\1\uffff\1\u00cd\1"+
        "\uffff\1\u00cf\3\uffff\1\61\1\u00d3\1\61\1\u00d5\1\u00d6\10\uffff"+
        "\1\61\1\uffff\1\u00dd\6\uffff\1\u00e0\7\uffff";
    static final String DFA28_eofS =
        "\u00e3\uffff";
    static final String DFA28_minS =
        "\1\0\1\146\1\145\1\156\1\141\1\154\1\141\1\157\1\162\1\55\1\42\1"+
        "\156\1\76\1\75\1\60\1\75\1\76\2\75\1\162\1\76\7\0\1\uffff\1\0\1"+
        "\72\10\0\1\uffff\1\56\3\uffff\1\56\1\uffff\1\55\1\160\1\55\1\uffff"+
        "\1\146\1\144\1\141\2\163\1\154\1\144\1\165\1\174\4\uffff\1\0\1\173"+
        "\3\uffff\1\144\1\76\1\0\2\uffff\1\0\4\uffff\1\0\5\uffff\1\0\2\uffff"+
        "\1\0\1\55\1\uffff\7\0\2\uffff\1\0\1\uffff\10\0\1\uffff\1\0\1\uffff"+
        "\1\60\1\56\1\60\2\55\1\uffff\1\145\1\0\1\157\1\0\1\141\2\145\1\163"+
        "\2\145\1\163\2\145\5\uffff\1\55\7\uffff\1\0\20\uffff\5\60\1\162"+
        "\1\uffff\1\162\1\uffff\1\165\1\156\1\163\1\146\1\163\1\55\1\146"+
        "\1\55\1\145\2\55\1\0\1\uffff\1\151\1\164\1\154\1\145\1\163\2\55"+
        "\1\0\1\55\1\0\1\55\2\0\1\uffff\1\164\1\55\1\164\2\55\2\0\1\uffff"+
        "\1\0\1\uffff\1\0\2\uffff\1\163\1\0\1\55\2\0\4\uffff\1\55\1\uffff"+
        "\1\0\2\uffff\1\0\2\uffff";
    static final String DFA28_maxS =
        "\1\uffff\1\156\1\145\1\156\2\154\1\141\1\157\1\162\1\176\1\134\1"+
        "\156\1\76\1\176\1\173\2\76\1\176\1\76\1\162\1\76\7\0\1\uffff\1\uffff"+
        "\1\72\7\0\1\uffff\1\uffff\1\170\3\uffff\1\145\1\uffff\1\172\1\160"+
        "\1\172\1\uffff\1\146\1\154\1\141\2\163\1\154\1\144\1\165\1\174\4"+
        "\uffff\1\0\1\173\3\uffff\1\144\1\76\1\0\2\uffff\1\0\4\uffff\1\0"+
        "\5\uffff\1\0\2\uffff\1\0\1\172\1\uffff\7\0\2\uffff\1\0\1\uffff\10"+
        "\0\1\uffff\1\0\1\uffff\1\146\1\145\1\146\2\71\1\uffff\1\145\1\0"+
        "\1\157\1\0\1\151\2\145\1\163\1\145\1\151\1\163\2\145\5\uffff\1\172"+
        "\7\uffff\1\0\20\uffff\3\146\2\71\1\162\1\uffff\1\162\1\uffff\1\165"+
        "\1\156\1\163\1\146\1\163\1\172\1\146\1\172\1\145\2\172\1\0\1\uffff"+
        "\1\151\1\164\1\154\1\145\1\163\2\172\1\0\1\172\1\0\1\172\2\0\1\uffff"+
        "\1\164\1\172\1\164\2\172\2\0\1\uffff\1\0\1\uffff\1\0\2\uffff\1\163"+
        "\1\0\1\172\2\0\4\uffff\1\172\1\uffff\1\0\2\uffff\1\0\2\uffff";
    static final String DFA28_acceptS =
        "\34\uffff\1\65\12\uffff\1\105\1\uffff\3\107\1\uffff\1\112\3\uffff"+
        "\1\107\11\uffff\1\30\1\32\1\33\1\34\2\uffff\1\42\1\44\1\45\3\uffff"+
        "\1\22\1\23\1\uffff\1\24\1\106\1\25\1\26\1\uffff\1\27\1\64\1\35\1"+
        "\36\1\37\1\uffff\1\40\1\41\2\uffff\1\52\7\uffff\1\65\1\103\1\uffff"+
        "\1\111\10\uffff\1\104\1\uffff\1\105\5\uffff\1\110\15\uffff\1\16"+
        "\1\31\1\71\1\17\1\43\1\uffff\1\21\1\51\1\101\1\53\1\62\1\72\1\73"+
        "\1\uffff\1\54\1\55\1\56\1\57\1\60\1\61\1\63\1\66\1\67\1\70\1\74"+
        "\1\75\1\76\1\77\1\100\1\102\6\uffff\1\47\1\uffff\1\46\14\uffff\1"+
        "\50\15\uffff\1\20\7\uffff\1\12\1\uffff\1\13\1\uffff\1\14\1\15\5"+
        "\uffff\1\11\1\6\1\7\1\10\1\uffff\1\4\1\uffff\1\3\1\5\1\uffff\1\2"+
        "\1\1";
    static final String DFA28_specialS =
        "\1\4\10\uffff\1\5\2\uffff\1\2\1\1\1\uffff\1\63\1\7\1\40\1\3\1\uffff"+
        "\1\31\10\uffff\1\60\10\uffff\1\0\23\uffff\1\64\4\uffff\1\56\5\uffff"+
        "\1\6\1\46\2\uffff\1\41\4\uffff\1\36\5\uffff\1\55\2\uffff\1\54\2"+
        "\uffff\1\42\1\43\1\32\1\33\1\34\1\35\1\37\2\uffff\1\62\1\uffff\1"+
        "\61\1\57\1\53\1\52\1\51\1\50\1\47\1\45\1\uffff\1\44\10\uffff\1\30"+
        "\1\uffff\1\26\26\uffff\1\27\44\uffff\1\24\10\uffff\1\23\1\uffff"+
        "\1\20\1\uffff\1\21\1\25\6\uffff\1\22\1\16\1\uffff\1\15\1\uffff\1"+
        "\14\3\uffff\1\10\1\uffff\1\11\1\17\6\uffff\1\12\2\uffff\1\13\2\uffff}>";
    static final String[] DFA28_transitionS = {
            "\11\55\2\47\2\55\1\47\22\55\1\47\1\15\1\25\1\46\1\16\1\26\1"+
            "\55\1\27\1\30\1\31\1\32\1\17\1\33\1\20\1\34\1\35\1\50\11\54"+
            "\1\36\1\37\1\11\1\21\1\22\1\40\1\41\32\52\1\42\1\12\1\43\1\55"+
            "\1\53\1\55\1\13\1\51\1\4\1\2\1\5\1\6\2\51\1\1\4\51\1\7\1\23"+
            "\4\51\1\10\1\3\5\51\1\44\1\14\1\45\1\24\41\55\1\47\uff5f\55",
            "\1\60\6\uffff\1\57\1\56",
            "\1\62",
            "\1\63",
            "\1\65\12\uffff\1\64",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\71",
            "\1\73\16\uffff\1\72\1\74\76\uffff\1\75\1\uffff\1\76",
            "\1\101\1\uffff\1\100\2\uffff\1\102\64\uffff\1\103",
            "\1\104",
            "\1\105",
            "\1\107\100\uffff\1\110",
            "\13\113\6\uffff\32\113\4\uffff\1\113\1\uffff\32\113\1\112",
            "\1\114\1\115",
            "\1\117",
            "\1\121\1\122\77\uffff\1\123",
            "\1\125\1\126",
            "\1\130",
            "\1\131",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "\12\144\1\uffff\37\144\1\142\4\144\1\uffff\uffd0\144",
            "\1\61",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\0\155",
            "",
            "\1\165\1\uffff\12\161\13\uffff\1\164\22\uffff\1\162\14\uffff"+
            "\1\163\22\uffff\1\160",
            "",
            "",
            "",
            "\1\165\1\uffff\12\161\13\uffff\1\164\37\uffff\1\163",
            "",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\7\61"+
            "\1\166\22\61",
            "\1\170",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "\1\172",
            "\1\174\7\uffff\1\173",
            "\1\175",
            "\1\176",
            "\1\177",
            "\1\u0080",
            "\1\u0081",
            "\1\u0082",
            "\1\u0083",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\u0086",
            "",
            "",
            "",
            "\1\u0088",
            "\1\u0089",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
            "",
            "\12\u00a1\7\uffff\6\u00a3\32\uffff\6\u00a2",
            "\1\165\1\uffff\12\161\13\uffff\1\164\37\uffff\1\163",
            "\12\u00a1\7\uffff\6\u00a3\32\uffff\6\u00a2",
            "\1\u00a5\2\uffff\12\u00a4",
            "\1\u00a5\2\uffff\12\u00a4",
            "",
            "\1\u00a6",
            "\1\uffff",
            "\1\u00a8",
            "\1\uffff",
            "\1\u00aa\7\uffff\1\u00ab",
            "\1\u00ac",
            "\1\u00ad",
            "\1\u00ae",
            "\1\u00af",
            "\1\u00b1\3\uffff\1\u00b0",
            "\1\u00b2",
            "\1\u00b3",
            "\1\u00b4",
            "",
            "",
            "",
            "",
            "",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\u00a1\7\uffff\6\u00a3\32\uffff\6\u00a2",
            "\12\u00a1\7\uffff\6\u00a3\32\uffff\6\u00a2",
            "\12\u00a1\7\uffff\6\u00a3\32\uffff\6\u00a2",
            "\12\u00a4",
            "\12\u00a4",
            "\1\u00b7",
            "",
            "\1\u00b8",
            "",
            "\1\u00b9",
            "\1\u00ba",
            "\1\u00bb",
            "\1\u00bc",
            "\1\u00bd",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u00bf",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u00c1",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\uffff",
            "",
            "\1\u00c5",
            "\1\u00c6",
            "\1\u00c7",
            "\1\u00c8",
            "\1\u00c9",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\uffff",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\uffff",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\u00d2",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u00d4",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
            "",
            "\1\uffff",
            "",
            "",
            "\1\u00db",
            "\1\uffff",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "\1\61\2\uffff\13\61\6\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA28_eot = DFA.unpackEncodedString(DFA28_eotS);
    static final short[] DFA28_eof = DFA.unpackEncodedString(DFA28_eofS);
    static final char[] DFA28_min = DFA.unpackEncodedStringToUnsignedChars(DFA28_minS);
    static final char[] DFA28_max = DFA.unpackEncodedStringToUnsignedChars(DFA28_maxS);
    static final short[] DFA28_accept = DFA.unpackEncodedString(DFA28_acceptS);
    static final short[] DFA28_special = DFA.unpackEncodedString(DFA28_specialS);
    static final short[][] DFA28_transition;

    static {
        int numStates = DFA28_transitionS.length;
        DFA28_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA28_transition[i] = DFA.unpackEncodedString(DFA28_transitionS[i]);
        }
    }

    class DFA28 extends DFA {

        public DFA28(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 28;
            this.eot = DFA28_eot;
            this.eof = DFA28_eof;
            this.min = DFA28_min;
            this.max = DFA28_max;
            this.accept = DFA28_accept;
            this.special = DFA28_special;
            this.transition = DFA28_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( KW_INHERITS | KW_DEFAULT | KW_DEFINE | KW_IMPORT | KW_UNLESS | KW_CLASS | KW_ELSIF | KW_FALSE | KW_UNDEF | KW_CASE | KW_ELSE | KW_NODE | KW_TRUE | KW_LLCOLLECT | KW_ESC_DLR_BRACE | KW_AND | KW_RRCOLLECT | KW_NOT_EQ | KW_NOT_MATCHES | KW_DLR_BRACE | KW_PLUS_EQ | KW_APPEND | KW_RARR | KW_IN_EDGE | KW_LSHIFT | KW_LT_EQ | KW_LCOLLECT | KW_IN_EDGE_SUB | KW_EQUALS | KW_FARROW | KW_MATCHES | KW_GT_EQ | KW_RSHIFT | KW_ESC_DQ | KW_ESC_DLR | KW_ESC_SQ | KW_ESC_ESC | KW_IF | KW_IN | KW_OR | KW_RCOLLECT | KW_OUT_EDGE_SUB | KW_NOT | KW_DQ | KW_MODULO | KW_SQ | KW_LPAR | KW_RPAR | KW_MUL | KW_PLUS | KW_COMMA | KW_MINUS | KW_DOT | KW_SLASH | KW_COLON | KW_SEMI | KW_LT | KW_EQ | KW_GT | KW_QMARK | KW_AT | KW_LBRACK | KW_RBRACK | KW_LBRACE | KW_PIPE | KW_RBRACE | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_DOLLAR_VAR | RULE_WORD_CHARS | RULE_NUMBER | RULE_REGULAR_EXPRESSION | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA28_38 = input.LA(1);

                         
                        int index28_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA28_38>='\u0000' && LA28_38<='\uFFFF')) && ((isNotInString()))) {s = 109;}

                        else s = 110;

                         
                        input.seek(index28_38);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA28_13 = input.LA(1);

                         
                        int index28_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA28_13=='=') && ((isNotInString()))) {s = 71;}

                        else if ( (LA28_13=='~') && ((isNotInString()))) {s = 72;}

                        else s = 73;

                         
                        input.seek(index28_13);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA28_12 = input.LA(1);

                         
                        int index28_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA28_12=='>') && ((isNotInString()))) {s = 69;}

                        else s = 70;

                         
                        input.seek(index28_12);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA28_18 = input.LA(1);

                         
                        int index28_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA28_18=='=') && ((isNotInString()))) {s = 85;}

                        else if ( (LA28_18=='>') && ((isNotInString()))) {s = 86;}

                        else s = 87;

                         
                        input.seek(index28_18);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA28_0 = input.LA(1);

                        s = -1;
                        if ( (LA28_0=='i') ) {s = 1;}

                        else if ( (LA28_0=='d') ) {s = 2;}

                        else if ( (LA28_0=='u') ) {s = 3;}

                        else if ( (LA28_0=='c') ) {s = 4;}

                        else if ( (LA28_0=='e') ) {s = 5;}

                        else if ( (LA28_0=='f') ) {s = 6;}

                        else if ( (LA28_0=='n') ) {s = 7;}

                        else if ( (LA28_0=='t') ) {s = 8;}

                        else if ( (LA28_0=='<') ) {s = 9;}

                        else if ( (LA28_0=='\\') ) {s = 10;}

                        else if ( (LA28_0=='a') ) {s = 11;}

                        else if ( (LA28_0=='|') ) {s = 12;}

                        else if ( (LA28_0=='!') ) {s = 13;}

                        else if ( (LA28_0=='$') ) {s = 14;}

                        else if ( (LA28_0=='+') ) {s = 15;}

                        else if ( (LA28_0=='-') ) {s = 16;}

                        else if ( (LA28_0=='=') ) {s = 17;}

                        else if ( (LA28_0=='>') ) {s = 18;}

                        else if ( (LA28_0=='o') ) {s = 19;}

                        else if ( (LA28_0=='~') ) {s = 20;}

                        else if ( (LA28_0=='\"') ) {s = 21;}

                        else if ( (LA28_0=='%') ) {s = 22;}

                        else if ( (LA28_0=='\'') ) {s = 23;}

                        else if ( (LA28_0=='(') ) {s = 24;}

                        else if ( (LA28_0==')') ) {s = 25;}

                        else if ( (LA28_0=='*') ) {s = 26;}

                        else if ( (LA28_0==',') ) {s = 27;}

                        else if ( (LA28_0=='.') ) {s = 28;}

                        else if ( (LA28_0=='/') ) {s = 29;}

                        else if ( (LA28_0==':') ) {s = 30;}

                        else if ( (LA28_0==';') ) {s = 31;}

                        else if ( (LA28_0=='?') ) {s = 32;}

                        else if ( (LA28_0=='@') ) {s = 33;}

                        else if ( (LA28_0=='[') ) {s = 34;}

                        else if ( (LA28_0==']') ) {s = 35;}

                        else if ( (LA28_0=='{') ) {s = 36;}

                        else if ( (LA28_0=='}') ) {s = 37;}

                        else if ( (LA28_0=='#') ) {s = 38;}

                        else if ( ((LA28_0>='\t' && LA28_0<='\n')||LA28_0=='\r'||LA28_0==' '||LA28_0=='\u00A0') ) {s = 39;}

                        else if ( (LA28_0=='0') ) {s = 40;}

                        else if ( (LA28_0=='b'||(LA28_0>='g' && LA28_0<='h')||(LA28_0>='j' && LA28_0<='m')||(LA28_0>='p' && LA28_0<='s')||(LA28_0>='v' && LA28_0<='z')) ) {s = 41;}

                        else if ( ((LA28_0>='A' && LA28_0<='Z')) ) {s = 42;}

                        else if ( (LA28_0=='_') ) {s = 43;}

                        else if ( ((LA28_0>='1' && LA28_0<='9')) ) {s = 44;}

                        else if ( ((LA28_0>='\u0000' && LA28_0<='\b')||(LA28_0>='\u000B' && LA28_0<='\f')||(LA28_0>='\u000E' && LA28_0<='\u001F')||LA28_0=='&'||LA28_0=='^'||LA28_0=='`'||(LA28_0>='\u007F' && LA28_0<='\u009F')||(LA28_0>='\u00A1' && LA28_0<='\uFFFF')) ) {s = 45;}

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA28_9 = input.LA(1);

                         
                        int index28_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA28_9=='<') && ((isNotInString()))) {s = 58;}

                        else if ( (LA28_9=='-') && ((isNotInString()))) {s = 59;}

                        else if ( (LA28_9=='=') && ((isNotInString()))) {s = 60;}

                        else if ( (LA28_9=='|') && ((isNotInString()))) {s = 61;}

                        else if ( (LA28_9=='~') && ((isNotInString()))) {s = 62;}

                        else s = 63;

                         
                        input.seek(index28_9);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA28_69 = input.LA(1);

                         
                        int index28_69 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA28_69=='>') && ((isNotInString()))) {s = 137;}

                        else s = 138;

                         
                        input.seek(index28_69);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA28_16 = input.LA(1);

                         
                        int index28_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA28_16=='>') && ((isNotInString()))) {s = 79;}

                        else s = 80;

                         
                        input.seek(index28_16);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA28_211 = input.LA(1);

                         
                        int index28_211 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 220;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_211);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA28_213 = input.LA(1);

                         
                        int index28_213 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 222;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_213);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA28_221 = input.LA(1);

                         
                        int index28_221 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 225;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_221);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA28_224 = input.LA(1);

                         
                        int index28_224 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 226;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_224);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA28_207 = input.LA(1);

                         
                        int index28_207 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 218;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_207);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA28_205 = input.LA(1);

                         
                        int index28_205 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 217;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_205);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA28_203 = input.LA(1);

                         
                        int index28_203 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 216;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_203);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA28_214 = input.LA(1);

                         
                        int index28_214 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 223;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_214);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA28_192 = input.LA(1);

                         
                        int index28_192 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 206;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_192);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA28_194 = input.LA(1);

                         
                        int index28_194 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 208;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_194);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA28_202 = input.LA(1);

                         
                        int index28_202 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 215;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_202);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA28_190 = input.LA(1);

                         
                        int index28_190 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 204;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_190);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA28_181 = input.LA(1);

                         
                        int index28_181 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 196;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_181);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA28_195 = input.LA(1);

                         
                        int index28_195 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 209;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_195);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA28_121 = input.LA(1);

                         
                        int index28_121 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 169;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_121);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA28_144 = input.LA(1);

                         
                        int index28_144 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 182;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_144);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA28_119 = input.LA(1);

                         
                        int index28_119 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 167;}

                        else if ( (true) ) {s = 49;}

                         
                        input.seek(index28_119);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA28_20 = input.LA(1);

                         
                        int index28_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA28_20=='>') && ((isNotInString()))) {s = 89;}

                        else s = 45;

                         
                        input.seek(index28_20);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA28_92 = input.LA(1);

                         
                        int index28_92 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((!doubleQuotedString)) ) {s = 147;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_92);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA28_93 = input.LA(1);

                         
                        int index28_93 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 148;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_93);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA28_94 = input.LA(1);

                         
                        int index28_94 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 149;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_94);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA28_95 = input.LA(1);

                         
                        int index28_95 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 150;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_95);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA28_78 = input.LA(1);

                         
                        int index28_78 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 141;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_78);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA28_96 = input.LA(1);

                         
                        int index28_96 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 151;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_96);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA28_17 = input.LA(1);

                         
                        int index28_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA28_17=='=') && ((isNotInString()))) {s = 81;}

                        else if ( (LA28_17=='>') && ((isNotInString()))) {s = 82;}

                        else if ( (LA28_17=='~') && ((isNotInString()))) {s = 83;}

                        else s = 84;

                         
                        input.seek(index28_17);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA28_73 = input.LA(1);

                         
                        int index28_73 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 140;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_73);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA28_90 = input.LA(1);

                         
                        int index28_90 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((!singleQuotedString)) ) {s = 145;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_90);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA28_91 = input.LA(1);

                         
                        int index28_91 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 146;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_91);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA28_110 = input.LA(1);

                         
                        int index28_110 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 109;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_110);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA28_108 = input.LA(1);

                         
                        int index28_108 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 160;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_108);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA28_70 = input.LA(1);

                         
                        int index28_70 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 139;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_70);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA28_107 = input.LA(1);

                         
                        int index28_107 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 159;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_107);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA28_106 = input.LA(1);

                         
                        int index28_106 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 158;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_106);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA28_105 = input.LA(1);

                         
                        int index28_105 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 157;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_105);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA28_104 = input.LA(1);

                         
                        int index28_104 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 156;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_104);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA28_103 = input.LA(1);

                         
                        int index28_103 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 155;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_103);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA28_87 = input.LA(1);

                         
                        int index28_87 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 143;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_87);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA28_84 = input.LA(1);

                         
                        int index28_84 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 142;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_84);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA28_63 = input.LA(1);

                         
                        int index28_63 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 133;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_63);
                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA28_102 = input.LA(1);

                         
                        int index28_102 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 154;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_102);
                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA28_29 = input.LA(1);

                         
                        int index28_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA28_29=='*') && ((isNotInString()))) {s = 98;}

                        else if ( ((LA28_29>='\u0000' && LA28_29<='\t')||(LA28_29>='\u000B' && LA28_29<=')')||(LA28_29>='+' && LA28_29<='.')||(LA28_29>='0' && LA28_29<='\uFFFF')) && ((isReAcceptable()))) {s = 100;}

                        else s = 99;

                         
                        input.seek(index28_29);
                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA28_101 = input.LA(1);

                         
                        int index28_101 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 153;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_101);
                        if ( s>=0 ) return s;
                        break;
                    case 50 : 
                        int LA28_99 = input.LA(1);

                         
                        int index28_99 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 152;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index28_99);
                        if ( s>=0 ) return s;
                        break;
                    case 51 : 
                        int LA28_15 = input.LA(1);

                         
                        int index28_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA28_15=='=') && ((isNotInString()))) {s = 76;}

                        else if ( (LA28_15=='>') && ((isNotInString()))) {s = 77;}

                        else s = 78;

                         
                        input.seek(index28_15);
                        if ( s>=0 ) return s;
                        break;
                    case 52 : 
                        int LA28_58 = input.LA(1);

                         
                        int index28_58 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA28_58=='|') && ((isNotInString()))) {s = 131;}

                        else s = 132;

                         
                        input.seek(index28_58);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 28, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}