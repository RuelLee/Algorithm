package 캐시;

import java.util.Objects;
import java.util.PriorityQueue;

class City {
    String name;
    int inputTime;

    public City(String name, int inputTime) {
        this.name = name;
        this.inputTime = inputTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return this.name.equals(city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

public class Solution {
    public static void main(String[] args) {
        // 정해진 크기의 cache 공간에 지속적으로 새로운 데이터를 넣으면서, 새로운 데이터라면 가장 오래된 데이터를 제거하고,
        // 새로운 데이터가 기존 데이터가 중복될 떄는, 해당 데이터의 위치만 변경해준다.

        int cacheSize = 2;
        String[] cities = {"Jeju", "Jeju", "Jeju", "Jeju"};

        PriorityQueue<City> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.inputTime, o2.inputTime));

        int runningTime = 0;
        for (int i = 0; i < cities.length; i++) {
            City city = new City(cities[i].toLowerCase(), i);
            if (priorityQueue.contains(city)) {     // City 클래스에 equals와 hashCode 메소드를 재정의해서, 이름이 같다면 같은 클래스로 판단하게끔 한다.
                runningTime += 1;
                priorityQueue.remove(city);     // 이미 cache에 존재한다면, 그 자리에 있는 데이터를 제거하고, 가장 최신 데이터로 새롭게 넣는다.
            }
            else
                runningTime += 5;

            priorityQueue.add(city);    // 새 데이터를 넣고,
            if (priorityQueue.size() > cacheSize)   // cache 크기를 넘었다면 가장 오래된 데이터를 제거한다..
                priorityQueue.poll();
        }

        System.out.println(runningTime);
    }
}