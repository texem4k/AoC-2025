package software.aoc.day01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Dial {
    private final List<Order> orders;
    private int num;
    private Dial() {
        this.orders = new ArrayList<>();
        this.num=0;
    }
    public static Dial create() {
        return new Dial();
    }

    public Dial add(String... orders) {
        Arrays.stream(orders)
                .map(this::parse)
                .forEach(this::add);
        return this;
    }

    private void add(Order order) {
        orders.add(order);
    }

    private Order parse(String order) {
        return new Order(signOf(order) * valueOf(order));
    }

    private int signOf(String order) {
        return order.charAt(0) == 'L' ? -1 : 1;
    }

    private int valueOf(String order) {
        return Integer.parseInt(order.substring(1));
    }


    private int sumAll() {
        return sum(orders.stream());
    }

    public int count() {
        return (int) iterate()
                .map(this::sumPartial)
                .filter(s -> s == 0)
                .count();
    }

    private IntStream iterate() {
        return IntStream.rangeClosed(1, orders.size()).parallel();
    }

    private int sumPartial(int size) {
        return normalize(sum(orders.stream().limit(size)));
    }

    private int sum(Stream<Order> orders) {
        return orders.mapToInt(Order::getStep).sum()+50;
    }

    private int normalize(int value) {
        if(value<=0) {
            num += 1;
            return (100 + value) % 100;
        }
        return value%100;

    }

    public int countZeros(){
        return count()+num;
    }


    public Dial execute(String orders) {
        return add(orders.split("\n"));
    }

    public int countAllClicksZeros() {
        int pos = 50; // posición inicial
        int zeros = (pos == 0) ? 1 : 0; // normalmente 0

        for (Order order : orders) {
            int step = order.getStep(); // puede ser negativo o positivo
            int dir = step > 0 ? 1 : -1;

            for (int i = 0; i < Math.abs(step); i++) {
                pos = (pos + dir + 100) % 100;
                if (pos == 0) {
                    zeros++;
                }
            }
        }
        return zeros;
    }



}
