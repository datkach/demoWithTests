package com.example.demowithtests.util.annotations;

import com.example.demowithtests.domain.Employee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class MyAnnotations {
@Pointcut("@annotation(ActivateMyAnnotations) && within(com.example.demowithtests.service.*)")
        public void callAnnotations(){
        }
    @Before("callAnnotations()")
    public void getValid(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ActivateMyAnnotations annotation = signature.getMethod().getAnnotation(ActivateMyAnnotations.class);
        var annotations = annotation.value();
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Employee) {
                for (Field field : arg.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(Name.class)) {
                        setFormattedName(arg, field);
                    }
                    if(field.isAnnotationPresent(Country.class)){
                        setFormattedCountry(arg,field);
                    }
                }
            }
        }
    }

    private void setFormattedName(Object arg, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(arg);
        if (value instanceof String) {
            field.set(arg, toNameFormat((String) value));
        }
    }

    private String toNameFormat(String name) {
        return name.trim().substring(0, 1).toUpperCase() + name.trim().substring(1).toLowerCase();
    }
    private void setFormattedCountry(Object arg, Field field) throws IllegalAccessException {
    field.setAccessible(true);
    var value  = field.get(arg);
    if (value instanceof String){
        field.set(arg,toCountryFormat((String) value));
    }
    }
    private String toCountryFormat(String country){
        String newCountry=country.replaceAll("[^a-zA-Z` ]", "").trim();
        String[] words = newCountry.split(" ");
        String result = "";
        for (String word : words) {
            String firstLetter = word.substring(0, 1);
            result+= firstLetter.toUpperCase()  + word.substring(1).toLowerCase() + " ";
        }
        System.err.println("Result : " + result);
        return result.trim();
    }
}
