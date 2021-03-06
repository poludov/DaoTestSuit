package ru.mds.testing.dao.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author maksimenko
 * @since 24.07.2018 (v1.0)
 */
@Slf4j
@Component
public class ProxyingBeanPostProcessor implements BeanPostProcessor {
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof DataSource) {
      try {
        log.debug("Модифицируем DataSource");
        return ProxyFactory.createProxy((DataSource) bean, DataSource.class, DataSourceProxy.class);
      } catch (Exception e) {
        log.error("Не удалось создать proxy: ", e);
      }
    }
    return bean;
  }
}