package ru.otus.lesson08_stream_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StreamTask implements Comparable<StreamTask> {
    private Long id;
    private String name;
    private Integer status;

    @Override
    public int compareTo(StreamTask o) {
        if (this.getStatus() > o.status) {
            return -1;
        } else if (this.getStatus() < o.status) {
            return 1;
        }
        return 0;
    }
}
