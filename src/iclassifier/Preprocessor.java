package iclassifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Preprocessor {

	public static void main(String[] args) {

		try {
			BufferedReader br1 = new BufferedReader(new FileReader(new File(args[0])));
			BufferedWriter bw_poisonous = new BufferedWriter(new FileWriter(new File(args[1])));
			BufferedWriter bw_edible = new BufferedWriter(new FileWriter(new File(args[2])));
			BufferedWriter bw_indices_map = new BufferedWriter(new FileWriter(new File(args[3])));
			Set <String> edible_itemSet = new TreeSet <String> ();
			Set <String> poisonous_itemSet = new TreeSet <String> ();
			Set <String> itemSet = new TreeSet <String> ();
			String newLine = null;
			StringBuilder sb_edible = new StringBuilder("");
			StringBuilder sb_poisonous = new StringBuilder("");
			StringBuilder sb_indexes = new StringBuilder("");
			while ((newLine = br1.readLine()) != null) {
				String[] items = newLine.split(",");
				int noOfItems = items.length;
				if (items[0].charAt(0) == 'p') {
					for (int i=1; i<noOfItems; i++) {
						if (items[i].charAt(0) != '?') {
							items[i] = i+"_"+items[i];
							poisonous_itemSet.add(items[i]);
							itemSet.add(items[i]);
							if (i != noOfItems-1) {
								sb_poisonous.append(items[i]+" ");
							} else {
								sb_poisonous.append(items[i]+"\n");
							}
						}
					}
				} else if (items[0].charAt(0) == 'e'){
					for (int i=1; i<noOfItems; i++) {
						if (items[i].charAt(0) != '?') {
							items[i] = i+"_"+items[i];
							edible_itemSet.add(items[i]);
							itemSet.add(items[i]);
							if (i != noOfItems-1) {
								sb_edible.append(items[i]+" ");
							} else {
								sb_edible.append(items[i]+"\n");
							}
						}
					}
				}
			}
			br1.close();

			bw_poisonous.write(sb_poisonous.toString().trim());
			bw_poisonous.close();

			bw_edible.write(sb_edible.toString().trim());
			bw_edible.close();
			
			System.out.println("Edible Items : "+edible_itemSet.size());
			System.out.println("Poisonous Items : "+poisonous_itemSet.size());
			System.out.println("Items : "+itemSet.size());
			Map <String, Integer> indexMap = new HashMap <String, Integer> ();
			int index = 0;
			for (String s : itemSet) {
				sb_indexes.append(s+" "+index+"\n");
				indexMap.put(s, index++);
				System.out.println(s+" "+indexMap.get(s));
			}
			
			bw_indices_map.write(sb_indexes.toString().trim());
			bw_indices_map.close();

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
