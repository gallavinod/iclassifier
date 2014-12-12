package iclassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class FrequentItemMiner {
	
	public static ArrayList <BigInteger> transactionsList = new ArrayList <BigInteger> ();
	public static HashSet <BigInteger> baseItemSet = new HashSet <BigInteger> ();
	public static Map <Integer, HashSet <BigInteger>> frequentPatternMap = new HashMap <Integer, HashSet <BigInteger>> ();
	public static int size = 1;
	
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
		BufferedReader dataReader = null;
		BufferedReader baseItemReader = null;
		int threshold = 0;
		
		
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
					System.out.println(new BigInteger(itemLine,10)+" "+itemFrequency);
					baseItemSet.add(new BigInteger(itemLine,10));
				}
			}
			baseItemReader.close();
			
			frequentPatternMap.put(size, baseItemSet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
