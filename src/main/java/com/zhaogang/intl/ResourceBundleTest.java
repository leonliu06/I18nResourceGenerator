package com.zhaogang.intl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by li.liu on 2017/9/13.
 */
public class ResourceBundleTest {

    public static void main(String[] args){

        ApplicationContext context = new ClassPathXmlApplicationContext("beanConfig.xml");

        String name = context.getMessage("welcome", null, Locale.CHINA);

        System.out.println(name);

        System.out.println(context.getMessage("welcome", null, Locale.US));




    }






}
