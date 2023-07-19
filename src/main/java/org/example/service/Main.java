package org.example.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.response.Response;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://api.weatherapi.com/v1/current.json" +
                "?key=ca82b8e4aed4423a92545220230507&" +
                "q=Moscow")).GET().build();


        HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(send.body());

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();

        String body = send.body();
        Response response = gson.fromJson(body, Response.class);
        System.out.println(response);
        System.out.println(response.getCurrent());


    }
}
