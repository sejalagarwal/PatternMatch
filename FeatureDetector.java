import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.emory.mathcs.nlp.tokenization.Token;

public class FeatureDetector {

	public Map<String,Integer> wordMap=new HashMap<String, Integer>();
	private double punctuation;
	private int[] sent=new int[2];
	private int[] para=new int[3];
	private List<List<Token>> myList;

	
	
	FeatureDetector(List<List<Token>> myList)
	{
		this.myList=myList;
	}

	public static <Token, Integer extends Comparable<Integer>> TreeMap<Token, Integer> sortByValues(final TreeMap<Token, Integer> map) {
		Comparator<Token> valueComparator =  new Comparator<Token>() {
			public int compare(Token k1, Token k2) {
				int compare = map.get(k2).compareTo(map.get(k1));
				if (compare == 0) return 1;
				else return compare;
			}
		};
		TreeMap<Token, Integer> sortedByValues = new TreeMap<Token, Integer>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}

	private boolean isPunctuation(String word){
		char[] punctuations = {',', '!', '.', ';', '?', ':', '[', ']', '{', '}', '(', ')', '\'', '*', '/', '"'};
		for(int i=0; i<punctuations.length; i++){
			if (word.indexOf(punctuations[i]) != -1)
				return true;
		}
		return false;
	}
	
	public List<Map.Entry<String, Integer>> CalculateWordFreq()
	{
		int value;
		//System.out.println(myList);
		for(int i=0;i<myList.size();i++)
		{
			for(int j=0;j<myList.get(i).size();j++)
			{
				String word=myList.get(i).get(j).toString();
				if(!isPunctuation(word)){
					if(wordMap.get(word)!=null)
						wordMap.put(word, wordMap.get(word)+1);
					else 
						wordMap.put(word, 1);
				}
			}
		}
		
		List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(wordMap.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2){
				return -entry1.getValue().compareTo(entry2.getValue());
			}
		});
		
		return entries;
		//tm = sortByValues(tm);
	}

	public int sentenceCalculator(){
		int sentencesCount = myList.size();

		return sentencesCount;
	}

	public int wordNumberCalculator(){
		int rows,columns=0;

		rows = myList.size();

		for(int i=0;i<rows;i++){
			for(int j=0;j<myList.get(i).size();j++){
				columns++;
			}
		}

		return columns;
	}

	public double avgSentenceLength(){
		int rows;
		double count=0;

		rows = myList.size();

		for(int i=0;i<rows;i++){

			count+=myList.get(i).size();

		}

		return count/rows;
	}

	public int characterNumberCalculator(){
		int rows,columns=0;

		rows = myList.size();

		for(int i=0;i<rows;i++){
			for(int j=0;j<myList.get(i).size();j++){
				columns+=myList.get(i).get(j).toString().length();
			}
		}

		return columns;
	}

	public double calculatePunctuationFrequency(){
		int i,j;
		double puncCount=0,punAvg;
		for(i=0;i<myList.size();i++)
		{
			for(j=0;j<myList.get(i).size();j++)
			{
				//TODO create

				if(isPunctuation(myList.get(i).get(j).toString()))
					puncCount++;
			}


		}
		punctuation=puncCount/myList.size();
		return punctuation;
	}

	public double calcAvgParagraphWords(TokenizerDemo tokenizer){
		
		
		try {
			ArrayList<String> para=tokenizer.parseFileToParagraphs();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public void calculateMetrics(){
		List<Entry<String, Integer>> wordFreq = CalculateWordFreq();
		int totalSentences = sentenceCalculator();
		int totalWords = wordNumberCalculator();
		double avgSentencLength = avgSentenceLength();
		int totalCharacterCount = characterNumberCalculator();
		double totalPunctuationCount = calculatePunctuationFrequency();
	}
	
}
