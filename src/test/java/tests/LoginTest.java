package tests;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.TestException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import kdt.KeyWordEngine;

public class LoginTest {
	
	@Test
	@Parameters("sheetName")
	public void loginTest(String sheetName) throws TestException, FileNotFoundException, IOException{
		System.out.println(sheetName);
		KeyWordEngine keyWordEngine = new KeyWordEngine();
		keyWordEngine.startExecution(sheetName);
	}

}
