package cn.yiynx.example.extensions.java.util.List;

import manifold.ext.rt.api.Extension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * List 扩展方法
 * @author <a href="https://zhuanlan.zhihu.com/p/607107073">Java 缺失的特性：扩展方法</a>
 */
@Extension
public class ListExt {

    /**
     * 【扩展方法】返回只包含一个元素的不可变 List
     * @deprecated 已弃用，List.of(E element)
     */
    @Deprecated
    @Extension
    public static <E> List<E> xOf(E element) {
        return Collections.singletonList(element);
    }

    /**
     * 【扩展方法】返回包含多个元素的不可变 List
     * @deprecated 已弃用，List.of(E... elements)
     */
    @Deprecated
    @Extension @SafeVarargs
    public static <E> List<E> xOf(E... elements) {
        return Collections.unmodifiableList(Arrays.asList(elements));
    }
}