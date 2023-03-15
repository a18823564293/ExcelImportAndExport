package cn.yiynx.example.util.dp.strategy;

/**
 * 策略模式接口
 */
public interface XStrategyInterface {
    String type();

    Object handler(Object param);
}
