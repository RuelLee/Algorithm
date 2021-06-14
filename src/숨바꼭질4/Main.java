package 숨바꼭질4;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // n인 지점에서 1초당 -1 or +1 or *2로 위치를 바꿀 때,
        // k에 도달하는 최소 시간과 그 때의 경로를 구하여라
        // 각 지점에 도달하는 최소시간을 저장할 minTime 배열.
        // 각 지점에 도달하기 직전에 방문한 지점을 저장할 pi 배열.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        int[] minTime = new int[200001];        // 입력 값의 범위는 0 ~ 100000. 따라서 *2 이동을 한다해도 최대 200000이다.
        Arrays.fill(minTime, Integer.MAX_VALUE);
        minTime[n] = 0;
        int[] pi = new int[200001];
        pi[n] = -1;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(n);           // 현재 지점을 입력

        while (!queue.isEmpty()) {
            int currentLoc = queue.poll();          // 현재 지점에서

            if (minTime[currentLoc] >= minTime[k])      // 혹시 현재 지점이 이전에 계산된 k에 도달하는 시간보다 같거나 오래걸린다면 그냥 패쓰.
                continue;

            // +1, -1, *2 이동을 했을 때, 해당 지점에 도착하는 시간이 최소시간으로 갱신된다면, minTime[currentLoc + 1] 값을 갱신하고,
            // pi[currentLoc + 1] 에 currentLoc 을 저장하고,
            // Queue 에 담아 다음 번에 방문하도록 하자.
            if (currentLoc - 1 >= 0 && minTime[currentLoc - 1] > minTime[currentLoc] + 1) {
                minTime[currentLoc - 1] = minTime[currentLoc] + 1;
                pi[currentLoc - 1] = currentLoc;
                queue.add(currentLoc - 1);
            }

            if (currentLoc + 1 < minTime.length && minTime[currentLoc + 1] > minTime[currentLoc] + 1) {
                minTime[currentLoc + 1] = minTime[currentLoc] + 1;
                pi[currentLoc + 1] = currentLoc;
                queue.add(currentLoc + 1);
            }

            if (currentLoc * 2 < minTime.length && minTime[currentLoc * 2] > minTime[currentLoc] + 1) {
                minTime[currentLoc * 2] = minTime[currentLoc] + 1;
                pi[currentLoc * 2] = currentLoc;
                queue.add(currentLoc * 2);
            }
        }
        // k 지점에 도달하는 최소시간은 minTime[k]에 기록되었을 것이다.
        System.out.println(minTime[k]);

        // n -> k로 도달하는 최소 경로는 pi 배열을 살펴보면 된다.
        // 역순으로 저장되어있으므로, pi[k]로 부터 역순으로 Stack 에 담아가며 경로를 확인하고, 다시 Stack 에서 꺼내 정순으로 확인하자.
        Stack<Integer> stack = new Stack<>();
        stack.add(k);
        while (pi[stack.peek()] != -1)
            stack.add(pi[stack.peek()]);

        while (!stack.isEmpty())
            System.out.print(stack.pop() + " ");
    }
}