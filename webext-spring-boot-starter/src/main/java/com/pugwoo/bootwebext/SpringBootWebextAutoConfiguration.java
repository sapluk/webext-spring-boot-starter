package com.pugwoo.bootwebext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pugwoo.bootwebext.converter.StringToDateConverter;
import com.pugwoo.bootwebext.converter.StringToLocalDateTimeConverter;
import com.pugwoo.bootwebext.resolver.json.JsonParamArgumentResolver;
import com.pugwoo.bootwebext.resolver.json.MyObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 */
@ConditionalOnWebApplication
@Configuration
public class SpringBootWebextAutoConfiguration implements WebMvcConfigurer {

	@Value("${spring.jackson.default-property-inclusion:}")
	private String inclusion;
	
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
	 * 配置json解析
	 * {@link com.pugwoo.bootwebext.resolver.json.MyObjectMapper}
	 */
	@Bean
	public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		boolean isIgnoreNullValue = "non_null".equalsIgnoreCase(inclusion);
		ObjectMapper objectMapper = new MyObjectMapper(isIgnoreNullValue);
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}


    /**
     * 支持@JsonParam注解
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
