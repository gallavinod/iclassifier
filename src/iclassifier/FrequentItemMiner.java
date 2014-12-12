package iclassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class FrequentItemMiner {

	public static ArrayList <BigInteger> transactionsList = new ArrayList <BigInteger> ();
	public static HashMap <BigInteger,Integer> baseItemMap = new HashMap <BigInteger,Integer> ();
	public static HashMap <Integer, HashMap <BigInteger,Integer>> frequentPatternMap = new HashMap <Integer, HashMap <BigInteger,Integer>> ();
	//	public static HashMap <BigInteger,Integer> totalFrequentMap = new HashMap <BigInteger,Integer> ();
	//	public static Map <Integer, Integer> countTracker = new HashMap <Integer, Integer> ();

	public static int frequency (BigInteger pattern) {
		int count = 0;
		for (BigInteger transaction : transactionsList) {
			if (transaction.and(pattern).equals(pattern)) {
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		Long startTime = System.nanoTime();
		BufferedReader dataReader = null;
		BufferedReader baseItemReader = null;
		int threshold = 2000;


		try {
			//	dataReader = new BufferedReader(new FileReader(new File(args[0])));
			dataReader = new BufferedReader(new FileReader(new File("mushroom-edible-biginteger")));
			String dataLine = null;

			while ((dataLine = dataReader.readLine()) != null) {
				transactionsList.add(new BigInteger(dataLine,10));
			}
			dataReader.close();
			System.out.println(transactionsList.size());

			baseItemReader = new BufferedReader(new FileReader(new File("item-index-biginteger")));
			String itemLine = null;

			while ((itemLine = baseItemReader.readLine()) != null) {
				int itemFrequency = frequency(new BigInteger(itemLine,10));
				if (itemFrequency >= threshold) {
					baseItemMap.put(new BigInteger(itemLine,10),itemFrequency);
				}
			}
			baseItemReader.close();
			System.out.println(1+" : "+baseItemMap.size());
			frequentPatternMap.put(1, baseItemMap);
			//	totalFrequentMap.putAll(baseItemMap);
			int baseSize = baseItemMap.size();
			for (int i=1;i<baseSize; i++) {
				Set <BigInteger> previousCandidateSet = frequentPatternMap.get(i).keySet();
				HashMap <BigInteger,Integer> currentCandidateMap = new HashMap <BigInteger,Integer> ();
				for (BigInteger previousCandidate : previousCandidateSet) {
					for (BigInteger baseItem : baseItemMap.keySet()) {
						BigInteger newCandidate = previousCandidate.or(baseItem);
						if (!newCandidate.equals(previousCandidate)) {
							int candidateFrequency = frequency(newCandidate);
							if (candidateFrequency >= threshold) {
								currentCandidateMap.put(newCandidate,candidateFrequency);
							}
						}
					}
				}
				System.out.println((i+1)+" : "+currentCandidateMap.size());
				frequentPatternMap.put(i+1, currentCandidateMap);
			}
			System.out.println(frequentPatternMap.size());

			long endTime = System.nanoTime();
			System.out.println("Took "+(endTime - startTime) + " ns");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
