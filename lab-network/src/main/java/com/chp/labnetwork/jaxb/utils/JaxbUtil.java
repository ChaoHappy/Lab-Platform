package com.chp.labnetwork.jaxb.utils;

import com.chp.labnetwork.jaxb.domain.Car;
import com.chp.labnetwork.jaxb.domain.Person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class JaxbUtil {
    public static <T> String toXml(Object o,Class<T> c ){
        try {
            JAXBContext jContext = JAXBContext.newInstance(c);
            Marshaller marshallObj = jContext.createMarshaller();
            marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallObj.setProperty(Marshaller.JAXB_FRAGMENT, true);
            StringWriter writer = new StringWriter();
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            marshallObj.marshal(o,writer);
            return writer.toString();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static <T> T toBean(String xml,Class<T> c){
        StringReader reader = new StringReader(xml);
        try {
            JAXBContext jContext = JAXBContext.newInstance(c);
            Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
            return (T)unmarshallerObj.unmarshal(reader);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        Person p = new Person();
        p.setName("张三");
        p.setAge("20");
        List<Car> cars = new ArrayList<>();
        Car car = new Car();
        car.setName("宝马");
        Car car1 = new Car();
        car1.setName("奔驰");
        cars.add(car);
        cars.add(car1);
        p.setCars(cars);
        String xml = JaxbUtil.toXml(p,Person.class);
        System.out.println("转XML："+xml);;
        System.out.println("转对象："+JaxbUtil.toBean(xml,Person.class));
    }
}
