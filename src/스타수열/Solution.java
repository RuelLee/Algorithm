package 스타수열;

import java.util.HashMap;
import java.util.PriorityQueue;

class Num {
    int value;
    int count;

    public Num(int value, int count) {
        this.value = value;
        this.count = count;
    }
}

public class Solution {
    public static void main(String[] args) {
        // 원래 수열에서 순서를 바꾸지 않고, 수를 제거만 하여, 순서대로 쌍으로 묶었을 때 하나 이상의 공통 수를 갖는다면 스타수열
        // 일단 숫자의 갯수를 모두 세고서, 갯수가 큰 숫자부터, 앞, 뒤로 자신과 다른 숫자가 있는지 확인한다.
        // 자신의 뒤에서 쓰인 숫자가 다음 번에 앞에서 쓰일 수 있으므로, 뒤에서 쓰인 경우 idx를 남겨서 중복되지 않도록 하여야한다.
        int[] a = {5, 2, 3, 3, 5, 3};

        HashMap<Integer, Integer> hashMap = new HashMap<>();

        for (int i : a) {   // 각 숫자의 출연갯수를 센다.
            if (hashMap.containsKey(i))
                hashMap.put(i, hashMap.get(i) + 1);
            else
                hashMap.put(i, 1);
        }

        PriorityQueue<Num> numPriorityQueue = new PriorityQueue<>(((o1, o2) -> Integer.compare(o2.count, o1.count)));   // 갯수 순으로 정렬
        for (int i : hashMap.keySet())
            numPriorityQueue.add(new Num(i, hashMap.get(i)));

        int maxCount = 0;
        do {
            int count = 0;
            int lastUedIdx = -1;
            Num num = numPriorityQueue.poll();

            for (int i = 0; i < a.length; i++) {    // num.value 값과 비교하여, 같은 값일 경우, 앞 뒤로 다른 숫자인지, 이전 반복에서 쓰였는지 확인 후, count를 증가시켜준다.
                if (a[i] == num.value) {
                    if (checkRange(i - 1, a) && a[i - 1] != a[i] && lastUedIdx != i - 1) {
                        lastUedIdx = i - 1;
                        count++;
                    } else if (checkRange(i + 1, a) && a[i + 1] != a[i]) {
                        lastUedIdx = i + 1;
                        count++;
                    }
                }
            }
            maxCount = Math.max(maxCount, count);   // num.value일 때 스타 수열을 생성 완료. 그 길이를 남겨둔다.
        } while (!numPriorityQueue.isEmpty() && maxCount < numPriorityQueue.peek().count);
        // 이를 numPriorityQueue가 비어있지 않고,
        // maxCount가 다음 숫자의 갯수보다 적은 동안(더 많은 maxCount가 생길 수 있는 가능성이 있는 동안) 반복한다.

        System.out.println(maxCount * 2);   // count는 쌍을 센 것이므로, 수열의 길이는 count * 2
    }

    static boolean checkRange(int i, int[] a) {
        if (i < 0 || i >= a.length)
            return false;
        return true;
    }
}