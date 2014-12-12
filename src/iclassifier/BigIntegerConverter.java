package iclassifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class BigIntegerConverter {

	public static void main(String[] args) {
		BufferedReader dataReader = null;
		BufferedWriter dataWriter = null;

		try {

			dataReader = new BufferedReader(new FileReader(new File(args[0])));
			String dataRecord = null;
			StringBuilder bigIntSb = new StringBuilder("");
			while ((dataRecord = dataReader.readLine()) != null) {
				BigInteger bigRecord = new BigInteger(dataRecord,2);
				bigIntSb.append(bigRecord+"\n");
			}
			dataReader.close();

			dataWriter = new BufferedWriter(new FileWriter(new File(args[1])));
			dataWriter.write(bigIntSb.toString().trim());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
