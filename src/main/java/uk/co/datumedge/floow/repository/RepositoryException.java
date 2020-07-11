package uk.co.datumedge.floow.repository;

/**
 * Represents a failure to load or save repository data.
 */
public class RepositoryException extends RuntimeException {
    public RepositoryException(Throwable cause) {
        super(cause);
    }
}
