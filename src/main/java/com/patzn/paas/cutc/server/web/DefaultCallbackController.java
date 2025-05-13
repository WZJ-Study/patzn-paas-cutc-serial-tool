package com.patzn.paas.cutc.server.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/serial-tool")
public class DefaultCallbackController {

    @PostMapping("/callback")
    public String callback(@RequestBody String jsonData) {
        log.info("==== callback ==== 接收到JSON数据： {}", jsonData);
        return jsonData;
    }

}
