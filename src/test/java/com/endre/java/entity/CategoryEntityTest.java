package com.endre.java.entity;

import org.junit.Test;


import static org.junit.Assert.*;

public class CategoryEntityTest extends EntityTestBase{

    @Test
    public void testTooLongName(){
        Category cars = new Category();
        cars.setName("frgtyuikjhgfvbnmuhygtfrderegtrhytykiggggggu" +
                "jyhrtgerfretgryhtjyijutyhgrtfrreetbrhyjtyhrtgetr" +
                "hyjtyhrtggetgrhytjnybrtvebrntmntbrvetryuyntbgvff" +
                "fdrgfthftgdrfsefesgrdhtfjygkujyhtgrfrrgthsyjukiu");
        assertFalse(persistInATransaction(cars));

        cars.setId(null);
        cars.setName("Cars");
        assertTrue(persistInATransaction(cars));
    }

    @Test
    public void testUniqueName() {
        Category cars = new Category();
        cars.setName("Cars");

        assertTrue(persistInATransaction(cars));

        Category cars2 = new Category();
        cars2.setName("Cars");

        assertFalse(persistInATransaction(cars2));
    }
}
