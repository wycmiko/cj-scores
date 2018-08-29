package com.cj.scores.web.utils;

import com.cj.scores.api.pojo.Result;
import com.cj.scores.api.pojo.request.UserScoresRequest;
import com.cj.scores.web.grpc.GrpcResult;
import com.cj.scores.web.grpc.UserScoreGrpcRequest;

import java.util.Objects;

/**
 * @author yuchuanWeng
 * @date 2018/8/29
 * @since 1.0
 */
public final class GrpcBeanFactory {
    private GrpcBeanFactory() {
    }

    /**
     * 根据GRPC对象构造SERVICE对象
     *
     * @return
     */
    public static UserScoresRequest createUserScoreRequest(UserScoreGrpcRequest request) {
        UserScoresRequest var1 = new UserScoresRequest();
        var1.setUid(request.getUid());
        var1.setChangeScores(request.getChangeScores());
        var1.setSrcId(request.getSrcId());
        var1.setComment(request.getComment());
        var1.setType(request.getType());
        return var1;
    }

    /**
     * 构造Grpc返回对象
     *
     * @return
     */
    public static GrpcResult createGrpcResult(Result result) {
        Objects.requireNonNull(result);
        return GrpcResult.newBuilder().setCode(result.getCode()).setMsg(result.getMsg())
                .setData(result.getData() == null ? null : result.getData().toString()).build();
    }
}
