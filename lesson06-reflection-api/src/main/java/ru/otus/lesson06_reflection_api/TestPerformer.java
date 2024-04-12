package ru.otus.lesson06_reflection_api;

import ru.otus.lesson06_reflection_api.annotation.AfterSuite;
import ru.otus.lesson06_reflection_api.annotation.BeforeSuite;
import ru.otus.lesson06_reflection_api.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class TestPerformer {
    Logger logger = Logger.getLogger(TestPerformer.class.getName());
    
    public void performTests(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        logger.info("performTests started");

        Method[] methods = clazz.getDeclaredMethods();

        checkBeforeAfterUsedOnce(methods);

        Object invoker = clazz.getConstructor().newInstance();

        Method beforeMethod = getAnnotatedMethod(methods, BeforeSuite.class);
        Method afterMethod = getAnnotatedMethod(methods, AfterSuite.class);
        List<Method> testMethods = getAnnotatedTestMethods(methods);

        try {
            assert beforeMethod != null;
            beforeMethod.invoke(invoker);
        } catch (Exception e) {
            logger.warning("beforeMethod throws exception " + e.getMessage());
        }

        int allTests = testMethods.size();
        AtomicInteger failedTests = new AtomicInteger();
        AtomicInteger successTests = new AtomicInteger();

        testMethods.forEach(method -> {
            try {
                method.invoke(invoker);
                successTests.getAndIncrement();
            } catch (Exception e) {
                logger.warning("Exception in method " + method.getName() + " text " + e.getMessage());
                failedTests.getAndIncrement();
            }
        });

        try {
            assert afterMethod != null;
            afterMethod.invoke(invoker);
        } catch (Exception e) {
            logger.warning("afterMethod throws exception " + e.getMessage());
        }

        logger.info("all tests = " + allTests + " success = " + successTests.get() + " failed = " + failedTests.get());

        logger.info("performTests end");
    }

    private List<Method> getAnnotatedTestMethods(Method[] methods) {
        Map<Integer, Method> result = new TreeMap<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class))
                result.put(method.getAnnotation(Test.class).priority(), method);
        }
        return result.values().stream().toList();
    }


    private <T extends Annotation> Method getAnnotatedMethod(Method[] methods, Class<T> annotationToSearch) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationToSearch))
                return method;
        }
        return null;
    }

    private void checkBeforeAfterUsedOnce(Method[] methods) {
        int afterAnnotationCounter = 0;
        int beforeAnnotationCounter = 0;

        for (Method method : methods) {
            if (method.isAnnotationPresent(AfterSuite.class))
                afterAnnotationCounter++;
            if (method.isAnnotationPresent(BeforeSuite.class))
                beforeAnnotationCounter++;
        }

        if (beforeAnnotationCounter > 1) {
            throw new IllegalStateException("it is prohibited to set BeforeSuite annotation more than once in class");
        } else if (afterAnnotationCounter > 1) {
            throw new IllegalStateException("it is prohibited to set AfterSuite annotation more than once in class");
        }
    }
}
