/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-04-19 23:25:36 UTC+08:00
 ****************************************************/
package host.fairy.service.impl;

import host.fairy.grpc.HelloProto;
import host.fairy.grpc.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void hello(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        String name = request.getName();
        
        System.out.println("gRPC handler hello");
        
        HelloProto.HelloResponse.Builder builder = HelloProto.HelloResponse.newBuilder();
        builder.setResult("ok. + " + name);
        HelloProto.HelloResponse response = builder.build();
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        // super.hello(request, responseObserver);
    }
    
    @Override
    public void hello2(HelloProto.HelloRequest2 request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        request.getNameList().forEach(System.out::println);
        
        System.out.println("gRPC handler hello2");
        
        responseObserver.onNext(HelloProto.HelloResponse.newBuilder().setResult("ok").build());
        responseObserver.onCompleted();
    }
    
    @Override
    public void getLiveTimestampStream(HelloProto.LiveTimestampStreamRequest request, StreamObserver<HelloProto.LiveTimestampStreamResponse> responseObserver) {
        String date = request.getDate();
        System.out.println("gRPC request params: data=" + date);
        
        for (int i = 0; i < 11; i++) {
            String currentTimestamp = String.valueOf(System.currentTimeMillis());
            
            responseObserver.onNext(HelloProto.LiveTimestampStreamResponse.newBuilder().setTimestamp(currentTimestamp).build());
            
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        responseObserver.onCompleted();
    }
    
    @Override
    public StreamObserver<HelloProto.SendRequest> send(StreamObserver<HelloProto.SendResponse> responseObserver) {
        return new StreamObserver<HelloProto.SendRequest>() {
            @Override
            public void onNext(HelloProto.SendRequest value) {
                System.out.printf("[服务端]接收到了一条客户端消息: %s\n", value.getMessage());
            }
            
            @Override
            public void onError(Throwable t) {
                
            }
            
            @Override
            public void onCompleted() {
                // 监听客户端所有的消息都都发送到了服务端
                System.out.println("[服务端]接收到了所有的客户端消息");
                responseObserver.onNext(HelloProto.SendResponse.newBuilder().setMessage("ok").build());
                responseObserver.onCompleted();
            }
        };
    }
    
    @Override
    public StreamObserver<HelloProto.SendRequest> rotationNews(StreamObserver<HelloProto.SendResponse> responseObserver) {
        return new StreamObserver<HelloProto.SendRequest>() {
            @Override
            public void onNext(HelloProto.SendRequest value) {
                System.out.printf("[服务端]接收到了一条客户端消息: %s\n", value.getMessage());
                responseObserver.onNext(HelloProto.SendResponse.newBuilder().setMessage("ok").build());
            }
            
            @Override
            public void onError(Throwable t) {
                
            }
            
            @Override
            public void onCompleted() {
                System.out.println("[服务端]接收到了所有的客户端消息");
                responseObserver.onCompleted();
            }
        };
    }
}
