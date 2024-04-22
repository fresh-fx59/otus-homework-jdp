package ru.otus.lesson08_stream_api;

import ru.otus.lesson08_stream_api.model.StreamTask;
import ru.otus.lesson08_stream_api.service.StreamTaskService;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        StreamTaskService service = new StreamTaskService();
        Supplier<Stream<StreamTask>> streamOfTasks = service::generateTasks;

        Predicate<StreamTask> status2Predicate = task -> 2 == task.getStatus();
        List<StreamTask> tasksWithStatus2 = streamOfTasks.get().filter(status2Predicate).toList();

        Predicate<StreamTask> id50Predicate = task -> 50 == task.getId();
        boolean isTaskWithId50Present = streamOfTasks.get()
                .anyMatch(id50Predicate);

        List<StreamTask> sortedByStatusTasks = streamOfTasks.get()
                .sorted()
                .toList();

        long countTaskOfSpecificType = streamOfTasks.get()
                .filter(status2Predicate)
                .count();

        System.out.println("tasksWithStatus2 " + tasksWithStatus2);
        System.out.println("isTaskWithId50Present " + isTaskWithId50Present);
        System.out.println("sortedByStatusTasks " + sortedByStatusTasks);
        System.out.println("countTaskOfSpecificType " + countTaskOfSpecificType);
    }
}
