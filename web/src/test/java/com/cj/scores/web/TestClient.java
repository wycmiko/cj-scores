package com.cj.scores.web;

import com.cj.scores.web.grpc.GrpcResult;
import com.cj.scores.web.grpc.UserScoreGrpc;
import com.cj.scores.web.grpc.UserScoreGrpcRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestClient {


    private final ManagedChannel channel;


    private final UserScoreGrpc.UserScoreBlockingStub blockingStub;


    public TestClient(String host, int port) {


        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build();


        blockingStub = UserScoreGrpc.newBlockingStub(channel);


    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }


    public void testGprcUpdateScoreService() {
        UserScoreGrpc.UserScoreBlockingStub stub = UserScoreGrpc.newBlockingStub(channel);
        UserScoreGrpcRequest testGrpc = UserScoreGrpcRequest.newBuilder()
                .setUid(505).setType(1).setChangeScores(3000)
                .setSrcId(1)
                .setOrderNo("878411411115")
                .setComment("管理员代加-测试用")
                .build();
        GrpcResult response = stub.updateScore(testGrpc);
        log.info("result= {}", response.getData());

    }



    public static void main(String[] args) throws InterruptedException {
//        TestClient client = new TestClient("172.28.3.45", 9091);
//        TestClient client = new TestClient("172.28.3.9", 9091);
        TestClient client = new TestClient("47.100.201.54", 9091);
        client.testGprcUpdateScoreService();
    }


}