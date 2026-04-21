/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-04-21 03:26:07 UTC+08:00
 ****************************************************/
package host.fairy.application.service.grpc.impl;

import host.fairy.grpc.welcome.WelcomeProto;
import host.fairy.grpc.welcome.WelcomeServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author Beau Dean
 * @version 1.0
 */
@GrpcService
public class WelComeGrpcServiceImpl extends WelcomeServiceGrpc.WelcomeServiceImplBase {
    @Override
    public void welcome(WelcomeProto.WelcomeRequest request, StreamObserver<WelcomeProto.WelcomeResponse> responseObserver) {
        String name = request.getName();
        
        WelcomeProto.WelcomeResponse response = WelcomeProto.WelcomeResponse.newBuilder()
                .setMessage("Hello, " + name + "!")
                .build();
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
