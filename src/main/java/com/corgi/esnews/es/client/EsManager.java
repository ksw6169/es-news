package com.corgi.esnews.es.client;

import com.corgi.esnews.common.util.StringUtil;
import com.corgi.esnews.properties.EsConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
public class EsManager {

    private static EsConfiguration configuration;
    private RestHighLevelClient client;

    private static RestHighLevelClient getClient() {
        return getClient(Arrays.asList(
                new HttpHost(configuration.getHost(), Integer.parseInt(configuration.getPort()), configuration.getScheme())));
    }

    private static RestHighLevelClient getClient(List<HttpHost> hosts) {
        return new RestHighLevelClient(
                RestClient.builder(hosts.toArray(new HttpHost[0])));
    }

    public boolean exist(String index, String id) {

        GetRequest request = new GetRequest(index, id);

        try (RestHighLevelClient client = getClient()) {
            return client.exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return false;
    }

    public boolean createDocument(String index, String id, Map<String, Object> params) {

        IndexRequest request = new IndexRequest(index);
        request.source(params);

        if (!StringUtil.isEmpty(id)) {
            request.id(id);
        }

        try (RestHighLevelClient client = getClient()) {
            ReplicationResponse.ShardInfo shardInfo = client.index(request, RequestOptions.DEFAULT).getShardInfo();
            return (shardInfo.getTotal() == shardInfo.getSuccessful());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return false;
    }

    public boolean updateDocument(String index, String id, Map<String, Object> params) {

        UpdateRequest request = new UpdateRequest(index, id).doc(params);

        try (RestHighLevelClient client = getClient()) {
            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
            return (response.getResult() == DocWriteResponse.Result.UPDATED);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return false;
    }

    public boolean updateDocumentByQuery(String index, String id, QueryBuilder query) {

        UpdateByQueryRequest request = new UpdateByQueryRequest(index);
        request.setQuery(query);

        try (RestHighLevelClient client = getClient()) {
            BulkByScrollResponse response = client.updateByQuery(request, RequestOptions.DEFAULT);
            return (response.getTotal() == response.getUpdated());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return false;
    }

    public boolean deleteDocument(String index, String id) {

        DeleteRequest request = new DeleteRequest(index, id);

        try (RestHighLevelClient client = getClient()) {
            DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);

            if (response.getResult() == DocWriteResponse.Result.NOT_FOUND) {
//                throw new DocumentNotFoundException("Document not found. docId => " + id);
            }

            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();

            return (shardInfo.getTotal() == shardInfo.getSuccessful());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return false;
    }

    public <T> List<T> multiGetRequest(String index, List<String> ids, Class<T> t) {

        MultiGetRequest request = new MultiGetRequest();
        List<T> responses = null;

        for (String id : ids) {
            request.add(new MultiGetRequest.Item(index, id));
        }

        try (RestHighLevelClient client = getClient()) {
            MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
            responses = (List<T>) Arrays.asList(response.getResponses());   // converting 필요
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return responses;
    }
}
