package ru.otus.lesson06_reflection_api;

import ru.otus.lesson06_reflection_api.service.TestAnnotationClass;

import java.lang.reflect.InvocationTargetException;


public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestPerformer tp = new TestPerformer();
        tp.performTests(TestAnnotationClass.class);
    }


}
