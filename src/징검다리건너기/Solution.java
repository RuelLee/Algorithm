package 징검다리건너기;

import java.util.PriorityQueue;

class Dp {
    int n;
    int loc;

    public Dp(int n, int loc) {
        this.n = n;
        this.loc = loc;
    }
}

public class Solution {
    public static void main(String[] args) {
        // 돌을 밟을 수 있는 횟수와, 한 번에 최대 이동할 수 있는 거리인 k가 주어진다
        // 이동할 때는 무조건 가장 가까운 돌로 이동한다.
        // 이 징검다리를 건널 수 있는 최대 인원은 몇명인가.
        // n번째 돌에 도달하는 경우는 n-1, n-2 ... n-k 번째 돌에 도달하는 인원 중 가장 큰 수와
        // n번째 돌을 밟을 수 있는 횟수 중의 작은 값이다.
        int[] stones = {2, 4, 5, 3, 2, 1, 4, 2, 5, 1};
        int k = 3;

        // n-1, n-2 ... n-k의 돌을 갖고서, 그 중 가장 큰 수만 반환해줄 우선순위큐.
        PriorityQueue<Dp> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.n, o1.n));
        Dp[] dp = new Dp[stones.length];
        // k번째 돌까지는 돌을 밟을 수 있는 횟수 자체가 최대 도달할 수 있는 인원이다.
        // 징검다리 밖에서 건너뛰면 되므로.
        for (int i = 0; i < k; i++) {
            dp[i] = new Dp(stones[i], i);
            priorityQueue.add(dp[i]);
        }

        for (int i = k; i < dp.length; i++) {
            // dp[i]에는 우선순위큐에 있는 가장 큰 수와, i번째 돌을 밟을 수 있는 횟수 중 적은 값을 저장한다.
            dp[i] = new Dp(Math.min(stones[i], priorityQueue.peek().n), i + 1);
            priorityQueue.add(dp[i]);       // 그 후, dp[i]를 우선순위 큐에 넣고
            priorityQueue.remove(dp[i - k]);    // dp[i - k]번째 돌을 지워준다.
        }
        // 마지막에 큐에 남이있는 돌 중 가장 큰 값이 징검다리를 건널 수 있는 가장 큰 인원의 수
        System.out.println(priorityQueue.poll().n);
    }
}