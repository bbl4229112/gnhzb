package edu.zju.cims201.GOF.dynamicSpring;

import org.springframework.beans.factory.config.BeanDefinition;

public interface RegisterBeanDefinitionListener {
	  public void beanRegistered(String beanName, BeanDefinition beanDefinition);
	}
