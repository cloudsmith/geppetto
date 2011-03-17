package org.cloudsmith.geppetto.ruby;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.jruby.CompatVersion;
import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.lexer.yacc.InputStreamLexerSource;
import org.jruby.lexer.yacc.LexerSource;
import org.jruby.parser.ParserConfiguration;
import org.jruby.parser.RubyParser;
import org.jruby.parser.RubyParserPool;
import org.jruby.parser.RubyParserResult;

public class RubyHelper {
	
	/**
	 * IOExceptions thrown FileNotFound, and while reading
	 * @param file
	 * @throws IOException
	 */
	public void parse(File file) throws IOException {
		// Ruby Compatibility to use
		CompatVersion rubyVersion = CompatVersion.RUBY1_9;
		// Configure a Ruby Runtime, it is needed for the warnings processor even if we are not
		// going to be running any of the scripts.
		RubyInstanceConfig config = new RubyInstanceConfig();
		config.setCompatVersion(rubyVersion);
		Ruby rubyRuntime = Ruby.newInstance(config);
		
		// Get a parser
		RubyParser parser = RubyParserPool.getInstance().borrowParser(rubyVersion);
		
		// Create a warnings collector and give it to the parser
		RubyParserWarningsCollector warnings = new RubyParserWarningsCollector(rubyRuntime);
		parser.setWarnings(warnings);
		
		// Create a parser configuration
		final int startLine = 1;
		final boolean extraPositionInfo = true;
		final boolean inlineSource = false;
		final List<String> lexerCapture = null;
		// TODO: encoding is US-ASCII by default, wonder if that should be UTF8 instead, or picked up from 
		// somwhere...
		ParserConfiguration parserConfiguration = new ParserConfiguration(rubyRuntime,startLine,extraPositionInfo,inlineSource,rubyVersion);
		
		FileInputStream input = new FileInputStream(file);
		
		LexerSource source = new InputStreamLexerSource(file.getName(), 
				input, lexerCapture, startLine, extraPositionInfo);
		RubyParserResult parserResult = parser.parse(parserConfiguration, source);
		parserResult.getAST();
	}
}
