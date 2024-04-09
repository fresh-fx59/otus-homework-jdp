package ru.otus.lesson06_reflection_api.service;

import ru.otus.lesson06_reflection_api.annotation.AfterSuite;
import ru.otus.lesson06_reflection_api.annotation.BeforeSuite;
import ru.otus.lesson06_reflection_api.annotation.Test;

import java.util.logging.Logger;


public class TestAnnotationClass {
    Logger logger = Logger.getLogger(TestAnnotationClass.class.getName());

    public TestAnnotationClass() {}

    @Test(priority = 99)
    public void annotatedWithTest99Priority() {
        logger.info("annotatedWithTest99Priority");
    }

    @Test(priority = 1)
    public void annotatedWithTest1Priority() {
        logger.info("annotatedWithTest1Priority");
    }

    @Test(priority = 3)
    public void annotatedWithTest3Priority() {
        logger.info("annotatedWithTest3Priority");
    }

    @Test(priority = 10)
    public void annotatedWithTest10Priority() {
        throw new RuntimeException("I am an exception");
    }

    @BeforeSuite
    public void annotatedWithBefore() {
        logger.info("I am annotated with BeforeSuite");
    }

    @AfterSuite
    public void annotatedWithAfter() {
        logger.info("I am annotated with AfterSuite");
    }


}
