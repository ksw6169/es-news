package com.corgi.esnews.es.client;

import com.corgi.esnews.properties.ElasticsearchProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
public class EsManager {

    private static ElasticsearchProperties properties;

    private final static Settings DEFAULT_SETTINGS = Settings.builder()
            .put("index.number_of_shards", 3)
            .put("index.number_of_replicas", 2).build();

    private static RestHighLevelClient getClient() {
        return getClient(Arrays.asList(
                new HttpHost(
                        properties.getHost(),
                        Integer.parseInt(properties.getPort()),
                        properties.getScheme())));
    }

    private static RestHighLevelClient getClient(List<HttpHost> hosts) {
        return new RestHighLevelClient(
                RestClient.builder(hosts.toArray(new HttpHost[0])));
    }

    public static boolean createIndex(String index, Settings settings, Map<String, Object> mappings) {

        CreateIndexRequest request = new CreateIndexRequest(index);

        if (settings == null) {
            settings = DEFAULT_SETTINGS;
        }

        request.settings(settings);

        if (mappings != null) {
            request.mapping(mappings);
        }

        try (RestHighLevelClient client = getClient()) {
            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
            return response.isAcknowledged();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return false;
    }

    public static boolean deleteIndex(String index) {

        DeleteIndexRequest request = new DeleteIndexRequest(index);

        try (RestHighLevelClient client = getClient()) {
            AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
            return response.isAcknowledged();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return false;
    }

    public boolean putDocument(String index, Map<String, Object> params) {

        IndexRequest request = new IndexRequest(index);
        request.source(params);
//        request.id();

        try (RestHighLevelClient client = getClient()) {
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
            return (shardInfo.getFailed() == 0);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return false;
    }

//    public void putDocuments(String index) {
//
//    }
}
