/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-04-19 23:37:45 UTC+08:00
 ****************************************************/
package host.fairy;

import host.fairy.service.impl.HelloServiceImpl;
import host.fairy.service.impl.TestServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@Slf4j
public class GRPCServer {
    public static void main(String[] args) throws Exception {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(9000);
        
        serverBuilder.addService(new HelloServiceImpl());
        serverBuilder.addService(new TestServiceImpl());
        
        Server server = serverBuilder.build();
        server.start();
        server.awaitTermination();
    }
}
