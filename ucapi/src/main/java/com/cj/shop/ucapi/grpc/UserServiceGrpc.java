package com.cj.shop.ucapi.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
        value = "by gRPC proto compiler (version 1.10.0)",
        comments = "Source: user.proto")
public final class UserServiceGrpc {

    private UserServiceGrpc() {}

    public static final String SERVICE_NAME = "UserService";

    // Static method descriptors that strictly reflect the proto.
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getGetByUidMethod()} instead.
    public static final io.grpc.MethodDescriptor<User.Request,
            User.Result> METHOD_GET_BY_UID = getGetByUidMethodHelper();

    private static volatile io.grpc.MethodDescriptor<User.Request,
            User.Result> getGetByUidMethod;

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<User.Request,
            User.Result> getGetByUidMethod() {
        return getGetByUidMethodHelper();
    }

    private static io.grpc.MethodDescriptor<User.Request,
            User.Result> getGetByUidMethodHelper() {
        io.grpc.MethodDescriptor<User.Request, User.Result> getGetByUidMethod;
        if ((getGetByUidMethod = UserServiceGrpc.getGetByUidMethod) == null) {
            synchronized (UserServiceGrpc.class) {
                if ((getGetByUidMethod = UserServiceGrpc.getGetByUidMethod) == null) {
                    UserServiceGrpc.getGetByUidMethod = getGetByUidMethod =
                            io.grpc.MethodDescriptor.<User.Request, User.Result>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "UserService", "GetByUid"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Request.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Result.getDefaultInstance()))
                                    .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("GetByUid"))
                                    .build();
                }
            }
        }
        return getGetByUidMethod;
    }
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getGetByTokenMethod()} instead.
    public static final io.grpc.MethodDescriptor<User.Request,
            User.Result> METHOD_GET_BY_TOKEN = getGetByTokenMethodHelper();

    private static volatile io.grpc.MethodDescriptor<User.Request,
            User.Result> getGetByTokenMethod;

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<User.Request,
            User.Result> getGetByTokenMethod() {
        return getGetByTokenMethodHelper();
    }

    private static io.grpc.MethodDescriptor<User.Request,
            User.Result> getGetByTokenMethodHelper() {
        io.grpc.MethodDescriptor<User.Request, User.Result> getGetByTokenMethod;
        if ((getGetByTokenMethod = UserServiceGrpc.getGetByTokenMethod) == null) {
            synchronized (UserServiceGrpc.class) {
                if ((getGetByTokenMethod = UserServiceGrpc.getGetByTokenMethod) == null) {
                    UserServiceGrpc.getGetByTokenMethod = getGetByTokenMethod =
                            io.grpc.MethodDescriptor.<User.Request, User.Result>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "UserService", "GetByToken"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Request.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Result.getDefaultInstance()))
                                    .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("GetByToken"))
                                    .build();
                }
            }
        }
        return getGetByTokenMethod;
    }
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getIsExistMethod()} instead.
    public static final io.grpc.MethodDescriptor<User.Request,
            User.Result> METHOD_IS_EXIST = getIsExistMethodHelper();

    private static volatile io.grpc.MethodDescriptor<User.Request,
            User.Result> getIsExistMethod;

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<User.Request,
            User.Result> getIsExistMethod() {
        return getIsExistMethodHelper();
    }

    private static io.grpc.MethodDescriptor<User.Request,
            User.Result> getIsExistMethodHelper() {
        io.grpc.MethodDescriptor<User.Request, User.Result> getIsExistMethod;
        if ((getIsExistMethod = UserServiceGrpc.getIsExistMethod) == null) {
            synchronized (UserServiceGrpc.class) {
                if ((getIsExistMethod = UserServiceGrpc.getIsExistMethod) == null) {
                    UserServiceGrpc.getIsExistMethod = getIsExistMethod =
                            io.grpc.MethodDescriptor.<User.Request, User.Result>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "UserService", "IsExist"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Request.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Result.getDefaultInstance()))
                                    .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("IsExist"))
                                    .build();
                }
            }
        }
        return getIsExistMethod;
    }
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getSignInMethod()} instead.
    public static final io.grpc.MethodDescriptor<User.Request,
            User.Result> METHOD_SIGN_IN = getSignInMethodHelper();

    private static volatile io.grpc.MethodDescriptor<User.Request,
            User.Result> getSignInMethod;

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<User.Request,
            User.Result> getSignInMethod() {
        return getSignInMethodHelper();
    }

    private static io.grpc.MethodDescriptor<User.Request,
            User.Result> getSignInMethodHelper() {
        io.grpc.MethodDescriptor<User.Request, User.Result> getSignInMethod;
        if ((getSignInMethod = UserServiceGrpc.getSignInMethod) == null) {
            synchronized (UserServiceGrpc.class) {
                if ((getSignInMethod = UserServiceGrpc.getSignInMethod) == null) {
                    UserServiceGrpc.getSignInMethod = getSignInMethod =
                            io.grpc.MethodDescriptor.<User.Request, User.Result>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "UserService", "SignIn"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Request.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Result.getDefaultInstance()))
                                    .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("SignIn"))
                                    .build();
                }
            }
        }
        return getSignInMethod;
    }
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getOAuthSignInMethod()} instead.
    public static final io.grpc.MethodDescriptor<User.Request,
            User.Result> METHOD_OAUTH_SIGN_IN = getOAuthSignInMethodHelper();

    private static volatile io.grpc.MethodDescriptor<User.Request,
            User.Result> getOAuthSignInMethod;

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<User.Request,
            User.Result> getOAuthSignInMethod() {
        return getOAuthSignInMethodHelper();
    }

    private static io.grpc.MethodDescriptor<User.Request,
            User.Result> getOAuthSignInMethodHelper() {
        io.grpc.MethodDescriptor<User.Request, User.Result> getOAuthSignInMethod;
        if ((getOAuthSignInMethod = UserServiceGrpc.getOAuthSignInMethod) == null) {
            synchronized (UserServiceGrpc.class) {
                if ((getOAuthSignInMethod = UserServiceGrpc.getOAuthSignInMethod) == null) {
                    UserServiceGrpc.getOAuthSignInMethod = getOAuthSignInMethod =
                            io.grpc.MethodDescriptor.<User.Request, User.Result>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "UserService", "OAuthSignIn"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Request.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Result.getDefaultInstance()))
                                    .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("OAuthSignIn"))
                                    .build();
                }
            }
        }
        return getOAuthSignInMethod;
    }
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getRegisterMethod()} instead.
    public static final io.grpc.MethodDescriptor<User.Request,
            User.Result> METHOD_REGISTER = getRegisterMethodHelper();

    private static volatile io.grpc.MethodDescriptor<User.Request,
            User.Result> getRegisterMethod;

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<User.Request,
            User.Result> getRegisterMethod() {
        return getRegisterMethodHelper();
    }

    private static io.grpc.MethodDescriptor<User.Request,
            User.Result> getRegisterMethodHelper() {
        io.grpc.MethodDescriptor<User.Request, User.Result> getRegisterMethod;
        if ((getRegisterMethod = UserServiceGrpc.getRegisterMethod) == null) {
            synchronized (UserServiceGrpc.class) {
                if ((getRegisterMethod = UserServiceGrpc.getRegisterMethod) == null) {
                    UserServiceGrpc.getRegisterMethod = getRegisterMethod =
                            io.grpc.MethodDescriptor.<User.Request, User.Result>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "UserService", "Register"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Request.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Result.getDefaultInstance()))
                                    .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("Register"))
                                    .build();
                }
            }
        }
        return getRegisterMethod;
    }
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getOAuthRegisterMethod()} instead.
    public static final io.grpc.MethodDescriptor<User.Request,
            User.Result> METHOD_OAUTH_REGISTER = getOAuthRegisterMethodHelper();

    private static volatile io.grpc.MethodDescriptor<User.Request,
            User.Result> getOAuthRegisterMethod;

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<User.Request,
            User.Result> getOAuthRegisterMethod() {
        return getOAuthRegisterMethodHelper();
    }

    private static io.grpc.MethodDescriptor<User.Request,
            User.Result> getOAuthRegisterMethodHelper() {
        io.grpc.MethodDescriptor<User.Request, User.Result> getOAuthRegisterMethod;
        if ((getOAuthRegisterMethod = UserServiceGrpc.getOAuthRegisterMethod) == null) {
            synchronized (UserServiceGrpc.class) {
                if ((getOAuthRegisterMethod = UserServiceGrpc.getOAuthRegisterMethod) == null) {
                    UserServiceGrpc.getOAuthRegisterMethod = getOAuthRegisterMethod =
                            io.grpc.MethodDescriptor.<User.Request, User.Result>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "UserService", "OAuthRegister"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Request.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Result.getDefaultInstance()))
                                    .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("OAuthRegister"))
                                    .build();
                }
            }
        }
        return getOAuthRegisterMethod;
    }
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getOAuthBindMobileMethod()} instead.
    public static final io.grpc.MethodDescriptor<User.Request,
            User.Result> METHOD_OAUTH_BIND_MOBILE = getOAuthBindMobileMethodHelper();

    private static volatile io.grpc.MethodDescriptor<User.Request,
            User.Result> getOAuthBindMobileMethod;

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<User.Request,
            User.Result> getOAuthBindMobileMethod() {
        return getOAuthBindMobileMethodHelper();
    }

    private static io.grpc.MethodDescriptor<User.Request,
            User.Result> getOAuthBindMobileMethodHelper() {
        io.grpc.MethodDescriptor<User.Request, User.Result> getOAuthBindMobileMethod;
        if ((getOAuthBindMobileMethod = UserServiceGrpc.getOAuthBindMobileMethod) == null) {
            synchronized (UserServiceGrpc.class) {
                if ((getOAuthBindMobileMethod = UserServiceGrpc.getOAuthBindMobileMethod) == null) {
                    UserServiceGrpc.getOAuthBindMobileMethod = getOAuthBindMobileMethod =
                            io.grpc.MethodDescriptor.<User.Request, User.Result>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "UserService", "OAuthBindMobile"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Request.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Result.getDefaultInstance()))
                                    .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("OAuthBindMobile"))
                                    .build();
                }
            }
        }
        return getOAuthBindMobileMethod;
    }
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getCheckTokenMethod()} instead.
    public static final io.grpc.MethodDescriptor<User.Request,
            User.Result> METHOD_CHECK_TOKEN = getCheckTokenMethodHelper();

    private static volatile io.grpc.MethodDescriptor<User.Request,
            User.Result> getCheckTokenMethod;

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<User.Request,
            User.Result> getCheckTokenMethod() {
        return getCheckTokenMethodHelper();
    }

    private static io.grpc.MethodDescriptor<User.Request,
            User.Result> getCheckTokenMethodHelper() {
        io.grpc.MethodDescriptor<User.Request, User.Result> getCheckTokenMethod;
        if ((getCheckTokenMethod = UserServiceGrpc.getCheckTokenMethod) == null) {
            synchronized (UserServiceGrpc.class) {
                if ((getCheckTokenMethod = UserServiceGrpc.getCheckTokenMethod) == null) {
                    UserServiceGrpc.getCheckTokenMethod = getCheckTokenMethod =
                            io.grpc.MethodDescriptor.<User.Request, User.Result>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "UserService", "CheckToken"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Request.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Result.getDefaultInstance()))
                                    .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("CheckToken"))
                                    .build();
                }
            }
        }
        return getCheckTokenMethod;
    }
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getHandleMethod()} instead.
    public static final io.grpc.MethodDescriptor<User.Request,
            User.Result> METHOD_HANDLE = getHandleMethodHelper();

    private static volatile io.grpc.MethodDescriptor<User.Request,
            User.Result> getHandleMethod;

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<User.Request,
            User.Result> getHandleMethod() {
        return getHandleMethodHelper();
    }

    private static io.grpc.MethodDescriptor<User.Request,
            User.Result> getHandleMethodHelper() {
        io.grpc.MethodDescriptor<User.Request, User.Result> getHandleMethod;
        if ((getHandleMethod = UserServiceGrpc.getHandleMethod) == null) {
            synchronized (UserServiceGrpc.class) {
                if ((getHandleMethod = UserServiceGrpc.getHandleMethod) == null) {
                    UserServiceGrpc.getHandleMethod = getHandleMethod =
                            io.grpc.MethodDescriptor.<User.Request, User.Result>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "UserService", "Handle"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Request.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            User.Result.getDefaultInstance()))
                                    .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("Handle"))
                                    .build();
                }
            }
        }
        return getHandleMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static UserServiceStub newStub(io.grpc.Channel channel) {
        return new UserServiceStub(channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static UserServiceBlockingStub newBlockingStub(
            io.grpc.Channel channel) {
        return new UserServiceBlockingStub(channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the service
     */
    public static UserServiceFutureStub newFutureStub(
            io.grpc.Channel channel) {
        return new UserServiceFutureStub(channel);
    }

    /**
     */
    public static abstract class UserServiceImplBase implements io.grpc.BindableService {

        /**
         */
        public void getByUid(User.Request request,
                             io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnimplementedUnaryCall(getGetByUidMethodHelper(), responseObserver);
        }

        /**
         */
        public void getByToken(User.Request request,
                               io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnimplementedUnaryCall(getGetByTokenMethodHelper(), responseObserver);
        }

        /**
         */
        public void isExist(User.Request request,
                            io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnimplementedUnaryCall(getIsExistMethodHelper(), responseObserver);
        }

        /**
         */
        public void signIn(User.Request request,
                           io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnimplementedUnaryCall(getSignInMethodHelper(), responseObserver);
        }

        /**
         */
        public void oAuthSignIn(User.Request request,
                                io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnimplementedUnaryCall(getOAuthSignInMethodHelper(), responseObserver);
        }

        /**
         */
        public void register(User.Request request,
                             io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnimplementedUnaryCall(getRegisterMethodHelper(), responseObserver);
        }

        /**
         */
        public void oAuthRegister(User.Request request,
                                  io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnimplementedUnaryCall(getOAuthRegisterMethodHelper(), responseObserver);
        }

        /**
         */
        public void oAuthBindMobile(User.Request request,
                                    io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnimplementedUnaryCall(getOAuthBindMobileMethodHelper(), responseObserver);
        }

        /**
         */
        public void checkToken(User.Request request,
                               io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnimplementedUnaryCall(getCheckTokenMethodHelper(), responseObserver);
        }

        /**
         */
        public void handle(User.Request request,
                           io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnimplementedUnaryCall(getHandleMethodHelper(), responseObserver);
        }

        @Override public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            getGetByUidMethodHelper(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            User.Request,
                                            User.Result>(
                                            this, METHODID_GET_BY_UID)))
                    .addMethod(
                            getGetByTokenMethodHelper(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            User.Request,
                                            User.Result>(
                                            this, METHODID_GET_BY_TOKEN)))
                    .addMethod(
                            getIsExistMethodHelper(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            User.Request,
                                            User.Result>(
                                            this, METHODID_IS_EXIST)))
                    .addMethod(
                            getSignInMethodHelper(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            User.Request,
                                            User.Result>(
                                            this, METHODID_SIGN_IN)))
                    .addMethod(
                            getOAuthSignInMethodHelper(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            User.Request,
                                            User.Result>(
                                            this, METHODID_OAUTH_SIGN_IN)))
                    .addMethod(
                            getRegisterMethodHelper(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            User.Request,
                                            User.Result>(
                                            this, METHODID_REGISTER)))
                    .addMethod(
                            getOAuthRegisterMethodHelper(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            User.Request,
                                            User.Result>(
                                            this, METHODID_OAUTH_REGISTER)))
                    .addMethod(
                            getOAuthBindMobileMethodHelper(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            User.Request,
                                            User.Result>(
                                            this, METHODID_OAUTH_BIND_MOBILE)))
                    .addMethod(
                            getCheckTokenMethodHelper(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            User.Request,
                                            User.Result>(
                                            this, METHODID_CHECK_TOKEN)))
                    .addMethod(
                            getHandleMethodHelper(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            User.Request,
                                            User.Result>(
                                            this, METHODID_HANDLE)))
                    .build();
        }
    }

    /**
     */
    public static final class UserServiceStub extends io.grpc.stub.AbstractStub<UserServiceStub> {
        private UserServiceStub(io.grpc.Channel channel) {
            super(channel);
        }

        private UserServiceStub(io.grpc.Channel channel,
                                io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected UserServiceStub build(io.grpc.Channel channel,
                                        io.grpc.CallOptions callOptions) {
            return new UserServiceStub(channel, callOptions);
        }

        /**
         */
        public void getByUid(User.Request request,
                             io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getGetByUidMethodHelper(), getCallOptions()), request, responseObserver);
        }

        /**
         */
        public void getByToken(User.Request request,
                               io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getGetByTokenMethodHelper(), getCallOptions()), request, responseObserver);
        }

        /**
         */
        public void isExist(User.Request request,
                            io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getIsExistMethodHelper(), getCallOptions()), request, responseObserver);
        }

        /**
         */
        public void signIn(User.Request request,
                           io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getSignInMethodHelper(), getCallOptions()), request, responseObserver);
        }

        /**
         */
        public void oAuthSignIn(User.Request request,
                                io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getOAuthSignInMethodHelper(), getCallOptions()), request, responseObserver);
        }

        /**
         */
        public void register(User.Request request,
                             io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getRegisterMethodHelper(), getCallOptions()), request, responseObserver);
        }

        /**
         */
        public void oAuthRegister(User.Request request,
                                  io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getOAuthRegisterMethodHelper(), getCallOptions()), request, responseObserver);
        }

        /**
         */
        public void oAuthBindMobile(User.Request request,
                                    io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getOAuthBindMobileMethodHelper(), getCallOptions()), request, responseObserver);
        }

        /**
         */
        public void checkToken(User.Request request,
                               io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getCheckTokenMethodHelper(), getCallOptions()), request, responseObserver);
        }

        /**
         */
        public void handle(User.Request request,
                           io.grpc.stub.StreamObserver<User.Result> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getHandleMethodHelper(), getCallOptions()), request, responseObserver);
        }
    }

    /**
     */
    public static final class UserServiceBlockingStub extends io.grpc.stub.AbstractStub<UserServiceBlockingStub> {
        private UserServiceBlockingStub(io.grpc.Channel channel) {
            super(channel);
        }

        private UserServiceBlockingStub(io.grpc.Channel channel,
                                        io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected UserServiceBlockingStub build(io.grpc.Channel channel,
                                                io.grpc.CallOptions callOptions) {
            return new UserServiceBlockingStub(channel, callOptions);
        }

        /**
         */
        public User.Result getByUid(User.Request request) {
            return blockingUnaryCall(
                    getChannel(), getGetByUidMethodHelper(), getCallOptions(), request);
        }

        /**
         */
        public User.Result getByToken(User.Request request) {
            return blockingUnaryCall(
                    getChannel(), getGetByTokenMethodHelper(), getCallOptions(), request);
        }

        /**
         */
        public User.Result isExist(User.Request request) {
            return blockingUnaryCall(
                    getChannel(), getIsExistMethodHelper(), getCallOptions(), request);
        }

        /**
         */
        public User.Result signIn(User.Request request) {
            return blockingUnaryCall(
                    getChannel(), getSignInMethodHelper(), getCallOptions(), request);
        }

        /**
         */
        public User.Result oAuthSignIn(User.Request request) {
            return blockingUnaryCall(
                    getChannel(), getOAuthSignInMethodHelper(), getCallOptions(), request);
        }

        /**
         */
        public User.Result register(User.Request request) {
            return blockingUnaryCall(
                    getChannel(), getRegisterMethodHelper(), getCallOptions(), request);
        }

        /**
         */
        public User.Result oAuthRegister(User.Request request) {
            return blockingUnaryCall(
                    getChannel(), getOAuthRegisterMethodHelper(), getCallOptions(), request);
        }

        /**
         */
        public User.Result oAuthBindMobile(User.Request request) {
            return blockingUnaryCall(
                    getChannel(), getOAuthBindMobileMethodHelper(), getCallOptions(), request);
        }

        /**
         */
        public User.Result checkToken(User.Request request) {
            return blockingUnaryCall(
                    getChannel(), getCheckTokenMethodHelper(), getCallOptions(), request);
        }

        /**
         */
        public User.Result handle(User.Request request) {
            return blockingUnaryCall(
                    getChannel(), getHandleMethodHelper(), getCallOptions(), request);
        }
    }

    /**
     */
    public static final class UserServiceFutureStub extends io.grpc.stub.AbstractStub<UserServiceFutureStub> {
        private UserServiceFutureStub(io.grpc.Channel channel) {
            super(channel);
        }

        private UserServiceFutureStub(io.grpc.Channel channel,
                                      io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected UserServiceFutureStub build(io.grpc.Channel channel,
                                              io.grpc.CallOptions callOptions) {
            return new UserServiceFutureStub(channel, callOptions);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<User.Result> getByUid(
                User.Request request) {
            return futureUnaryCall(
                    getChannel().newCall(getGetByUidMethodHelper(), getCallOptions()), request);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<User.Result> getByToken(
                User.Request request) {
            return futureUnaryCall(
                    getChannel().newCall(getGetByTokenMethodHelper(), getCallOptions()), request);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<User.Result> isExist(
                User.Request request) {
            return futureUnaryCall(
                    getChannel().newCall(getIsExistMethodHelper(), getCallOptions()), request);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<User.Result> signIn(
                User.Request request) {
            return futureUnaryCall(
                    getChannel().newCall(getSignInMethodHelper(), getCallOptions()), request);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<User.Result> oAuthSignIn(
                User.Request request) {
            return futureUnaryCall(
                    getChannel().newCall(getOAuthSignInMethodHelper(), getCallOptions()), request);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<User.Result> register(
                User.Request request) {
            return futureUnaryCall(
                    getChannel().newCall(getRegisterMethodHelper(), getCallOptions()), request);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<User.Result> oAuthRegister(
                User.Request request) {
            return futureUnaryCall(
                    getChannel().newCall(getOAuthRegisterMethodHelper(), getCallOptions()), request);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<User.Result> oAuthBindMobile(
                User.Request request) {
            return futureUnaryCall(
                    getChannel().newCall(getOAuthBindMobileMethodHelper(), getCallOptions()), request);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<User.Result> checkToken(
                User.Request request) {
            return futureUnaryCall(
                    getChannel().newCall(getCheckTokenMethodHelper(), getCallOptions()), request);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<User.Result> handle(
                User.Request request) {
            return futureUnaryCall(
                    getChannel().newCall(getHandleMethodHelper(), getCallOptions()), request);
        }
    }

    private static final int METHODID_GET_BY_UID = 0;
    private static final int METHODID_GET_BY_TOKEN = 1;
    private static final int METHODID_IS_EXIST = 2;
    private static final int METHODID_SIGN_IN = 3;
    private static final int METHODID_OAUTH_SIGN_IN = 4;
    private static final int METHODID_REGISTER = 5;
    private static final int METHODID_OAUTH_REGISTER = 6;
    private static final int METHODID_OAUTH_BIND_MOBILE = 7;
    private static final int METHODID_CHECK_TOKEN = 8;
    private static final int METHODID_HANDLE = 9;

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final UserServiceImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(UserServiceImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_GET_BY_UID:
                    serviceImpl.getByUid((User.Request) request,
                            (io.grpc.stub.StreamObserver<User.Result>) responseObserver);
                    break;
                case METHODID_GET_BY_TOKEN:
                    serviceImpl.getByToken((User.Request) request,
                            (io.grpc.stub.StreamObserver<User.Result>) responseObserver);
                    break;
                case METHODID_IS_EXIST:
                    serviceImpl.isExist((User.Request) request,
                            (io.grpc.stub.StreamObserver<User.Result>) responseObserver);
                    break;
                case METHODID_SIGN_IN:
                    serviceImpl.signIn((User.Request) request,
                            (io.grpc.stub.StreamObserver<User.Result>) responseObserver);
                    break;
                case METHODID_OAUTH_SIGN_IN:
                    serviceImpl.oAuthSignIn((User.Request) request,
                            (io.grpc.stub.StreamObserver<User.Result>) responseObserver);
                    break;
                case METHODID_REGISTER:
                    serviceImpl.register((User.Request) request,
                            (io.grpc.stub.StreamObserver<User.Result>) responseObserver);
                    break;
                case METHODID_OAUTH_REGISTER:
                    serviceImpl.oAuthRegister((User.Request) request,
                            (io.grpc.stub.StreamObserver<User.Result>) responseObserver);
                    break;
                case METHODID_OAUTH_BIND_MOBILE:
                    serviceImpl.oAuthBindMobile((User.Request) request,
                            (io.grpc.stub.StreamObserver<User.Result>) responseObserver);
                    break;
                case METHODID_CHECK_TOKEN:
                    serviceImpl.checkToken((User.Request) request,
                            (io.grpc.stub.StreamObserver<User.Result>) responseObserver);
                    break;
                case METHODID_HANDLE:
                    serviceImpl.handle((User.Request) request,
                            (io.grpc.stub.StreamObserver<User.Result>) responseObserver);
                    break;
                default:
                    throw new AssertionError();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(
                io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new AssertionError();
            }
        }
    }

    private static abstract class UserServiceBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
        UserServiceBaseDescriptorSupplier() {}

        @Override
        public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
            return User.getDescriptor();
        }

        @Override
        public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
            return getFileDescriptor().findServiceByName("UserService");
        }
    }

    private static final class UserServiceFileDescriptorSupplier
            extends UserServiceBaseDescriptorSupplier {
        UserServiceFileDescriptorSupplier() {}
    }

    private static final class UserServiceMethodDescriptorSupplier
            extends UserServiceBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
        private final String methodName;

        UserServiceMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
            return getServiceDescriptor().findMethodByName(methodName);
        }
    }

    private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        io.grpc.ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (UserServiceGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                            .setSchemaDescriptor(new UserServiceFileDescriptorSupplier())
                            .addMethod(getGetByUidMethodHelper())
                            .addMethod(getGetByTokenMethodHelper())
                            .addMethod(getIsExistMethodHelper())
                            .addMethod(getSignInMethodHelper())
                            .addMethod(getOAuthSignInMethodHelper())
                            .addMethod(getRegisterMethodHelper())
                            .addMethod(getOAuthRegisterMethodHelper())
                            .addMethod(getOAuthBindMobileMethodHelper())
                            .addMethod(getCheckTokenMethodHelper())
                            .addMethod(getHandleMethodHelper())
                            .build();
                }
            }
        }
        return result;
    }
}
