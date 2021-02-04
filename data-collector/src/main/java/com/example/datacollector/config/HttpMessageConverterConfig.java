package com.example.datacollector.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

@Configuration
public class HttpMessageConverterConfig {

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        Gson gson = new GsonBuilder()
                //自定义序列化反序列化排除策略
                .setExclusionStrategies(new ProtoBufMessageGsonExclusionStrategy())
                .create();
        gsonHttpMessageConverter.setGson(gson);
        return new HttpMessageConverters(gsonHttpMessageConverter);
    }
}
