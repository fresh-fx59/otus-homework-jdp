package ru.otus.bank.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Setter
@Getter
public class Agreement {
    private Long id;
    private String name;
    @Override
    public String toString() {
        return "Agreement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
