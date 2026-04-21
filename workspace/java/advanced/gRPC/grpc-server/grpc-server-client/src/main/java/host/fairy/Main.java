/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-04-19 23:46:45 UTC+08:00
 ****************************************************/
package host.fairy;

import host.fairy.grpc.HelloProto;
import host.fairy.grpc.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Slf4j
public class Main {
    
    public static void main(String[] args) {
        // blockCall();
        // nonBlockCall();
        // nonBlockCallClientStream();
        nonBlockCallBothwayStream();
    }
    
    public static void blockCall() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();
        
        HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub = HelloServiceGrpc.newBlockingStub(channel);  // 阻塞调用
        
        HelloProto.HelloRequest request = HelloProto.HelloRequest.newBuilder()
                .setName("gRPC Client")
                .build();
        
        HelloProto.HelloRequest2 request2 = HelloProto.HelloRequest2.newBuilder()
                .addName("name1")
                .addName("name2")
                .addName("name3")
                .build();
        
        HelloProto.LiveTimestampStreamRequest liveTimestampStreamRequest = HelloProto.LiveTimestampStreamRequest.newBuilder()
                .setDate("2026/04/20")
                .build();
        
        HelloProto.HelloResponse response = helloServiceBlockingStub.hello(request);
        System.out.println(response.getResult());
        
        HelloProto.HelloResponse response2 = helloServiceBlockingStub.hello2(request2);
        System.out.println(response2.getResult());
        
        Iterator<HelloProto.LiveTimestampStreamResponse> liveTimestampStream = helloServiceBlockingStub.getLiveTimestampStream(liveTimestampStreamRequest);
        liveTimestampStream.forEachRemaining((liveTimestampStreamResponse -> {
            System.out.println(liveTimestampStreamResponse.getTimestamp());
        }));
    }
    
    public static void nonBlockCall() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();
        
        HelloProto.LiveTimestampStreamRequest request = HelloProto.LiveTimestampStreamRequest.newBuilder()
                .setDate("小白")
                .build();
        
        try {
            HelloServiceGrpc.HelloServiceStub serviceStub = HelloServiceGrpc.newStub(channel);
            serviceStub.getLiveTimestampStream(request, new StreamObserver<HelloProto.LiveTimestampStreamResponse>() {
                @Override
                public void onNext(HelloProto.LiveTimestampStreamResponse value) {
                    // 服务端响应了一个消息后，立即处理
                    System.out.printf("服务端每次响应的消息: %s%n", value.getTimestamp());
                }
                
                @Override
                public void onError(Throwable t) {
                    
                }
                
                @Override
                public void onCompleted() {
                    // 需要把服务端响应的所有数据拿到后再进行业务处理
                    System.out.println("响应结束后的所有消息");
                }
            });
            
            System.out.printf("当前的时间戳: %s%n", System.currentTimeMillis());
            
            channel.awaitTermination(10, TimeUnit.MINUTES);
        } catch (Exception exception) {
            log.error("Error: {}", exception.getMessage(), exception);
        } finally {
            channel.shutdown();
        }
    }
    
    public static void nonBlockCallClientStream() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();
        
        List<HelloProto.SendRequest> sendRequests = List.of(
                HelloProto.SendRequest.newBuilder()
                        .setMessage("Alice")
                        .build(),
                HelloProto.SendRequest.newBuilder()
                        .setMessage("Bob")
                        .build(),
                HelloProto.SendRequest.newBuilder()
                        .setMessage("C")
                        .build()
        );
        
        try {
            HelloServiceGrpc.HelloServiceStub serviceStub = HelloServiceGrpc.newStub(channel);
            
            StreamObserver<HelloProto.SendRequest> send = serviceStub.send(new StreamObserver<HelloProto.SendResponse>() {
                @Override
                public void onNext(HelloProto.SendResponse value) {
                    // 监控响应
                    System.out.printf("[客户端]收到响应: %s%n", value.getMessage());
                }
                
                @Override
                public void onError(Throwable t) {
                    
                }
                
                @Override
                public void onCompleted() {
                    System.out.println("[客户端]服务端响应结束");
                }
            });
            
            for (HelloProto.SendRequest request : sendRequests) {
                send.onNext(request);
            }
            
            System.out.printf("当前的时间戳: %s%n", System.currentTimeMillis());
            
            send.onCompleted();
            channel.awaitTermination(10, TimeUnit.SECONDS);
        } catch (Exception exception) {
            log.error("Error: {}", exception.getMessage(), exception);
        } finally {
            channel.shutdown();
        }
    }
    
    public static void nonBlockCallBothwayStream() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();
        
        List<HelloProto.SendRequest> sendRequests = List.of(
                HelloProto.SendRequest.newBuilder()
                        .setMessage("Alice")
                        .build(),
                HelloProto.SendRequest.newBuilder()
                        .setMessage("Bob")
                        .build(),
                HelloProto.SendRequest.newBuilder()
                        .setMessage("C")
                        .build()
        );
        
        try {
            HelloServiceGrpc.HelloServiceStub serviceStub = HelloServiceGrpc.newStub(channel);
            
            StreamObserver<HelloProto.SendRequest> observer = serviceStub.rotationNews(new StreamObserver<HelloProto.SendResponse>() {
                @Override
                public void onNext(HelloProto.SendResponse value) {
                    System.out.printf("[客户端]接收服务端的消息: %s\n", value.getMessage());
                }
                
                @Override
                public void onError(Throwable t) {
                    
                }
                
                @Override
                public void onCompleted() {
                    System.out.println("[客户端]服务端的消息已经全部接收完成");
                }
            });
            
            sendRequests.forEach(observer::onNext);
            
            observer.onCompleted();
            Thread.sleep(5000);
        } catch (Exception exception) {
            log.error("Error: {}", exception.getMessage(), exception);
        } finally {
            channel.shutdown();
        }
    }
}
