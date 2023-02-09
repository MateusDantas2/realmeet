package br.com.sw2you.realmeet.config;

import br.com.sw2you.realmeet.domain.repository.ClientRepository;
import br.com.sw2you.realmeet.filter.VerifyApiKeyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {
    private final ClientRepository clientRepository;

    public FilterConfiguration(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Bean
    public FilterRegistrationBean<VerifyApiKeyFilter> verifyApiKeyFilter() {
        var filterRegistrationBean = new FilterRegistrationBean<VerifyApiKeyFilter>();
        filterRegistrationBean.setFilter(new VerifyApiKeyFilter(clientRepository));
        filterRegistrationBean.addUrlPatterns("/rooms/*", "/allocations/*");
        return filterRegistrationBean;
    }
}
