1. redis内存存储单个命令的执行效率在微妙级别,效率很高
2. redis性能瓶颈在网络io,采用了多路复用来解决网络io的性能瓶颈
3. redis内命令执行顺序是单个串行执行的，所以慢查询/并发数/key的数量 都会影响redis的性能

redis的命令是单线程执行的，一个线程每秒可执行1万个redis命令，多个连接并不会提升应用程序的性能。连接池
1. lettuce：基于netty实现，线程安全，但默认只有一个实例，所有连接共享一个实例。Lettuce连接被设计为线程安全的，因此一个连接可以在多个线程之间共享。
2. jedis：不是线程安全的，一个连接只能一个线程使用，所以使用jedis连接池会不断创建连接。
3. 经典问题： https://github.com/lettuce-io/lettuce-core/issues/360 

springboot cache:

https://blog.csdn.net/sz85850597/article/details/89301331