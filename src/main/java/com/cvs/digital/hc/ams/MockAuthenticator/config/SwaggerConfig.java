package com.cvs.digital.hc.ams.MockAuthenticator.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RestController
public class SwaggerConfig implements WebMvcConfigurer {

    private final String DISPLAY_NAME = "DB Abstraction Layer Mock Service";

    @GetMapping(value = {"/", "/api"})
    public @ResponseBody
    ModelAndView swaggerui() {
        return new ModelAndView("redirect:swagger-ui.html");
    }

    @Bean
    public GroupedOpenApi groupOpenApi() {
        String[] paths = {"/v1/**"};
        String[] packagesToScan = {"com.cvs.digital.hc.ams.MockAuthenticator.controller"};
        String[] packagesToExclude = {""};
        return GroupedOpenApi.builder()
                .displayName(DISPLAY_NAME)
                .group("falcon")
                .pathsToMatch(paths)
                .packagesToScan(packagesToScan)
                .packagesToExclude(packagesToExclude)
                .build();
    }

    @Bean
    public OpenAPI externalServiceMockOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .version("1.0.0")
                        .title(DISPLAY_NAME)
                        .contact(new Contact().name("falcon-team").email("falcon@cvshealth.com"))
                        .description("The Micro Service is used to Mock the DB Abstraction Layer")
                );
    }

//    SpringDocUtils .getConfig().addRequestWrapperToIgnore


}
