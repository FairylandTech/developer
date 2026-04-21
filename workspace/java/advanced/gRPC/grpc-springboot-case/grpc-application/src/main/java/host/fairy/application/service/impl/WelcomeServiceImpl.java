/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-04-21 15:32:03 UTC+08:00
 ****************************************************/
package host.fairy.application.service.impl;

import com.google.common.util.concurrent.ListenableFuture;
import host.fairy.application.service.WelcomeService;
import host.fairy.grpc.welcome.WelcomeProto;
import host.fairy.grpc.welcome.WelcomeServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@Slf4j
@Service
public class WelcomeServiceImpl implements WelcomeService {
    
    @GrpcClient("grpc-local-server")
    private WelcomeServiceGrpc.WelcomeServiceFutureStub stub;
    
    @Override
    public String welcome(String name) {
        try {
            ListenableFuture<WelcomeProto.WelcomeResponse> welcome = this.stub.welcome(WelcomeProto.WelcomeRequest.newBuilder().setName(name).build());
            String message = welcome.get().getMessage();
            log.info("调用 gRPC 成功.");
            return String.format("Welcome, %s, [gRPC: %s]!", name, message);
        } catch (Exception exception) {
            log.error("Error: {}", exception.getMessage(), exception);
            return "";
        }
    }
}
