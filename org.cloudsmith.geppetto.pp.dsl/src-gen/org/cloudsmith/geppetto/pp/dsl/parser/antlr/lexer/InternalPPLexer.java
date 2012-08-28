package org.cloudsmith.geppetto.pp.dsl.parser.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalPPLexer extends Lexer {
    public static final int RULE_REGULAR_EXPRESSION=74;
    public static final int RULE_ANY_OTHER=78;
    public static final int RULE_RE_FLAGS=73;
    public static final int KEYWORD_19=64;
    public static final int KEYWORD_56=10;
    public static final int KEYWORD_55=9;
    public static final int KEYWORD_17=62;
    public static final int KEYWORD_54=16;
    public static final int RULE_RE_FOLLOW_CHAR=76;
    public static final int KEYWORD_18=63;
    public static final int KEYWORD_53=15;
    public static final int KEYWORD_15=60;
    public static final int KEYWORD_52=14;
    public static final int KEYWORD_16=61;
    public static final int KEYWORD_51=13;
    public static final int KEYWORD_13=58;
    public static final int KEYWORD_50=20;
    public static final int KEYWORD_14=59;
    public static final int KEYWORD_11=56;
    public static final int KEYWORD_12=57;
    public static final int EOF=-1;
    public static final int KEYWORD_10=55;
    public static final int KEYWORD_59=6;
    public static final int KEYWORD_58=12;
    public static final int KEYWORD_57=11;
    public static final int KEYWORD_6=51;
    public static final int KEYWORD_7=52;
    public static final int KEYWORD_8=53;
    public static final int KEYWORD_9=54;
    public static final int KEYWORD_28=27;
    public static final int KEYWORD_29=28;
    public static final int KEYWORD_61=8;
    public static final int KEYWORD_24=23;
    public static final int KEYWORD_60=7;
    public static final int KEYWORD_25=24;
    public static final int KEYWORD_63=4;
    public static final int KEYWORD_26=25;
    public static final int RULE_RE_FIRST_CHAR=75;
    public static final int KEYWORD_62=5;
    public static final int KEYWORD_27=26;
    public static final int KEYWORD_20=65;
    public static final int KEYWORD_21=66;
    public static final int KEYWORD_22=21;
    public static final int KEYWORD_23=22;
    public static final int RULE_RE_BACKSLASH_SEQUENCE=77;
    public static final int KEYWORD_1=46;
    public static final int KEYWORD_30=29;
    public static final int KEYWORD_5=50;
    public static final int KEYWORD_34=33;
    public static final int RULE_WORD_CHARS=71;
    public static final int KEYWORD_4=49;
    public static final int KEYWORD_33=32;
    public static final int KEYWORD_3=48;
    public static final int KEYWORD_32=31;
    public static final int KEYWORD_2=47;
    public static final int KEYWORD_31=30;
    public static final int KEYWORD_38=37;
    public static final int RULE_RE_BODY=72;
    public static final int RULE_SL_COMMENT=68;
    public static final int KEYWORD_37=36;
    public static final int KEYWORD_36=35;
    public static final int KEYWORD_35=34;
    public static final int RULE_ML_COMMENT=67;
    public static final int KEYWORD_39=38;
    public static final int KEYWORD_41=40;
    public static final int KEYWORD_40=39;
    public static final int KEYWORD_43=42;
    public static final int KEYWORD_42=41;
    public static final int KEYWORD_45=44;
    public static final int KEYWORD_44=43;
    public static final int RULE_WS=69;
    public static final int KEYWORD_47=17;
    public static final int KEYWORD_46=45;
    public static final int KEYWORD_49=19;
    public static final int KEYWORD_48=18;
    public static final int RULE_DOLLAR_VAR=70;

    // delegates
    // delegators

    public InternalPPLexer() {;} 
    public InternalPPLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalPPLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g"; }

    // $ANTLR start "KEYWORD_63"
    public final void mKEYWORD_63() throws RecognitionException {
        try {
            int _type = KEYWORD_63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:19:12: ( 'inherits' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:19:14: 'inherits'
            {
            match("inherits"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:21:12: ( 'default' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:21:14: 'default'
            {
            match("default"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_62"

    // $ANTLR start "KEYWORD_59"
    public final void mKEYWORD_59() throws RecognitionException {
        try {
            int _type = KEYWORD_59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:23:12: ( 'define' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:23:14: 'define'
            {
            match("define"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_59"

    // $ANTLR start "KEYWORD_60"
    public final void mKEYWORD_60() throws RecognitionException {
        try {
            int _type = KEYWORD_60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:25:12: ( 'import' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:25:14: 'import'
            {
            match("import"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:27:12: ( 'unless' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:27:14: 'unless'
            {
            match("unless"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_61"

    // $ANTLR start "KEYWORD_55"
    public final void mKEYWORD_55() throws RecognitionException {
        try {
            int _type = KEYWORD_55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:29:12: ( 'class' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:29:14: 'class'
            {
            match("class"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_55"

    // $ANTLR start "KEYWORD_56"
    public final void mKEYWORD_56() throws RecognitionException {
        try {
            int _type = KEYWORD_56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:31:12: ( 'elsif' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:31:14: 'elsif'
            {
            match("elsif"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:33:12: ( 'false' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:33:14: 'false'
            {
            match("false"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:35:12: ( 'undef' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:35:14: 'undef'
            {
            match("undef"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_58"

    // $ANTLR start "KEYWORD_51"
    public final void mKEYWORD_51() throws RecognitionException {
        try {
            int _type = KEYWORD_51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:37:12: ( 'case' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:37:14: 'case'
            {
            match("case"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_51"

    // $ANTLR start "KEYWORD_52"
    public final void mKEYWORD_52() throws RecognitionException {
        try {
            int _type = KEYWORD_52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:39:12: ( 'else' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:39:14: 'else'
            {
            match("else"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:41:12: ( 'node' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:41:14: 'node'
            {
            match("node"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:43:12: ( 'true' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:43:14: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_54"

    // $ANTLR start "KEYWORD_47"
    public final void mKEYWORD_47() throws RecognitionException {
        try {
            int _type = KEYWORD_47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:45:12: ( '<<|' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:45:14: '<<|'
            {
            match("<<|"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_47"

    // $ANTLR start "KEYWORD_48"
    public final void mKEYWORD_48() throws RecognitionException {
        try {
            int _type = KEYWORD_48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:47:12: ( '\\\\${' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:47:14: '\\\\${'
            {
            match("\\${"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:49:12: ( 'and' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:49:14: 'and'
            {
            match("and"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:51:12: ( '|>>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:51:14: '|>>'
            {
            match("|>>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_50"

    // $ANTLR start "KEYWORD_22"
    public final void mKEYWORD_22() throws RecognitionException {
        try {
            int _type = KEYWORD_22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:53:12: ( '!=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:53:14: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_22"

    // $ANTLR start "KEYWORD_23"
    public final void mKEYWORD_23() throws RecognitionException {
        try {
            int _type = KEYWORD_23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:55:12: ( '!~' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:55:14: '!~'
            {
            match("!~"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:57:12: ( '${' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:57:14: '${'
            {
            match("${"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:59:12: ( '+=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:59:14: '+='
            {
            match("+="); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:61:12: ( '+>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:61:14: '+>'
            {
            match("+>"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:63:12: ( '->' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:63:14: '->'
            {
            match("->"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:65:12: ( '<-' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:65:14: '<-'
            {
            match("<-"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:67:12: ( '<<' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:67:14: '<<'
            {
            match("<<"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:69:12: ( '<=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:69:14: '<='
            {
            match("<="); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:71:12: ( '<|' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:71:14: '<|'
            {
            match("<|"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:73:12: ( '<~' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:73:14: '<~'
            {
            match("<~"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:75:12: ( '==' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:75:14: '=='
            {
            match("=="); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:77:12: ( '=>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:77:14: '=>'
            {
            match("=>"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:79:12: ( '=~' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:79:14: '=~'
            {
            match("=~"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:81:12: ( '>=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:81:14: '>='
            {
            match(">="); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:83:12: ( '>>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:83:14: '>>'
            {
            match(">>"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:85:12: ( '\\\\\"' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:85:14: '\\\\\"'
            {
            match("\\\""); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:87:12: ( '\\\\$' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:87:14: '\\\\$'
            {
            match("\\$"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:89:12: ( '\\\\\\'' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:89:14: '\\\\\\''
            {
            match("\\'"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:91:12: ( '\\\\\\\\' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:91:14: '\\\\\\\\'
            {
            match("\\\\"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:93:12: ( 'if' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:93:14: 'if'
            {
            match("if"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:95:12: ( 'in' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:95:14: 'in'
            {
            match("in"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:97:12: ( 'or' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:97:14: 'or'
            {
            match("or"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:99:12: ( '|>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:99:14: '|>'
            {
            match("|>"); 


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:101:12: ( '~>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:101:14: '~>'
            {
            match("~>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_46"

    // $ANTLR start "KEYWORD_1"
    public final void mKEYWORD_1() throws RecognitionException {
        try {
            int _type = KEYWORD_1;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:103:11: ( '!' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:103:13: '!'
            {
            match('!'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:105:11: ( '\"' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:105:13: '\"'
            {
            match('\"'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:107:11: ( '\\'' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:107:13: '\\''
            {
            match('\''); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:109:11: ( '(' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:109:13: '('
            {
            match('('); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:111:11: ( ')' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:111:13: ')'
            {
            match(')'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:113:11: ( '*' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:113:13: '*'
            {
            match('*'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:115:11: ( '+' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:115:13: '+'
            {
            match('+'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:117:11: ( ',' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:117:13: ','
            {
            match(','); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:119:11: ( '-' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:119:13: '-'
            {
            match('-'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:121:12: ( '/' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:121:14: '/'
            {
            match('/'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:123:12: ( ':' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:123:14: ':'
            {
            match(':'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:125:12: ( ';' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:125:14: ';'
            {
            match(';'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:127:12: ( '<' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:127:14: '<'
            {
            match('<'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:129:12: ( '=' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:129:14: '='
            {
            match('='); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:131:12: ( '>' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:131:14: '>'
            {
            match('>'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:133:12: ( '?' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:133:14: '?'
            {
            match('?'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:135:12: ( '@' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:135:14: '@'
            {
            match('@'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:137:12: ( '[' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:137:14: '['
            {
            match('['); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:139:12: ( ']' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:139:14: ']'
            {
            match(']'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:141:12: ( '{' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:141:14: '{'
            {
            match('{'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:143:12: ( '}' )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:143:14: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEYWORD_21"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:147:17: ( '/*' ( options {greedy=false; } : . )* '*/' ( ' ' | '\\u00A0' | '\\t' )* ( ( '\\r' )? '\\n' )? )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:147:19: '/*' ( options {greedy=false; } : . )* '*/' ( ' ' | '\\u00A0' | '\\t' )* ( ( '\\r' )? '\\n' )?
            {
            match("/*"); 

            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:147:24: ( options {greedy=false; } : . )*
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
            	    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:147:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match("*/"); 

            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:147:61: ( ' ' | '\\u00A0' | '\\t' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='\t'||LA2_0==' '||LA2_0=='\u00A0') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' '||input.LA(1)=='\u00A0' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:147:82: ( ( '\\r' )? '\\n' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\n'||LA4_0=='\r') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:147:83: ( '\\r' )? '\\n'
                    {
                    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:147:83: ( '\\r' )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0=='\r') ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:147:83: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

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
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:149:17: ( '#' (~ ( ( '\\r' | '\\n' ) ) )* ( ( '\\r' )? '\\n' )? )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:149:19: '#' (~ ( ( '\\r' | '\\n' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match('#'); 
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:149:23: (~ ( ( '\\r' | '\\n' ) ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='\u0000' && LA5_0<='\t')||(LA5_0>='\u000B' && LA5_0<='\f')||(LA5_0>='\u000E' && LA5_0<='\uFFFF')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:149:23: ~ ( ( '\\r' | '\\n' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:149:39: ( ( '\\r' )? '\\n' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='\n'||LA7_0=='\r') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:149:40: ( '\\r' )? '\\n'
                    {
                    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:149:40: ( '\\r' )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0=='\r') ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:149:40: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:151:9: ( ( ' ' | '\\u00A0' | '\\t' | '\\r' | '\\n' )+ )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:151:11: ( ' ' | '\\u00A0' | '\\t' | '\\r' | '\\n' )+
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:151:11: ( ' ' | '\\u00A0' | '\\t' | '\\r' | '\\n' )+
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
            	    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' '||input.LA(1)=='\u00A0' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
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

    // $ANTLR start "RULE_DOLLAR_VAR"
    public final void mRULE_DOLLAR_VAR() throws RecognitionException {
        try {
            int _type = RULE_DOLLAR_VAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:153:17: ( '$' ( '::' )? ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+ ( '::' ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+ )* )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:153:19: '$' ( '::' )? ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+ ( '::' ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+ )*
            {
            match('$'); 
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:153:23: ( '::' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==':') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:153:23: '::'
                    {
                    match("::"); 


                    }
                    break;

            }

            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:153:29: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+
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
            	    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);

            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:153:63: ( '::' ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+ )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==':') ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:153:64: '::' ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+
            	    {
            	    match("::"); 

            	    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:153:69: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' )+
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
            	    	    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:
            	    	    {
            	    	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	    	        input.consume();

            	    	    }
            	    	    else {
            	    	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	    	        recover(mse);
            	    	        throw mse;}


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt11 >= 1 ) break loop11;
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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:155:17: ( ( '::' )? ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '.' | '-' )+ ( '::' ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '.' | '-' )+ )* )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:155:19: ( '::' )? ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '.' | '-' )+ ( '::' ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '.' | '-' )+ )*
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:155:19: ( '::' )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==':') ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:155:19: '::'
                    {
                    match("::"); 


                    }
                    break;

            }

            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:155:25: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '.' | '-' )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>='-' && LA14_0<='.')||(LA14_0>='0' && LA14_0<='9')||(LA14_0>='A' && LA14_0<='Z')||LA14_0=='_'||(LA14_0>='a' && LA14_0<='z')) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:
            	    {
            	    if ( (input.LA(1)>='-' && input.LA(1)<='.')||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);

            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:155:67: ( '::' ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '.' | '-' )+ )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==':') ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:155:68: '::' ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '.' | '-' )+
            	    {
            	    match("::"); 

            	    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:155:73: ( '0' .. '9' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '.' | '-' )+
            	    int cnt15=0;
            	    loop15:
            	    do {
            	        int alt15=2;
            	        int LA15_0 = input.LA(1);

            	        if ( ((LA15_0>='-' && LA15_0<='.')||(LA15_0>='0' && LA15_0<='9')||(LA15_0>='A' && LA15_0<='Z')||LA15_0=='_'||(LA15_0>='a' && LA15_0<='z')) ) {
            	            alt15=1;
            	        }


            	        switch (alt15) {
            	    	case 1 :
            	    	    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:
            	    	    {
            	    	    if ( (input.LA(1)>='-' && input.LA(1)<='.')||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	    	        input.consume();

            	    	    }
            	    	    else {
            	    	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	    	        recover(mse);
            	    	        throw mse;}


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt15 >= 1 ) break loop15;
            	                EarlyExitException eee =
            	                    new EarlyExitException(15, input);
            	                throw eee;
            	        }
            	        cnt15++;
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:157:25: ( '/' RULE_RE_BODY '/' ( RULE_RE_FLAGS )? )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:157:27: '/' RULE_RE_BODY '/' ( RULE_RE_FLAGS )?
            {
            match('/'); 
            mRULE_RE_BODY(); 
            match('/'); 
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:157:48: ( RULE_RE_FLAGS )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( ((LA17_0>='a' && LA17_0<='z')) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:157:48: RULE_RE_FLAGS
                    {
                    mRULE_RE_FLAGS(); 

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

    // $ANTLR start "RULE_RE_BODY"
    public final void mRULE_RE_BODY() throws RecognitionException {
        try {
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:159:23: ( RULE_RE_FIRST_CHAR ( RULE_RE_FOLLOW_CHAR )* )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:159:25: RULE_RE_FIRST_CHAR ( RULE_RE_FOLLOW_CHAR )*
            {
            mRULE_RE_FIRST_CHAR(); 
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:159:44: ( RULE_RE_FOLLOW_CHAR )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>='\u0000' && LA18_0<='\t')||(LA18_0>='\u000B' && LA18_0<='.')||(LA18_0>='0' && LA18_0<='\uFFFF')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:159:44: RULE_RE_FOLLOW_CHAR
            	    {
            	    mRULE_RE_FOLLOW_CHAR(); 

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_RE_BODY"

    // $ANTLR start "RULE_RE_FIRST_CHAR"
    public final void mRULE_RE_FIRST_CHAR() throws RecognitionException {
        try {
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:161:29: ( (~ ( ( '\\n' | '*' | '/' | '\\\\' ) ) | RULE_RE_BACKSLASH_SEQUENCE ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:161:31: (~ ( ( '\\n' | '*' | '/' | '\\\\' ) ) | RULE_RE_BACKSLASH_SEQUENCE )
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:161:31: (~ ( ( '\\n' | '*' | '/' | '\\\\' ) ) | RULE_RE_BACKSLASH_SEQUENCE )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( ((LA19_0>='\u0000' && LA19_0<='\t')||(LA19_0>='\u000B' && LA19_0<=')')||(LA19_0>='+' && LA19_0<='.')||(LA19_0>='0' && LA19_0<='[')||(LA19_0>=']' && LA19_0<='\uFFFF')) ) {
                alt19=1;
            }
            else if ( (LA19_0=='\\') ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:161:32: ~ ( ( '\\n' | '*' | '/' | '\\\\' ) )
                    {
                    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<=')')||(input.LA(1)>='+' && input.LA(1)<='.')||(input.LA(1)>='0' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 2 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:161:55: RULE_RE_BACKSLASH_SEQUENCE
                    {
                    mRULE_RE_BACKSLASH_SEQUENCE(); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:163:30: ( ( RULE_RE_FIRST_CHAR | '*' ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:163:32: ( RULE_RE_FIRST_CHAR | '*' )
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:163:32: ( RULE_RE_FIRST_CHAR | '*' )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( ((LA20_0>='\u0000' && LA20_0<='\t')||(LA20_0>='\u000B' && LA20_0<=')')||(LA20_0>='+' && LA20_0<='.')||(LA20_0>='0' && LA20_0<='\uFFFF')) ) {
                alt20=1;
            }
            else if ( (LA20_0=='*') ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:163:33: RULE_RE_FIRST_CHAR
                    {
                    mRULE_RE_FIRST_CHAR(); 

                    }
                    break;
                case 2 :
                    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:163:52: '*'
                    {
                    match('*'); 

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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:165:37: ( '\\\\' ~ ( '\\n' ) )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:165:39: '\\\\' ~ ( '\\n' )
            {
            match('\\'); 
            if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_RE_BACKSLASH_SEQUENCE"

    // $ANTLR start "RULE_RE_FLAGS"
    public final void mRULE_RE_FLAGS() throws RecognitionException {
        try {
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:167:24: ( ( 'a' .. 'z' )+ )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:167:26: ( 'a' .. 'z' )+
            {
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:167:26: ( 'a' .. 'z' )+
            int cnt21=0;
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( ((LA21_0>='a' && LA21_0<='z')) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:167:27: 'a' .. 'z'
            	    {
            	    matchRange('a','z'); 

            	    }
            	    break;

            	default :
            	    if ( cnt21 >= 1 ) break loop21;
                        EarlyExitException eee =
                            new EarlyExitException(21, input);
                        throw eee;
                }
                cnt21++;
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
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:169:16: ( . )
            // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:169:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:8: ( KEYWORD_63 | KEYWORD_62 | KEYWORD_59 | KEYWORD_60 | KEYWORD_61 | KEYWORD_55 | KEYWORD_56 | KEYWORD_57 | KEYWORD_58 | KEYWORD_51 | KEYWORD_52 | KEYWORD_53 | KEYWORD_54 | KEYWORD_47 | KEYWORD_48 | KEYWORD_49 | KEYWORD_50 | KEYWORD_22 | KEYWORD_23 | KEYWORD_24 | KEYWORD_25 | KEYWORD_26 | KEYWORD_27 | KEYWORD_28 | KEYWORD_29 | KEYWORD_30 | KEYWORD_31 | KEYWORD_32 | KEYWORD_33 | KEYWORD_34 | KEYWORD_35 | KEYWORD_36 | KEYWORD_37 | KEYWORD_38 | KEYWORD_39 | KEYWORD_40 | KEYWORD_41 | KEYWORD_42 | KEYWORD_43 | KEYWORD_44 | KEYWORD_45 | KEYWORD_46 | KEYWORD_1 | KEYWORD_2 | KEYWORD_3 | KEYWORD_4 | KEYWORD_5 | KEYWORD_6 | KEYWORD_7 | KEYWORD_8 | KEYWORD_9 | KEYWORD_10 | KEYWORD_11 | KEYWORD_12 | KEYWORD_13 | KEYWORD_14 | KEYWORD_15 | KEYWORD_16 | KEYWORD_17 | KEYWORD_18 | KEYWORD_19 | KEYWORD_20 | KEYWORD_21 | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_DOLLAR_VAR | RULE_WORD_CHARS | RULE_REGULAR_EXPRESSION | RULE_ANY_OTHER )
        int alt22=70;
        alt22 = dfa22.predict(input);
        switch (alt22) {
            case 1 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:10: KEYWORD_63
                {
                mKEYWORD_63(); 

                }
                break;
            case 2 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:21: KEYWORD_62
                {
                mKEYWORD_62(); 

                }
                break;
            case 3 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:32: KEYWORD_59
                {
                mKEYWORD_59(); 

                }
                break;
            case 4 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:43: KEYWORD_60
                {
                mKEYWORD_60(); 

                }
                break;
            case 5 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:54: KEYWORD_61
                {
                mKEYWORD_61(); 

                }
                break;
            case 6 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:65: KEYWORD_55
                {
                mKEYWORD_55(); 

                }
                break;
            case 7 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:76: KEYWORD_56
                {
                mKEYWORD_56(); 

                }
                break;
            case 8 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:87: KEYWORD_57
                {
                mKEYWORD_57(); 

                }
                break;
            case 9 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:98: KEYWORD_58
                {
                mKEYWORD_58(); 

                }
                break;
            case 10 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:109: KEYWORD_51
                {
                mKEYWORD_51(); 

                }
                break;
            case 11 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:120: KEYWORD_52
                {
                mKEYWORD_52(); 

                }
                break;
            case 12 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:131: KEYWORD_53
                {
                mKEYWORD_53(); 

                }
                break;
            case 13 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:142: KEYWORD_54
                {
                mKEYWORD_54(); 

                }
                break;
            case 14 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:153: KEYWORD_47
                {
                mKEYWORD_47(); 

                }
                break;
            case 15 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:164: KEYWORD_48
                {
                mKEYWORD_48(); 

                }
                break;
            case 16 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:175: KEYWORD_49
                {
                mKEYWORD_49(); 

                }
                break;
            case 17 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:186: KEYWORD_50
                {
                mKEYWORD_50(); 

                }
                break;
            case 18 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:197: KEYWORD_22
                {
                mKEYWORD_22(); 

                }
                break;
            case 19 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:208: KEYWORD_23
                {
                mKEYWORD_23(); 

                }
                break;
            case 20 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:219: KEYWORD_24
                {
                mKEYWORD_24(); 

                }
                break;
            case 21 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:230: KEYWORD_25
                {
                mKEYWORD_25(); 

                }
                break;
            case 22 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:241: KEYWORD_26
                {
                mKEYWORD_26(); 

                }
                break;
            case 23 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:252: KEYWORD_27
                {
                mKEYWORD_27(); 

                }
                break;
            case 24 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:263: KEYWORD_28
                {
                mKEYWORD_28(); 

                }
                break;
            case 25 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:274: KEYWORD_29
                {
                mKEYWORD_29(); 

                }
                break;
            case 26 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:285: KEYWORD_30
                {
                mKEYWORD_30(); 

                }
                break;
            case 27 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:296: KEYWORD_31
                {
                mKEYWORD_31(); 

                }
                break;
            case 28 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:307: KEYWORD_32
                {
                mKEYWORD_32(); 

                }
                break;
            case 29 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:318: KEYWORD_33
                {
                mKEYWORD_33(); 

                }
                break;
            case 30 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:329: KEYWORD_34
                {
                mKEYWORD_34(); 

                }
                break;
            case 31 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:340: KEYWORD_35
                {
                mKEYWORD_35(); 

                }
                break;
            case 32 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:351: KEYWORD_36
                {
                mKEYWORD_36(); 

                }
                break;
            case 33 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:362: KEYWORD_37
                {
                mKEYWORD_37(); 

                }
                break;
            case 34 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:373: KEYWORD_38
                {
                mKEYWORD_38(); 

                }
                break;
            case 35 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:384: KEYWORD_39
                {
                mKEYWORD_39(); 

                }
                break;
            case 36 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:395: KEYWORD_40
                {
                mKEYWORD_40(); 

                }
                break;
            case 37 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:406: KEYWORD_41
                {
                mKEYWORD_41(); 

                }
                break;
            case 38 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:417: KEYWORD_42
                {
                mKEYWORD_42(); 

                }
                break;
            case 39 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:428: KEYWORD_43
                {
                mKEYWORD_43(); 

                }
                break;
            case 40 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:439: KEYWORD_44
                {
                mKEYWORD_44(); 

                }
                break;
            case 41 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:450: KEYWORD_45
                {
                mKEYWORD_45(); 

                }
                break;
            case 42 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:461: KEYWORD_46
                {
                mKEYWORD_46(); 

                }
                break;
            case 43 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:472: KEYWORD_1
                {
                mKEYWORD_1(); 

                }
                break;
            case 44 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:482: KEYWORD_2
                {
                mKEYWORD_2(); 

                }
                break;
            case 45 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:492: KEYWORD_3
                {
                mKEYWORD_3(); 

                }
                break;
            case 46 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:502: KEYWORD_4
                {
                mKEYWORD_4(); 

                }
                break;
            case 47 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:512: KEYWORD_5
                {
                mKEYWORD_5(); 

                }
                break;
            case 48 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:522: KEYWORD_6
                {
                mKEYWORD_6(); 

                }
                break;
            case 49 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:532: KEYWORD_7
                {
                mKEYWORD_7(); 

                }
                break;
            case 50 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:542: KEYWORD_8
                {
                mKEYWORD_8(); 

                }
                break;
            case 51 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:552: KEYWORD_9
                {
                mKEYWORD_9(); 

                }
                break;
            case 52 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:562: KEYWORD_10
                {
                mKEYWORD_10(); 

                }
                break;
            case 53 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:573: KEYWORD_11
                {
                mKEYWORD_11(); 

                }
                break;
            case 54 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:584: KEYWORD_12
                {
                mKEYWORD_12(); 

                }
                break;
            case 55 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:595: KEYWORD_13
                {
                mKEYWORD_13(); 

                }
                break;
            case 56 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:606: KEYWORD_14
                {
                mKEYWORD_14(); 

                }
                break;
            case 57 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:617: KEYWORD_15
                {
                mKEYWORD_15(); 

                }
                break;
            case 58 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:628: KEYWORD_16
                {
                mKEYWORD_16(); 

                }
                break;
            case 59 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:639: KEYWORD_17
                {
                mKEYWORD_17(); 

                }
                break;
            case 60 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:650: KEYWORD_18
                {
                mKEYWORD_18(); 

                }
                break;
            case 61 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:661: KEYWORD_19
                {
                mKEYWORD_19(); 

                }
                break;
            case 62 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:672: KEYWORD_20
                {
                mKEYWORD_20(); 

                }
                break;
            case 63 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:683: KEYWORD_21
                {
                mKEYWORD_21(); 

                }
                break;
            case 64 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:694: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 65 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:710: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 66 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:726: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 67 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:734: RULE_DOLLAR_VAR
                {
                mRULE_DOLLAR_VAR(); 

                }
                break;
            case 68 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:750: RULE_WORD_CHARS
                {
                mRULE_WORD_CHARS(); 

                }
                break;
            case 69 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:766: RULE_REGULAR_EXPRESSION
                {
                mRULE_REGULAR_EXPRESSION(); 

                }
                break;
            case 70 :
                // ../org.cloudsmith.geppetto.pp.dsl/src-gen/org/cloudsmith/geppetto/pp/dsl/parser/antlr/lexer/InternalPPLexer.g:1:790: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA22 dfa22 = new DFA22(this);
    static final String DFA22_eotS =
        "\1\uffff\10\53\1\71\1\47\1\53\1\47\1\102\1\47\1\107\1\111\1\115"+
        "\1\120\1\53\1\47\6\uffff\1\132\1\134\13\uffff\1\147\1\53\1\151\1"+
        "\uffff\10\53\1\164\5\uffff\1\166\3\uffff\1\53\1\171\21\uffff\1\172"+
        "\24\uffff\1\53\1\uffff\1\53\1\uffff\11\53\4\uffff\1\u0088\3\uffff"+
        "\7\53\1\u0090\1\53\1\u0092\1\53\1\u0094\1\u0095\1\uffff\5\53\1\u009b"+
        "\1\u009c\1\uffff\1\u009d\1\uffff\1\u009e\2\uffff\1\53\1\u00a0\1"+
        "\53\1\u00a2\1\u00a3\4\uffff\1\53\1\uffff\1\u00a5\2\uffff\1\u00a6"+
        "\2\uffff";
    static final String DFA22_eofS =
        "\u00a7\uffff";
    static final String DFA22_minS =
        "\1\0\1\146\1\145\1\156\1\141\1\154\1\141\1\157\1\162\1\55\1\42\1"+
        "\156\1\76\1\75\1\60\1\75\1\55\2\75\1\162\1\76\6\uffff\1\0\1\72\13"+
        "\uffff\1\55\1\160\1\55\1\uffff\1\146\1\144\1\141\2\163\1\154\1\144"+
        "\1\165\1\174\5\uffff\1\173\3\uffff\1\144\1\76\21\uffff\1\55\24\uffff"+
        "\1\145\1\uffff\1\157\1\uffff\1\141\2\145\1\163\2\145\1\163\2\145"+
        "\4\uffff\1\55\3\uffff\2\162\1\165\1\156\1\163\1\146\1\163\1\55\1"+
        "\146\1\55\1\145\2\55\1\uffff\1\151\1\164\1\154\1\145\1\163\2\55"+
        "\1\uffff\1\55\1\uffff\1\55\2\uffff\1\164\1\55\1\164\2\55\4\uffff"+
        "\1\163\1\uffff\1\55\2\uffff\1\55\2\uffff";
    static final String DFA22_maxS =
        "\1\uffff\1\156\1\145\1\156\2\154\1\141\1\157\1\162\1\176\1\134\1"+
        "\156\1\76\1\176\1\173\1\76\1\172\1\176\1\76\1\162\1\76\6\uffff\1"+
        "\uffff\1\72\13\uffff\1\172\1\160\1\172\1\uffff\1\146\1\154\1\141"+
        "\2\163\1\154\1\144\1\165\1\174\5\uffff\1\173\3\uffff\1\144\1\76"+
        "\21\uffff\1\172\24\uffff\1\145\1\uffff\1\157\1\uffff\1\151\2\145"+
        "\1\163\1\145\1\151\1\163\2\145\4\uffff\1\172\3\uffff\2\162\1\165"+
        "\1\156\1\163\1\146\1\163\1\172\1\146\1\172\1\145\2\172\1\uffff\1"+
        "\151\1\164\1\154\1\145\1\163\2\172\1\uffff\1\172\1\uffff\1\172\2"+
        "\uffff\1\164\1\172\1\164\2\172\4\uffff\1\163\1\uffff\1\172\2\uffff"+
        "\1\172\2\uffff";
    static final String DFA22_acceptS =
        "\25\uffff\1\54\1\55\1\56\1\57\1\60\1\62\2\uffff\1\66\1\72\1\73\1"+
        "\74\1\75\1\76\1\77\1\101\1\102\1\104\1\106\3\uffff\1\104\11\uffff"+
        "\1\30\1\32\1\33\1\34\1\67\1\uffff\1\42\1\44\1\45\2\uffff\1\22\1"+
        "\23\1\53\1\24\1\103\1\25\1\26\1\61\1\27\1\63\1\35\1\36\1\37\1\70"+
        "\1\40\1\41\1\71\1\uffff\1\52\1\54\1\55\1\56\1\57\1\60\1\62\1\100"+
        "\1\64\1\105\1\65\1\66\1\72\1\73\1\74\1\75\1\76\1\77\1\101\1\102"+
        "\1\uffff\1\47\1\uffff\1\46\11\uffff\1\16\1\31\1\17\1\43\1\uffff"+
        "\1\21\1\51\1\50\15\uffff\1\20\7\uffff\1\12\1\uffff\1\13\1\uffff"+
        "\1\14\1\15\5\uffff\1\11\1\6\1\7\1\10\1\uffff\1\4\1\uffff\1\3\1\5"+
        "\1\uffff\1\2\1\1";
    static final String DFA22_specialS =
        "\1\0\32\uffff\1\1\u008b\uffff}>";
    static final String[] DFA22_transitionS = {
            "\11\47\2\45\2\47\1\45\22\47\1\45\1\15\1\25\1\44\1\16\2\47\1"+
            "\26\1\27\1\30\1\31\1\17\1\32\1\20\1\46\1\33\12\46\1\34\1\35"+
            "\1\11\1\21\1\22\1\36\1\37\32\46\1\40\1\12\1\41\1\47\1\46\1\47"+
            "\1\13\1\46\1\4\1\2\1\5\1\6\2\46\1\1\4\46\1\7\1\23\4\46\1\10"+
            "\1\3\5\46\1\42\1\14\1\43\1\24\41\47\1\45\uff5f\47",
            "\1\52\6\uffff\1\51\1\50",
            "\1\54",
            "\1\55",
            "\1\57\12\uffff\1\56",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\65\16\uffff\1\64\1\66\76\uffff\1\67\1\uffff\1\70",
            "\1\73\1\uffff\1\72\2\uffff\1\74\64\uffff\1\75",
            "\1\76",
            "\1\77",
            "\1\100\100\uffff\1\101",
            "\13\104\6\uffff\32\104\4\uffff\1\104\1\uffff\32\104\1\103",
            "\1\105\1\106",
            "\2\53\1\uffff\13\53\3\uffff\1\110\2\uffff\32\53\4\uffff\1\53"+
            "\1\uffff\32\53",
            "\1\112\1\113\77\uffff\1\114",
            "\1\116\1\117",
            "\1\121",
            "\1\122",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\133\1\uffff\37\133\1\131\4\133\1\uffff\uffd0\133",
            "\1\53",
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
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\7\53"+
            "\1\146\22\53",
            "\1\150",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            "\1\152",
            "\1\154\7\uffff\1\153",
            "\1\155",
            "\1\156",
            "\1\157",
            "\1\160",
            "\1\161",
            "\1\162",
            "\1\163",
            "",
            "",
            "",
            "",
            "",
            "\1\165",
            "",
            "",
            "",
            "\1\167",
            "\1\170",
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
            "",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
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
            "",
            "",
            "",
            "",
            "\1\173",
            "",
            "\1\174",
            "",
            "\1\175\7\uffff\1\176",
            "\1\177",
            "\1\u0080",
            "\1\u0081",
            "\1\u0082",
            "\1\u0084\3\uffff\1\u0083",
            "\1\u0085",
            "\1\u0086",
            "\1\u0087",
            "",
            "",
            "",
            "",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            "",
            "",
            "\1\u0089",
            "\1\u008a",
            "\1\u008b",
            "\1\u008c",
            "\1\u008d",
            "\1\u008e",
            "\1\u008f",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u0091",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u0093",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            "\1\u0096",
            "\1\u0097",
            "\1\u0098",
            "\1\u0099",
            "\1\u009a",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            "",
            "\1\u009f",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u00a1",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            "",
            "",
            "",
            "\1\u00a4",
            "",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            "",
            "\2\53\1\uffff\13\53\6\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            ""
    };

    static final short[] DFA22_eot = DFA.unpackEncodedString(DFA22_eotS);
    static final short[] DFA22_eof = DFA.unpackEncodedString(DFA22_eofS);
    static final char[] DFA22_min = DFA.unpackEncodedStringToUnsignedChars(DFA22_minS);
    static final char[] DFA22_max = DFA.unpackEncodedStringToUnsignedChars(DFA22_maxS);
    static final short[] DFA22_accept = DFA.unpackEncodedString(DFA22_acceptS);
    static final short[] DFA22_special = DFA.unpackEncodedString(DFA22_specialS);
    static final short[][] DFA22_transition;

    static {
        int numStates = DFA22_transitionS.length;
        DFA22_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
        }
    }

    class DFA22 extends DFA {

        public DFA22(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 22;
            this.eot = DFA22_eot;
            this.eof = DFA22_eof;
            this.min = DFA22_min;
            this.max = DFA22_max;
            this.accept = DFA22_accept;
            this.special = DFA22_special;
            this.transition = DFA22_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( KEYWORD_63 | KEYWORD_62 | KEYWORD_59 | KEYWORD_60 | KEYWORD_61 | KEYWORD_55 | KEYWORD_56 | KEYWORD_57 | KEYWORD_58 | KEYWORD_51 | KEYWORD_52 | KEYWORD_53 | KEYWORD_54 | KEYWORD_47 | KEYWORD_48 | KEYWORD_49 | KEYWORD_50 | KEYWORD_22 | KEYWORD_23 | KEYWORD_24 | KEYWORD_25 | KEYWORD_26 | KEYWORD_27 | KEYWORD_28 | KEYWORD_29 | KEYWORD_30 | KEYWORD_31 | KEYWORD_32 | KEYWORD_33 | KEYWORD_34 | KEYWORD_35 | KEYWORD_36 | KEYWORD_37 | KEYWORD_38 | KEYWORD_39 | KEYWORD_40 | KEYWORD_41 | KEYWORD_42 | KEYWORD_43 | KEYWORD_44 | KEYWORD_45 | KEYWORD_46 | KEYWORD_1 | KEYWORD_2 | KEYWORD_3 | KEYWORD_4 | KEYWORD_5 | KEYWORD_6 | KEYWORD_7 | KEYWORD_8 | KEYWORD_9 | KEYWORD_10 | KEYWORD_11 | KEYWORD_12 | KEYWORD_13 | KEYWORD_14 | KEYWORD_15 | KEYWORD_16 | KEYWORD_17 | KEYWORD_18 | KEYWORD_19 | KEYWORD_20 | KEYWORD_21 | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_DOLLAR_VAR | RULE_WORD_CHARS | RULE_REGULAR_EXPRESSION | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA22_0 = input.LA(1);

                        s = -1;
                        if ( (LA22_0=='i') ) {s = 1;}

                        else if ( (LA22_0=='d') ) {s = 2;}

                        else if ( (LA22_0=='u') ) {s = 3;}

                        else if ( (LA22_0=='c') ) {s = 4;}

                        else if ( (LA22_0=='e') ) {s = 5;}

                        else if ( (LA22_0=='f') ) {s = 6;}

                        else if ( (LA22_0=='n') ) {s = 7;}

                        else if ( (LA22_0=='t') ) {s = 8;}

                        else if ( (LA22_0=='<') ) {s = 9;}

                        else if ( (LA22_0=='\\') ) {s = 10;}

                        else if ( (LA22_0=='a') ) {s = 11;}

                        else if ( (LA22_0=='|') ) {s = 12;}

                        else if ( (LA22_0=='!') ) {s = 13;}

                        else if ( (LA22_0=='$') ) {s = 14;}

                        else if ( (LA22_0=='+') ) {s = 15;}

                        else if ( (LA22_0=='-') ) {s = 16;}

                        else if ( (LA22_0=='=') ) {s = 17;}

                        else if ( (LA22_0=='>') ) {s = 18;}

                        else if ( (LA22_0=='o') ) {s = 19;}

                        else if ( (LA22_0=='~') ) {s = 20;}

                        else if ( (LA22_0=='\"') ) {s = 21;}

                        else if ( (LA22_0=='\'') ) {s = 22;}

                        else if ( (LA22_0=='(') ) {s = 23;}

                        else if ( (LA22_0==')') ) {s = 24;}

                        else if ( (LA22_0=='*') ) {s = 25;}

                        else if ( (LA22_0==',') ) {s = 26;}

                        else if ( (LA22_0=='/') ) {s = 27;}

                        else if ( (LA22_0==':') ) {s = 28;}

                        else if ( (LA22_0==';') ) {s = 29;}

                        else if ( (LA22_0=='?') ) {s = 30;}

                        else if ( (LA22_0=='@') ) {s = 31;}

                        else if ( (LA22_0=='[') ) {s = 32;}

                        else if ( (LA22_0==']') ) {s = 33;}

                        else if ( (LA22_0=='{') ) {s = 34;}

                        else if ( (LA22_0=='}') ) {s = 35;}

                        else if ( (LA22_0=='#') ) {s = 36;}

                        else if ( ((LA22_0>='\t' && LA22_0<='\n')||LA22_0=='\r'||LA22_0==' '||LA22_0=='\u00A0') ) {s = 37;}

                        else if ( (LA22_0=='.'||(LA22_0>='0' && LA22_0<='9')||(LA22_0>='A' && LA22_0<='Z')||LA22_0=='_'||LA22_0=='b'||(LA22_0>='g' && LA22_0<='h')||(LA22_0>='j' && LA22_0<='m')||(LA22_0>='p' && LA22_0<='s')||(LA22_0>='v' && LA22_0<='z')) ) {s = 38;}

                        else if ( ((LA22_0>='\u0000' && LA22_0<='\b')||(LA22_0>='\u000B' && LA22_0<='\f')||(LA22_0>='\u000E' && LA22_0<='\u001F')||(LA22_0>='%' && LA22_0<='&')||LA22_0=='^'||LA22_0=='`'||(LA22_0>='\u007F' && LA22_0<='\u009F')||(LA22_0>='\u00A1' && LA22_0<='\uFFFF')) ) {s = 39;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA22_27 = input.LA(1);

                        s = -1;
                        if ( (LA22_27=='*') ) {s = 89;}

                        else if ( ((LA22_27>='\u0000' && LA22_27<='\t')||(LA22_27>='\u000B' && LA22_27<=')')||(LA22_27>='+' && LA22_27<='.')||(LA22_27>='0' && LA22_27<='\uFFFF')) ) {s = 91;}

                        else s = 90;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 22, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}