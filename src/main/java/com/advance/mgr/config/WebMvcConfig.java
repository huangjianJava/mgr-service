package com.advance.mgr.config;

import com.advance.mgr.common.TimestampFormatter;
import com.advance.mgr.component.MyHandlerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index/login");   // 登录url不做拦截
    }

    /**
     * 处理静态资源和防止freeMarker和swagger-ui冲突
     * @param registry
     */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/webapp/static/");
        super.addResourceHandlers(registry);
    }

    /**
     * 定制http消息转换器
     * HttpMessageConverter支持@RequestMapping
     * 或@ExceptionHandler method的 @RequestBody method parameters
     * 和@ResponseBody method 返回值。 -- 比较长，
     * 其实就是支持handler (controller)的@RequestBody参数/@ResponseBody返回值。
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
     * 将对象转为json格式输出
     * @param _halObjectMapper
     * @return
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper _halObjectMapper) {
        List<MediaType> mediaTypes = new ArrayList();
        mediaTypes.addAll(MediaType.parseMediaTypes("text/plain; charset=utf-8,plain/text; charset=utf-8,application/json; charset=utf-8,application/hal+json; charset=UTF-8"));
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(mediaTypes);
        jsonConverter.setObjectMapper(_halObjectMapper);
        return jsonConverter;
    }
    /**
     *日期格式化 日期转换为时间戳
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new TimestampFormatter());
    }

    /**
     * 你可以使用一个ContentNegotiationManager来配置ExceptionHandlerExceptionResolver，
     * 然后总是将请求的媒体类型处理成”application/json”。或者，你可能想插入一个定制的策略，
     * 如果没有内容类型被请求时，使用一些逻辑来选择默认的内容类型--如XML或JSON。
     * @param c
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer c) {
        c.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    /**
     * FreeMarkerViewResolver视图解析器配置
     * @param icecResourcesBean
     * @return
     */
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
