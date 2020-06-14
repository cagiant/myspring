package spring.helloworld.demo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Foo {
    private long id;
    private String bar;
}
