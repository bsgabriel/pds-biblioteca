package com.pds.biblioteca.service.firestore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.pds.biblioteca.entity.AbstractFirestoreEntity;
import com.pds.biblioteca.exception.FirestoreExecuteException;
import com.pds.biblioteca.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public abstract class AbstractFirestoreService<T extends AbstractFirestoreEntity> {

    @Autowired
    private Firestore firestore;

    @Autowired
    private ObjectMapper objectMapper;

    protected abstract String getCollectionName();

    protected DocumentReference addDocument(T obj) {
        if (isNull(obj))
            throw new FirestoreExecuteException("FIRESTORE_DATA_CREATION", "No data provided to save.");

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

    protected void deleteDocument(String documentId) {
        if (StringUtils.isBlank(documentId))
            throw new FirestoreExecuteException("FIRESTORE_DATA_DELETION", "Document id must not be empty.");

        try {
            this.firestore.collection(this.getCollectionName())
                    .document(documentId)
                    .delete()
                    .get();
        } catch (ExecutionException | InterruptedException e) {
            throw new FirestoreExecuteException("FIRESTORE_DATA_DELETION", "Failed to delete document from Firestore.", e);
        }
    }

    protected void updateDocument(String documentId, T data) {
        if (StringUtils.isBlank(documentId))
            throw new FirestoreExecuteException("FIRESTORE_DATA_UPDATE", "Document id must not be empty.");

        var map = removeNulls(toMap(data));

        if (map.isEmpty())
            throw new FirestoreExecuteException("FIRESTORE_DATA_UPDATE", "No data provided.");

        this.firestore.collection(this.getCollectionName())
                .document(documentId)
                .update(map);
    }

    private Optional<T> fromDocument(DocumentSnapshot document, Class<T> cls) {
        final T obj = document.toObject(cls);

        if (obj == null)
            return Optional.empty();

        obj.setId(document.getId());
        return Optional.of(obj);
    }

    private Map<String, Object> toMap(T obj) {
        if (obj == null)
            return new HashMap<>(0);

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
