package iclassifier;

import java.math.BigInteger;

public class TestRunner {

	public static void main(String[] args) {
		String num = "10010000100010000000010000000010100100100000100000100010000000100001000000010000000000101100000000011010000010000000";
		String bum = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010000000";
		System.out.println(new BigInteger(num,2));
		System.out.println((new BigInteger(num,2).toString(2)));
		
		byte[] byteArray = {1,0,1,0};
		System.out.println((new BigInteger(num,2)).and(new BigInteger(bum,2)));
	}

}
