package cn.yiynx.example.controller;

import cn.yiynx.example.xif.Message;
import cn.yiynx.xif.core.Xif;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.yiynx.example.xif.MessageXifHandler.XIF_GROUP_MESSAGE;

/**
 * Xif 消息处理示例
 */
@RestController
public class XifMessageController {

    /**
     * 测试Xif处理
     * @param typeNo 有效值1,2，其它值则由默认处理器处理
     * @see cn.yiynx.example.xif.MessageXifHandler
     */
    @RequestMapping("/xif")
    public String testXif(@RequestParam(name = "type", required = false) String typeNo) {
        return (String) Xif.handler(XIF_GROUP_MESSAGE, new Message<>().setType("type$typeNo"));
    }
}