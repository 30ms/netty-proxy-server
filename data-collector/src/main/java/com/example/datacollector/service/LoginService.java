package com.example.datacollector.service;

import com.asprise.ocr.Ocr;
import com.example.datacollector.config.RPCRequestTemplate;
import com.example.datacollector.rpc.DefaultRequest;
import com.example.datacollector.rpc.UserToken;
import com.example.datacollector.rpc.protobuf.LoginRequestProto;
import com.example.datacollector.rpc.protobuf.LoginResponseProto;
import com.example.datacollector.rpc.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class LoginService {

    @Autowired
    private RPCRequestTemplate rpcRequestTemplate;

    public String login() throws Exception {
        String randomCodeToken;
        String randomCode;
        try {
            randomCodeToken = getRandomCode();
            randomCode= ocrRecognize();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("获取/解码验证码失败");
        }
        DefaultRequest request=new LoginRequest(new UserToken(""), LoginRequestProto.LoginRequestMessage.newBuilder()
                .setUsername("GD574004").setPassword("keyubeijing09").setCode(randomCode).setCodeToken(randomCodeToken).setUndefinedInt(0).setFixedString("6d0154ac30079f5d14b91010916d46ca").build());
        LoginResponseProto.LoginResponseMessage message = (LoginResponseProto.LoginResponseMessage) rpcRequestTemplate.call(request, LoginResponseProto.LoginResponseMessage.getDefaultInstance());
        return message.getUserToken();
    }

    private String getRandomCode() throws IOException {
        String url="http://sale.6kfs.com:8180/randomcode";
        RestTemplate restTemplate=new RestTemplate();
        System.out.println("开始获取验证码图片");
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, byte[].class);
        byte[] body = responseEntity.getBody();
        String src = ResourceUtils.getURL("image").getPath() + "/randomcode.jpg";
        File file = new File(src);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(src,false)) {
            fileOutputStream.write(body);
        }
        return responseEntity.getHeaders().get("Token").get(0);
    }

    private String ocrRecognize() throws IOException {
        Ocr.setUp();
        Ocr ocr=new Ocr();
        ocr.startEngine("eng",Ocr.SPEED_SLOW);
        String src=ResourceUtils.getURL("image").getPath() + "/randomcode.jpg";
        String s = ocr.recognize(new File[]{new File(src)}, Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT);
        s = s.substring(0, 4);//含有换行符'\n' 截取验证码
        System.out.println("验证码:"+s);
        ocr.stopEngine();
        return s;
    }
}
