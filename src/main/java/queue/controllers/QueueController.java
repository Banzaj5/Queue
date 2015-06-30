package queue.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import queue.services.QueueService;

/**
 * Created by Arek on 2015-06-28.
 */
@Controller
public class QueueController {

    private QueueService queueService = new QueueService();

    @RequestMapping(value = "/addQueue", method = RequestMethod.GET)
    public void addQueue(ModelMap model) {
//        try {
//            queueService.addQueue("Test");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
        model.addAttribute("message", "Queue has been added");
        System.out.print("Success");
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String addTask()
    {
//        try {
//            queueService.addTask("Test", "1");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.print("Success");
        return "test";
    }
}
