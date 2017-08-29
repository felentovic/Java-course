package hr.fer.zemris.custom.scripting.exec;


import hr.fer.zemris.java.custom.scripting.exec.EmptyStackException;
import hr.fer.zemris.java.custom.scripting.exec.MapContainstNoSuchKey;
import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

import org.junit.Assert;
import org.junit.Test;

public class ObjectMultistackTest {
	
	@Test
	public void push_True_EqualValues(){
		ObjectMultistack stack= new ObjectMultistack();
		stack.push("first", new ValueWrapper(5));
		ValueWrapper wrapper= stack.peek("first");
		stack.pop("first");
		Assert.assertEquals(5, wrapper.getValue());
	}
	
	@Test
	public void isEmpty_True_EmptyStack(){
		ObjectMultistack stack= new ObjectMultistack();
		stack.push("first", new ValueWrapper(5));
		stack.pop("first");
		Assert.assertTrue(stack.isEmpty("first"));
	}
	
	//exceptions
	@Test(expected=MapContainstNoSuchKey.class)
	public void pop_MapContainsNoSuchKey_NoValueForGivenKey(){
		ObjectMultistack stack= new ObjectMultistack();
		stack.push("first", new ValueWrapper(5));
		stack.pop("second");
	}
	
	@Test(expected=MapContainstNoSuchKey.class)
	public void isEmpty_MapContainsNoSuchKey_NoValueForGivenKey(){
		ObjectMultistack stack= new ObjectMultistack();
		stack.push("first", new ValueWrapper(5));
		stack.isEmpty("second");
	}
	
	@Test(expected= EmptyStackException.class)
	public void pop_EmptyStackException_EmptyStack(){
		ObjectMultistack stack= new ObjectMultistack();
		stack.push("first", new ValueWrapper(5));
		stack.pop("first");
		stack.pop("first");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void push_IllegalArgumentException_NullValue(){
		ObjectMultistack stack= new ObjectMultistack();
		stack.push("first", null);
	}
}
