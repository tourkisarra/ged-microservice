package com.example.ged_microservice.service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Service
public class SearchService {

    @Autowired
    private RestHighLevelClient client;

    // Historique persistable (à adapter avec une DB réelle, ici simulée en mémoire)
    private final Map<String, List<String>> userSearchHistory = new HashMap<>();

    public List<Map<String, Object>> searchDocuments(String keyword, String type, String date, String workspace, String username, String status) throws IOException {
        SearchRequest request = new SearchRequest("ged_documents");
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (keyword != null && !keyword.isEmpty()) {
            boolQuery.must(QueryBuilders.multiMatchQuery(keyword, "title", "content"));
        }
        if (type != null && !type.isEmpty()) {
            boolQuery.filter(QueryBuilders.termQuery("file_type", type));
        }
        if (date != null && !date.isEmpty()) {
            boolQuery.filter(QueryBuilders.rangeQuery("created_date").gte(date));
        }
        if (workspace != null && !workspace.isEmpty()) {
            boolQuery.filter(QueryBuilders.termQuery("workspace", workspace));
        }
        if (status != null && !status.isEmpty()) {
            boolQuery.filter(QueryBuilders.termQuery("status", status));
        }


        if (username != null && keyword != null) {
            userSearchHistory.computeIfAbsent(username, k -> new ArrayList<>()).add("[" + Instant.now() + "] " + keyword);
        }

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(boolQuery)
                .size(50);

        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        List<Map<String, Object>> results = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            results.add(hit.getSourceAsMap());
        }

        return results;
    }

    public List<String> getSuggestions(String keyword) throws IOException {
        SearchRequest request = new SearchRequest("ged_documents");

        CompletionSuggestionBuilder suggestionBuilder = new CompletionSuggestionBuilder("title.suggest")
                .prefix(keyword)
                .size(5);

        SuggestBuilder suggestBuilder = new SuggestBuilder().addSuggestion("suggest_title", suggestionBuilder);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().suggest(suggestBuilder);

        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        List<String> suggestions = new ArrayList<>();
        response.getSuggest().getSuggestion("suggest_title").getEntries().forEach(entry ->
                entry.getOptions().forEach(option -> suggestions.add(option.getText().string())));

        return suggestions;
    }

    public List<String> getSearchHistory(String username) {
        return userSearchHistory.getOrDefault(username, Collections.emptyList());
    }
}

