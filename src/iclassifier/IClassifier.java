package iclassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class IClassifier {

	public static void main(String[] args) {
		
		
		
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(new File(args[0])));
			BufferedReader br2 = new BufferedReader(new FileReader(new File(args[1])));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
