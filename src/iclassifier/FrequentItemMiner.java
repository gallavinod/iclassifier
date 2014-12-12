package iclassifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FrequentItemMiner {

	public static void main(String[] args) {
		Map <String, Integer> itemIndexMap = null;
		ArrayList <BigInteger> transactions = null;
		BufferedReader itemIndexReader = null;
		BufferedReader dataReader = null;
		BufferedWriter dataWriter = null;

		try {
			itemIndexReader = new BufferedReader(new FileReader(new File(args[0])));
			itemIndexMap = new HashMap <String, Integer> ();
			String itemIndexLine = null;
			while ((itemIndexLine = itemIndexReader.readLine()) != null) {
				String[] itemIndex = itemIndexLine.split(" ");
				itemIndexMap.put(itemIndex[0], Integer.getInteger(itemIndex[1]));
			}
			itemIndexReader.close();

			dataReader = new BufferedReader(new FileReader(new File(args[1])));
			transactions = new ArrayList <BigInteger> ();
			String dataRecord = null;
			StringBuilder bigIntSb = new StringBuilder("");
			while ((dataRecord = dataReader.readLine()) != null) {
				BigInteger bigRecord = new BigInteger(dataRecord,2);
				transactions.add(bigRecord);
				bigIntSb.append(bigRecord+"\n");
			}
			dataReader.close();

			dataWriter = new BufferedWriter(new FileWriter(new File(args[2])));
			dataWriter.write(bigIntSb.toString().trim());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
