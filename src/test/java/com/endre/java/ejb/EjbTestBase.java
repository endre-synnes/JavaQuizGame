package com.endre.java.ejb;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.runner.RunWith;

import javax.ejb.EJB;


@RunWith(Arquillian.class)
public abstract class EjbTestBase {

    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "com.endre.java")
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    private ResetEjb resetEjb;

    @Before
    public void setUp() throws Exception {
        resetEjb.resetDatabase();
    }

}
