package com.skopik.confluence.plugins.productivity.task;

/**
 * Created by skopa01 on 9/3/2016.
 */
public interface AsynchronousTaskManager {
    void getTask(String taskId);

    String addTask();
}
