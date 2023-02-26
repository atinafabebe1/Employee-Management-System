package com.ems.ems;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpServicePool extends Service {
    private URI uri;
    private String postData;

    public HttpServicePool(URI uri, String postData){
        this.uri = uri;
        this.postData = postData;
    }

    public void setUri(URI uri){
        this.uri = uri;
    }

    public URI getURI(){
        return uri;
    }

    public void setPostData(String postData){
        this.postData = postData;
    }

    public String getPostData(){
        return postData;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                try{
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(uri)
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(postData))
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    return response.body();
                }catch (Exception ex){
                    return "";
                }
            }
        };
    }

}
