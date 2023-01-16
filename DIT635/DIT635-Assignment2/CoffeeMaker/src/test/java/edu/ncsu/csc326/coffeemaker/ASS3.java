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


public class ASS3 {
	
	private Inventory inv;
	private RecipeBook rb;
	private Recipe r;

	@Before
	public void setUp() throws Exception {
		//Set up for r1
		r = new Recipe();
		r.setName("Coffee");
		r.setAmtChocolate("15");
		r.setAmtCoffee("15");
		r.setAmtMilk("15");
		r.setAmtSugar("15");
		r.setPrice("50");
		
		inv = new Inventory();
		rb = new RecipeBook();
	}
	@Test
    public void testRecipeBookDeleteRecipe_Normal() {
    	rb.addRecipe(r);
    	assertEquals("Coffee", rb.deleteRecipe(0));
    	assertNotEquals(r, rb.getRecipes()[0]);
}
    @Test
    public void testInventoryEnoughIngredients_EdgeCaseInside() {
    	boolean result = inv.enoughIngredients(r);
    	assertTrue(result);
	}
}