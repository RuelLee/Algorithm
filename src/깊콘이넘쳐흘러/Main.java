/*
 Author : Ruel
 Problem : Baekjoon 17420번 깊콘이 넘쳐흘러
 Problem address : https://www.acmicpc.net/problem/17420
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 깊콘이넘쳐흘러;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Gifticon {
    int expiredDay;
    int usingDay;

    public Gifticon(int expiredDay, int usingDay) {
        this.expiredDay = expiredDay;
        this.usingDay = usingDay;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 기프티콘 n개의 만료일과 사용하고자 하는 날이 주어진다
        // 기프티콘은 30일씩 기간을 연장할 수 있으며, 만료일이 적게 남은 기프티콘만 사용이 가능하다.
        // 최소한으로 기간 연장을 하며 기프티콘을 모두 사용했을 때, 기간 연장의 횟수를 구하여라
        // 만료일 가장 적은 기프티콘만 사용할 수 있으므로, 기프티콘을 사용하고자 하는 날에 대해 오름차순 정렬한 후
        // 이전에 사용한 기프티콘들의 만료일과 이번 기프티콘을 사용하고자 하는 날 중 큰 값보다 크거나 같도록 만료일을 늘려줘가며 사용을 하면 된다
        // 예를 들어 만료일과 사용일이 5 2, 4 3가 주어진다면
        // 첫번째 기프티콘은 연장 없이 사용이 가능하고, 이 때의 만료일인 5를 남겨둔다
        // 두번째 기프티콘도 만료일보다 사용일이 이르지만, 이전 쿠폰을 사용하기 위한 만료일인 5보다는 만료일이 더 커야만 한다(안그러면 첫번째 쿠폰을 사용할 때 만료일이 가장 적지 않아서 사용이 불가능)
        // 따라서 두번째 기프티콘은 한번 사용 기한을 연장 해야한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer expiredDays = new StringTokenizer(br.readLine());
        StringTokenizer usingDays = new StringTokenizer(br.readLine());

        PriorityQueue<Gifticon> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.usingDay));      // 사용일에 따라서 오름차순 정렬.
        for (int i = 0; i < n; i++)
            priorityQueue.offer(new Gifticon(Integer.parseInt(expiredDays.nextToken()), Integer.parseInt(usingDays.nextToken())));

        long count = 0;     // 연장 횟수를 센다.
        int lastExpiredDay = 0;     // 사용한 쿠폰들의 가장 긴 만료일을 계산한다.
        while (!priorityQueue.isEmpty()) {
            Queue<Gifticon> current = new LinkedList<>();
            current.offer(priorityQueue.poll());

            // 같은 사용일을 갖고 있다면 만료일이 서로 다르더라도 상관 없이 한 번에 처리하는 게 가능하다.
            while (!priorityQueue.isEmpty() && current.peek().usingDay == priorityQueue.peek().usingDay)
                current.offer(priorityQueue.poll());

            int maxExpiredDay = lastExpiredDay;     // 최대 만료일의 초기값은 lastExpiredDay를 가져오고,
            while (!current.isEmpty()) {
                int boundary = Math.max(lastExpiredDay, current.peek().usingDay);       // 현재 기프티콘의 사용일과 이전 기프티콘들의 만료일들 중 큰 값을 가져온다.
                if (boundary <= current.peek().expiredDay) {
                    // 만료일이 boundary 값보다 같거나 크다면 기한 연장이 필요 없는 경우이다.
                    // 따라서 현자 기프티콘의 만료일만 maxExpiredDay에 최대값 갱신만 해두자.
                    maxExpiredDay = Math.max(maxExpiredDay, current.poll().expiredDay);
                    continue;
                }
                // 만료일이 boundary 보다 이른 날이라면 몇 회 기한 연장을 해야하는지 계산한다.
                int cycle = (int) Math.ceil((boundary - current.peek().expiredDay) / (double) 30);
                // 연장 횟수를 더하고
                count += cycle;
                // 이 때의 연장된 만료일이 더 큰 값으로 갱신됐다면 maxExpiredDay에 갱신해주자
                maxExpiredDay = Math.max(maxExpiredDay, current.poll().expiredDay + cycle * 30);
            }
            lastExpiredDay = maxExpiredDay;
        }
        System.out.println(count);
    }
}