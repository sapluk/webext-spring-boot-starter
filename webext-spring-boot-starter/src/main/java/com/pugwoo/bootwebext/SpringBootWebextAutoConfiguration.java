package com.pugwoo.bootwebext;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.pugwoo.bootwebext.converter.StringToDateConverter;
import com.pugwoo.bootwebext.converter.StringToLocalDateTimeConverter;
import com.pugwoo.bootwebext.resolver.json.JsonParamArgumentResolver;

/**
 * @date 2018-06-21
 */
@ConditionalOnWebApplication
@Configuration
public class SpringBootWebextAutoConfiguration implements WebMvcConfigurer {
	
	/**
	 * 设置Date类型的输出格式为yyyy-MM-dd HH:mm:ss
	 */
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
	    return new Jackson2ObjectMapperBuilderCustomizer() {
	        @Override
	        public void customize(Jackson2ObjectMapperBuilder builder) {
	        	/**
	        	 * 虽然SimpleDateFormat非线程安全，但jackson拿到DateFormat后会clone一份
	        	 * 使得jackson内部是线程安全的
	        	 * https://stackoverflow.com/questions/33672037/is-it-safe-to-use-simpledateformat-in-fasterxmls-objectmapper
	        	 */
	            builder.dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	        }           
	    };
	}

    /**
     * 支持@JsonParam注解<br>
     * 
     *  示例: request:  time=20180621&who={"name":"abc","age":16}<br>
     *  接口接收参数为 (@JsonParam("who") UserDO user)<br>
     */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new JsonParamArgumentResolver());
	}

    /**
     * 支持将参数转化为Date<br>
     * 
     *  示例: request: xxx?time=20180621<br>
     *  接口接收参数为 (Date time)<br>
     */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToDateConverter());
		registry.addConverter(new StringToLocalDateTimeConverter());
	}


}
