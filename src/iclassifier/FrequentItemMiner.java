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
	
	public static int threshold = 4;
	public static double thresholdRate = 5.0;
	public static double inverseThresholdRate = 1.00/thresholdRate;

	public static ArrayList <BigInteger> class1TransactionsList = new ArrayList <BigInteger> ();
	public static ArrayList <BigInteger> class2TransactionsList = new ArrayList <BigInteger> ();

	public static HashMap <BigInteger,Integer> baseItemMap1 = new HashMap <BigInteger,Integer> ();
	public static HashMap <BigInteger,Integer> baseItemMap2 = new HashMap <BigInteger,Integer> ();

	public static HashMap <Integer, HashMap <BigInteger,Integer>> frequentPatternMap1 = new HashMap <Integer, HashMap <BigInteger,Integer>> ();
	public static HashMap <Integer, HashMap <BigInteger,Integer>> frequentPatternMap2 = new HashMap <Integer, HashMap <BigInteger,Integer>> ();

	public static HashMap <BigInteger, Double> patternSupportMap1 = new HashMap <BigInteger, Double> ();
	public static HashMap <BigInteger, Double> patternSupportMap2 = new HashMap <BigInteger, Double> ();

	public static HashMap <BigInteger, Double> patternGrowthMap1 = new HashMap <BigInteger, Double> ();
	public static HashMap <BigInteger, Double> patternGrowthMap2 = new HashMap <BigInteger, Double> ();
	//	public static HashMap <BigInteger,Integer> totalFrequentMap = new HashMap <BigInteger,Integer> ();
	//	public static Map <Integer, Integer> countTracker = new HashMap <Integer, Integer> ();

	public static int frequency (BigInteger pattern, int classId) {
		int count = 0;
		if (classId == 1) {
			for (BigInteger transaction : class1TransactionsList) {
				if (transaction.and(pattern).equals(pattern)) {
					count++;
				}
			}
		} else if (classId == 2) {
			for (BigInteger transaction : class2TransactionsList) {
				if (transaction.and(pattern).equals(pattern)) {
					count++;
				}
			}
		}

		return count;
	}

	public static double support (int freq, int classId) {
		double sup = 0.0;
		if (classId == 1) {
			return (double) (freq*1.00 /class1TransactionsList.size()); 
		} else if (classId == 2) {
			return (double) (freq*1.00 /class2TransactionsList.size());
		}
		return sup;
	}

	public static double supportRatio (double supp1, double supp2, int classId) {
		double suppRatio = 0.0;

		if (classId == 1) {
			if (supp2 > 0) {
				suppRatio = supp1/supp2;
			} else {
				suppRatio = Double.MAX_VALUE;
			}
		} else if (classId == 2) {
			if (supp1 > 0) {
				suppRatio = supp2/supp1;
			} else {
				suppRatio = Double.MAX_VALUE;
			}
		}

		return suppRatio;
	}

	public static void extractPatterns(int classId) {
		if (classId == 1) {
			int baseSize1 = baseItemMap1.size();
			for (int i=1;i<baseSize1; i++) {
				Set <BigInteger> previousCandidateSet1 = frequentPatternMap1.get(i).keySet();
				HashMap <BigInteger,Integer> currentCandidateMap1 = new HashMap <BigInteger,Integer> ();
				for (BigInteger previousCandidate1 : previousCandidateSet1) {
					for (BigInteger baseItem1 : baseItemMap1.keySet()) {
						BigInteger newCandidate1 = previousCandidate1.or(baseItem1);
						if (!newCandidate1.equals(previousCandidate1)) {
							int candidateFrequency1 = frequency(newCandidate1,1);
							if (candidateFrequency1 >= threshold) {
								currentCandidateMap1.put(newCandidate1,candidateFrequency1);
							}
						}
					}
				}
				if (currentCandidateMap1.size() <= 0) {
					return;
				}
				System.out.println((i+1)+" : "+currentCandidateMap1.size());
				frequentPatternMap1.put(i+1, currentCandidateMap1);
			}
			System.out.println(frequentPatternMap1.size());
		} else if (classId == 2) {
			int baseSize2 = baseItemMap2.size();
			for (int i=1;i<baseSize2; i++) {
				Set <BigInteger> previousCandidateSet2 = frequentPatternMap2.get(i).keySet();
				HashMap <BigInteger,Integer> currentCandidateMap2 = new HashMap <BigInteger,Integer> ();
				for (BigInteger previousCandidate2 : previousCandidateSet2) {
					for (BigInteger baseItem2 : baseItemMap2.keySet()) {
						BigInteger newCandidate2 = previousCandidate2.or(baseItem2);
						if (!newCandidate2.equals(previousCandidate2)) {
							int candidateFrequency2 = frequency(newCandidate2,2);
							if (candidateFrequency2 >= threshold) {
								currentCandidateMap2.put(newCandidate2,candidateFrequency2);
							}
						}
					}
				}
				if (currentCandidateMap2.size() <= 0) {
					return;
				}
				System.out.println((i+1)+" : "+currentCandidateMap2.size());
				frequentPatternMap2.put(i+1, currentCandidateMap2);
			}
			System.out.println(frequentPatternMap2.size());
		}
	}

	public static void main(String[] args) {
		Long startTime = System.nanoTime();
		BufferedReader dataReader1 = null;
		BufferedReader dataReader2 = null;
		BufferedReader baseItemReader = null;
		
		
		System.out.println("Support Threshold = "+threshold);
		System.out.println("Support Threshold Rate = "+threshold);


		try {
			//	dataReader = new BufferedReader(new FileReader(new File(args[0])));
			dataReader1 = new BufferedReader(new FileReader(new File("mushroom-edible-biginteger-sample")));
			dataReader2 = new BufferedReader(new FileReader(new File("mushroom-poisonous-biginteger-sample")));
			String dataLine = null;

			while ((dataLine = dataReader1.readLine()) != null) {
				class1TransactionsList.add(new BigInteger(dataLine,10));
			}
			dataReader1.close();
			System.out.println("Training Sample Size class 1 = "+class1TransactionsList.size());

			while ((dataLine = dataReader2.readLine()) != null) {
				class2TransactionsList.add(new BigInteger(dataLine,10));
			}
			dataReader2.close();
			System.out.println("Training Sample Size class 2 = "+class2TransactionsList.size());

			baseItemReader = new BufferedReader(new FileReader(new File("item-index-biginteger")));
			String itemLine = null;

			while ((itemLine = baseItemReader.readLine()) != null) {

				BigInteger bigItem = new BigInteger(itemLine,10);
				int frequency1 = frequency (bigItem, 1);
				if (frequency1 >= threshold) {
					baseItemMap1.put(bigItem, frequency1);
				}
				int frequency2 = frequency (bigItem, 2);
				if (frequency2 >= threshold) {
					baseItemMap2.put(bigItem, frequency2);
				}
				double support1 = support(frequency1,1);
				patternSupportMap1.put(bigItem, support1);

				double support2 = support(frequency2,2);
				patternSupportMap2.put(bigItem, support2);

				double growthRate1 = supportRatio (support1, support2, 1);
				double growthRate2 = supportRatio (support1, support2, 2);

				// Store the base patterns and their corresponding supports //

				patternSupportMap1.put(bigItem, support1);
				patternSupportMap2.put(bigItem, support2);

				patternGrowthMap1.put(bigItem, growthRate1);
				patternGrowthMap1.put(bigItem, growthRate2);


			}
			baseItemReader.close();
			System.out.println(1+"Base Set 1 : "+baseItemMap1.size());
			System.out.println(1+"Base Set 2 : "+baseItemMap2.size());
			frequentPatternMap1.put(1, baseItemMap1);
			frequentPatternMap2.put(1, baseItemMap2);

			extractPatterns(1);
			extractPatterns(2);

			long endTime = System.nanoTime();
			System.out.println("Took "+(endTime - startTime) + " ns");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
