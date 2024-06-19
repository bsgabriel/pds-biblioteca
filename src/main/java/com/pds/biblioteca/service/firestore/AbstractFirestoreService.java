package com.pds.biblioteca.service.firestore;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.pds.biblioteca.entity.AbstractFirestoreEntity;
import com.pds.biblioteca.exception.FirestoreExecuteException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public abstract class AbstractFirestoreService <T extends AbstractFirestoreEntity> {

    @Autowired
    private Firestore firestore;

    protected abstract String getCollectionName();

    protected DocumentReference addDocument(T obj) {
        try {
            return this.firestore.collection(getCollectionName()).add(obj.toMap2()).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new FirestoreExecuteException("FIRESTORE_DATA_CREATION", "Failed to add document to Firestore.", e);
        }
    }

    protected List<T> getAllDocuments(Class<T> type) {
        try {
            return this.firestore.collection(getCollectionName()).get().get().getDocuments().stream()
                    .map(document -> this.fromDocument(document, type).orElse(null))
                    .filter(Objects::nonNull)
                    .toList();
        } catch (ExecutionException | InterruptedException e) {
            throw new FirestoreExecuteException("FIRESTORE_DATA_RETRIEVAL", "Failed to retrieve documents from Firestore.", e);
        }
    }

    private Optional<T> fromDocument(DocumentSnapshot document, Class<T> cls) {
        final T obj = document.toObject(cls);

        if (obj == null)
            return Optional.empty();

        obj.setId(document.getId());
        return Optional.of(obj);
    }
}
