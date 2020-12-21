package com.shuailee.rocketmqprovider.controller;

import com.shuailee.rocketmqprovider.model.result.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderController {
    @Autowired
    DefaultMQProducer defaultMQProducer;

    /**
     * 发送简单的MQ消息
     * @param msg
     * @return
     */
    @GetMapping("/send")
    public DataResult send(String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        if (StringUtils.isEmpty(msg)) {
            return DataResult.ok();
        }
        log.info("发送MQ消息内容：" + msg);
        Message sendMsg = new Message("shuailee2", "shuaitag", msg.getBytes());
        /**
         * waitStoreMsgOK：表示发送消息后，是否需要等待消息同步刷新到磁盘上。如果broker配置为ASYNC_MASTER，那么只需要消息在master上刷新到磁盘即可；
         * 如果配置为SYNC_MASTER，那么还需要等待slave也刷新到磁盘。需要注意的是，waitStoreMsgOK默认为false，只有将设置为true的情况下，才会等待刷盘成功再返回。
         * */
        sendMsg.setWaitStoreMsgOK(true);
        // 延时消息顺序级别如下：  messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        // 延时消息需要做如下设置：30s
        sendMsg.setDelayTimeLevel(4);

        // 默认3秒超时
        SendResult sendResult = defaultMQProducer.send(sendMsg);
        log.info("消息发送响应：" + sendResult.toString());
        return DataResult.ok(sendResult);
    }
}
