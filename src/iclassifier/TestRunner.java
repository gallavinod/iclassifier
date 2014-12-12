package iclassifier;

import java.math.BigInteger;

public class TestRunner {

	public static void main(String[] args) {
		String num = "00000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000";
		String bum = "1125899906842624";
		System.out.println(new BigInteger(num,2));
		
		BigInteger big = new BigInteger(bum,10);
		System.out.println(big);
		System.out.println((new BigInteger(num,2).toString(2)));
		
		byte[] byteArray = {1,0,1,0};
		System.out.println((new BigInteger(num,2)).and(new BigInteger(bum,10)));
		
		if (0.0045 > 0) {
			System.out.println("true");
		}
	}

}
