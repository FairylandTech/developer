/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-04-21 00:47:49 UTC+08:00
 ****************************************************/
package host.fairy;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import host.fairy.grpc.TestProto;
import host.fairy.grpc.TestServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@Slf4j
public class TestProtoClient {
    private static final ManagedChannel CHANNEL = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
    
    public static void main(String[] args) {
        sync();
        async();
        CHANNEL.shutdown();
    }
    
    public static void sync() {
        try {
            TestServiceGrpc.TestServiceFutureStub testServiceFutureStub = TestServiceGrpc.newFutureStub(CHANNEL);
            
            ListenableFuture<TestProto.TestResponse> responseFuture = testServiceFutureStub.testMethod(TestProto.TestRequest.newBuilder()
                    .setRequestMessage("Future Stub")
                    .build());
            
            String responseMessage = responseFuture.get().getResponseMessage();
            System.out.printf("Response message: %s\n", responseMessage);
        } catch (Exception exception) {
            log.error("Error: {}", exception.getMessage(), exception);
        }
    }
    
    public static void async() {
        try {
            TestServiceGrpc.TestServiceFutureStub testServiceFutureStub = TestServiceGrpc.newFutureStub(CHANNEL);
            
            ListenableFuture<TestProto.TestResponse> responseFuture = testServiceFutureStub.testMethod(TestProto.TestRequest.newBuilder()
                    .setRequestMessage("Future Stub")
                    .build());
            
            Futures.addCallback(responseFuture, new FutureCallback<TestProto.TestResponse>() {
                @Override
                public void onSuccess(TestProto.TestResponse result) {
                    System.out.printf("Response message: %s\n", result.getResponseMessage());
                }
                
                @Override
                public void onFailure(Throwable t) {
                    
                }
            }, Executors.newCachedThreadPool());
            
            System.out.println("后续的操作...");
            CHANNEL.awaitTermination(10, TimeUnit.SECONDS);
        } catch (Exception exception) {
            log.error("Error: {}", exception.getMessage(), exception);
        }
    }
}
