package com.cj.scores.web;

import com.cj.scores.web.grpc.GrpcResult;
import com.cj.scores.web.grpc.UserScoreGrpc;
import com.cj.scores.web.grpc.UserScoreGrpcRequest;
import com.cj.scores.web.grpc.UserSelectRequest;
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
                .setUid(476).setType(1).setChangeScores(22).setComment("test grpc")
                .setSrcId(1)
                .build();
        GrpcResult response = stub.updateScore(testGrpc);
        log.info("result= {}", response.getData());

    }


    private UserSelectRequest request = UserSelectRequest.newBuilder().setUid(476).setPageNum(1).setPageSize(20).build();

    public void testGetScoreByUid() {
        UserScoreGrpc.UserScoreBlockingStub stub = UserScoreGrpc.newBlockingStub(channel);

        GrpcResult scoreByUid = stub.getScoreByUid(request);

        log.info("result = {}", scoreByUid.getData());
    }

    public void testGetScoreLogByUid() {
        UserScoreGrpc.UserScoreBlockingStub stub = UserScoreGrpc.newBlockingStub(channel);
        GrpcResult scoreByUid = stub.getScoreLogByUid(request);
        log.info("result = {}", scoreByUid.getData());
    }


    public static void main(String[] args) throws InterruptedException {
        TestClient client = new TestClient("172.28.3.9", 9091);
        client.testGprcUpdateScoreService();
        client.testGetScoreByUid();
        client.testGetScoreLogByUid();
    }


}