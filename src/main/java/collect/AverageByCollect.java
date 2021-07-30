package collect;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

class Avg {
  private double sum;
  private long count;

  public Avg() {
    this(0, 0);
  }

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

  public void include(double d) {
    this.sum += d;
    this.count++;
  }
  public void merge(Avg other) {
    this.sum += other.sum;
    this.count += other.count;
  }
}

public class AverageByCollect {
  public static void main(String[] args) {
    long start = System.nanoTime();
//    ThreadLocalRandom.current().doubles(20_000_000_000L, -Math.PI, +Math.PI)
//    DoubleStream.generate(() -> ThreadLocalRandom.current().nextDouble(-Math.PI, +Math.PI))
    DoubleStream.iterate(0.0, x -> ThreadLocalRandom.current().nextDouble(-Math.PI, +Math.PI))
        .limit(4_000_000_000L)
        .unordered()
        .parallel()
//        .sequential()
//        .mapToObj(d -> new Avg(d, 1))
//        .collect(() -> new Avg(), (a, d) -> a.include(d), (a1, a2) -> a1.merge(a2))
        .collect(Avg::new, Avg::include, Avg::merge)
        .get().ifPresent(System.out::println);
    long time = System.nanoTime() - start;
    System.out.println("time was " + (time / 1_000_000_000.0));
  }
}
