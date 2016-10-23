package com.skopik.confluence.plugins.productivity.api;

/**
 * A generic interface for page operations.
 *
 * @param <T> Object type associated with the operation.
 */
public interface PageOperation<T> {

    /**
     * Runs a page operation.
     *
     * @return Associated object type.
     */
    T run();

}
