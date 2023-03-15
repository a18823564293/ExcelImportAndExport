package cn.yiynx.example.util.dp.strategy;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 策略模式
 * @author www.yiynx.cn
 */
@Slf4j
@UtilityClass
public class XStrategy {
    private static final Map<String, XStrategyInterface> XSTRATEGY_HANDLER_MAP = new ConcurrentHashMap<>();

    static {
        long startTime = System.currentTimeMillis();
        ServiceLoader.load(XStrategyInterface.class).forEach(xStrategyInterface -> {
            log.info("注册XStrategy策略：{} -> {}", xStrategyInterface.type(), xStrategyInterface.getClass().getName());
            register(xStrategyInterface);
        });
        log.info("XStrategy初始化用时:{}ms", System.currentTimeMillis() - startTime);
    }

    private static void register(XStrategyInterface strategyInterface) {
        XSTRATEGY_HANDLER_MAP.put(strategyInterface.type(), strategyInterface);
    }

    public static XStrategyInterface strategy(String strategyType) {
        return Optional.ofNullable(XSTRATEGY_HANDLER_MAP.get(strategyType))
                .orElseThrow(() -> new IllegalArgumentException("XStrategyInterface->type:$strategyType not found"));
    }

}