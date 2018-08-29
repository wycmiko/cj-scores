package com.cj.scores.web.grpc;

import com.cj.scores.service.impl.ScoreService;
import com.cj.scores.web.utils.GrpcBeanFactory;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * grpc 对外服务
 *
 * @author yuchuanWeng
 * @date 2018/8/29
 * @since 1.0
 */
@GrpcService(UserScoreGrpc.class)
@Slf4j
public class ScoreGrpcServer extends UserScoreGrpc.UserScoreImplBase {
    @Autowired
    private ScoreService service;

    /**
     * <pre>
     * 服务端接口方法
     * </pre>
     * 修改积分
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void updateScore(UserScoreGrpcRequest request, StreamObserver<GrpcResult> responseObserver) {
        log.info("grpc updateScore request = {}", request);
        GrpcResult reply = GrpcBeanFactory.createGrpcResult(service.updateUserScoresGrpc(GrpcBeanFactory.createUserScoreRequest(request)));
        log.info("grpc updateScore end reply = {}", reply.toString());
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    /**
     * <pre>
     * 服务端接口方法
     * </pre>
     * 根据uid查询积分
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void getScoreByUid(UserSelectRequest request, StreamObserver<GrpcResult> responseObserver) {
        log.info("grpc getScoreByUid request = {}", request);
        GrpcResult reply = GrpcBeanFactory.createGrpcResult(service.getUserScoreByUidGrpc(request.getUid()));
        log.info("grpc getScoreByUid end reply = {}", reply.toString());
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    /**
     * <pre>
     * 服务端接口方法
     * </pre>
     * 根据uid查询积分日志
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void getScoreLogByUid(UserSelectRequest request, StreamObserver<GrpcResult> responseObserver) {
        log.info("grpc getScoreLogByUid request = {}", request);
        GrpcResult reply = GrpcBeanFactory.createGrpcResult(service.getUserScoreLogByUidGrpc(request.getUid(), request.getPageNum(), request.getPageSize()));
        log.info("grpc getScoreLogByUid end reply = {}", reply.toString());
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
