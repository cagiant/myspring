package com.example.streamdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class StreamDemoApplication {

	public static void main(String[] args) {
		Arrays.asList("Foo", "Bar").stream()
				.filter(s -> s.equalsIgnoreCase("foo"))
				.map(s -> s.toUpperCase())
				.forEach(System.out::println);

		Arrays.stream(new String[] {"s1", "s2", "s3"})
				.map(s -> Arrays.asList(s))
				.flatMap(l -> l.stream())
				.forEach(System.out::println);
	}
}
