package com.ems.ems;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class HttpService extends Service<String> {
    URI uri;

    public HttpService(URI uri){
        this.uri=uri;
    }

    public void setUri(URI uri){
        this.uri=uri;
    }

    public URI getURI(){
        return uri;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                try{
                    HttpClient client=HttpClient.newHttpClient();
                    HttpRequest request=HttpRequest
                            .newBuilder()
                            .GET()
                            .header("accept","application/json")
                            .uri(uri)
                            .build();

                    HttpResponse<String> response=client
                            .send(request,HttpResponse.BodyHandlers.ofString());
                    return response.body();
                }catch (Exception ex){
                    return "";
                }
            }
        };
    }
}
