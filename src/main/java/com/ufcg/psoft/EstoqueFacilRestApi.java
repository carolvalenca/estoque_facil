package com.ufcg.psoft;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ufcg.psoft.util.TokenFilter;


@EnableTransactionManagement
@EntityScan(basePackages = {"com.ufcg.psoft"})
@SpringBootApplication(scanBasePackages={"com.ufcg.psoft"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
@EnableEncryptableProperties
public class EstoqueFacilRestApi extends SpringBootServletInitializer {

	@Bean
	public FilterRegistrationBean<TokenFilter> filterJwt() {
		FilterRegistrationBean<TokenFilter> filterRB = new FilterRegistrationBean<TokenFilter>();
		filterRB.setFilter(new TokenFilter());
		filterRB.addUrlPatterns("/api/produto/", "/api/produto/{id}", "/api/produto/indisponiveis", "/api/produto/em-baixa", "/api/produto/{id}/lote", "/api/lote/", "/api/lista-perto-vencer", "/api/lista-vencidos");
		return filterRB;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EstoqueFacilRestApi.class, args);
	}
}
