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

	public Map<String,Integer> wordMap=new HashMap<>();
	public Map<String,Integer> firstWordMap=new HashMap<>();
	public Map<String,HashMap<String, Integer>> nextWordMap=new HashMap<>();
	
	private double punctuation;
	private int[] sent=new int[2];
	private int[] para=new int[3];
	private List<List<Token>> myList;
	
	double avgSentenceLength = -1;
	double punctuationFrequency = -1;
	double avgCharactersPerWord = -1;
	double sentenceCount = -1;
	double wordCount = -1;
	
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

	private boolean hasPunctuation(String word){
		char[] punctuations = {',', '!', '.', ';', '?', ':', '[', ']', '{', '}', '(', ')', '\'', '*', '/', '"'};
		for(int i=0; i<punctuations.length; i++){
			if (word.indexOf(punctuations[i]) != -1)
				return true;
		}
		return false;
	}
	
	private List<Map.Entry<String, Integer>> CalculateWordFreq()
	{
		int value;
		//System.out.println(myList);
		for(int i=0;i<myList.size();i++)
		{
			if(myList.get(i).size()>0 && !hasPunctuation(myList.get(i).get(0).toString())){
				String word = myList.get(i).get(0).toString();
				if(firstWordMap.get(word) != null){
					firstWordMap.put(word, firstWordMap.get(word)+1);
				} else {
					firstWordMap.put(word, 1);
				}
			}
			
			for(int j=0;j<myList.get(i).size()-1;j++)
			{
				String word=myList.get(i).get(j).toString();
				if(!hasPunctuation(word)){
					if(wordMap.get(word)!=null){
						wordMap.put(word, wordMap.get(word)+1);
						HashMap<String, Integer> nextWordsHashMap = nextWordMap.get(word);
						String nextWord = myList.get(i).get(j+1).toString();
						if(nextWordsHashMap.get(nextWord) != null){
							nextWordsHashMap.put(nextWord, nextWordsHashMap.get(nextWord) + 1);
						} else {
							nextWordsHashMap.put(nextWord, 1);
						}
					}
					else { 
						wordMap.put(word, 1);
						HashMap<String, Integer> nextWordsHashMap = new HashMap<>();
						String nextWord = myList.get(i).get(j+1).toString();
						nextWordsHashMap.put(nextWord, 1);
						nextWordMap.put(word, nextWordsHashMap);
					}
				}
			}
		}
		
		List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(wordMap.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2){
				return -entry1.getValue().compareTo(entry2.getValue());
			}
		});
		
		//System.out.println(firstWordMap);
		
		return entries;
		//tm = sortByValues(tm);
	}

	private int sentenceCalculator(){
		int sentencesCount = myList.size();

		return sentencesCount;
	}

	private int wordNumberCalculator(){
		int rows,columns=0;

		rows = myList.size();

		for(int i=0;i<rows;i++){
			for(int j=0;j<myList.get(i).size();j++){
				columns++;
			}
		}

		return columns;
	}

	private double avgSentenceLength(){
		int rows;
		double count=0;

		rows = myList.size();

		for(int i=0;i<rows;i++){

			count+=myList.get(i).size();

		}

		return count/rows;
	}

	private int characterNumberCalculator(){
		int rows,columns=0;

		rows = myList.size();

		for(int i=0;i<rows;i++){
			for(int j=0;j<myList.get(i).size();j++){
				columns+=myList.get(i).get(j).toString().length();
			}
		}

		return columns;
	}

	private double calculatePunctuationFrequency(){
		int i,j;
		double puncCount=0, punAvg;
		for(i=0;i<myList.size();i++)
		{
			for(j=0;j<myList.get(i).size();j++)
			{
				//TODO create

				if(hasPunctuation(myList.get(i).get(j).toString()))
					puncCount++;
			}


		}
		punctuation=puncCount/myList.size();
		return punctuation;
	}

	private double calcAvgParagraphWords(TokenizerDemo tokenizer){
		try {
			ArrayList<String> para=tokenizer.parseFileToParagraphs();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	private String getFirstWord(){
		int firstWordCount = 0;
		
		List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(firstWordMap.entrySet());
		for(int i=0; i<entries.size(); i++){
			firstWordCount += entries.get(i).getValue();
		}
		double random = Math.random()*firstWordCount;
		
		int runningCount = 0;
		int i;
		for(i=0; i<entries.size(); i++){
			if(random < (runningCount+entries.get(i).getValue())){
				break;
			}
			runningCount += entries.get(i).getValue();
		}
		//System.out.println(nextWordMap);
		return entries.get(i).getKey();
	}
	
	private String getNextWord(String firstWord){
		HashMap<String, Integer> nextWordsMap;
		while ((nextWordsMap = nextWordMap.get(firstWord))==null);
		
		int nextWordCount = 0;
		
		List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(nextWordsMap.entrySet());
		for(int i=0; i<entries.size(); i++){
			nextWordCount += entries.get(i).getValue();
		}
		
		double random = Math.random()*nextWordCount;
		
		int runningCount = 0;
		int i;
		for(i=0; i<entries.size(); i++){
			if(random < (runningCount+entries.get(i).getValue())){
				break;
			}
			runningCount += entries.get(i).getValue();
		}
		return entries.get(i).getKey();
	}
	
	public void sentenceGenerate()
	{
		String prev=getFirstWord();
		int freq=0;
		for(int i=0;i<30;i++)
		{
			System.out.print(prev + " ");
			double random=Math.random(),sum = 0;
			freq=wordMap.get(prev);
			HashMap<String, Integer> next=nextWordMap.get(prev);
			List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(next.entrySet());
			for(int j=0;j<next.size();j++)
			{
				sum+=(double)entries.get(j).getValue()/freq;
				if(sum>random)
				{
					prev=entries.get(j).getKey();break;
				}
			}
		}
	}

	
	public String generateSentence(){
		String firstWord = getFirstWord();
		String sentence = firstWord;
		int length = (int) avgSentenceLength;
		
		for(int i=0; i<length*3; i++){
			String nextWord = getNextWord(firstWord);
			sentence += " " + nextWord;
			firstWord = nextWord;
		}
		
		return sentence;
	}
	
	public void calculateMetrics(){
		List<Entry<String, Integer>> wordFreq = CalculateWordFreq();
		int totalSentences = sentenceCalculator();
		int totalWords = wordNumberCalculator();
		double avgSentenceLength = avgSentenceLength();
		int totalCharacterCount = characterNumberCalculator();
		double totalPunctuationFrequency = calculatePunctuationFrequency();
		
		this.avgCharactersPerWord = totalCharacterCount*1.0/totalWords;
		this.avgSentenceLength = avgSentenceLength;
		this.punctuationFrequency = totalPunctuationFrequency;
		this.sentenceCount = totalSentences;
		this.wordCount = totalWords;
	}
	
	public void printMetrics(){
		System.out.println();
		System.out.println("Avg Characters per Word : " + avgCharactersPerWord);
		System.out.println("Avg Sentence Length     : " + avgSentenceLength);
		System.out.println("Punctuation Frequency   : " + punctuationFrequency);
		System.out.println();
	}
	
}
