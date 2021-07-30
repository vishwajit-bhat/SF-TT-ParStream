package reduce;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

class Avg {
  private double sum;
  private long count;

  public Avg(double sum, long count) {
    this.sum = sum;
    this.count = count;
  }

  public Optional<Double> get() {
    if (count > 0) {
      return Optional.of(sum / count);
    } else {
      return Optional.empty();
    }
  }

  public Avg merge(Avg other) {
    return new Avg(this.sum + other.sum, this.count + other.count);
  }
}

public class AverageByReduce {
  public static void main(String[] args) {
    long start = System.nanoTime();
    ThreadLocalRandom.current().doubles(4_000_000_000L, -Math.PI, +Math.PI)
        .parallel()
//        .sequential()
        .mapToObj(d -> new Avg(d, 1))
        .reduce(new Avg(0, 0), (a1, a2) -> a1.merge(a2))
        .get().ifPresent(System.out::println);
    long time = System.nanoTime() - start;
    System.out.println("time was " + (time / 1_000_000_000.0));
  }
}
