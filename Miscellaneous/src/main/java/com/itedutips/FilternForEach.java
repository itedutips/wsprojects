package com.itedutips;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

 
public class FilternForEach {

	public static void main(String[] args) {
		  
        List<String> names = Arrays.asList("Peter","Sam","Greg","Alex");
        System.out.println("Traditional Style");
        for(String name : names)
        {
        	if(!name.equals("Sam")) {
        		System.out.println("Name " + name); 
        	}
        }
        System.out.println("Expanded Style- Predicate-functional reference  returns boolean");
        names.stream().filter(new Predicate<String>() {

			public boolean test(String name) {
				return !name.equals("Sam"); 
			} 
		}).forEach(name -> System.out.println(name));
        
        System.out.println("Fully Expanded Style");
        		
        names.stream().filter(new Predicate<String>() {

			public boolean test(String name) {
				return !name.equals("Sam"); 
			} 
		}).forEach(new Consumer<String>() {

			@Override
			public void accept(String name) {
				System.out.println(name);
				
			}
		});
        
        System.out.println("Expansion avoided");
        names.stream().filter(name -> !name.equals("Sam"))
					  .forEach(name -> System.out.println(name));
        
        System.out.println("Uses method reference as well");
        names.stream().filter(name -> !name.equals("Sam"))
		  .forEach(System.out::println);
        
        System.out.println("Custom method");
        
        names.stream().filter(name -> isNotSam(name))
		  .forEach(System.out::println);
        
        System.out.println("Custom method & method refereence");
        
        names.stream().filter(FilternForEach::isNotSam)
		  .forEach(System.out::println);
        
	}
	
	private static boolean isNotSam(String name)
	{
		return !name.equals("Sam");
	}

}
