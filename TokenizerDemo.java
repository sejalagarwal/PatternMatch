/**
 * Copyright 2015, Emory University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.nlp.common.util.IOUtils;
import edu.emory.mathcs.nlp.common.util.Joiner;
import edu.emory.mathcs.nlp.tokenization.EnglishTokenizer;
import edu.emory.mathcs.nlp.tokenization.Token;
import edu.emory.mathcs.nlp.tokenization.Tokenizer;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class TokenizerDemo
{
	
	private String inputFile;
	
	public TokenizerDemo(String filename){
		inputFile = filename;
	}
	
//	@Test
	public List<List<Token>> tokenizeRaw() throws Exception
	{
		Tokenizer tokenizer = new EnglishTokenizer();
		
		InputStream in = IOUtils.createFileInputStream(inputFile);
		
		ArrayList<List<Token>> tokensList = new ArrayList<>();
		for (List<Token> tokens : tokenizer.segmentize(in)){
			//System.out.println(Joiner.join(tokens, " "));
			tokensList.add(tokens);
		}
		
		in.close();
		return tokensList;
	}
	
//	@Test
	public void tokenizeLine() throws Exception
	{
		Tokenizer tokenizer = new EnglishTokenizer();
		//String inputFile = "C:\\GitRepo\\PatternMatch\\Macbeth.txt";
		BufferedReader in = IOUtils.createBufferedReader(inputFile);
		List<Token> tokens;
		String line;
		
		while ((line = in.readLine()) != null)
		{
			tokens = tokenizer.tokenize(line);
			System.out.println(Joiner.join(tokens, " "));	

		}
		
		in.close();
	}
	
	public ArrayList<String> parseFileToParagraphs() throws IOException
	{
		FileInputStream in = null;

		in = new FileInputStream(inputFile);

		char c;
		String wholeFile = "";
		int i = 0;
		ArrayList<String> para = new ArrayList<String>();
		while((c = (char)in.read()) != 1)
		{
			wholeFile += c;
			if(wholeFile.matches("[.?'!\"]\n\n")){
				para.add(wholeFile);
				wholeFile = "";
			}
		}
		//String[] para= wholeFile.matches("[.?'!\"]\n\n");
		return para;

	}
	
	public static void main(String[] args) throws Exception{
		TokenizerDemo td = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\Anna Karenina.txt");
		
		List<List<Token>> tokenList = td.tokenizeRaw();
		//System.out.println(tokenList);
		
		FeatureDetector detector = new FeatureDetector(tokenList);
		System.out.println(detector.CalculateWordFreq());
		//System.out.println(detector.wordMap);
		System.out.println(detector.sentenceCalculator());
		System.out.println(detector.wordNumberCalculator());
		System.out.println(detector.avgSentenceLength());
		System.out.println(detector.characterNumberCalculator());
		System.out.println(detector.calculatePunctuationFrequency());
		
		//System.out.println(detector.calcAvgParagraphWords(td));
		
		//td.tokenizeLine();
	}
}