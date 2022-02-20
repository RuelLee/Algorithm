/*
 Author : Ruel
 Problem : Baekjoon 1168번 요세푸스 문제 2
 Problem address : https://www.acmicpc.net/problem/1168
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1168_요세푸스문제2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // n과 k가 주어진다
        // 1 ~ n까지의 수가 원형으로 배치되어있고, k번째 수를 지속적으로 뽑아내 수열을 만들 때, 만들어지는 수열을 구하라
        // 예를 들어 7, 3이 주어진다면, 1 2 '3' 4 5 6 7 -> 3, 1 2 4 5 '6' 7 -> 6, 1 '2' 4 5 7 -> 2, 1 4 5 '7' -> 7, 1 4 '5' -> 5, '1' 4 -> 1, '4' -> 4
        // 3 6 2 7 5 1 4라는 수열이 만들어진다
        // 각 수가 이전 수의 위치로부터 몇번째에 있는지를 빨리 구하는 것이 중요하다 -> 세그먼트 트리를 이용하자
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        fenwickTree = new int[n + 1];       // 1 ~ n 까지의 수들을 펜윅트리로 표현하고
        for (int i = 1; i < fenwickTree.length; i++)
            inputValue(i, 1);       // 각 숫자가 아직 있음을 1로 표현하자.

        Queue<Integer> queue = new LinkedList<>();
        int preOrder = 0;       // 이전 숫자의 위치 초기값 0
        while (queue.size() < n) {
            int remain = k;     // 남은 순서
            // 만약 n번까지 가더라도 순서가 남아 다시 1번부터 탐색을 시작해야할 경우
            // preOrder 부터 n까지의 수의 개수만큼을 remain에서 빼주고, 다시 1부터 시작하도록 하자.
            while (remain > getOrder(n) - getOrder(preOrder)) {
                remain -= (getOrder(n) - getOrder(preOrder));
                preOrder = 0;
            }
            // 이전 숫자의 위치 preOrder로부터, 우리가 원하는 k번째(= 한바퀴를 돈 것을 감안한 remain) 수를 찾자.
            // 이분탐색을 이용!
            int start = preOrder;
            int end = n;
            while (start < end) {
                int mid = (start + end) / 2;
                // preOrder로부터 mid까지의 수의 개수가 remain보다 작다면 start = mid + 1
                if (getOrder(mid) - getOrder(preOrder) < remain)
                    start = mid + 1;
                else        // 그렇지 않다면 end = mid
                    end = mid;
            }
            // 최종적으로 찾아진 start가 원형으로 생각했을 때 이전 수(preOrder)로부터, k번째 수
            // 큐에 담고
            queue.offer(start);
            // 수를 사용했으므로, -1을 해주고
            inputValue(start, -1);
            // preOrder에 현재 수의 위치인 start를 기록해주자.
            preOrder = start;
        }

        // 최종적으로 쌓인 큐를 순서대로 뽑아내며 답안을 작성해주면 된다.
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        while (queue.size() > 1)
            sb.append(queue.poll()).append(", ");
        sb.append(queue.poll()).append(">");
        System.out.println(sb);
    }

    static int getOrder(int n) {        // n이 남아있는 수들 중 몇번째인지 구한다.
        int sum = 0;
        while (n > 0) {
            sum += fenwickTree[n];
            n -= (n & -n);
        }
        return sum;
    }

    static void inputValue(int n, int value) {      // 수가 있음 없음을 표현해준다. 1이면 있음, 0이면 없음. 최종적으로 누적합이 남아있는 수들 중 해당 수의 순서가 될 것이다.
        while (n < fenwickTree.length) {
            fenwickTree[n] += value;
            n += (n & -n);
        }
    }
}