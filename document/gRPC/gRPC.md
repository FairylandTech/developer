# developer

> @software: IntelliJ IDEA  
> @author: [Lionel Johnson](https://fairy.host)  
> @contact: [Blog](https://blog.fairy.host/) | [GitHub](https://github.com/FairylandTech) | [Telegram](https://t.me/FairylandFuture)  
> @organization: [GitHub·FairylandFuture](https://github.com/FairylandFuture)  
> @datetime: 2026-04-19 21:27:06 UTC+08:00

[![Author](https://img.shields.io/badge/Author-Lionel_Johnson-orange)](https://t.me/FairylandFuture) [![github](https://img.shields.io/badge/Github-FairylandFuture-green)](https://github.com/FairylandTech) [![GitBook](https://img.shields.io/badge/GitBook-Interesting-green)](https://interestingbooks.gitbook.io/) [![Editor](https://img.shields.io/badge/Editor-Typora-yellow)]() [![Language](https://img.shields.io/badge/Language-Markdown-orange)]() [![Version](https://img.shields.io/badge/Version-Release-blue)]() [![Docs](https://img.shields.io/badge/Docs-Passing-brightgreen)]() [![Type](https://img.shields.io/badge/Type-Documents-blue)]() [![wakatime](https://wakatime.com/badge/user/fa851759-c657-4b1e-8bcb-3ec3a693a2cd.svg)](https://wakatime.com/@fa851759-c657-4b1e-8bcb-3ec3a693a2cd) [![Sign](https://img.shields.io/badge/%E7%AD%89%E6%88%91%E4%BB%A3%E7%A0%81%E7%BC%96%E6%88%90-%E5%A8%B6%E4%BD%A0%E4%B8%BA%E5%A6%BB%E5%8F%AF%E5%A5%BD-red)](https://github.com/FairylandTech)

---


```protobuf
syntax = "proto3";

// 生成 Java 源文件是一个还是多个
option java_multiple_files = false;
// 生成的类放在那个包中
option java_package = "host.fairy";
// 生成 Java 外包类的名称，管理内部类才是真正开发使用的
option java_outer_classname = "First";

/*
定义 message
message 名称 {
  message关键字 字段类型 字段名称 = 编号（从1开始到2^29-1，19000-19999 protobuf 自己保留）
}

message 关键字 修饰字段
singular 默认 字段的值只能有0个或1个，0个就是 null，1个就是具体的内容。
repeated 字段的返回值是多个，等价于 Java 的 List。
*/

// 枚举的编号从0开始
enum Gender {
  MALE = 0;
  FEMALE = 1;
}

message LoginRequest {
  string username = 1;
  string password = 2;
  int32 age = 3;
  string email = 4;
}

message Result {
  string content = 1;
  string status = 2;
  repeated string tags = 3;
}

// message 消息嵌套
message SearchResponse {
  message Result {
    string url = 1;
    string title = 2;
  }

  string name = 1;
  Result data = 2;
}

// oneof 表示其中的一个。
message SimpleMessage {
  // content 传值只能是 string 或者是 bytes
  oneof content {
    string text = 1;
    bytes binaryData = 2;
  }
}


service HelloService {
  rpc hello(LoginRequest) returns (Result) {};
}
```
