///**
// * Copyright 2015, Emory University
// * 
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * 
// *     http://www.apache.org/licenses/LICENSE-2.0
// * 
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import edu.emory.mathcs.nlp.common.util.IOUtils;
//import edu.emory.mathcs.nlp.common.util.Joiner;
//import edu.emory.mathcs.nlp.tokenization.EnglishTokenizer;
//import edu.emory.mathcs.nlp.tokenization.Token;
//import edu.emory.mathcs.nlp.tokenization.Tokenizer;
//
///**
// * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
// */
//public class TokenizerDemo
//{
//	
//	private String inputFile;
//	
//	public TokenizerDemo(String filename){
//		inputFile = filename;
//	}
//	
////	@Test
//	public List<List<Token>> tokenizeRaw() throws Exception
//	{
//		Tokenizer tokenizer = new EnglishTokenizer();
//		
//		InputStream in = IOUtils.createFileInputStream(inputFile);
//		
//		ArrayList<List<Token>> tokensList = new ArrayList<>();
//		for (List<Token> tokens : tokenizer.segmentize(in)){
//			//System.out.println(Joiner.join(tokens, " "));
//			tokensList.add(tokens);
//		}
//		
//		in.close();
//		return tokensList;
//	}
//	
////	@Test
//	public void tokenizeLine() throws Exception
//	{
//		Tokenizer tokenizer = new EnglishTokenizer();
//		//String inputFile = "C:\\GitRepo\\PatternMatch\\Macbeth.txt";
//		BufferedReader in = IOUtils.createBufferedReader(inputFile);
//		List<Token> tokens;
//		String line;
//		
//		while ((line = in.readLine()) != null)
//		{
//			tokens = tokenizer.tokenize(line);
//			System.out.println(Joiner.join(tokens, " "));	
//
//		}
//		
//		in.close();
//	}
//	
//	public ArrayList<String> parseFileToParagraphs() throws IOException
//	{
//		FileInputStream in = null;
//
//		in = new FileInputStream(inputFile);
//
//		char c;
//		String wholeFile = "";
//		int i = 0;
//		ArrayList<String> para = new ArrayList<String>();
//		while((c = (char)in.read()) != 1)
//		{
//			wholeFile += c;
//			if(wholeFile.matches("[.?'!\"]\n\n")){
//				para.add(wholeFile);
//				wholeFile = "";
//			}
//		}
//		//String[] para= wholeFile.matches("[.?'!\"]\n\n");
//		return para;
//
//	}
//	
//	private static double distanceMetric(FeatureDetector object1, FeatureDetector object2){
//		return Math.abs(object1.avgSentenceLength - object2.avgSentenceLength);
//	}
//	
//	private static void predictAuthor(FeatureDetector testObject, ArrayList<FeatureDetector> trainDetectors, ArrayList<String> classLabels) {
//		double min = 1000;
//		int minIndex = -1;
//		for(int i=0; i<trainDetectors.size(); i++){
//			double distance = distanceMetric(testObject, trainDetectors.get(i));
//			if(distance < min){
//				min = distance;
//				minIndex = i;
//			}
//		}
//		System.out.println(classLabels.get(minIndex));
//	}
//	
//	public static void main(String[] args) throws Exception{
//		TokenizerDemo othelloTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\Othello.txt");
//		List<List<Token>> othelloTokenList = othelloTokenizer.tokenizeRaw();
//		TokenizerDemo macbethTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\Macbeth.txt");
//		List<List<Token>> macbethTokenList = macbethTokenizer.tokenizeRaw();
//		TokenizerDemo juliusCaesarTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\Julius Caesar.txt");
//		List<List<Token>> juliusCaesarTokenList = juliusCaesarTokenizer.tokenizeRaw();
//		TokenizerDemo romeoJulietTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\Romeo Juliet.txt");
//		List<List<Token>> romeoJulietTokenList = romeoJulietTokenizer.tokenizeRaw();
//		
//		TokenizerDemo annaKareninaTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\Anna Karenina.txt");
//		List<List<Token>> annaKareninaTokenList = annaKareninaTokenizer.tokenizeRaw();
//		TokenizerDemo warAndPeaceTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\War and Peace.txt");
//		List<List<Token>> warAndPeaceTokenList = warAndPeaceTokenizer.tokenizeRaw();
//		TokenizerDemo kingdomOfGodTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\KingdomOfGod.txt");
//		List<List<Token>> kingdomOfGodTokenList = kingdomOfGodTokenizer.tokenizeRaw();
//		
//		TokenizerDemo hamletTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\Hamlet.txt");
//		List<List<Token>> hamletTokenList = hamletTokenizer.tokenizeRaw();
//		
//		TokenizerDemo whatMenLiveByTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\WhatMenLiveBy.txt");
//		List<List<Token>> whatMenLiveByTokenList = whatMenLiveByTokenizer.tokenizeRaw();
//		
//		ArrayList<List<Token>> shakespeareTrainTokenList = new ArrayList<>();
//		shakespeareTrainTokenList.addAll(othelloTokenList);
//		shakespeareTrainTokenList.addAll(macbethTokenList);
//		shakespeareTrainTokenList.addAll(juliusCaesarTokenList);
//		shakespeareTrainTokenList.addAll(romeoJulietTokenList);
//		
//		ArrayList<List<Token>> tolstoyTrainTokenList = new ArrayList<>();
//		tolstoyTrainTokenList.addAll(annaKareninaTokenList);
//		tolstoyTrainTokenList.addAll(warAndPeaceTokenList);
//		tolstoyTrainTokenList.addAll(kingdomOfGodTokenList);
//		
//		FeatureDetector shakespeareTrainDetector = new FeatureDetector(shakespeareTrainTokenList);
//		shakespeareTrainDetector.calculateMetrics();
//		shakespeareTrainDetector.printMetrics();
//		
//		FeatureDetector tolstoyTrainDetector = new FeatureDetector(tolstoyTrainTokenList);
//		tolstoyTrainDetector.calculateMetrics();
//		tolstoyTrainDetector.printMetrics();
//		
//		FeatureDetector shakespeareTestDetector = new FeatureDetector(hamletTokenList);
//		shakespeareTestDetector.calculateMetrics();
//		shakespeareTestDetector.printMetrics();
//		
//		FeatureDetector tolstoyTestDetector = new FeatureDetector(whatMenLiveByTokenList);
//		tolstoyTestDetector.calculateMetrics();
//		tolstoyTestDetector.printMetrics();
//		
//		ArrayList<FeatureDetector> trainDetectors = new ArrayList<>();
//		trainDetectors.add(shakespeareTrainDetector);
//		trainDetectors.add(tolstoyTrainDetector);
//		
//		ArrayList<String> classLabels = new ArrayList<>();
//		classLabels.add("Shakespeare");
//		classLabels.add("Tolstoy");
//		
//		predictAuthor(shakespeareTestDetector, trainDetectors, classLabels);
//		predictAuthor(tolstoyTestDetector, trainDetectors, classLabels);
//		
//	}
//}

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
import java.util.Scanner;

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
	
	private boolean hasPunctuation(String word){
		char[] punctuations = {',', '!', '.', ';', '?', ':', '[', ']', '{', '}', '(', ')', '\'', '*', '/', '"'};
		for(int i=0; i<punctuations.length; i++){
			if (word.indexOf(punctuations[i]) != -1)
				return true;
		}
		return false;
	}
	
