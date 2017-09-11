package com.zhaogang.intl;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by li.liu on 2017/9/14.
 */
public class ExcelResource extends ResourceBundleMessageSource {



    protected ResourceBundle doGetBundle(String basename, Locale locale) throws MissingResourceException{


        System.out.println(basename + ": " + locale.toString());

        return null;
    }

}
