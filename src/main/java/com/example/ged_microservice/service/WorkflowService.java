package com.example.ged_microservice.service;

import com.example.ged_microservice.model.WorkflowStatus;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkflowService {

    @Autowired
    private RestHighLevelClient elasticsearchClient;

    public void updateWorkflowStatus(String documentId, WorkflowStatus status) throws IOException {
        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put("status", status.name());

        UpdateRequest updateRequest = new UpdateRequest("ged_documents", documentId)
                .doc(updateFields, XContentType.JSON);
        elasticsearchClient.update(updateRequest, RequestOptions.DEFAULT);
    }

    public void submitForApproval(String documentId) throws IOException {
        updateWorkflowStatus(documentId, WorkflowStatus.PENDING);
    }

    public void approveDocument(String documentId) throws IOException {
        updateWorkflowStatus(documentId, WorkflowStatus.APPROVED);
    }

    public void rejectDocument(String documentId) throws IOException {
        updateWorkflowStatus(documentId, WorkflowStatus.REJECTED);
    }
}
