package org.example.app.reposotories;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    boolean removeItemById(String bookIdToRemove);
}
