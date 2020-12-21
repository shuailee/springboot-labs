package helper;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Component;
import java.net.InetAddress;
import java.net.UnknownHostException;


@Component
public class EsHelper {

    public TransportClient getConn() {
        TransportClient client=null;
        Settings settings= Settings.builder()
                .put("client.transport.sniff",true)///自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
                .put("cluster.name","elasticsearch")//设置ES实例的名称
                // .put("network.host", "127.0.0.1")
                .put("client.transport.ignore_cluster_name", true) // 忽略集群名字验证, 打开后集群名字不对也能连接上
                .build();
        try {
            client=new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.181.131"),9300));
            System.out.println("success connect");
        } catch (Exception e) {
            if(client!=null) {
                client.close();
            }
        }
        return client;
    }





}
