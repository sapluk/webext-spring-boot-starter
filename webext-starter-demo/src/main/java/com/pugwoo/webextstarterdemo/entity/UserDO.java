package com.pugwoo.webextstarterdemo.entity;

import lombok.Data;

/**
 * 演示用<br/>
 *  此处使用 lombok 插件，自动生成getter/setter等<br/>
 *  使用 @Data == @Setter + @Getter + @ToString + @RequiredArgsConstructor + @EqualsAndHashCode<br/>
 * @date 2018-07-12
 */
@Data
public class UserDO {

    private String name;

    private String password;

    private Integer age;

}
