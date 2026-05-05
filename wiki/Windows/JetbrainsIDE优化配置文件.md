# Jetbrains IntelliJ IDE 优化配置

| 优先级 | 配置文件来源         | 路径说明                                                     | 特点                                                         |
| ------ | -------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1      | 用户自定义配置       | 在IDEA里点击 `Help -> Edit Custom VM Options` 自动生成的文件 | 优先级最高，只要这个文件存在，就优先用这里的参数，修改后重启IDEA就生效，不会被IDEA/Toolbox更新覆盖，**推荐改这个** |
| 2      | Toolbox全局配置      | `IntelliJ IDEA Ultimate.vmoptions`                           | 适合你用Toolbox管理多个IDEA版本时统一配置参数                |
| 3      | IDEA安装目录默认配置 | IDEA目录进入`bin`文件夹，里面的`idea64.exe.vmoptions`        | 是IDEA安装包自带的兜底默认配置，只有上面两个配置都不存在时才会读取 |

vmoption 优化

```tex
-Xms4096m
-Xmx4096m
-XX:MaxMetaspaceSize=1024m
-XX:ReservedCodeCacheSize=512m
-XX:MaxDirectMemorySize=2048m
-XX:TieredOldPercentage=100000
-XX:+UseG1GC
-XX:MaxGCPauseMillis=200
-XX:+IgnoreUnrecognizedVMOptions
-XX:+TieredCompilation
-XX:SoftRefLRUPolicyMSPerMB=50
-XX:+UseStringDeduplication
-XX:-OmitStackTraceInFastThrow
-XX:+UnlockDiagnosticVMOptions
-Dide.async.jps=true
-Dide.workspace.model.disable.auto.reload=true
-Dsun.io.useCanonCaches=false
-Dsun.java2d.opengl=true
-Dsun.stdout.encoding=UTF-8
-Dsun.stderr.encoding=UTF-8
-Dfile.encoding=UTF-8
-Dconsole.encoding=UTF-8
-Dskiko.rendering.useScreenMenuBar=false
-Djdk.http.auth.tunneling.disabledSchemes=""
-Djdk.attach.allowAttachSelf=true
-Djdk.module.illegalAccess.silent=true
-Djdk.nio.maxCachedBufferSize=2097152
-Djava.util.zip.use.nio.for.zip.file.access=true
-Djava.net.preferIPv4Stack=true
-Dkotlinx.coroutines.debug=off
```

