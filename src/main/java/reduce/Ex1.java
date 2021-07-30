package reduce;

import java.util.stream.IntStream;

public class Ex1 {
  public static void main(String[] args) {
//    var res = IntStream.range(1, 1)
//        .reduce((a, b) -> a + b);
//    res.ifPresentOrElse(System.out::println,
//        () -> System.out.println("Zero items"));

    var res = IntStream.range(1, 1)
        .reduce(0, (a, b) -> a + b);
    System.out.println(res);
  }
}
