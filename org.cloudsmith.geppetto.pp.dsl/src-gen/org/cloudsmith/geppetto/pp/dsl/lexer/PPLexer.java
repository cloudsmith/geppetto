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
    public static final int RULE_REGULAR_EXPRESSION=73;
    public static final int RULE_ANY_OTHER=77;
    public static final int RULE_RE_FLAGS=72;
    public static final int KEYWORD_56=8;
    public static final int KEYWORD_19=63;
    public static final int KEYWORD_55=15;
    public static final int KEYWORD_54=14;
    public static final int KEYWORD_17=61;
    public static final int KEYWORD_53=13;
    public static final int KEYWORD_18=62;
    public static final int RULE_RE_FOLLOW_CHAR=75;
    public static final int KEYWORD_52=12;
    public static final int KEYWORD_15=59;
    public static final int KEYWORD_51=19;
    public static final int KEYWORD_16=60;
    public static final int KEYWORD_13=57;
    public static final int KEYWORD_50=18;
    public static final int KEYWORD_14=58;
    public static final int KEYWORD_11=55;
    public static final int EOF=-1;
    public static final int KEYWORD_12=56;
    public static final int KEYWORD_10=54;
    public static final int KEYWORD_59=11;
    public static final int KEYWORD_58=10;
    public static final int KEYWORD_57=9;
    public static final int KEYWORD_6=50;
    public static final int KEYWORD_7=51;
    public static final int KEYWORD_8=52;
    public static final int KEYWORD_9=53;
    public static final int KEYWORD_28=25;
    public static final int KEYWORD_29=26;
    public static final int KEYWORD_61=7;
    public static final int KEYWORD_24=21;
    public static final int KEYWORD_60=6;
    public static final int KEYWORD_25=22;
    public static final int KEYWORD_63=4;
    public static final int KEYWORD_26=23;
    public static final int RULE_RE_FIRST_CHAR=74;
    public static final int KEYWORD_62=5;
    public static final int KEYWORD_27=24;
    public static final int KEYWORD_20=64;
    public static final int KEYWORD_21=65;
    public static final int KEYWORD_22=66;
    public static final int KEYWORD_23=20;
    public static final int RULE_RE_BACKSLASH_SEQUENCE=76;
    public static final int KEYWORD_30=27;
    public static final int KEYWORD_1=45;
    public static final int KEYWORD_34=31;
    public static final int KEYWORD_5=49;
    public static final int KEYWORD_33=30;
    public static final int KEYWORD_4=48;
    public static final int RULE_WORD_CHARS=70;
    public static final int KEYWORD_32=29;
    public static final int KEYWORD_3=47;
    public static final int KEYWORD_31=28;
    public static final int KEYWORD_2=46;
    public static final int RULE_NS=78;
    public static final int KEYWORD_38=35;
    public static final int KEYWORD_37=34;
    public static final int RULE_SL_COMMENT=68;
    public static final int RULE_RE_BODY=71;
    public static final int KEYWORD_36=33;
    public static final int KEYWORD_35=32;
    public static final int RULE_ML_COMMENT=67;
    public static final int KEYWORD_39=36;
    public static final int KEYWORD_41=38;
    public static final int KEYWORD_40=37;
    public static final int KEYWORD_43=40;
    public static final int KEYWORD_42=39;
    public static final int KEYWORD_45=42;
    public static final int KEYWORD_44=41;
    public static final int KEYWORD_47=44;
    public static final int RULE_WS=69;
    public static final int KEYWORD_46=43;
    public static final int KEYWORD_49=17;
    public static final int KEYWORD_48=16;

      private Map<String, Integer> literals = getLiteralsMap();

      private static Map<String, Integer> getLiteralsMap() {
     	Map<String, Integer> result = new HashMap<String, Integer>();  
      	result.put("inherits", KEYWORD_63 );
    	result.put("default", KEYWORD_62 );
    	result.put("import", KEYWORD_61 );
    	result.put("define", KEYWORD_60 );
    	result.put("undef", KEYWORD_59 );
    	result.put("false", KEYWORD_58 );
    	result.put("elsif", KEYWORD_57 );
    	result.put("class", KEYWORD_56 );
    	result.put("true", KEYWORD_55 );
    	result.put("node", KEYWORD_54 );
    	result.put("else", KEYWORD_53 );
    	result.put("case", KEYWORD_52 );
    	result.put("and", KEYWORD_50 );
    	result.put("if", KEYWORD_43 );
    	result.put("in", KEYWORD_44 );
    	result.put("or", KEYWORD_45 );
    	return result;
      }
      private boolean isReAcceptable() {
      	if(singleQuotedString || doubleQuotedString)
      		return false;
      	// accept after ',' 'node', '{','}, '=~', '!~'
      	switch(lastSignificantToken) {
      		// NOTE: Must manually make sure these refer to the correct KEYWORD numbers
      		case KEYWORD_9 : // ','
      		case KEYWORD_54 : // 'node'
      		case KEYWORD_21 : // '{'
      		case KEYWORD_22 : // '}'
      		case KEYWORD_36 : // '=~'
      		case KEYWORD_24 : // '!~'
      		case KEYWORD_63 : // 'inherits'
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

    // $ANTLR start "KEYWORD_63"
    public final void mKEYWORD_63() throws RecognitionException {
        try {
            int _type = KEYWORD_63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:127:12: ({...}? => 'inherits' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:127:14: {...}? => 'inherits'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_63", "isNotInString()");
            }
            match("inherits"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_63"

    // $ANTLR start "KEYWORD_62"
    public final void mKEYWORD_62() throws RecognitionException {
        try {
            int _type = KEYWORD_62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:129:12: ({...}? => 'default' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:129:14: {...}? => 'default'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_62", "isNotInString()");
            }
            match("default"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_62"

    // $ANTLR start "KEYWORD_60"
    public final void mKEYWORD_60() throws RecognitionException {
        try {
            int _type = KEYWORD_60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:131:12: ({...}? => 'define' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:131:14: {...}? => 'define'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_60", "isNotInString()");
            }
            match("define"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_60"

    // $ANTLR start "KEYWORD_61"
    public final void mKEYWORD_61() throws RecognitionException {
        try {
            int _type = KEYWORD_61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:133:12: ({...}? => 'import' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:133:14: {...}? => 'import'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_61", "isNotInString()");
            }
            match("import"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_61"

    // $ANTLR start "KEYWORD_56"
    public final void mKEYWORD_56() throws RecognitionException {
        try {
            int _type = KEYWORD_56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:135:12: ({...}? => 'class' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:135:14: {...}? => 'class'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_56", "isNotInString()");
            }
            match("class"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_56"

    // $ANTLR start "KEYWORD_57"
    public final void mKEYWORD_57() throws RecognitionException {
        try {
            int _type = KEYWORD_57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:137:12: ({...}? => 'elsif' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:137:14: {...}? => 'elsif'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_57", "isNotInString()");
            }
            match("elsif"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_57"

    // $ANTLR start "KEYWORD_58"
    public final void mKEYWORD_58() throws RecognitionException {
        try {
            int _type = KEYWORD_58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:139:12: ({...}? => 'false' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:139:14: {...}? => 'false'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_58", "isNotInString()");
            }
            match("false"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_58"

    // $ANTLR start "KEYWORD_59"
    public final void mKEYWORD_59() throws RecognitionException {
        try {
            int _type = KEYWORD_59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:141:12: ({...}? => 'undef' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:141:14: {...}? => 'undef'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_59", "isNotInString()");
            }
            match("undef"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_59"

    // $ANTLR start "KEYWORD_52"
    public final void mKEYWORD_52() throws RecognitionException {
        try {
            int _type = KEYWORD_52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:143:12: ({...}? => 'case' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:143:14: {...}? => 'case'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_52", "isNotInString()");
            }
            match("case"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_52"

    // $ANTLR start "KEYWORD_53"
    public final void mKEYWORD_53() throws RecognitionException {
        try {
            int _type = KEYWORD_53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:145:12: ({...}? => 'else' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:145:14: {...}? => 'else'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_53", "isNotInString()");
            }
            match("else"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_53"

    // $ANTLR start "KEYWORD_54"
    public final void mKEYWORD_54() throws RecognitionException {
        try {
            int _type = KEYWORD_54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:147:12: ({...}? => 'node' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:147:14: {...}? => 'node'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_54", "isNotInString()");
            }
            match("node"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_54"

    // $ANTLR start "KEYWORD_55"
    public final void mKEYWORD_55() throws RecognitionException {
        try {
            int _type = KEYWORD_55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:149:12: ({...}? => 'true' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:149:14: {...}? => 'true'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_55", "isNotInString()");
            }
            match("true"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_55"

    // $ANTLR start "KEYWORD_48"
    public final void mKEYWORD_48() throws RecognitionException {
        try {
            int _type = KEYWORD_48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:151:12: ({...}? => '<<|' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:151:14: {...}? => '<<|'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_48", "isNotInString()");
            }
            match("<<|"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_48"

    // $ANTLR start "KEYWORD_49"
    public final void mKEYWORD_49() throws RecognitionException {
        try {
            int _type = KEYWORD_49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:153:12: ( '\\\\${' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:153:14: '\\\\${'
            {
            match("\\${"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_49"

    // $ANTLR start "KEYWORD_50"
    public final void mKEYWORD_50() throws RecognitionException {
        try {
            int _type = KEYWORD_50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:155:12: ({...}? => 'and' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:155:14: {...}? => 'and'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_50", "isNotInString()");
            }
            match("and"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_50"

    // $ANTLR start "KEYWORD_51"
    public final void mKEYWORD_51() throws RecognitionException {
        try {
            int _type = KEYWORD_51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:157:12: ({...}? => '|>>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:157:14: {...}? => '|>>'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_51", "isNotInString()");
            }
            match("|>>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_51"

    // $ANTLR start "KEYWORD_23"
    public final void mKEYWORD_23() throws RecognitionException {
        try {
            int _type = KEYWORD_23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:159:12: ({...}? => '!=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:159:14: {...}? => '!='
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_23", "isNotInString()");
            }
            match("!="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_23"

    // $ANTLR start "KEYWORD_24"
    public final void mKEYWORD_24() throws RecognitionException {
        try {
            int _type = KEYWORD_24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:161:12: ({...}? => '!~' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:161:14: {...}? => '!~'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_24", "isNotInString()");
            }
            match("!~"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_24"

    // $ANTLR start "KEYWORD_25"
    public final void mKEYWORD_25() throws RecognitionException {
        try {
            int _type = KEYWORD_25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:163:12: ( '${' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:163:14: '${'
            {
            match("${"); if (state.failed) return ;

            if ( state.backtracking==0 ) {

              	if(doubleQuotedString) {
              		// in string expression interpolation mode
              		pushDq();
              		enterBrace();
              	}

            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_25"

    // $ANTLR start "KEYWORD_26"
    public final void mKEYWORD_26() throws RecognitionException {
        try {
            int _type = KEYWORD_26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:171:12: ({...}? => '+=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:171:14: {...}? => '+='
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_26", "isNotInString()");
            }
            match("+="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_26"

    // $ANTLR start "KEYWORD_27"
    public final void mKEYWORD_27() throws RecognitionException {
        try {
            int _type = KEYWORD_27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:173:12: ({...}? => '+>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:173:14: {...}? => '+>'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_27", "isNotInString()");
            }
            match("+>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_27"

    // $ANTLR start "KEYWORD_28"
    public final void mKEYWORD_28() throws RecognitionException {
        try {
            int _type = KEYWORD_28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:175:12: ({...}? => '->' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:175:14: {...}? => '->'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_28", "isNotInString()");
            }
            match("->"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_28"

    // $ANTLR start "KEYWORD_29"
    public final void mKEYWORD_29() throws RecognitionException {
        try {
            int _type = KEYWORD_29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:179:12: ({...}? => '<-' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:179:14: {...}? => '<-'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_29", "isNotInString()");
            }
            match("<-"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_29"

    // $ANTLR start "KEYWORD_30"
    public final void mKEYWORD_30() throws RecognitionException {
        try {
            int _type = KEYWORD_30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:181:12: ({...}? => '<<' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:181:14: {...}? => '<<'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_30", "isNotInString()");
            }
            match("<<"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_30"

    // $ANTLR start "KEYWORD_31"
    public final void mKEYWORD_31() throws RecognitionException {
        try {
            int _type = KEYWORD_31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:183:12: ({...}? => '<=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:183:14: {...}? => '<='
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_31", "isNotInString()");
            }
            match("<="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_31"

    // $ANTLR start "KEYWORD_32"
    public final void mKEYWORD_32() throws RecognitionException {
        try {
            int _type = KEYWORD_32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:185:12: ({...}? => '<|' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:185:14: {...}? => '<|'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_32", "isNotInString()");
            }
            match("<|"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_32"

    // $ANTLR start "KEYWORD_33"
    public final void mKEYWORD_33() throws RecognitionException {
        try {
            int _type = KEYWORD_33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:187:12: ({...}? => '<~' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:187:14: {...}? => '<~'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_33", "isNotInString()");
            }
            match("<~"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_33"

    // $ANTLR start "KEYWORD_34"
    public final void mKEYWORD_34() throws RecognitionException {
        try {
            int _type = KEYWORD_34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:189:12: ({...}? => '==' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:189:14: {...}? => '=='
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_34", "isNotInString()");
            }
            match("=="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_34"

    // $ANTLR start "KEYWORD_35"
    public final void mKEYWORD_35() throws RecognitionException {
        try {
            int _type = KEYWORD_35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:191:12: ({...}? => '=>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:191:14: {...}? => '=>'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_35", "isNotInString()");
            }
            match("=>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_35"

    // $ANTLR start "KEYWORD_36"
    public final void mKEYWORD_36() throws RecognitionException {
        try {
            int _type = KEYWORD_36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:193:12: ({...}? => '=~' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:193:14: {...}? => '=~'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_36", "isNotInString()");
            }
            match("=~"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_36"

    // $ANTLR start "KEYWORD_37"
    public final void mKEYWORD_37() throws RecognitionException {
        try {
            int _type = KEYWORD_37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:195:12: ({...}? => '>=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:195:14: {...}? => '>='
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_37", "isNotInString()");
            }
            match(">="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_37"

    // $ANTLR start "KEYWORD_38"
    public final void mKEYWORD_38() throws RecognitionException {
        try {
            int _type = KEYWORD_38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:197:12: ({...}? => '>>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:197:14: {...}? => '>>'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_38", "isNotInString()");
            }
            match(">>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_38"

    // $ANTLR start "KEYWORD_39"
    public final void mKEYWORD_39() throws RecognitionException {
        try {
            int _type = KEYWORD_39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:199:12: ( '\\\\\"' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:199:14: '\\\\\"'
            {
            match("\\\""); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_39"

    // $ANTLR start "KEYWORD_40"
    public final void mKEYWORD_40() throws RecognitionException {
        try {
            int _type = KEYWORD_40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:201:12: ( '\\\\$' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:201:14: '\\\\$'
            {
            match("\\$"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_40"

    // $ANTLR start "KEYWORD_41"
    public final void mKEYWORD_41() throws RecognitionException {
        try {
            int _type = KEYWORD_41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:203:12: ( '\\\\\\'' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:203:14: '\\\\\\''
            {
            match("\\'"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_41"

    // $ANTLR start "KEYWORD_42"
    public final void mKEYWORD_42() throws RecognitionException {
        try {
            int _type = KEYWORD_42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:205:12: ( '\\\\\\\\' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:205:14: '\\\\\\\\'
            {
            match("\\\\"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_42"

    // $ANTLR start "KEYWORD_43"
    public final void mKEYWORD_43() throws RecognitionException {
        try {
            int _type = KEYWORD_43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:207:12: ({...}? => 'if' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:207:14: {...}? => 'if'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_43", "isNotInString()");
            }
            match("if"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_43"

    // $ANTLR start "KEYWORD_44"
    public final void mKEYWORD_44() throws RecognitionException {
        try {
            int _type = KEYWORD_44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:209:12: ({...}? => 'in' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:209:14: {...}? => 'in'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_44", "isNotInString()");
            }
            match("in"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_44"

    // $ANTLR start "KEYWORD_45"
    public final void mKEYWORD_45() throws RecognitionException {
        try {
            int _type = KEYWORD_45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:211:12: ({...}? => 'or' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:211:14: {...}? => 'or'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_45", "isNotInString()");
            }
            match("or"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_45"

    // $ANTLR start "KEYWORD_46"
    public final void mKEYWORD_46() throws RecognitionException {
        try {
            int _type = KEYWORD_46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:213:12: ({...}? => '|>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:213:14: {...}? => '|>'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_46", "isNotInString()");
            }
            match("|>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_46"

    // $ANTLR start "KEYWORD_47"
    public final void mKEYWORD_47() throws RecognitionException {
        try {
            int _type = KEYWORD_47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:215:12: ({...}? => '~>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:215:14: {...}? => '~>'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_47", "isNotInString()");
            }
            match("~>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_47"

    // $ANTLR start "KEYWORD_1"
    public final void mKEYWORD_1() throws RecognitionException {
        try {
            int _type = KEYWORD_1;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:217:11: ({...}? => '!' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:217:13: {...}? => '!'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_1", "isNotInString()");
            }
            match('!'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_1"

    // $ANTLR start "KEYWORD_2"
    public final void mKEYWORD_2() throws RecognitionException {
        try {
            int _type = KEYWORD_2;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:219:11: ({...}? => '\"' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:219:13: {...}? => '\"'
            {
            if ( !((!singleQuotedString)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_2", "!singleQuotedString");
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
    // $ANTLR end "KEYWORD_2"

    // $ANTLR start "KEYWORD_3"
    public final void mKEYWORD_3() throws RecognitionException {
        try {
            int _type = KEYWORD_3;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:224:11: ( '$' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:224:13: '$'
            {
            match('$'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_3"

    // $ANTLR start "KEYWORD_4"
    public final void mKEYWORD_4() throws RecognitionException {
        try {
            int _type = KEYWORD_4;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:226:11: ({...}? => '\\'' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:226:13: {...}? => '\\''
            {
            if ( !((!doubleQuotedString)) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_4", "!doubleQuotedString");
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
    // $ANTLR end "KEYWORD_4"

    // $ANTLR start "KEYWORD_5"
    public final void mKEYWORD_5() throws RecognitionException {
        try {
            int _type = KEYWORD_5;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:231:11: ({...}? => '(' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:231:13: {...}? => '('
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_5", "isNotInString()");
            }
            match('('); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_5"

    // $ANTLR start "KEYWORD_6"
    public final void mKEYWORD_6() throws RecognitionException {
        try {
            int _type = KEYWORD_6;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:233:11: ({...}? => ')' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:233:13: {...}? => ')'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_6", "isNotInString()");
            }
            match(')'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_6"

    // $ANTLR start "KEYWORD_7"
    public final void mKEYWORD_7() throws RecognitionException {
        try {
            int _type = KEYWORD_7;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:235:11: ({...}? => '*' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:235:13: {...}? => '*'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_7", "isNotInString()");
            }
            match('*'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_7"

    // $ANTLR start "KEYWORD_8"
    public final void mKEYWORD_8() throws RecognitionException {
        try {
            int _type = KEYWORD_8;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:237:11: ({...}? => '+' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:237:13: {...}? => '+'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_8", "isNotInString()");
            }
            match('+'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_8"

    // $ANTLR start "KEYWORD_9"
    public final void mKEYWORD_9() throws RecognitionException {
        try {
            int _type = KEYWORD_9;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:239:11: ({...}? => ',' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:239:13: {...}? => ','
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_9", "isNotInString()");
            }
            match(','); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_9"

    // $ANTLR start "KEYWORD_10"
    public final void mKEYWORD_10() throws RecognitionException {
        try {
            int _type = KEYWORD_10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:241:12: ( '-' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:241:14: '-'
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
    // $ANTLR end "KEYWORD_10"

    // $ANTLR start "KEYWORD_11"
    public final void mKEYWORD_11() throws RecognitionException {
        try {
            int _type = KEYWORD_11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:246:12: ({...}? => '/' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:246:14: {...}? => '/'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_11", "isNotInString()");
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
    // $ANTLR end "KEYWORD_11"

    // $ANTLR start "KEYWORD_12"
    public final void mKEYWORD_12() throws RecognitionException {
        try {
            int _type = KEYWORD_12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:253:12: ({...}? => ':' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:253:14: {...}? => ':'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_12", "isNotInString()");
            }
            match(':'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_12"

    // $ANTLR start "KEYWORD_13"
    public final void mKEYWORD_13() throws RecognitionException {
        try {
            int _type = KEYWORD_13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:255:12: ({...}? => ';' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:255:14: {...}? => ';'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_13", "isNotInString()");
            }
            match(';'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_13"

    // $ANTLR start "KEYWORD_14"
    public final void mKEYWORD_14() throws RecognitionException {
        try {
            int _type = KEYWORD_14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:257:12: ({...}? => '<' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:257:14: {...}? => '<'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_14", "isNotInString()");
            }
            match('<'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_14"

    // $ANTLR start "KEYWORD_15"
    public final void mKEYWORD_15() throws RecognitionException {
        try {
            int _type = KEYWORD_15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:259:12: ({...}? => '=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:259:14: {...}? => '='
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_15", "isNotInString()");
            }
            match('='); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_15"

    // $ANTLR start "KEYWORD_16"
    public final void mKEYWORD_16() throws RecognitionException {
        try {
            int _type = KEYWORD_16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:261:12: ({...}? => '>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:261:14: {...}? => '>'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_16", "isNotInString()");
            }
            match('>'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_16"

    // $ANTLR start "KEYWORD_17"
    public final void mKEYWORD_17() throws RecognitionException {
        try {
            int _type = KEYWORD_17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:263:12: ({...}? => '?' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:263:14: {...}? => '?'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_17", "isNotInString()");
            }
            match('?'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_17"

    // $ANTLR start "KEYWORD_18"
    public final void mKEYWORD_18() throws RecognitionException {
        try {
            int _type = KEYWORD_18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:265:12: ({...}? => '@' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:265:14: {...}? => '@'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_18", "isNotInString()");
            }
            match('@'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_18"

    // $ANTLR start "KEYWORD_19"
    public final void mKEYWORD_19() throws RecognitionException {
        try {
            int _type = KEYWORD_19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:267:12: ({...}? => '[' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:267:14: {...}? => '['
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_19", "isNotInString()");
            }
            match('['); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_19"

    // $ANTLR start "KEYWORD_20"
    public final void mKEYWORD_20() throws RecognitionException {
        try {
            int _type = KEYWORD_20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:269:12: ({...}? => ']' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:269:14: {...}? => ']'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_20", "isNotInString()");
            }
            match(']'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_20"

    // $ANTLR start "KEYWORD_21"
    public final void mKEYWORD_21() throws RecognitionException {
        try {
            int _type = KEYWORD_21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:271:12: ({...}? => '{' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:271:14: {...}? => '{'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_21", "isNotInString()");
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
    // $ANTLR end "KEYWORD_21"

    // $ANTLR start "KEYWORD_22"
    public final void mKEYWORD_22() throws RecognitionException {
        try {
            int _type = KEYWORD_22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:273:12: ({...}? => '}' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:273:14: {...}? => '}'
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "KEYWORD_22", "isNotInString()");
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
    // $ANTLR end "KEYWORD_22"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:17: ({...}? => ( ( '/*' ( options {greedy=false; } : . )* '*/' ) ( ' ' | '\\u00A0' | '\\t' )* ( ( '\\r' )? '\\n' )? ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:19: {...}? => ( ( '/*' ( options {greedy=false; } : . )* '*/' ) ( ' ' | '\\u00A0' | '\\t' )* ( ( '\\r' )? '\\n' )? )
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "RULE_ML_COMMENT", "isNotInString()");
            }
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:40: ( ( '/*' ( options {greedy=false; } : . )* '*/' ) ( ' ' | '\\u00A0' | '\\t' )* ( ( '\\r' )? '\\n' )? )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:41: ( '/*' ( options {greedy=false; } : . )* '*/' ) ( ' ' | '\\u00A0' | '\\t' )* ( ( '\\r' )? '\\n' )?
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:41: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:42: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); if (state.failed) return ;

            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:47: ( options {greedy=false; } : . )*
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
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:75: .
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

            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:85: ( ' ' | '\\u00A0' | '\\t' )*
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

            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:106: ( ( '\\r' )? '\\n' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\n'||LA4_0=='\r') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:107: ( '\\r' )? '\\n'
                    {
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:107: ( '\\r' )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0=='\r') ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:276:107: '\\r'
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
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:280:17: ({...}? => '#' (~ ( ( '\\r' | '\\n' ) ) )* ( ( '\\r' )? '\\n' )? )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:280:19: {...}? => '#' (~ ( ( '\\r' | '\\n' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            if ( !((isNotInString())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "RULE_SL_COMMENT", "isNotInString()");
            }
            match('#'); if (state.failed) return ;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:280:44: (~ ( ( '\\r' | '\\n' ) ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='\u0000' && LA5_0<='\t')||(LA5_0>='\u000B' && LA5_0<='\f')||(LA5_0>='\u000E' && LA5_0<='\uFFFF')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:280:44: ~ ( ( '\\r' | '\\n' ) )
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

            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:280:60: ( ( '\\r' )? '\\n' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='\n'||LA7_0=='\r') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:280:61: ( '\\r' )? '\\n'
                    {
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:280:61: ( '\\r' )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0=='\r') ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:280:61: '\\r'
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
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:282:9: ( ( ' ' | '\\u00A0' | '\\t' | '\\r' | '\\n' )+ )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:282:11: ( ' ' | '\\u00A0' | '\\t' | '\\r' | '\\n' )+
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:282:11: ( ' ' | '\\u00A0' | '\\t' | '\\r' | '\\n' )+
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


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_WORD_CHARS"
    public final void mRULE_WORD_CHARS() throws RecognitionException {
        try {
            int _type = RULE_WORD_CHARS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:17: ( ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '.' | ( ':' ':' )=> RULE_NS ) ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '.' | '-' | ( ':' ':' )=> RULE_NS )* )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:19: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '.' | ( ':' ':' )=> RULE_NS ) ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '.' | '-' | ( ':' ':' )=> RULE_NS )*
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:19: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '.' | ( ':' ':' )=> RULE_NS )
            int alt9=6;
            int LA9_0 = input.LA(1);

            if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                alt9=1;
            }
            else if ( ((LA9_0>='a' && LA9_0<='z')) ) {
                alt9=2;
            }
            else if ( ((LA9_0>='A' && LA9_0<='Z')) ) {
                alt9=3;
            }
            else if ( (LA9_0=='_') ) {
                alt9=4;
            }
            else if ( (LA9_0=='.') ) {
                alt9=5;
            }
            else if ( (LA9_0==':') && (synpred1_PPLexer())) {
                alt9=6;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:20: '0' .. '9'
                    {
                    matchRange('0','9'); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:29: 'a' .. 'z'
                    {
                    matchRange('a','z'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:38: 'A' .. 'Z'
                    {
                    matchRange('A','Z'); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:47: '_'
                    {
                    match('_'); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:51: '.'
                    {
                    match('.'); if (state.failed) return ;

                    }
                    break;
                case 6 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:55: ( ':' ':' )=> RULE_NS
                    {
                    mRULE_NS(); if (state.failed) return ;

                    }
                    break;

            }

            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:75: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '.' | '-' | ( ':' ':' )=> RULE_NS )*
            loop10:
            do {
                int alt10=8;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='0' && LA10_0<='9')) ) {
                    alt10=1;
                }
                else if ( ((LA10_0>='a' && LA10_0<='z')) ) {
                    alt10=2;
                }
                else if ( ((LA10_0>='A' && LA10_0<='Z')) ) {
                    alt10=3;
                }
                else if ( (LA10_0=='_') ) {
                    alt10=4;
                }
                else if ( (LA10_0=='.') ) {
                    alt10=5;
                }
                else if ( (LA10_0=='-') ) {
                    alt10=6;
                }
                else if ( (LA10_0==':') && (synpred2_PPLexer())) {
                    alt10=7;
                }


                switch (alt10) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:76: '0' .. '9'
            	    {
            	    matchRange('0','9'); if (state.failed) return ;

            	    }
            	    break;
            	case 2 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:85: 'a' .. 'z'
            	    {
            	    matchRange('a','z'); if (state.failed) return ;

            	    }
            	    break;
            	case 3 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:94: 'A' .. 'Z'
            	    {
            	    matchRange('A','Z'); if (state.failed) return ;

            	    }
            	    break;
            	case 4 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:103: '_'
            	    {
            	    match('_'); if (state.failed) return ;

            	    }
            	    break;
            	case 5 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:107: '.'
            	    {
            	    match('.'); if (state.failed) return ;

            	    }
            	    break;
            	case 6 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:111: '-'
            	    {
            	    match('-'); if (state.failed) return ;

            	    }
            	    break;
            	case 7 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:115: ( ':' ':' )=> RULE_NS
            	    {
            	    mRULE_NS(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop10;
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

    // $ANTLR start "RULE_REGULAR_EXPRESSION"
    public final void mRULE_REGULAR_EXPRESSION() throws RecognitionException {
        try {
            int _type = RULE_REGULAR_EXPRESSION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:290:25: ({...}? => '/' RULE_RE_BODY '/' ( RULE_RE_FLAGS )? )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:290:27: {...}? => '/' RULE_RE_BODY '/' ( RULE_RE_FLAGS )?
            {
            if ( !((isReAcceptable())) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "RULE_REGULAR_EXPRESSION", "isReAcceptable()");
            }
            match('/'); if (state.failed) return ;
            mRULE_RE_BODY(); if (state.failed) return ;
            match('/'); if (state.failed) return ;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:290:69: ( RULE_RE_FLAGS )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( ((LA11_0>='a' && LA11_0<='z')) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:290:69: RULE_RE_FLAGS
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

    // $ANTLR start "RULE_NS"
    public final void mRULE_NS() throws RecognitionException {
        try {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:292:18: ( '::' )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:292:20: '::'
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
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:294:23: ( ( RULE_RE_FIRST_CHAR ( RULE_RE_FOLLOW_CHAR )* ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:294:25: ( RULE_RE_FIRST_CHAR ( RULE_RE_FOLLOW_CHAR )* )
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:294:25: ( RULE_RE_FIRST_CHAR ( RULE_RE_FOLLOW_CHAR )* )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:294:26: RULE_RE_FIRST_CHAR ( RULE_RE_FOLLOW_CHAR )*
            {
            mRULE_RE_FIRST_CHAR(); if (state.failed) return ;
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:294:45: ( RULE_RE_FOLLOW_CHAR )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>='\u0000' && LA12_0<='\t')||(LA12_0>='\u000B' && LA12_0<='.')||(LA12_0>='0' && LA12_0<='\uFFFF')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:294:45: RULE_RE_FOLLOW_CHAR
            	    {
            	    mRULE_RE_FOLLOW_CHAR(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop12;
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
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:296:29: ( (~ ( ( '\\n' | '*' | '/' | '\\\\' ) ) | RULE_RE_BACKSLASH_SEQUENCE ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:296:31: (~ ( ( '\\n' | '*' | '/' | '\\\\' ) ) | RULE_RE_BACKSLASH_SEQUENCE )
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:296:31: (~ ( ( '\\n' | '*' | '/' | '\\\\' ) ) | RULE_RE_BACKSLASH_SEQUENCE )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>='\u0000' && LA13_0<='\t')||(LA13_0>='\u000B' && LA13_0<=')')||(LA13_0>='+' && LA13_0<='.')||(LA13_0>='0' && LA13_0<='[')||(LA13_0>=']' && LA13_0<='\uFFFF')) ) {
                alt13=1;
            }
            else if ( (LA13_0=='\\') ) {
                alt13=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:296:32: ~ ( ( '\\n' | '*' | '/' | '\\\\' ) )
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
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:296:55: RULE_RE_BACKSLASH_SEQUENCE
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
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:298:30: ( ( RULE_RE_FIRST_CHAR | '*' ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:298:32: ( RULE_RE_FIRST_CHAR | '*' )
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:298:32: ( RULE_RE_FIRST_CHAR | '*' )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>='\u0000' && LA14_0<='\t')||(LA14_0>='\u000B' && LA14_0<=')')||(LA14_0>='+' && LA14_0<='.')||(LA14_0>='0' && LA14_0<='\uFFFF')) ) {
                alt14=1;
            }
            else if ( (LA14_0=='*') ) {
                alt14=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:298:33: RULE_RE_FIRST_CHAR
                    {
                    mRULE_RE_FIRST_CHAR(); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:298:52: '*'
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
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:300:37: ( ( '\\\\' ~ ( '\\n' ) ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:300:39: ( '\\\\' ~ ( '\\n' ) )
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:300:39: ( '\\\\' ~ ( '\\n' ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:300:40: '\\\\' ~ ( '\\n' )
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
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:302:24: ( ( 'a' .. 'z' )+ )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:302:26: ( 'a' .. 'z' )+
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:302:26: ( 'a' .. 'z' )+
            int cnt15=0;
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( ((LA15_0>='a' && LA15_0<='z')) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:302:27: 'a' .. 'z'
            	    {
            	    matchRange('a','z'); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt15 >= 1 ) break loop15;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(15, input);
                        throw eee;
                }
                cnt15++;
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
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:304:16: ( . )
            // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:304:18: .
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
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:8: ( KEYWORD_63 | KEYWORD_62 | KEYWORD_60 | KEYWORD_61 | KEYWORD_56 | KEYWORD_57 | KEYWORD_58 | KEYWORD_59 | KEYWORD_52 | KEYWORD_53 | KEYWORD_54 | KEYWORD_55 | KEYWORD_48 | KEYWORD_49 | KEYWORD_50 | KEYWORD_51 | KEYWORD_23 | KEYWORD_24 | KEYWORD_25 | KEYWORD_26 | KEYWORD_27 | KEYWORD_28 | KEYWORD_29 | KEYWORD_30 | KEYWORD_31 | KEYWORD_32 | KEYWORD_33 | KEYWORD_34 | KEYWORD_35 | KEYWORD_36 | KEYWORD_37 | KEYWORD_38 | KEYWORD_39 | KEYWORD_40 | KEYWORD_41 | KEYWORD_42 | KEYWORD_43 | KEYWORD_44 | KEYWORD_45 | KEYWORD_46 | KEYWORD_47 | KEYWORD_1 | KEYWORD_2 | KEYWORD_3 | KEYWORD_4 | KEYWORD_5 | KEYWORD_6 | KEYWORD_7 | KEYWORD_8 | KEYWORD_9 | KEYWORD_10 | KEYWORD_11 | KEYWORD_12 | KEYWORD_13 | KEYWORD_14 | KEYWORD_15 | KEYWORD_16 | KEYWORD_17 | KEYWORD_18 | KEYWORD_19 | KEYWORD_20 | KEYWORD_21 | KEYWORD_22 | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_WORD_CHARS | RULE_REGULAR_EXPRESSION | RULE_ANY_OTHER )
        int alt16=69;
        alt16 = dfa16.predict(input);
        switch (alt16) {
            case 1 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:10: KEYWORD_63
                {
                mKEYWORD_63(); if (state.failed) return ;

                }
                break;
            case 2 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:21: KEYWORD_62
                {
                mKEYWORD_62(); if (state.failed) return ;

                }
                break;
            case 3 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:32: KEYWORD_60
                {
                mKEYWORD_60(); if (state.failed) return ;

                }
                break;
            case 4 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:43: KEYWORD_61
                {
                mKEYWORD_61(); if (state.failed) return ;

                }
                break;
            case 5 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:54: KEYWORD_56
                {
                mKEYWORD_56(); if (state.failed) return ;

                }
                break;
            case 6 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:65: KEYWORD_57
                {
                mKEYWORD_57(); if (state.failed) return ;

                }
                break;
            case 7 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:76: KEYWORD_58
                {
                mKEYWORD_58(); if (state.failed) return ;

                }
                break;
            case 8 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:87: KEYWORD_59
                {
                mKEYWORD_59(); if (state.failed) return ;

                }
                break;
            case 9 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:98: KEYWORD_52
                {
                mKEYWORD_52(); if (state.failed) return ;

                }
                break;
            case 10 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:109: KEYWORD_53
                {
                mKEYWORD_53(); if (state.failed) return ;

                }
                break;
            case 11 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:120: KEYWORD_54
                {
                mKEYWORD_54(); if (state.failed) return ;

                }
                break;
            case 12 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:131: KEYWORD_55
                {
                mKEYWORD_55(); if (state.failed) return ;

                }
                break;
            case 13 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:142: KEYWORD_48
                {
                mKEYWORD_48(); if (state.failed) return ;

                }
                break;
            case 14 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:153: KEYWORD_49
                {
                mKEYWORD_49(); if (state.failed) return ;

                }
                break;
            case 15 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:164: KEYWORD_50
                {
                mKEYWORD_50(); if (state.failed) return ;

                }
                break;
            case 16 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:175: KEYWORD_51
                {
                mKEYWORD_51(); if (state.failed) return ;

                }
                break;
            case 17 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:186: KEYWORD_23
                {
                mKEYWORD_23(); if (state.failed) return ;

                }
                break;
            case 18 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:197: KEYWORD_24
                {
                mKEYWORD_24(); if (state.failed) return ;

                }
                break;
            case 19 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:208: KEYWORD_25
                {
                mKEYWORD_25(); if (state.failed) return ;

                }
                break;
            case 20 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:219: KEYWORD_26
                {
                mKEYWORD_26(); if (state.failed) return ;

                }
                break;
            case 21 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:230: KEYWORD_27
                {
                mKEYWORD_27(); if (state.failed) return ;

                }
                break;
            case 22 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:241: KEYWORD_28
                {
                mKEYWORD_28(); if (state.failed) return ;

                }
                break;
            case 23 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:252: KEYWORD_29
                {
                mKEYWORD_29(); if (state.failed) return ;

                }
                break;
            case 24 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:263: KEYWORD_30
                {
                mKEYWORD_30(); if (state.failed) return ;

                }
                break;
            case 25 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:274: KEYWORD_31
                {
                mKEYWORD_31(); if (state.failed) return ;

                }
                break;
            case 26 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:285: KEYWORD_32
                {
                mKEYWORD_32(); if (state.failed) return ;

                }
                break;
            case 27 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:296: KEYWORD_33
                {
                mKEYWORD_33(); if (state.failed) return ;

                }
                break;
            case 28 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:307: KEYWORD_34
                {
                mKEYWORD_34(); if (state.failed) return ;

                }
                break;
            case 29 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:318: KEYWORD_35
                {
                mKEYWORD_35(); if (state.failed) return ;

                }
                break;
            case 30 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:329: KEYWORD_36
                {
                mKEYWORD_36(); if (state.failed) return ;

                }
                break;
            case 31 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:340: KEYWORD_37
                {
                mKEYWORD_37(); if (state.failed) return ;

                }
                break;
            case 32 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:351: KEYWORD_38
                {
                mKEYWORD_38(); if (state.failed) return ;

                }
                break;
            case 33 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:362: KEYWORD_39
                {
                mKEYWORD_39(); if (state.failed) return ;

                }
                break;
            case 34 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:373: KEYWORD_40
                {
                mKEYWORD_40(); if (state.failed) return ;

                }
                break;
            case 35 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:384: KEYWORD_41
                {
                mKEYWORD_41(); if (state.failed) return ;

                }
                break;
            case 36 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:395: KEYWORD_42
                {
                mKEYWORD_42(); if (state.failed) return ;

                }
                break;
            case 37 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:406: KEYWORD_43
                {
                mKEYWORD_43(); if (state.failed) return ;

                }
                break;
            case 38 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:417: KEYWORD_44
                {
                mKEYWORD_44(); if (state.failed) return ;

                }
                break;
            case 39 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:428: KEYWORD_45
                {
                mKEYWORD_45(); if (state.failed) return ;

                }
                break;
            case 40 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:439: KEYWORD_46
                {
                mKEYWORD_46(); if (state.failed) return ;

                }
                break;
            case 41 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:450: KEYWORD_47
                {
                mKEYWORD_47(); if (state.failed) return ;

                }
                break;
            case 42 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:461: KEYWORD_1
                {
                mKEYWORD_1(); if (state.failed) return ;

                }
                break;
            case 43 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:471: KEYWORD_2
                {
                mKEYWORD_2(); if (state.failed) return ;

                }
                break;
            case 44 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:481: KEYWORD_3
                {
                mKEYWORD_3(); if (state.failed) return ;

                }
                break;
            case 45 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:491: KEYWORD_4
                {
                mKEYWORD_4(); if (state.failed) return ;

                }
                break;
            case 46 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:501: KEYWORD_5
                {
                mKEYWORD_5(); if (state.failed) return ;

                }
                break;
            case 47 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:511: KEYWORD_6
                {
                mKEYWORD_6(); if (state.failed) return ;

                }
                break;
            case 48 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:521: KEYWORD_7
                {
                mKEYWORD_7(); if (state.failed) return ;

                }
                break;
            case 49 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:531: KEYWORD_8
                {
                mKEYWORD_8(); if (state.failed) return ;

                }
                break;
            case 50 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:541: KEYWORD_9
                {
                mKEYWORD_9(); if (state.failed) return ;

                }
                break;
            case 51 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:551: KEYWORD_10
                {
                mKEYWORD_10(); if (state.failed) return ;

                }
                break;
            case 52 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:562: KEYWORD_11
                {
                mKEYWORD_11(); if (state.failed) return ;

                }
                break;
            case 53 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:573: KEYWORD_12
                {
                mKEYWORD_12(); if (state.failed) return ;

                }
                break;
            case 54 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:584: KEYWORD_13
                {
                mKEYWORD_13(); if (state.failed) return ;

                }
                break;
            case 55 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:595: KEYWORD_14
                {
                mKEYWORD_14(); if (state.failed) return ;

                }
                break;
            case 56 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:606: KEYWORD_15
                {
                mKEYWORD_15(); if (state.failed) return ;

                }
                break;
            case 57 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:617: KEYWORD_16
                {
                mKEYWORD_16(); if (state.failed) return ;

                }
                break;
            case 58 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:628: KEYWORD_17
                {
                mKEYWORD_17(); if (state.failed) return ;

                }
                break;
            case 59 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:639: KEYWORD_18
                {
                mKEYWORD_18(); if (state.failed) return ;

                }
                break;
            case 60 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:650: KEYWORD_19
                {
                mKEYWORD_19(); if (state.failed) return ;

                }
                break;
            case 61 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:661: KEYWORD_20
                {
                mKEYWORD_20(); if (state.failed) return ;

                }
                break;
            case 62 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:672: KEYWORD_21
                {
                mKEYWORD_21(); if (state.failed) return ;

                }
                break;
            case 63 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:683: KEYWORD_22
                {
                mKEYWORD_22(); if (state.failed) return ;

                }
                break;
            case 64 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:694: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); if (state.failed) return ;

                }
                break;
            case 65 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:710: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); if (state.failed) return ;

                }
                break;
            case 66 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:726: RULE_WS
                {
                mRULE_WS(); if (state.failed) return ;

                }
                break;
            case 67 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:734: RULE_WORD_CHARS
                {
                mRULE_WORD_CHARS(); if (state.failed) return ;

                }
                break;
            case 68 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:750: RULE_REGULAR_EXPRESSION
                {
                mRULE_REGULAR_EXPRESSION(); if (state.failed) return ;

                }
                break;
            case 69 :
                // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:1:774: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); if (state.failed) return ;

                }
                break;

        }

    }

    // $ANTLR start synpred1_PPLexer
    public final void synpred1_PPLexer_fragment() throws RecognitionException {   
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:55: ( ':' ':' )
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:56: ':' ':'
        {
        match(':'); if (state.failed) return ;
        match(':'); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_PPLexer

    // $ANTLR start synpred2_PPLexer
    public final void synpred2_PPLexer_fragment() throws RecognitionException {   
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:115: ( ':' ':' )
        // ../org.cloudsmith.geppetto.pp.dsl/src/org/cloudsmith/geppetto/pp/dsl/lexer/PPLexer.g:285:116: ':' ':'
        {
        match(':'); if (state.failed) return ;
        match(':'); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_PPLexer

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


    protected DFA16 dfa16 = new DFA16(this);
    static final String DFA16_eotS =
        "\1\uffff\10\57\1\75\1\53\1\57\1\53\1\106\1\110\1\113\1\115\1\121"+
        "\1\124\1\57\1\53\1\127\1\130\1\131\1\132\1\133\1\134\1\136\1\140"+
        "\1\141\1\142\1\143\1\144\1\145\1\146\1\147\1\150\7\uffff\1\154\1"+
        "\57\1\156\1\uffff\10\57\1\170\5\uffff\1\173\3\uffff\1\57\1\176\21"+
        "\uffff\1\u0083\25\uffff\1\57\1\uffff\1\57\1\uffff\10\57\5\uffff"+
        "\1\u00a1\26\uffff\1\57\1\uffff\1\57\1\uffff\3\57\1\u00a8\1\57\1"+
        "\u00aa\2\57\1\u00ad\1\u00ae\2\uffff\4\57\1\u00b4\1\uffff\1\u00b6"+
        "\1\uffff\1\u00b8\1\u00b9\3\uffff\1\57\1\u00bd\1\57\1\u00bf\10\uffff"+
        "\1\57\1\uffff\1\u00c6\5\uffff\1\u00c8\6\uffff";
    static final String DFA16_eofS =
        "\u00cb\uffff";
    static final String DFA16_minS =
        "\1\0\1\146\1\145\1\141\1\154\1\141\1\156\1\157\1\162\1\55\1\42\1"+
        "\156\1\76\1\75\1\173\1\75\1\76\2\75\1\162\1\76\7\0\1\72\10\0\7\uffff"+
        "\1\55\1\160\1\55\1\uffff\1\146\1\141\2\163\1\154\2\144\1\165\1\174"+
        "\4\uffff\1\0\1\173\3\uffff\1\144\1\76\2\uffff\1\0\4\uffff\1\0\5"+
        "\uffff\1\0\2\uffff\1\0\1\55\1\uffff\6\0\1\uffff\1\0\1\uffff\11\0"+
        "\2\uffff\1\145\1\0\1\157\1\0\1\141\1\163\2\145\1\163\3\145\5\uffff"+
        "\1\55\6\uffff\1\0\17\uffff\1\162\1\uffff\1\162\1\uffff\1\165\1\156"+
        "\1\163\1\55\1\146\1\55\1\145\1\146\2\55\1\0\1\uffff\1\151\1\164"+
        "\1\154\1\145\1\55\1\0\1\55\1\0\2\55\2\0\1\uffff\1\164\1\55\1\164"+
        "\1\55\1\0\1\uffff\1\0\1\uffff\2\0\2\uffff\1\163\1\0\1\55\1\0\4\uffff"+
        "\1\55\1\uffff\1\0\1\uffff\1\0\2\uffff";
    static final String DFA16_maxS =
        "\1\uffff\1\156\1\145\2\154\1\141\1\156\1\157\1\162\1\176\1\134\1"+
        "\156\1\76\1\176\1\173\2\76\1\176\1\76\1\162\1\76\6\0\1\uffff\1\72"+
        "\7\0\1\uffff\7\uffff\1\172\1\160\1\172\1\uffff\1\146\1\141\2\163"+
        "\1\154\2\144\1\165\1\174\4\uffff\1\0\1\173\3\uffff\1\144\1\76\2"+
        "\uffff\1\0\4\uffff\1\0\5\uffff\1\0\2\uffff\1\0\1\172\1\uffff\6\0"+
        "\1\uffff\1\0\1\uffff\11\0\2\uffff\1\145\1\0\1\157\1\0\1\151\1\163"+
        "\1\145\1\151\1\163\3\145\5\uffff\1\172\6\uffff\1\0\17\uffff\1\162"+
        "\1\uffff\1\162\1\uffff\1\165\1\156\1\163\1\172\1\146\1\172\1\145"+
        "\1\146\2\172\1\0\1\uffff\1\151\1\164\1\154\1\145\1\172\1\0\1\172"+
        "\1\0\2\172\2\0\1\uffff\1\164\1\172\1\164\1\172\1\0\1\uffff\1\0\1"+
        "\uffff\2\0\2\uffff\1\163\1\0\1\172\1\0\4\uffff\1\172\1\uffff\1\0"+
        "\1\uffff\1\0\2\uffff";
    static final String DFA16_acceptS =
        "\45\uffff\1\102\5\103\1\105\3\uffff\1\103\11\uffff\1\27\1\31\1\32"+
        "\1\33\2\uffff\1\41\1\43\1\44\2\uffff\1\21\1\22\1\uffff\1\23\1\54"+
        "\1\24\1\25\1\uffff\1\26\1\63\1\34\1\35\1\36\1\uffff\1\37\1\40\2"+
        "\uffff\1\51\6\uffff\1\100\1\uffff\1\104\11\uffff\1\101\1\102\14"+
        "\uffff\1\15\1\30\1\67\1\16\1\42\1\uffff\1\20\1\50\1\52\1\61\1\70"+
        "\1\71\1\uffff\1\53\1\55\1\56\1\57\1\60\1\62\1\64\1\65\1\66\1\72"+
        "\1\73\1\74\1\75\1\76\1\77\1\uffff\1\46\1\uffff\1\45\13\uffff\1\47"+
        "\14\uffff\1\17\5\uffff\1\11\1\uffff\1\12\2\uffff\1\13\1\14\4\uffff"+
        "\1\5\1\6\1\7\1\10\1\uffff\1\4\1\uffff\1\3\1\uffff\1\2\1\1";
    static final String DFA16_specialS =
        "\1\61\10\uffff\1\14\2\uffff\1\60\1\53\1\uffff\1\16\1\55\1\13\1\56"+
        "\1\uffff\1\50\6\uffff\1\1\10\uffff\1\57\23\uffff\1\15\4\uffff\1"+
        "\4\5\uffff\1\54\2\uffff\1\37\4\uffff\1\44\5\uffff\1\3\2\uffff\1"+
        "\6\2\uffff\1\36\1\35\1\34\1\46\1\45\1\43\1\uffff\1\42\1\uffff\1"+
        "\41\1\40\1\5\1\10\1\7\1\12\1\11\1\0\1\2\3\uffff\1\52\1\uffff\1\51"+
        "\24\uffff\1\47\35\uffff\1\17\6\uffff\1\21\1\uffff\1\20\2\uffff\1"+
        "\23\1\22\5\uffff\1\24\1\uffff\1\25\1\uffff\1\26\1\27\3\uffff\1\33"+
        "\1\uffff\1\32\6\uffff\1\31\1\uffff\1\30\2\uffff}>";
    static final String[] DFA16_transitionS = {
            "\11\53\2\45\2\53\1\45\22\53\1\45\1\15\1\25\1\44\1\16\2\53\1"+
            "\26\1\27\1\30\1\31\1\17\1\32\1\20\1\52\1\33\12\46\1\34\1\35"+
            "\1\11\1\21\1\22\1\36\1\37\32\50\1\40\1\12\1\41\1\53\1\51\1\53"+
            "\1\13\1\47\1\3\1\2\1\4\1\5\2\47\1\1\4\47\1\7\1\23\4\47\1\10"+
            "\1\6\5\47\1\42\1\14\1\43\1\24\41\53\1\45\uff5f\53",
            "\1\56\6\uffff\1\55\1\54",
            "\1\60",
            "\1\62\12\uffff\1\61",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\71\16\uffff\1\70\1\72\76\uffff\1\73\1\uffff\1\74",
            "\1\77\1\uffff\1\76\2\uffff\1\100\64\uffff\1\101",
            "\1\102",
            "\1\103",
            "\1\104\100\uffff\1\105",
            "\1\107",
            "\1\111\1\112",
            "\1\114",
            "\1\116\1\117\77\uffff\1\120",
            "\1\122\1\123",
            "\1\125",
            "\1\126",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\12\137\1\uffff\37\137\1\135\4\137\1\uffff\uffd0\137",
            "\1\57",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\0\151",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\7\57"+
            "\1\153\22\57",
            "\1\155",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "",
            "\1\157",
            "\1\160",
            "\1\161",
            "\1\162",
            "\1\163",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\167",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\172",
            "",
            "",
            "",
            "\1\174",
            "\1\175",
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
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
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
            "\1\uffff",
            "",
            "",
            "\1\u0093",
            "\1\uffff",
            "\1\u0095",
            "\1\uffff",
            "\1\u0097\7\uffff\1\u0098",
            "\1\u0099",
            "\1\u009a",
            "\1\u009c\3\uffff\1\u009b",
            "\1\u009d",
            "\1\u009e",
            "\1\u009f",
            "\1\u00a0",
            "",
            "",
            "",
            "",
            "",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
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
            "\1\u00a3",
            "",
            "\1\u00a4",
            "",
            "\1\u00a5",
            "\1\u00a6",
            "\1\u00a7",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u00a9",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u00ab",
            "\1\u00ac",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\uffff",
            "",
            "\1\u00b0",
            "\1\u00b1",
            "\1\u00b2",
            "\1\u00b3",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\uffff",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\uffff",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\u00bc",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u00be",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\uffff",
            "",
            "\1\uffff",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "\1\u00c4",
            "\1\uffff",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "\2\57\1\uffff\13\57\6\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "",
            "\1\uffff",
            "",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA16_eot = DFA.unpackEncodedString(DFA16_eotS);
    static final short[] DFA16_eof = DFA.unpackEncodedString(DFA16_eofS);
    static final char[] DFA16_min = DFA.unpackEncodedStringToUnsignedChars(DFA16_minS);
    static final char[] DFA16_max = DFA.unpackEncodedStringToUnsignedChars(DFA16_maxS);
    static final short[] DFA16_accept = DFA.unpackEncodedString(DFA16_acceptS);
    static final short[] DFA16_special = DFA.unpackEncodedString(DFA16_specialS);
    static final short[][] DFA16_transition;

    static {
        int numStates = DFA16_transitionS.length;
        DFA16_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
        }
    }

    class DFA16 extends DFA {

        public DFA16(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 16;
            this.eot = DFA16_eot;
            this.eof = DFA16_eof;
            this.min = DFA16_min;
            this.max = DFA16_max;
            this.accept = DFA16_accept;
            this.special = DFA16_special;
            this.transition = DFA16_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( KEYWORD_63 | KEYWORD_62 | KEYWORD_60 | KEYWORD_61 | KEYWORD_56 | KEYWORD_57 | KEYWORD_58 | KEYWORD_59 | KEYWORD_52 | KEYWORD_53 | KEYWORD_54 | KEYWORD_55 | KEYWORD_48 | KEYWORD_49 | KEYWORD_50 | KEYWORD_51 | KEYWORD_23 | KEYWORD_24 | KEYWORD_25 | KEYWORD_26 | KEYWORD_27 | KEYWORD_28 | KEYWORD_29 | KEYWORD_30 | KEYWORD_31 | KEYWORD_32 | KEYWORD_33 | KEYWORD_34 | KEYWORD_35 | KEYWORD_36 | KEYWORD_37 | KEYWORD_38 | KEYWORD_39 | KEYWORD_40 | KEYWORD_41 | KEYWORD_42 | KEYWORD_43 | KEYWORD_44 | KEYWORD_45 | KEYWORD_46 | KEYWORD_47 | KEYWORD_1 | KEYWORD_2 | KEYWORD_3 | KEYWORD_4 | KEYWORD_5 | KEYWORD_6 | KEYWORD_7 | KEYWORD_8 | KEYWORD_9 | KEYWORD_10 | KEYWORD_11 | KEYWORD_12 | KEYWORD_13 | KEYWORD_14 | KEYWORD_15 | KEYWORD_16 | KEYWORD_17 | KEYWORD_18 | KEYWORD_19 | KEYWORD_20 | KEYWORD_21 | KEYWORD_22 | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_WORD_CHARS | RULE_REGULAR_EXPRESSION | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA16_103 = input.LA(1);

                         
                        int index16_103 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 146;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_103);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA16_27 = input.LA(1);

                         
                        int index16_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA16_27=='*') && ((isNotInString()))) {s = 93;}

                        else if ( ((LA16_27>='\u0000' && LA16_27<='\t')||(LA16_27>='\u000B' && LA16_27<=')')||(LA16_27>='+' && LA16_27<='.')||(LA16_27>='0' && LA16_27<='\uFFFF')) && ((isReAcceptable()))) {s = 95;}

                        else s = 94;

                         
                        input.seek(index16_27);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA16_104 = input.LA(1);

                         
                        int index16_104 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 105;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_104);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA16_81 = input.LA(1);

                         
                        int index16_81 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 129;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_81);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA16_61 = input.LA(1);

                         
                        int index16_61 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 121;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_61);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA16_98 = input.LA(1);

                         
                        int index16_98 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 141;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_98);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA16_84 = input.LA(1);

                         
                        int index16_84 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 130;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_84);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA16_100 = input.LA(1);

                         
                        int index16_100 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 143;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_100);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA16_99 = input.LA(1);

                         
                        int index16_99 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 142;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_99);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA16_102 = input.LA(1);

                         
                        int index16_102 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 145;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_102);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA16_101 = input.LA(1);

                         
                        int index16_101 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 144;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_101);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA16_17 = input.LA(1);

                         
                        int index16_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA16_17=='=') && ((isNotInString()))) {s = 78;}

                        else if ( (LA16_17=='>') && ((isNotInString()))) {s = 79;}

                        else if ( (LA16_17=='~') && ((isNotInString()))) {s = 80;}

                        else s = 81;

                         
                        input.seek(index16_17);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA16_9 = input.LA(1);

                         
                        int index16_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA16_9=='<') && ((isNotInString()))) {s = 56;}

                        else if ( (LA16_9=='-') && ((isNotInString()))) {s = 57;}

                        else if ( (LA16_9=='=') && ((isNotInString()))) {s = 58;}

                        else if ( (LA16_9=='|') && ((isNotInString()))) {s = 59;}

                        else if ( (LA16_9=='~') && ((isNotInString()))) {s = 60;}

                        else s = 61;

                         
                        input.seek(index16_9);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA16_56 = input.LA(1);

                         
                        int index16_56 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA16_56=='|') && ((isNotInString()))) {s = 119;}

                        else s = 120;

                         
                        input.seek(index16_56);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA16_15 = input.LA(1);

                         
                        int index16_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA16_15=='=') && ((isNotInString()))) {s = 73;}

                        else if ( (LA16_15=='>') && ((isNotInString()))) {s = 74;}

                        else s = 75;

                         
                        input.seek(index16_15);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA16_161 = input.LA(1);

                         
                        int index16_161 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 175;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_161);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA16_170 = input.LA(1);

                         
                        int index16_170 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 183;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_170);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA16_168 = input.LA(1);

                         
                        int index16_168 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 181;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_168);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA16_174 = input.LA(1);

                         
                        int index16_174 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 187;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_174);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA16_173 = input.LA(1);

                         
                        int index16_173 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 186;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_173);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA16_180 = input.LA(1);

                         
                        int index16_180 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 192;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_180);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA16_182 = input.LA(1);

                         
                        int index16_182 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 193;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_182);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA16_184 = input.LA(1);

                         
                        int index16_184 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 194;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_184);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA16_185 = input.LA(1);

                         
                        int index16_185 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 195;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_185);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA16_200 = input.LA(1);

                         
                        int index16_200 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 202;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_200);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA16_198 = input.LA(1);

                         
                        int index16_198 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 201;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_198);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA16_191 = input.LA(1);

                         
                        int index16_191 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 199;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_191);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA16_189 = input.LA(1);

                         
                        int index16_189 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 197;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_189);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA16_89 = input.LA(1);

                         
                        int index16_89 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 134;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_89);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA16_88 = input.LA(1);

                         
                        int index16_88 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((!doubleQuotedString)) ) {s = 133;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_88);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA16_87 = input.LA(1);

                         
                        int index16_87 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((!singleQuotedString)) ) {s = 132;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_87);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA16_70 = input.LA(1);

                         
                        int index16_70 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 127;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_70);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA16_97 = input.LA(1);

                         
                        int index16_97 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 140;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_97);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA16_96 = input.LA(1);

                         
                        int index16_96 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 139;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_96);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA16_94 = input.LA(1);

                         
                        int index16_94 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 138;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_94);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA16_92 = input.LA(1);

                         
                        int index16_92 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 137;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_92);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA16_75 = input.LA(1);

                         
                        int index16_75 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 128;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_75);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA16_91 = input.LA(1);

                         
                        int index16_91 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 136;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_91);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA16_90 = input.LA(1);

                         
                        int index16_90 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 135;}

                        else if ( (true) ) {s = 43;}

                         
                        input.seek(index16_90);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA16_131 = input.LA(1);

                         
                        int index16_131 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 162;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_131);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA16_20 = input.LA(1);

                         
                        int index16_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA16_20=='>') && ((isNotInString()))) {s = 86;}

                        else s = 43;

                         
                        input.seek(index16_20);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA16_110 = input.LA(1);

                         
                        int index16_110 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 150;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_110);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA16_108 = input.LA(1);

                         
                        int index16_108 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((isNotInString())) ) {s = 148;}

                        else if ( (true) ) {s = 47;}

                         
                        input.seek(index16_108);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA16_13 = input.LA(1);

                         
                        int index16_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA16_13=='=') && ((isNotInString()))) {s = 68;}

                        else if ( (LA16_13=='~') && ((isNotInString()))) {s = 69;}

                        else s = 70;

                         
                        input.seek(index16_13);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA16_67 = input.LA(1);

                         
                        int index16_67 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA16_67=='>') && ((isNotInString()))) {s = 125;}

                        else s = 126;

                         
                        input.seek(index16_67);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA16_16 = input.LA(1);

                         
                        int index16_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA16_16=='>') && ((isNotInString()))) {s = 76;}

                        else s = 77;

                         
                        input.seek(index16_16);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA16_18 = input.LA(1);

                         
                        int index16_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA16_18=='=') && ((isNotInString()))) {s = 82;}

                        else if ( (LA16_18=='>') && ((isNotInString()))) {s = 83;}

                        else s = 84;

                         
                        input.seek(index16_18);
                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA16_36 = input.LA(1);

                         
                        int index16_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA16_36>='\u0000' && LA16_36<='\uFFFF')) && ((isNotInString()))) {s = 105;}

                        else s = 104;

                         
                        input.seek(index16_36);
                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA16_12 = input.LA(1);

                         
                        int index16_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA16_12=='>') && ((isNotInString()))) {s = 67;}

                        else s = 43;

                         
                        input.seek(index16_12);
                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA16_0 = input.LA(1);

                        s = -1;
                        if ( (LA16_0=='i') ) {s = 1;}

                        else if ( (LA16_0=='d') ) {s = 2;}

                        else if ( (LA16_0=='c') ) {s = 3;}

                        else if ( (LA16_0=='e') ) {s = 4;}

                        else if ( (LA16_0=='f') ) {s = 5;}

                        else if ( (LA16_0=='u') ) {s = 6;}

                        else if ( (LA16_0=='n') ) {s = 7;}

                        else if ( (LA16_0=='t') ) {s = 8;}

                        else if ( (LA16_0=='<') ) {s = 9;}

                        else if ( (LA16_0=='\\') ) {s = 10;}

                        else if ( (LA16_0=='a') ) {s = 11;}

                        else if ( (LA16_0=='|') ) {s = 12;}

                        else if ( (LA16_0=='!') ) {s = 13;}

                        else if ( (LA16_0=='$') ) {s = 14;}

                        else if ( (LA16_0=='+') ) {s = 15;}

                        else if ( (LA16_0=='-') ) {s = 16;}

                        else if ( (LA16_0=='=') ) {s = 17;}

                        else if ( (LA16_0=='>') ) {s = 18;}

                        else if ( (LA16_0=='o') ) {s = 19;}

                        else if ( (LA16_0=='~') ) {s = 20;}

                        else if ( (LA16_0=='\"') ) {s = 21;}

                        else if ( (LA16_0=='\'') ) {s = 22;}

                        else if ( (LA16_0=='(') ) {s = 23;}

                        else if ( (LA16_0==')') ) {s = 24;}

                        else if ( (LA16_0=='*') ) {s = 25;}

                        else if ( (LA16_0==',') ) {s = 26;}

                        else if ( (LA16_0=='/') ) {s = 27;}

                        else if ( (LA16_0==':') ) {s = 28;}

                        else if ( (LA16_0==';') ) {s = 29;}

                        else if ( (LA16_0=='?') ) {s = 30;}

                        else if ( (LA16_0=='@') ) {s = 31;}

                        else if ( (LA16_0=='[') ) {s = 32;}

                        else if ( (LA16_0==']') ) {s = 33;}

                        else if ( (LA16_0=='{') ) {s = 34;}

                        else if ( (LA16_0=='}') ) {s = 35;}

                        else if ( (LA16_0=='#') ) {s = 36;}

                        else if ( ((LA16_0>='\t' && LA16_0<='\n')||LA16_0=='\r'||LA16_0==' '||LA16_0=='\u00A0') ) {s = 37;}

                        else if ( ((LA16_0>='0' && LA16_0<='9')) ) {s = 38;}

                        else if ( (LA16_0=='b'||(LA16_0>='g' && LA16_0<='h')||(LA16_0>='j' && LA16_0<='m')||(LA16_0>='p' && LA16_0<='s')||(LA16_0>='v' && LA16_0<='z')) ) {s = 39;}

                        else if ( ((LA16_0>='A' && LA16_0<='Z')) ) {s = 40;}

                        else if ( (LA16_0=='_') ) {s = 41;}

                        else if ( (LA16_0=='.') ) {s = 42;}

                        else if ( ((LA16_0>='\u0000' && LA16_0<='\b')||(LA16_0>='\u000B' && LA16_0<='\f')||(LA16_0>='\u000E' && LA16_0<='\u001F')||(LA16_0>='%' && LA16_0<='&')||LA16_0=='^'||LA16_0=='`'||(LA16_0>='\u007F' && LA16_0<='\u009F')||(LA16_0>='\u00A1' && LA16_0<='\uFFFF')) ) {s = 43;}

                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 16, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}