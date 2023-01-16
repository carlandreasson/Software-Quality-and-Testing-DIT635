package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;


public class ASS2 {
	
	private CoffeeMaker cm;
	private CoffeeMaker cm2;
	private CoffeeMaker cm3;
	private Inventory inv;
	private RecipeBook rb;
	private Recipe r1;
	private Recipe r2;
	private Recipe r3;
	private Recipe r4;
	private Recipe r5;

	@Before
	//most of the object declaration borrowed from ExampleTest.java :)
	public void setUp() throws Exception {
		cm = new CoffeeMaker();
		cm2 = new CoffeeMaker();
		cm3 = new CoffeeMaker();
		//Set up for r1
		r1 = new Recipe();
		r1.setName("Coffee");
		r1.setAmtChocolate("0");
		r1.setAmtCoffee("3");
		r1.setAmtMilk("1");
		r1.setAmtSugar("1");
		r1.setPrice("50");
		
		//Set up for r2
		r2 = new Recipe();
		r2.setName("Mocha");
		r2.setAmtChocolate("20");
		r2.setAmtCoffee("3");
		r2.setAmtMilk("1");
		r2.setAmtSugar("1");
		r2.setPrice("75");
		
		//Set up for r3
		r3 = new Recipe();
		r3.setName("Latte");
		r3.setAmtChocolate("0");
		r3.setAmtCoffee("3");
		r3.setAmtMilk("3");
		r3.setAmtSugar("1");
		r3.setPrice("100");
		
		//Set up for r4
		r4 = new Recipe();
		r4.setName("Hot Chocolate");
		r4.setAmtChocolate("4");
		r4.setAmtCoffee("0");
		r4.setAmtMilk("1");
		r4.setAmtSugar("1");
		r4.setPrice("65");

		r5 = new Recipe();
		r5.setPrice("50");
		r5.setAmtChocolate("16");
		r5.setAmtCoffee("16");
		r5.setAmtMilk("16");
		r5.setAmtSugar("16");
		r5.setPrice("50");
	

		inv = new Inventory();
		rb = new RecipeBook();
	}
	
	/*
	*
	* TESTS FOR PROBLEM 2.1
	* BELOW
	*
	* */

	@Test
	public void testMakeCoffee_Regular() { //success if branch of make coffee
		cm3.addRecipe(r3);
		assertEquals(0, cm3.makeCoffee(0, 100));
		String inventory = cm3.checkInventory();
		String expected = "Coffee: 12\nMilk: 12\nSugar: 14\nChocolate: 15\n";
		assertEquals(expected,inventory);
	}

	@Test
	public void testMakeCoffee_InvalidRecipe() { //invalid recipe branch of make coffee
		assertEquals(75, cm.makeCoffee(3, 75));
	}

	@Test
	public void testMakeCoffee_LowMoney() { //not enough money branch of make coffee
		cm.addRecipe(r1);
		assertEquals(45, cm.makeCoffee(0, 45));
	}

	@Test
	public void testMakeCoffee_NoIngredients() { //not enough ingredients in machine branch of make coffee
		cm2.addRecipe(r5);
		assertEquals(50, cm2.makeCoffee(0, 50));
	}

	@Test
	public void testAddSugar_Normal() { //normal functionality branch

		try {
			inv.addSugar("15");
		} catch (InventoryException e) {
			fail("Exception should not be thrown");
		}
		assertEquals(30, inv.getSugar());
	}
	@Test
	public void testAddSugar_InvalidInput() { //invalid input branch
		Throwable exception = assertThrows(
				InventoryException.class, () -> {
					inv.addSugar("hello");
				}
		);
		assertEquals(15, inv.getSugar());
	}
	@Test
	public void testAddSugar_NegativeInputNumber() { //invalid input number branch
		Throwable exception = assertThrows(
				InventoryException.class, () -> {
					inv.addSugar("-55");
				}
		);
		assertEquals(15, inv.getSugar());
	}

	@Test
	public void testSetPrice_Normal() { //normal functionality branch
		try {
			r5.setPrice("15");
		} catch (RecipeException e) {
			fail("Exception should not be thrown");
		}
		assertEquals(15, r5.getPrice());
	}
	@Test
	public void testSetPrice_InvalidInput() { //invalid input branch
		Throwable exception = assertThrows(
				RecipeException.class, () -> {
					r5.setPrice("hello");
				}
		);
		assertEquals(50, r5.getPrice());
	}
	@Test
	public void testSetPrice_NegativeInputNumber() { //invalid input number branch
		Throwable exception = assertThrows(
				RecipeException.class, () -> {
					r5.setPrice("-55"); // Should throw an InventoryException
				}
		);
		assertEquals(50, r5.getPrice());
	}
	@Test
	public void testaddRecipe_Normal() { //normal functionality add recipe
		boolean success = rb.addRecipe(r5);
		assertEquals(rb.getRecipes()[0], r5);
		assertTrue(success);
	}
	@Test
	public void testaddRecipe_Duplicate() { //duplicate branch add recipe
		rb.addRecipe(r5);
		boolean success = rb.addRecipe(r5);
		assertNotEquals(rb.getRecipes()[1], r5);
		assertFalse(success);
	}
	@Test
	public void testaddRecipe_FullArray() { //no room in array branch add recipe
		rb.addRecipe(r1);
		rb.addRecipe(r2);
		rb.addRecipe(r3);
		rb.addRecipe(r4);
		boolean success = rb.addRecipe(r5);
		assertFalse(success);
	}
	
