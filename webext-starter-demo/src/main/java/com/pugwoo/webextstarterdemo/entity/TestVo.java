package com.pugwoo.webextstarterdemo.entity;

import lombok.Data;

/**
 * @author sapluk <br>
 *  泛型测试
 */
@Data
public class TestVo<A, B, C, D, E, F, G> {
    
    private A a;
    private B b;
    private C c;
    private D d;
    private E e;
    private F f;
    private G g;
}
