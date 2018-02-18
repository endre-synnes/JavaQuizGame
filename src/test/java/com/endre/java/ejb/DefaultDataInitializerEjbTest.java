package com.endre.java.ejb;

import com.endre.java.entity.Category;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class DefaultDataInitializerEjbTest {


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "com.endre.java")
                .addAsResource("META-INF/persistence.xml");
    }


    @EJB
    private CategoryEjb categoryEjb;

    @EJB
    private QuizEjb quizEjb;

    @Test
    public void testOneCtgAndOneSubCtg() {
        assertTrue(categoryEjb.getAllCategories(false).size() > 0);

        assertTrue(categoryEjb.getAllCategories(true).stream()
                .mapToLong(c -> c.getSubCategories().size())
                .sum() > 0);

        assertTrue(quizEjb.getQuizzes().size() > 0);
    }
}
