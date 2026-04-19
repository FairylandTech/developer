/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-04-19 23:37:45 UTC+08:00
 ****************************************************/
package host.fairy;

import host.fairy.service.impl.HelloServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
public class GRPCServer {
    public static void main(String[] args) throws Exception {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(9000);
        serverBuilder.addService(new HelloServiceImpl());
        
        Server server = serverBuilder.build();
        server.start();
        server.awaitTermination();
    }
}