	//END OF 2.1

	/*
	*
	* TESTS FOR PROBLEM 1.2
	* BELOW
	*
	* */
	@Test
	public void testCoffeeMakerAddRecipe_Normal() { //normal functionality add recipe
		boolean success = cm.addRecipe(r5);
		assertEquals(cm.getRecipes()[0], r5);
		assertTrue(success);
	}
	@Test
	public void testCoffeeMakerEditRecipe_Normal() { //normal functionality add recipe
		boolean success = cm.addRecipe(r5);
		String result = cm.editRecipe(0, r1);
		assertEquals(result, r1.getName());
		assertTrue(success);
		}
	@Test
	public void testAddInventory_Normal() { //adding inventory normal functionality
		try {
			cm.addInventory("1","1","1","1"); 
		} catch (InventoryException e) {
			fail("Exception should not be thrown");
		}
		String result = cm.checkInventory();
		String expectedResult = "Coffee: 16\nMilk: 16\nSugar: 16\nChocolate: 16\n";
		assertEquals(expectedResult, result);
	}
	@Test
	public void testMakeCoffee_Regular2() { //success if branch of make coffee
		cm3.addRecipe(r4);
		assertEquals(35, cm3.makeCoffee(0, 100));
		String result = cm3.checkInventory();
		String expectedResult = "Coffee: 12\nMilk: 12\nSugar: 14\nChocolate: 15\n";
		assertEquals(expectedResult,result);
	}
	@Test
    public void testDeleteRecipe_Normal() {
        cm.addRecipe(r1);
        assertEquals("Coffee", cm.deleteRecipe(0)); 
    }

    @Test
    public void testDeleteRecipe_Exception() {
        assertEquals(null, cm.deleteRecipe(2)); 
    }

    @Test
    public void testCheckInventory_Normal() {

        String inventory = cm.checkInventory();
        String expected = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n";

        assertEquals(expected, inventory); 

    }

    @Test
    public void testCheckInventory_Exception() {

        String inventory = cm.checkInventory();
        String expected = "Coffee: 14\nMilk: 14\nSugar: 14\nChocolate: 14\n";

        assertNotEquals(expected, inventory); 

    }
    @Test
    public void testAddChocolate_Normal() { //normal functionality branch

        try {
            inv.addChocolate("15");
        } catch (InventoryException e) {
        	fail("Exception should not be thrown");
            e.printStackTrace();
        }
        assertEquals(30, inv.getChocolate());
    }
    
    @Test
    public void testAddChocolate_InvalidInput() { //invalid input branch
        Throwable exception = assertThrows(
                InventoryException.class, () -> {
                    inv.addChocolate("hello");
                }
        );
        assertEquals(15, inv.getChocolate());
    }
    
    @Test
    public void testAddChocolate_NegativeInputNumber() { //invalid input number branch
        Throwable exception = assertThrows(
                InventoryException.class, () -> {
                    inv.addChocolate("-55");
                }
        );
        assertEquals(15, inv.getChocolate());
    }
    
    @Test
    public void testGetChocolate_Normal() {
    assertEquals(15, inv.getChocolate());    
    }
    
    @Test
    public void testAddCoffee_Normal() { //normal functionality branch

        try {
            inv.addCoffee("15");
        } catch (InventoryException e) {
        	fail("Exception should not be thrown");
            e.printStackTrace();
        }
        assertEquals(30, inv.getCoffee());
    }
    
    @Test
    public void testAddCoffee_InvalidInput() { //invalid input branch
        Throwable exception = assertThrows(
                InventoryException.class, () -> {
                    inv.addCoffee("hello");
                }
        );
        assertEquals(15, inv.getCoffee());
    }
    
    @Test
    public void testAddCoffee_NegativeInputNumber() { //invalid input number branch
        Throwable exception = assertThrows(
                InventoryException.class, () -> {
                    inv.addCoffee("-55");
                }
        );
        assertEquals(15, inv.getCoffee());
    }
    
    @Test
    public void testGetCoffee_Normal() {
    assertEquals(15, inv.getCoffee());    
    }

    @Test
    public void testAddMilk_Normal() { //normal functionality branch

        try {
            inv.addMilk("15");
        } catch (InventoryException e) {
        	fail("Exception should not be thrown");
            e.printStackTrace();
        }
        assertEquals(30, inv.getMilk());
    }
    
