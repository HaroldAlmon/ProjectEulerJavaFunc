package com.translationdata.p010;
/** Strategy: Brute Force. */
import static org.junit.Assert.assertEquals;

import java.util.stream.LongStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import JUnitTests.FastTest;
import misc.SieveOfEratosthenes;
public class P010_SummationOfPrimes {
	static int testNo = 1;

	public long primesSum(final int upperLimit) {
		SieveOfEratosthenes primeSieve = new SieveOfEratosthenes( upperLimit );
		final long primeSum = LongStream.range(2, upperLimit).filter(x -> primeSieve.isPrime((int) x)).sum();
		
		System.out.printf("Result(%d) = %d, %n", upperLimit, primeSum);
		return primeSum;
	}

	@Before
	public void before() {
		System.out.printf("Running unit test...%d%n", testNo);
		testNo += 1;
	}
	
	@Test(timeout = 300_000)
	@Category(FastTest.class)
	public void TwoMillionPrimes() {
		// takes ~133 seconds on an i7 laptop
		long primeSum = primesSum(2_000_000);
		assertEquals( "Incorrect sum", 142913828922L, primeSum );

	}
}
