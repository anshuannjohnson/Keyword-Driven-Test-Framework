package kdt;

import fuzzing.Fuzzer;

public class TestHelper {
	public boolean compareString(String expected, String actual) {
		if (expected.equals(actual)) return true;
		return false;
	}

	public String getFuzzedString() {
		Fuzzer fuzzer = new Fuzzer(5, 10, 'a', 'z');
		String string =  fuzzer.fuzz();
		return fuzzer.mutate(string);
	}
}
