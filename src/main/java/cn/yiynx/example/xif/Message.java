package cn.yiynx.example.xif;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Xif 消息类
 * @param <T>
 */
@Accessors(chain = true)
@Data
public class Message<T> {
    private String type;
    private T data;
}