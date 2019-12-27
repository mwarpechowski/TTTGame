package pl.mwinc.demo.ttt.controler.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class FooterController {

    @GetMapping(path = "/")
    public String welcome(){
        return "Welcome";
    }

    @GetMapping(path = "/license")
    public String getLicense(){
        return "License";
    }
}
