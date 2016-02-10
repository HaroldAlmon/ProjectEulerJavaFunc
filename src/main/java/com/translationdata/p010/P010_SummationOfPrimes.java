package com.translationdata.p010;
/** Strategy: Sieve of SieveOfEratosthenes. */
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
		final SieveOfEratosthenes primeSieve = new SieveOfEratosthenes( upperLimit );
		final long primeSum = LongStream.range(2, upperLimit)
			.filter(candidate -> primeSieve.isPrime((int) candidate))
			.sum();
		return primeSum;
	}

	@Before
	public void before() {
		System.out.printf("Running unit test...%d%n", testNo);
		testNo += 1;
	}
	
	@Test(timeout = 1_000)
	@Category(FastTest.class)
	public void TwoMillionPrimes() {
		final int upperLimit = 2_000_000;
		long primeSum = primesSum(upperLimit);
		assertEquals( "Incorrect sum", 142913828922L, primeSum );
		System.out.printf("Result(%d) = %d, %n", upperLimit, primeSum);
	}
}
