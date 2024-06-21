package com.pds.biblioteca.service.firestore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.pds.biblioteca.entity.AbstractFirestoreEntity;
import com.pds.biblioteca.exception.FirestoreExecuteException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public abstract class AbstractFirestoreService<T extends AbstractFirestoreEntity> {

    @Autowired
    private Firestore firestore;

    @Autowired
    private ObjectMapper objectMapper;

    protected abstract String getCollectionName();

    protected DocumentReference addDocument(T obj) {
        try {
            return this.firestore.collection(this.getCollectionName())
                    .add(this.toMap(obj))
                    .get();
        } catch (ExecutionException | InterruptedException e) {
            throw new FirestoreExecuteException("FIRESTORE_DATA_CREATION", "Failed to add document to Firestore.", e);
        }
    }

    protected List<T> getAllDocuments(Class<T> type) {
        try {
            return this.firestore.collection(this.getCollectionName())
                    .get()
                    .get()
                    .getDocuments()
                    .stream()
                    .map(document -> this.fromDocument(document, type).orElse(null))
                    .filter(Objects::nonNull)
                    .toList();
        } catch (ExecutionException | InterruptedException e) {
            throw new FirestoreExecuteException("FIRESTORE_DATA_RETRIEVAL", "Failed to retrieve documents from Firestore.", e);
        }
    }

    protected void deleteDocument(String id) {
        try {
            this.firestore.collection(this.getCollectionName())
                    .document(id)
                    .delete()
                    .get();
        } catch (ExecutionException | InterruptedException e) {
            throw new FirestoreExecuteException("FIRESTORE_DATA_DELETION", "Failed to delete document from Firestore.", e);
        }
    }

    protected void updateDocument(String documentId, T data) {
        this.firestore.collection(this.getCollectionName())
                .document(documentId)
                .update(removeNulls(toMap(data)));
    }

    private Optional<T> fromDocument(DocumentSnapshot document, Class<T> cls) {
        final T obj = document.toObject(cls);

        if (obj == null)
            return Optional.empty();

        obj.setId(document.getId());
        return Optional.of(obj);
    }

    private Map<String, Object> toMap(T obj) {
        Map<String, Object> map = this.objectMapper.convertValue(obj, new TypeReference<>() {
        });
        map.remove("id");
        return map;
    }

    private Map<String, Object> removeNulls(Map<String, Object> map) {
        return map.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
