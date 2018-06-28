package com.advance.mgr.config;

import com.advance.mgr.common.TimestampFormatter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 处理静态资源
     * @param registry
     */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/webapp/static/");
        super.addResourceHandlers(registry);
    }
    /**
     * 定制http消息转换器
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //System.out.println("-------------extendMessageConverters-------------------- ");
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter)converter).setSupportedMediaTypes(MediaType.parseMediaTypes(
                        "application/json;charset=utf-8,text/plain;charset=utf-8"));
            }
            //System.out.println("--------------------------------- " + converter + " | " + converter.getSupportedMediaTypes());
        }
    }
    /**
     *日期格式化 日期转换为时间戳
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new TimestampFormatter());
    }


    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer c) {
        c.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
    }
	
	@Bean
    public FreeMarkerViewResolver freeMarkerViewResolver(IcecResourcesBean icecResourcesBean) {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setRedirectHttp10Compatible(false);
        resolver.setRequestContextAttribute("requestContext");
        resolver.setCache(false);
        resolver.setAttributesMap(icecResourcesBean.getResources());

        return resolver;
    }

    /**
     * 启用资源前缀
     * 例如 <link rel="stylesheet" href="${commonStatic}/common/jquery/css/jquery-ui.css">
     */
    @Configuration
    @EnableConfigurationProperties({ IcecResourcesBean.class })
    public static class IcecResourcesConfig {
        @Bean
        public Map<String, String> icecResources(IcecResourcesBean icecResourcesBean) {
            return icecResourcesBean.getResources();
        }
    }

    @ConfigurationProperties(prefix="icec")
    public static class IcecResourcesBean {
        private Map<String, String> resources;

        public Map<String, String> getResources() {
            return resources;
        }

        public void setResources(Map<String, String> resources) {
            this.resources = resources;
        }
    }
}
