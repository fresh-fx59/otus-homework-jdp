package ru.otus.lesson08_stream_api.service;

import ru.otus.lesson08_stream_api.model.StreamTask;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.stream.Stream;

public class StreamTaskService {
    public Stream<StreamTask> generateTasks() {
        return Stream
                .generate(() -> new StreamTask(new Random().nextLong(), generateName(), generateStatus()))
                .limit(100);
    }

    private String generateName() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private Integer generateStatus() {
        return new Random().nextInt(1, 3);
    }
}
