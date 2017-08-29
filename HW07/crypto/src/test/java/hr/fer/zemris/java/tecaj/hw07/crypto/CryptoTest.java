package hr.fer.zemris.java.tecaj.hw07.crypto;

import org.junit.Assert;
import org.junit.Test;

public class CryptoTest {
	
	@Test
	public void hexToByte_True_ComparingArrays(){
		byte[] array= new byte[]{
				0x12,(byte) 0xAF};
		String str="12AF";
		Assert.assertArrayEquals(array, Crypto.hextobyte(str));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hextobyte_IllegalArgumentException_InvalidNumberOfHexNumbers(){
		byte[] array= new byte[]{
				0x12,(byte) 0xAF};
		String str="12afa";
		Assert.assertArrayEquals(array, Crypto.hextobyte(str));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hextobyte_IllegalArgumentException_NotHexCharacter(){
		byte[] array= new byte[]{
				0x53,0x74,0x72,0x69,0x6E,0x67};
		String str="String";
		Assert.assertArrayEquals(array, Crypto.hextobyte(str));
	}
}