    @Test
    public void testAddMilk_InvalidInput() { //invalid input branch
        Throwable exception = assertThrows(
                InventoryException.class, () -> {
                    inv.addMilk("hello");
                }
        );
        assertEquals(15, inv.getMilk());
    }
    
    @Test
    public void testAddMilk_NegativeInputNumber() { //invalid input number branch
        Throwable exception = assertThrows(
                InventoryException.class, () -> {
                    inv.addMilk("-55");
                }
        );
        assertEquals(15, inv.getMilk());
    }

    @Test
    public void testGetMilk_Normal() {
    assertEquals(15, inv.getMilk());    
    }

    @Test
    public void testRecipeBookDeleteRecipe_Normal() {
    	cm.addRecipe(r1);
    	assertEquals("Coffee", cm.deleteRecipe(0)); 
}

    @Test
	public void testRecipeBookDeleteRecipe_Exception() {
    	assertEquals(null, cm.deleteRecipe(2)); 
	}
    @Test
    public void testRecipebookEditRecipe_Exception() {
    	String result = cm.editRecipe(0, r1);
    	assertEquals(result, null);
	}
    @Test
    public void testInventoryEnoughIngredients_NotEnoughIngredients() { 
    	boolean result = inv.enoughIngredients(r5);
    	assertFalse(result);
	}
    public void testGetAmtChocolate_Normal() {
    	assertEquals(4, r4.getAmtChocolate());
    }

    @Test
    public void testGetAmtCoffee_Normal() {
    	assertEquals(0, r4.getAmtCoffee());
    }

    @Test
    public void testGetAmtMilk_Normal() {
    	assertEquals(1, r4.getAmtMilk());
    }

    @Test
    public void testGetAmtSugar_Normal() {
    	assertEquals(1, r4.getAmtSugar());
    }
    @Test
    public void testGetName_Normal() {
    	assertEquals("Hot Chocolate", r4.getName());
    }
    @Test
    public void testGetPrice_Normal() {
    	assertEquals(65, r4.getPrice());
    }
    @Test
    public void testRecipeHashCode_Normal() {
    	int before = r4.hashCode();
    	r4.setName("hello");
    	assertNotEquals(before, r4.hashCode());
    }
    @Test
    public void testRecipeHashCode_NullName() {
    	r5.setName(null);
    	int before = r5.hashCode();
    	r5.setName("hello");
    	assertNotEquals(before, r5.hashCode());
    }
    @Test
    public void testRecipeEquals_WrongObjectType() {
    	r5.setName(null);
    	boolean result = r5.equals(cm);
    	assertFalse(result);
    }
    @Test
    public void testRecipeEquals_Regular() {
    	boolean result = r1.equals(r2);
    	assertFalse(result);
    }
    @Test
    public void testSetAmtChocolate_InvalidInput() { //invalid input branch
        try {
            r4.setAmtChocolate("hello");
        } catch (RecipeException e) {
           e.printStackTrace();
        }
        assertEquals(4, r4.getAmtChocolate());
    }
    
    @Test
    public void testSetAmtChocolate_NegativeInputNumber() { //invalid input number branch
        try {
            r4.setAmtChocolate("-1");
        } catch (RecipeException e) {
           e.printStackTrace();
        }
        assertEquals(4, r4.getAmtChocolate());
    }
    @Test
    public void testSetAmtCoffee_InvalidInput() { //invalid input branch
        try {
            r4.setAmtCoffee("hello");
        } catch (RecipeException e) {
           e.printStackTrace();
        }
        assertEquals(0, r4.getAmtCoffee());
    }
    
    
    @Test
    public void testSetAmtCoffee_NegativeInputNumber() { //invalid input number branch
        try {
            r4.setAmtCoffee("-1");
        } catch (RecipeException e) {
           e.printStackTrace();
        }
        assertEquals(0, r4.getAmtCoffee());
    }
    
    @Test
    public void testSetAmtSugar_InvalidInput() { //invalid input branch
        try {
            r4.setAmtSugar("hello");
        } catch (RecipeException e) {
           e.printStackTrace();
        }
        assertEquals(1, r4.getAmtSugar());
    }
    
    
    @Test
    public void testSetAmtSugar_NegativeInputNumber() { //invalid input number branch
        try {
            r4.setAmtSugar("-1");
        } catch (RecipeException e) {
           e.printStackTrace();
        }
        assertEquals(1, r4.getAmtSugar());
    }
    
    @Test
    public void testSetAmtMilk_InvalidInput() { //invalid input branch
        try {
            r4.setAmtMilk("hello");
        } catch (RecipeException e) {
           e.printStackTrace();
        }
        assertEquals(1, r4.getAmtMilk());
    }
    
    
    @Test
    public void testSetAmtMilk_NegativeInputNumber() { //invalid input number branch
        try {
            r4.setAmtMilk("-1");
        } catch (RecipeException e) {
           e.printStackTrace();
        }
        assertEquals(1, r4.getAmtMilk());
    }
    @Test
    public void testToString_Normal() {
        assertEquals(r1.getName(), r1.toString());
    }
}

//END OF 1.2
