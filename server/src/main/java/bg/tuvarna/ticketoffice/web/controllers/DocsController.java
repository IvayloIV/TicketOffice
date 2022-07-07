package bg.tuvarna.ticketoffice.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DocsController {

    @RequestMapping("/docs")
    public String getSwaggerApiDocsPage() {
        return "redirect:swagger-ui/index.html";
    }
}
