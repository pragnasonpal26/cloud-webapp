package com.neu.csye6225.webApplication;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.stereotype.Service;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
	
	@Service
	public class ProductService {
	   public String getBookName() {
	      return "Book1";
	   } 
	   
	}
	
}
	
//    /**
//     * Create the test case
//     *
//     * @param testName name of the test case
//     */
//    public AppTest( String testName )
//    {
//        super( testName );
//    }
//
//    /**
//     * @return the suite of tests being tested
//     */
//    public static Test suite()
//    {
//        return new TestSuite( AppTest.class );
//    }
//
//    /**
//     * Rigourous Test :-)
//     */
//    public void testApp()
//    {
//        assertTrue( true );
//    }
//}
