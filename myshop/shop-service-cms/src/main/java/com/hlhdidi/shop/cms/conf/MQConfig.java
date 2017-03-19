package com.hlhdidi.shop.cms.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
	 @Bean(name="solrQueue")
     public Queue AMessage() {
         return new Queue("solrQueue");
     }

     @Bean(name="cmsQueue")
     public Queue CMessage() {
         return new Queue("cmsQueue");
     }

     @Bean
     FanoutExchange fanoutExchange() {
         return new FanoutExchange("product");//配置广播路由器
     }
     
     @Bean
     Binding bindingExchangeB(@Qualifier("solrQueue") Queue BMessage, FanoutExchange fanoutExchange) {
         return BindingBuilder.bind(BMessage).to(fanoutExchange);
     }

     @Bean
     Binding bindingExchangeC(@Qualifier("cmsQueue") Queue CMessage, FanoutExchange fanoutExchange) {
         return BindingBuilder.bind(CMessage).to(fanoutExchange);
     }
}
