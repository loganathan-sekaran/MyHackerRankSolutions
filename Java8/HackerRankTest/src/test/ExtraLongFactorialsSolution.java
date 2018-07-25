package test;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class ExtraLongFactorialsSolution {

    // Complete the extraLongFactorials function below.
    static void extraLongFactorials(int n) {
        BigInteger res= new BigInteger("1");
        for (int i = 1; i <= n; i++) {
        	res = res.multiply(new BigInteger("" + i));
        }
       System.out.println(res);

    }

    private static final Scanner scanner = getScanner();

	private static Scanner getScanner()  {
		try {
			return new Scanner(new FileInputStream(System.getenv("INPUT_PATH")));
		} catch (FileNotFoundException e) {
			return new Scanner(System.in);
		}
	}

    public static void main(String[] args) {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        extraLongFactorials(n);

        scanner.close();
    }
}
