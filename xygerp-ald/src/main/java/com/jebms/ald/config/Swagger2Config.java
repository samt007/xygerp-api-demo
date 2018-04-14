package com.jebms.ald.config;

import springfox.documentation.service.Parameter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//参考：http://blog.csdn.net/catoop/article/details/50668896
//Swagger2在header中添加token（java）http://blog.csdn.net/wanzhix/article/details/76019948
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
		        .groupName("api")
		        .select() .apis(RequestHandlerSelectors.basePackage("com.jebms.ald.controller"))
		        .paths(PathSelectors.any())
		        .build()
		        .pathMapping("/")
		        .globalOperationParameters(setHeaderToken())
		        .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ERP ALD核心模块RESTful API")
		        .description("基于自主开发的jebms框架")
                .termsOfServiceUrl("http://www.xinyiglass.com")
                .contact(new Contact("samt007", "http://www.xinyiglass.com", "samt007@qq.com"))
                .version("1.0")
                .build();
    }

	private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Authorization").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add((Parameter) tokenPar.build());
        return pars;
    }
}