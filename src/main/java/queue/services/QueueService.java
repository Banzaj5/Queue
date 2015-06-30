package queue.services;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;


/**
 * Created by Arek on 2015-06-28.
 */
@Service
public class QueueService {

    private static String HOST = "localhost";
    private Channel channel;

    public QueueService(){
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void init() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

    }

    public void addQueue(String queueName) throws IOException,  TimeoutException {
        channel.queueDeclare(queueName, true, false, false, null);
    }

    public void deleteQueue(String queueName) throws IOException {
//        channel.queueDelete(queueName);
        getHTML();
    }

    public void addTask(String queueName, String text) throws InterruptedException, IOException{
        channel.basicPublish("", queueName,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                text.getBytes());
    }

    public void getTask(String queueName) throws IOException {
        channel.basicGet(queueName, true);
    }

    public String getHTML() {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("localhost", 15672),
                new UsernamePasswordCredentials("guest", "guest"));
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider)
                .build();
        try {
            HttpGet httpget = new HttpGet("http://localhost:15672/api/queues");

            System.out.println("Executing request " + httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            String bodyAsString;
            Map[] map;
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                bodyAsString = EntityUtils.toString(response.getEntity());

                ObjectMapper mapper = new ObjectMapper();

                try {

                    //convert JSON string to Map
                    map = mapper.readValue(bodyAsString,
                            Map[].class);

                    System.out.println(map);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return "test";
    }

}
