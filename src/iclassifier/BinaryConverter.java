package iclassifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BinaryConverter {

	public static void main(String[] args) {
		try {
			BufferedReader indexFile = null;
			BufferedReader dataFile = null;
			BufferedWriter binaryDataFile = null;
			
			indexFile = new BufferedReader(new FileReader(new File(args[0])));
			Map <String, Integer> itemIndexMap = new HashMap <String, Integer> ();
			Map <Integer, String> indexItemMap = new HashMap <Integer, String> ();
			String line = null;
			while ((line=indexFile.readLine()) != null) {
				String[] indexesMap = line.split(" ");
				itemIndexMap.put(indexesMap[0], Integer.parseInt(indexesMap[1]));
				indexItemMap.put(Integer.parseInt(indexesMap[1]),indexesMap[0]);
			}
			indexFile.close();
			
			int length=indexItemMap.size();
			dataFile = new BufferedReader(new FileReader(new File(args[1])));
			String dataLine = null;
			byte[] binaryArray = null;
			StringBuilder sb = new StringBuilder("");
			while ((dataLine = dataFile.readLine()) != null) {
				binaryArray = new byte[length];
				String[] items = dataLine.split(" ");
				for (String item : items) {
					binaryArray[itemIndexMap.get(item)] = 1;
				}
				for (int i=0; i<length; i++) {
					sb.append(binaryArray[i]);
				}
				sb.append("\n");
			}
			dataFile.close();
			
			binaryDataFile = new BufferedWriter(new FileWriter(new File(args[2])));
			binaryDataFile.write(sb.toString().trim());
			binaryDataFile.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
