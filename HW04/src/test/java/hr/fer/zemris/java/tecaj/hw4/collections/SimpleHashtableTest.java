package hr.fer.zemris.java.tecaj.hw4.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

public class SimpleHashtableTest {

	/**
	 * Testing basic methods: put, remove, containsKey, containsValue, clear,
	 * isEmpty, toString
	 */
	@Test
	public void basicTest() {
		SimpleHashtable table = new SimpleHashtable(3);
		Assert.assertTrue(table.isEmpty());
		Assert.assertFalse(table.containsKey("Ivana"));

		table.put("Ivana", Integer.valueOf(2));
		table.put("Ante", Integer.valueOf(2));
		table.put("Jasna", Integer.valueOf(2));
		table.put("Kristina", Integer.valueOf(5));
		table.put("Ivana", Integer.valueOf(5));

		Assert.assertEquals(4, table.size());
		Assert.assertEquals(5, table.get("Ivana"));
		Assert.assertTrue(table.containsKey("Kristina"));
		Assert.assertTrue(table.containsValue(Integer.valueOf(2)));
		Assert.assertFalse(table.containsKey(null));

		table.remove("Kristina");
		table.remove(null);
		table.remove("Ante");
		Assert.assertEquals(2, table.size());
		Assert.assertEquals(null, table.get("Kristina"));
		Assert.assertEquals(null, table.get(null));

		table.clear();
		Assert.assertTrue(table.isEmpty());
		Assert.assertFalse(table.containsKey("Ivana"));

		table.put("Kristina", Integer.valueOf(5));
		Assert.assertEquals(1, table.size());
		Assert.assertEquals(5, table.get("Kristina"));
		table.put("Jasna", Integer.valueOf(2));
		Assert.assertEquals("[Kristina=5, Jasna=2]", table.toString());

	}

	@Test
	public void iteratorRemove_False_ContainingElementAfterRemove() {
		SimpleHashtable table = new SimpleHashtable(3);
		table.put("Ivana", Integer.valueOf(2));
		table.put("Ante", Integer.valueOf(2));
		table.put("Jasna", Integer.valueOf(2));
		table.put("Kristina", Integer.valueOf(5));
		table.put("Ivana", Integer.valueOf(5));

		Iterator<SimpleHashtable.TableEntry> iter = table.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
			}
		}

		Assert.assertFalse(table.containsKey("Ivana"));
	}

	// exceptions
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void constructor_IllegalArgumentException_NegativeCapacity() {
		SimpleHashtable table = new SimpleHashtable(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void put_IllegalArgumentException_NullKey() {
		SimpleHashtable table = new SimpleHashtable(1);
		table.put(null, Integer.valueOf(5));
	}

	@Test(expected = IllegalStateException.class)
	public void iteratorRemove_IllegalStateException_CallingRemoveTwicwInArow() {
		SimpleHashtable table = new SimpleHashtable(3);
		table.put("Ivana", Integer.valueOf(2));
		table.put("Ante", Integer.valueOf(2));
		table.put("Jasna", Integer.valueOf(2));
		table.put("Kristina", Integer.valueOf(5));
		table.put("Ivana", Integer.valueOf(5));

		Iterator<SimpleHashtable.TableEntry> iter = table.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
				iter.remove();
			}
		}
	}

	@Test(expected = ConcurrentModificationException.class)
	public void iteratorHasNext_ConcurrentModificationException_RemovigMappingOutsideIterator() {
		SimpleHashtable table = new SimpleHashtable(3);
		table.put("Ivana", Integer.valueOf(2));
		table.put("Ante", Integer.valueOf(2));
		table.put("Jasna", Integer.valueOf(2));
		table.put("Kristina", Integer.valueOf(5));
		table.put("Ivana", Integer.valueOf(5));

		Iterator<SimpleHashtable.TableEntry> iter = table.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				table.remove("Ivana");
			}
		}
	}

	@Test(expected = ConcurrentModificationException.class)
	public void iteratorNext_ConcurrentModificationException_RemovigMappingOutsideIterator() {
		SimpleHashtable table = new SimpleHashtable(3);
		table.put("Ivana", Integer.valueOf(2));
		table.put("Ante", Integer.valueOf(2));
		table.put("Jasna", Integer.valueOf(2));
		table.put("Kristina", Integer.valueOf(5));
		table.put("Ivana", Integer.valueOf(5));

		Iterator<SimpleHashtable.TableEntry> iter = table.iterator();
		while (iter.hasNext()) {
			table.remove("Ivana");
			iter.next();
		}
	}

	@Test(expected = ConcurrentModificationException.class)
	public void iteratorRemove_ConcurrentModificationException_RemovigMappingOutsideIterator() {
		SimpleHashtable table = new SimpleHashtable(3);
		table.put("Ivana", Integer.valueOf(2));
		table.put("Ante", Integer.valueOf(2));
		table.put("Jasna", Integer.valueOf(2));
		table.put("Kristina", Integer.valueOf(5));
		table.put("Ivana", Integer.valueOf(5));

		Iterator<SimpleHashtable.TableEntry> iter = table.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				table.remove("Ivana");
			}
			iter.remove();
		}
	}
}