//	@Test
	public List<List<Token>> tokenizeRaw() throws Exception
	{
		Tokenizer tokenizer = new EnglishTokenizer();
		
		InputStream in = IOUtils.createFileInputStream(inputFile);
		
		ArrayList<List<Token>> tokensList = new ArrayList<>();
		
		for (List<Token> tokens : tokenizer.segmentize(in)){
			List<Token> tokenCopy = new ArrayList<Token>(tokens);
			//System.out.println(Joiner.join(tokens, " "));
			for(int i=0, k=0; i<tokens.size(); i++, k++){
				if(hasPunctuation(tokens.get(i).toString())){
					tokenCopy.remove(k);
					k--;
				}
			}
			tokensList.add(tokenCopy);
		}
		//System.out.println(tokensList);
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
	
	private static double distanceMetric(FeatureDetector object1, FeatureDetector object2){
		return Math.abs(object1.avgSentenceLength - object2.avgSentenceLength);
	}
	
	private static boolean predictAuthor(FeatureDetector testObject, FeatureDetector classADetector, FeatureDetector classBDetector) {
		return distanceMetric(testObject, classADetector) < distanceMetric(testObject, classBDetector);
	}
	
	public static void main(String[] args) throws Exception{
		TokenizerDemo othelloTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\Othello.txt");
		List<List<Token>> othelloTokenList = othelloTokenizer.tokenizeRaw();
		TokenizerDemo macbethTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\Macbeth.txt");
		List<List<Token>> macbethTokenList = macbethTokenizer.tokenizeRaw();
		TokenizerDemo juliusCaesarTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\Julius Caesar.txt");
		List<List<Token>> juliusCaesarTokenList = juliusCaesarTokenizer.tokenizeRaw();
		TokenizerDemo romeoJulietTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\Romeo Juliet.txt");
		List<List<Token>> romeoJulietTokenList = romeoJulietTokenizer.tokenizeRaw();
		
				
//		TokenizerDemo hamletTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\Hamlet.txt");
//		List<List<Token>> hamletTokenList = hamletTokenizer.tokenizeRaw();
//		
//		TokenizerDemo annaKareninaTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\Anna Karenina.txt");
//		List<List<Token>> annaKareninaTokenList = annaKareninaTokenizer.tokenizeRaw();
//		TokenizerDemo warAndPeaceTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\War and Peace.txt");
//		List<List<Token>> warAndPeaceTokenList = warAndPeaceTokenizer.tokenizeRaw();
//		TokenizerDemo kingdomOfGodTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\KingdomOfGod.txt");
//		List<List<Token>> kingdomOfGodTokenList = kingdomOfGodTokenizer.tokenizeRaw();
//		TokenizerDemo whatMenLiveByTokenizer = new TokenizerDemo("C:\\GitRepo\\PatternMatch\\WhatMenLiveBy.txt");
//		List<List<Token>> whatMenLiveByTokenList = whatMenLiveByTokenizer.tokenizeRaw();
		
		
		ArrayList<List<Token>> shakespeareTrainTokenList = new ArrayList<>();
		shakespeareTrainTokenList.addAll(othelloTokenList);
		shakespeareTrainTokenList.addAll(macbethTokenList);
		shakespeareTrainTokenList.addAll(juliusCaesarTokenList);
		shakespeareTrainTokenList.addAll(romeoJulietTokenList);
		
//		ArrayList<List<Token>> tolstoyTrainTokenList = new ArrayList<>();
//		tolstoyTrainTokenList.addAll(annaKareninaTokenList);
//		tolstoyTrainTokenList.addAll(warAndPeaceTokenList);
//		tolstoyTrainTokenList.addAll(kingdomOfGodTokenList);
		
		FeatureDetector shakespeareTrainDetector = new FeatureDetector(shakespeareTrainTokenList);
		shakespeareTrainDetector.calculateMetrics();
		shakespeareTrainDetector.printMetrics();
		
//		FeatureDetector tolstoyTrainDetector = new FeatureDetector(tolstoyTrainTokenList);
//		tolstoyTrainDetector.calculateMetrics();
//		tolstoyTrainDetector.printMetrics();
//		
//		FeatureDetector shakespeareTestDetector = new FeatureDetector(hamletTokenList);
//		shakespeareTestDetector.calculateMetrics();
//		shakespeareTestDetector.printMetrics();
//		
//		FeatureDetector tolstoyTestDetector = new FeatureDetector(whatMenLiveByTokenList);
//		tolstoyTestDetector.calculateMetrics();
//		tolstoyTestDetector.printMetrics();
//		
//		double shakespeare=shakespeareTrainDetector.avgSentenceLength;
//		double tolstoy=tolstoyTrainDetector.avgSentenceLength;
//		System.out.println("Enter path of a new book : ");
//		//C:\GitRepo\PatternMatch\WhatMenLiveBy.txt
//		Scanner in = new Scanner(System.in);
//		String str=in.nextLine();
//		TokenizerDemo newBook = new TokenizerDemo(str);
//		List<List<Token>> newBookList = newBook.tokenizeRaw();
//		
//		FeatureDetector newBookDetector = new FeatureDetector(newBookList);
//		newBookDetector.calculateMetrics();
//		
//		double newB=newBookDetector.avgSentenceLength;
//		if(Math.abs(shakespeare-newB)>Math.abs(tolstoy-newB) && Math.abs(tolstoy-newB)<1)
//		{	
//				System.out.println("newBook is Tolstoy");
//		}
//		else if(Math.abs(shakespeare-newB)<Math.abs(tolstoy-newB) && Math.abs(shakespeare-newB)<1)
//		{
//			System.out.println("newBook is Shakespeare");
//		}
//		else
//			System.out.println("Neither of them");
		
		
		System.out.println(shakespeareTrainDetector.generateSentence());
//		shakespeareTrainDetector.sentenceGenerate();
		//td.tokenizeLine();
	}
}