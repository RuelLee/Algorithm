/*
 Author : Ruel
 Problem : Baekjoon 2550번 전구
 Problem address : https://www.acmicpc.net/problem/2550
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2550_전구;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int[] bulbs, minValues;

    public static void main(String[] args) throws IOException {
        // 왼편에는 스위치, 오른편에는 전구들이 주어진다.
        // 같은 번호의 스위치와 전구들이 연결되어있다.
        // 연결된 선은 일직선이며, 다른 전구와 스위치를 연결하는 선과 교차할 수 있다.
        // 스위치에 전원을 넣으면 연결된 전구가 켜지는데,
        // 교차된 다른 선에도 전원을 넣었다면 모두 켜지지 않는다.
        // 최대한 많이 켤 수 있는 전구는 몇 개인가?
        // 그 때의 개수와 스위치 번호를 오름차순으로 출력하라
        //
        // 가장 긴 증가하는 부분 수열 문제
        // 왼편에 있는 스위치들은 그대로
        // 오른편에 있는 전구들은 번호에 해당하는 순서를 저장한다.
        // 그 후 교차하지 않는 최대 선의 개수를 최장증가수열로 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        // switches[순서] = 번호
        int[] switches = new int[n + 1];
        for (int i = 1; i < switches.length; i++)
            switches[i] = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // bulbs[번호] = 순서
        bulbs = new int[n + 1];
        for (int i = 1; i < bulbs.length; i++)
            bulbs[Integer.parseInt(st.nextToken())] = i;

        int[] orders = new int[n + 1];
        minValues = new int[n + 1];
        Arrays.fill(minValues, Integer.MAX_VALUE);
        minValues[0] = 0;
        for (int i = 1; i < switches.length; i++) {
            // i번째 스위치와 연결된 전구를 살펴보고 해당 전구가
            // 이전에 등장했던 다른 전구들보다 최대 몇번째 위치에 올 수 있는지 찾는다.
            int order = findOrder(switches[i]);
            // 찾아진 순서
            orders[i] = order;
            // 해당 위치에 올 수 있는 전구들 중 가장 적은 순서인지 찾는다.
            minValues[order] = Math.min(minValues[order], bulbs[switches[i]]);
        }

        StringBuilder sb = new StringBuilder();
        // 최장증가수열의 크기를 찾고
        int maxOrder = Arrays.stream(orders).max().getAsInt();
        sb.append(maxOrder).append("\n");
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = orders.length - 1; i > 0; i--) {
            if (maxOrder == 0)
                break;
            
            // maxOrder에 해당하는 스위치 하나를 찾아 우선순위큐에 담고
            // maxOrder -1 번째에 해당하는 스위치를 또 찾는다.
            if (maxOrder == orders[i]) {
                priorityQueue.offer(switches[i]);
                maxOrder--;
            }
        }

        // 답안 출력
        while (!priorityQueue.isEmpty())
            sb.append(priorityQueue.poll()).append(" ");
        System.out.println(sb);
    }

    // switchNum에 해당하는 스위치를 켰을 때
    // 최장 증가수열의 순서 중 가장 늦은 순서에 오는 경우를 찾는다.
    static int findOrder(int switchNum) {
        // 해당하는 전구의 순서
        int value = bulbs[switchNum];
        int start = 1;
        int end = minValues.length - 1;
        // 이분 탐색을 통해
        // 현재 찾은 동시에 켤 수 있는 전구들 중
        // 가장 많은 전구의 수를 갱신할 수 있는지 살핀다.
        while (start <= end) {
            int mid = (start + end) / 2;
            if (minValues[mid] < value)
                start = mid + 1;
            else
                end = mid - 1;
        }
        // 해당 순서 반환.
        return start;
    }
}