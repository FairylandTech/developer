package host.fairy.grpc.welcome;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class WelcomeServiceGrpc {

  private WelcomeServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "WelcomeService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<host.fairy.grpc.welcome.WelcomeProto.WelcomeRequest,
      host.fairy.grpc.welcome.WelcomeProto.WelcomeResponse> getWelcomeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "welcome",
      requestType = host.fairy.grpc.welcome.WelcomeProto.WelcomeRequest.class,
      responseType = host.fairy.grpc.welcome.WelcomeProto.WelcomeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<host.fairy.grpc.welcome.WelcomeProto.WelcomeRequest,
      host.fairy.grpc.welcome.WelcomeProto.WelcomeResponse> getWelcomeMethod() {
    io.grpc.MethodDescriptor<host.fairy.grpc.welcome.WelcomeProto.WelcomeRequest, host.fairy.grpc.welcome.WelcomeProto.WelcomeResponse> getWelcomeMethod;
    if ((getWelcomeMethod = WelcomeServiceGrpc.getWelcomeMethod) == null) {
      synchronized (WelcomeServiceGrpc.class) {
        if ((getWelcomeMethod = WelcomeServiceGrpc.getWelcomeMethod) == null) {
          WelcomeServiceGrpc.getWelcomeMethod = getWelcomeMethod =
              io.grpc.MethodDescriptor.<host.fairy.grpc.welcome.WelcomeProto.WelcomeRequest, host.fairy.grpc.welcome.WelcomeProto.WelcomeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "welcome"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  host.fairy.grpc.welcome.WelcomeProto.WelcomeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  host.fairy.grpc.welcome.WelcomeProto.WelcomeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new WelcomeServiceMethodDescriptorSupplier("welcome"))
              .build();
        }
      }
    }
    return getWelcomeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WelcomeServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WelcomeServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WelcomeServiceStub>() {
        @java.lang.Override
        public WelcomeServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WelcomeServiceStub(channel, callOptions);
        }
      };
    return WelcomeServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static WelcomeServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WelcomeServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WelcomeServiceBlockingV2Stub>() {
        @java.lang.Override
        public WelcomeServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WelcomeServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return WelcomeServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WelcomeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WelcomeServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WelcomeServiceBlockingStub>() {
        @java.lang.Override
        public WelcomeServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WelcomeServiceBlockingStub(channel, callOptions);
        }
      };
    return WelcomeServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WelcomeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WelcomeServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WelcomeServiceFutureStub>() {
        @java.lang.Override
        public WelcomeServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WelcomeServiceFutureStub(channel, callOptions);
        }
      };
    return WelcomeServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void welcome(host.fairy.grpc.welcome.WelcomeProto.WelcomeRequest request,
        io.grpc.stub.StreamObserver<host.fairy.grpc.welcome.WelcomeProto.WelcomeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getWelcomeMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service WelcomeService.
   */
  public static abstract class WelcomeServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return WelcomeServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service WelcomeService.
   */
  public static final class WelcomeServiceStub
      extends io.grpc.stub.AbstractAsyncStub<WelcomeServiceStub> {
    private WelcomeServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WelcomeServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WelcomeServiceStub(channel, callOptions);
    }

    /**
     */
    public void welcome(host.fairy.grpc.welcome.WelcomeProto.WelcomeRequest request,
        io.grpc.stub.StreamObserver<host.fairy.grpc.welcome.WelcomeProto.WelcomeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getWelcomeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service WelcomeService.
   */
  public static final class WelcomeServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<WelcomeServiceBlockingV2Stub> {
    private WelcomeServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WelcomeServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WelcomeServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public host.fairy.grpc.welcome.WelcomeProto.WelcomeResponse welcome(host.fairy.grpc.welcome.WelcomeProto.WelcomeRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getWelcomeMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service WelcomeService.
   */
  public static final class WelcomeServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<WelcomeServiceBlockingStub> {
    private WelcomeServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WelcomeServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WelcomeServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public host.fairy.grpc.welcome.WelcomeProto.WelcomeResponse welcome(host.fairy.grpc.welcome.WelcomeProto.WelcomeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getWelcomeMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service WelcomeService.
   */
  public static final class WelcomeServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<WelcomeServiceFutureStub> {
    private WelcomeServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WelcomeServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WelcomeServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<host.fairy.grpc.welcome.WelcomeProto.WelcomeResponse> welcome(
        host.fairy.grpc.welcome.WelcomeProto.WelcomeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getWelcomeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_WELCOME = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_WELCOME:
          serviceImpl.welcome((host.fairy.grpc.welcome.WelcomeProto.WelcomeRequest) request,
              (io.grpc.stub.StreamObserver<host.fairy.grpc.welcome.WelcomeProto.WelcomeResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getWelcomeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              host.fairy.grpc.welcome.WelcomeProto.WelcomeRequest,
              host.fairy.grpc.welcome.WelcomeProto.WelcomeResponse>(
                service, METHODID_WELCOME)))
        .build();
  }

  private static abstract class WelcomeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WelcomeServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return host.fairy.grpc.welcome.WelcomeProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("WelcomeService");
    }
  }

  private static final class WelcomeServiceFileDescriptorSupplier
      extends WelcomeServiceBaseDescriptorSupplier {
    WelcomeServiceFileDescriptorSupplier() {}
  }

  private static final class WelcomeServiceMethodDescriptorSupplier
      extends WelcomeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    WelcomeServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WelcomeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WelcomeServiceFileDescriptorSupplier())
              .addMethod(getWelcomeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
