package cn.yiynx.example.xif;

import cn.yiynx.xif.core.XifListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Xif 消息处理示例
 */
@Slf4j
@Component
public class MessageXifHandler {
    public static final String XIF_GROUP_MESSAGE = "xif-group-message";

    @XifListener(group = XIF_GROUP_MESSAGE, condition = "#message.type eq 'type1'")
    public String type1(Message message) {
        log.info("【xif】type等于type1处理:{}", message);
        return message.getType();
    }

    @XifListener(group = XIF_GROUP_MESSAGE, condition = "#message.type eq 'type2'")
    public String type2(Message message) {
        log.info("【xif】type等于type2 处理:{}", message);
        return message.getType();
    }

    @XifListener(group = XIF_GROUP_MESSAGE)
    public String typeElse(Message message) {
        log.info("【xif】type else 处理:{}", message);
        return "else";
    }
}
