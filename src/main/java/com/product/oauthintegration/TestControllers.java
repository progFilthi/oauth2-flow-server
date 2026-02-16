package com.product.oauthintegration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class TestControllers {

    @GetMapping("products")
    public String products() {
        return "How many products do you have?";
    }

    @GetMapping("hello")
    public String hello() {
        return "hello, world!";
    }

    @GetMapping("me")
    public ResponseEntity<MeResponse> me(@AuthenticationPrincipal OAuth2User user) {
        if(user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

      return ResponseEntity.ok(
              new MeResponse(
                      user.getAttribute("email"),
                      user.getAttribute("name"),
                      user.getAttribute("picture")
              )
      );
    }
}
