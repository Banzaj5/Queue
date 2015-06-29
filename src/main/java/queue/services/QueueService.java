package queue.services;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * Created by Arek on 2015-06-28.
 */
@Service
@NoArgsConstructor
public class QueueService {

    Channel channel;

    public void addQueue(String queueName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(queueName, true, false, false, null);
    }

    public void addTask(String queueName, String text) throws InterruptedException, IOException {
        channel.basicPublish("", queueName,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                text.getBytes());
        System.out.println(" [x] Sent '" + text + "'");
    }
}
