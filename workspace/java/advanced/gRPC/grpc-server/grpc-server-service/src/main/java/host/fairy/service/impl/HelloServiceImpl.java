/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-04-19 23:25:36 UTC+08:00
 ****************************************************/
package host.fairy.service.impl;

import host.fariy.grpc.HelloProto;
import host.fariy.grpc.HelloServiceGrpc;
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
}
