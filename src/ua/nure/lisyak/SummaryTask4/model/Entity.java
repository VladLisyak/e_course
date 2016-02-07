package ua.nure.lisyak.SummaryTask4.model;

import java.io.Serializable;

/**
 * Base class for all entity class.
 *
 */
public abstract class Entity implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
