package 이중우선순위큐;

import java.util.Arrays;
import java.util.PriorityQueue;

class Op {
    char order;
    int value;

    public Op(char order, int value) {
        this.order = order;
        this.value = value;
    }
}

public class Solution {
    public static void main(String[] args) {
        String[] operations = {"I 7", "I 5", "I -5", "D -1"};

        PriorityQueue<Integer> minQueue = new PriorityQueue<>();
        PriorityQueue<Integer> maxQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1));

        for (String s : operations) {
            Op cur = parsing(s);

            switch (cur.order) {
                case 'I':
                    minQueue.add(cur.value);
                    maxQueue.add(cur.value);
                    break;
                case 'D':
                    if (!minQueue.isEmpty()) {
                        switch (cur.value) {
                            case 1 -> {
                                int max = maxQueue.poll();
                                minQueue.remove(max);
                            }
                            case -1 -> {
                                int min = minQueue.poll();
                                maxQueue.remove(min);
                            }
                        }
                    }
            }
        }

        int[] answer = new int[2];
        if (minQueue.size() >= 2) {
            answer[1] = minQueue.poll();
            answer[0] = maxQueue.poll();
        } else if (minQueue.size() == 1)
            answer[0] = answer[1] = minQueue.poll();

        System.out.println(Arrays.toString(answer));
    }

    static Op parsing(String s) {
        return new Op(s.charAt(0), Integer.parseInt(s.substring(2)));
    }
}