/*
 Author : Ruel
 Problem : Baekjoon 24955번 숫자 이어 붙이기
 Problem address : https://www.acmicpc.net/problem/24955
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24955_숫자이어붙이기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int LIMIT = 1_000_000_007;
    static int[] depths, parents;
    static long[] pows;

    public static void main(String[] args) throws IOException {
        // n개의 집이 트리 형태로 이어져있고, 각 집에 수가 할당되어있다.
        // 트리의 형태가 주어진다.
        // q개의 쿼리 x y가 주어진다.
        // x에서 y로 가는 동안, 지나치는 모든 집의 수를 이어붙인 뒤, 1_000_000_007로 나눈 나머지 값을 출력한다.
        //
        // 공통 조상 문제
        // 트리 형태이기 때문에, 두 집의 깊이를 계산해두고, 공통 조상을 만날 때까지 거슬러 올라가며
        // 만나는 집을 큐와 스택으로 기록해둔다.
        // 그리고 순서대로 수를 이어붙여주면 된다.
        // 이어붙이는 도중, 뒤의 수가 10^9일 경우, 앞 수에 10^10을 곱하고, 계산해야하는데, 만약 앞의 수도
        // 10^9이라면 10^19으로 long 범위 오버플로우가 나올 수 있다.
        // 따라서 10^10을 곱할 때, 10^10이 아니라 모듈러 연산 성질을 이용하여 10^10 mod 1_000_000_007를 곱해준다.

        // 10의 제곱들을 미리 계산
        pows = new long[11];
        pows[0] = 1;
        for (int i = 1; i < 11; i++)
            pows[i] = pows[i - 1] * 10;
        // 10의 10은 미리 mod LIMIT을 해두고 해당 값으로 곱해준다.
        pows[10] %= LIMIT;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 집, q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // 각 집에 할당된 수
        int[] nums = new int[n + 1];
        for (int i = 1; i <= n; i++)
            nums[i] = Integer.parseInt(st.nextToken());

        // 트리의 형태
        List<List<Integer>> connected = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            connected.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            connected.get(a).add(b);
            connected.get(b).add(a);
        }

        // 각 노드의 깊이
        depths = new int[n + 1];
        depths[1] = 1;
        // 각 노드의 부모 노드
        parents = new int[n + 1];
        // 1번 노드를 루트 노드로 보고, 깊이를 계산
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int next : connected.get(current)) {
                if (depths[next] == 0) {
                    depths[next] = depths[current] + 1;
                    parents[next] = current;
                    queue.offer(next);
                }
            }
        }

        Stack<Integer> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        // q개의 쿼리 처리
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // x -> y
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            queue.clear();
            queue.offer(x);
            stack.clear();
            stack.push(y);

            // 양쪽의 부모 노드가 다를 경우
            while (parents[x] != parents[y]) {
                // 깊이 더 깊은 노드를 하나 끌어올린다.
                if (depths[x] > depths[y]) {
                    x = parents[x];
                    queue.offer(x);
                } else if (depths[x] < depths[y]) {
                    y = parents[y];
                    stack.push(y);
                } else {
                    // 두 깊이가 같다면, 아직 부모 노드는 다른 상태이니
                    // 두 노드 모두 부모 노드로 끌어올린다.
                    x = parents[x];
                    queue.offer(x);
                    y = parents[y];
                    stack.push(y);
                }
            }

            // 부모 노드가 같아졌는데
            // x와 y가 서로 다른 노드인 경우
            // 부모 노드를 한번 들려야한다.
            if (x != y)
                queue.offer(parents[x]);
            else   // 서로 같은 노드가 됐다면, 현재 노드가 중복으로 입력됐으니 하나를 제거
                stack.pop();

            // 큐에 스택을 꺼내가며 값을 이어붙인다.
            long answer = 0;
            while (!queue.isEmpty()) {
                int next = queue.poll();
                answer = (answer * pows[calcLength(nums[next])] + nums[next]) % LIMIT;
            }
            while (!stack.isEmpty()) {
                int next = stack.pop();
                answer = (answer * pows[calcLength(nums[next])] + nums[next]) % LIMIT;
            }
            sb.append(answer).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    // 수 n의 길이 계산
    static int calcLength(long n) {
        int cnt = 0;
        while (n > 0) {
            cnt++;
            n /= 10;
        }
        return cnt;
    }
}