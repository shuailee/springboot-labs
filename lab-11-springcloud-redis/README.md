# springboot-labs
####      1 本地项目启动接入SkyWalking

- agent 文件夹为apache-skywalking-apm-bin/agent 目录，想要使用skywalking需要在应用服务器上存放该文件夹

- 本地调试需要在VM options 中设置启动脚本

  ```shell
  -javaagent:C:\opt\agent\skywalking-agent.jar
  ```

  

- 还要配置一个环境变量指定skywalking的colluct地址

- ```shell
  SW_AGENT_COLLECTOR_BACKEND_SERVICES=192.168.5.14:11800
  ```

  

  #### 2 shell脚本部署接入SkyWalking

  ```
  # SkyWalking Agent 配置
  # 配置 Agent 名字。一般来说，我们直接使用 Spring Boot 项目的 `spring.application.name` 。
  export SW_AGENT_NAME=demo-application 
  # 配置 Collector 收集器地址。在目录apache-skywalking-apm-bin-es7/agent/config下，
  # 查看agent.config文件里面会有collector配置信息
  export SW_AGENT_COLLECTOR_BACKEND_SERVICES=192.168.5.14:11800 
  # 配置链路的最大 Span 数量。一般情况下，不需要配置，默认为 300 。主要考虑，有些新上 SkyWalking Agent 的项目，代码可能比较糟糕。
  export SW_AGENT_SPAN_LIMIT=2000 
  # SkyWalking Agent jar 地址。
  export JAVA_AGENT=-javaagent:/usr/local/skywalking/apache-skywalking-apm-bin-es7/agent/skywalking-agent.jar 
  
  # Jar 启动
  java -jar $JAVA_AGENT -jar lab-39-demo-2.2.2.RELEASE.jar
  ```

  

  

