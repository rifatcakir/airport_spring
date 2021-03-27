package com.airport.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class RootController {

  @GetMapping
  public RedirectView rootToSwagger(RedirectAttributes attributes) {
    return new RedirectView("/swagger-ui/index.html");
  }


}
