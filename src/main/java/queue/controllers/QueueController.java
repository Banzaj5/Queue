package queue.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import queue.services.QueueService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Arek on 2015-06-28.
 */
@Controller
public class QueueController {

    private QueueService queueService = new QueueService();

    @RequestMapping(value = "/addQueue", method = RequestMethod.GET)
    public ResponseEntity<String> addQueue() {
        try {
            queueService.addQueue("Test");
        } catch (IOException e) {
            return new ResponseEntity<String>("I/O Exception", HttpStatus.BAD_REQUEST);
        } catch (TimeoutException e) {
            return new ResponseEntity<String>("Request timeout", HttpStatus.REQUEST_TIMEOUT);
        }
        return new ResponseEntity<String>("Queue has been added", HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteQueue", method = RequestMethod.GET)
    public ResponseEntity<String> deleteQueue()
    {
        try {
            queueService.deleteQueue("Test");
        } catch (IOException e) {
            return new ResponseEntity<String>("I/O Exception", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Queue has been removed", HttpStatus.OK);
    }

    @RequestMapping(value = "/addTask", method = RequestMethod.GET)
    public ResponseEntity<String> addTask()
    {
        try {
            queueService.addTask("Test", "1");
        } catch (InterruptedException e) {
            return new ResponseEntity<String>("Interrupted", HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<String>("I/O Exception", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Task has been added", HttpStatus.OK);
    }
}
