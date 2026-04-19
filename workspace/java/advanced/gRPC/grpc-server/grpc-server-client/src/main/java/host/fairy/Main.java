/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-04-19 23:46:45 UTC+08:00
 ****************************************************/
package host.fairy;

import host.fariy.grpc.HelloProto;
import host.fariy.grpc.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();
        
        HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub = HelloServiceGrpc.newBlockingStub(channel);
        
        HelloProto.HelloRequest request = HelloProto.HelloRequest.newBuilder()
                .setName("gRPC Client")
                .build();
        
        HelloProto.HelloRequest2 request2 = HelloProto.HelloRequest2.newBuilder()
                .addName("name1")
                .addName("name2")
                .addName("name3")
                .build();
        
        HelloProto.HelloResponse response = helloServiceBlockingStub.hello(request);
        System.out.println(response.getResult());
        
        HelloProto.HelloResponse response2 = helloServiceBlockingStub.hello2(request2);
        System.out.println(response2.getResult());
    }
}
