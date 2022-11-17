package com.msdn.security.controller;

import com.google.code.kaptcha.Producer;
import com.msdn.security.service.VerifyService;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/13 8:36 下午
 * @description
 */
@RestController
@Slf4j
public class VerifyCodeController {

  @Autowired
  VerifyService verifyService;

  @GetMapping("/verify-code")
  public void getVerifyCodePng(HttpServletRequest request, HttpServletResponse resp)
      throws IOException {
    resp.setDateHeader("Expires", 0);
    resp.setHeader("Cache-Control",
        "no-store, no-cache, must-revalidate");
    resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
    resp.setHeader("Pragma", "no-cache");
    resp.setContentType("image/jpeg");

    Producer producer = verifyService.getProducer();
    String text = producer.createText();
    HttpSession session = request.getSession();
    session.setAttribute("verify_code", text);
//    log.info("code is " + text + " session id is " + session.getId());
    BufferedImage image = producer.createImage(text);
    try (ServletOutputStream out = resp.getOutputStream()) {
      ImageIO.write(image, "jpg", out);
    }
  }

  @GetMapping("/code")
  public String getVerifyCode(HttpServletRequest request) {
    HttpSession session = request.getSession();
    String verify_code = (String) session.getAttribute("verify_code");
    log.info("input code is " + verify_code + " session id is " + session.getId());
    return verify_code;
  }
}
