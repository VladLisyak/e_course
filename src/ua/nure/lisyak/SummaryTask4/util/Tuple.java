package ua.nure.lisyak.SummaryTask4.util;

import java.io.Serializable;

public class Tuple<T, R> implements Serializable{
    private T firstEntity;
    private R secondEntity;

    public Tuple() {
    }

    public Tuple(T firstEntity, R secondEntity) {
        this.firstEntity = firstEntity;
        this.secondEntity = secondEntity;
    }

    public T getFirstEntity() {
        return firstEntity;
    }

    public void setFirstEntity(T firstEntity) {
        this.firstEntity = firstEntity;
    }

    public R getSecondEntity() {
        return secondEntity;
    }

    public void setSecondEntity(R secondEntity) {
        this.secondEntity = secondEntity;
    }
}
