/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-04-21 00:45:39 UTC+08:00
 ****************************************************/
package host.fairy.service.impl;

import host.fairy.grpc.TestProto;
import host.fairy.grpc.TestServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@Slf4j
public class TestServiceImpl extends TestServiceGrpc.TestServiceImplBase {
    @Override
    public void testMethod(TestProto.TestRequest request, StreamObserver<TestProto.TestResponse> responseObserver) {
        String requestMessage = request.getRequestMessage();
        
        TestProto.TestResponse response = TestProto.TestResponse.newBuilder()
                .setResponseMessage("Received: " + requestMessage)
                .build();
        
        try {
            Thread.sleep(5000);
        } catch (Exception exception) {
            log.error("Error: {}", exception.getMessage(), exception);
        }
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
