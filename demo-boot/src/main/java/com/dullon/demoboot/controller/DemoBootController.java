package com.dullon.demoboot.controller;

import com.dullon.demoboot.mapper.DemoBootRepository;
import com.dullon.demoboot.pojo.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;


/**
 * 前端控制器模板 总控
 */
@Controller
@RequestMapping("/test")

public class DemoBootController {

    @Resource
    private DemoBootRepository dbr ;

    Logger logger = LogManager.getLogger(this.getClass().getName());

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){

        return "Hello World!";
    }
    @RequestMapping(value = "/{reader}",method = RequestMethod.GET)
    public String readerBooks(@PathVariable("reader") String reader , Model model){

        List<Book> readerList = dbr.findByReader(reader);
        if (readerList != null) {
            model.addAttribute("readerList",readerList);
        }
        return "demo";
    }

    @RequestMapping(value = "/{reader}",method = RequestMethod.POST)
    public String addToReadingList(@PathVariable("reader") String reader, Book book){
        book.setReader(reader);
        dbr.save(book);
        return "redirect:/test/{reader}";
    }
}
