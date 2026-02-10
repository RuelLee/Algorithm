/*
 Author : Ruel
 Problem : Baekjoon 2481번 해밍 경로
 Problem address : https://www.acmicpc.net/problem/2481
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2481_해밍경로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 이진 코드 사이의 거리는 서로 다른 비트의 개수이며, 이를 해밍 거리라고 부른다.
        // 해밍 경로는 해밍 거리가 1인 비트들로 이루어진 경로이다.
        // 길이가 k인 n개의 이진 코드들이 주어진다.
        // m개의 질의가 주어진다.
        // j : 1번 코드부터 j번 코드까지의 해밍 경로
        // 불가능하다면 -1을 출력하낟.
        //
        // 해쉬 맵, BFS, 비트마스킹 무넺
        // 먼저 각각 코드들을 입력 받고, 하나의 수로 표현한다.
        // 그리고 해당 코드의 순서를 맵을 통해 기록한다.
        // 그 후, 1번 코드부터 시작하여 하나의 비트만 바꾼 코드가 존재하는지
        // 그리고 이미 방문한 코드인지를 확인해나가며, 해당 코드에 도달하기 직전 코드들을
        // 맵을 통해 기록한다.
        // 모든 작업이 완료되면, m개의 질의에 대해
        // 해당 코드부터 1번코드까지 스택에 거치는 코드들의 번호를 담고, 꺼내가며 답안을 작성하여 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 이진 코드, 각 코드의 길이 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 이진 코드의 순서
        HashMap<Integer, Integer> toIdx = new HashMap<>();
        int[] codes = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            codes[i] = Integer.parseInt(br.readLine(), 2);
            toIdx.put(codes[i], i);
        }

        // 각 코드에 도달하기 위한 이전 코드의 순서
        HashMap<Integer, Integer> pre = new HashMap<>();
        pre.put(1, 0);
        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        while (!queue.isEmpty()) {
            // 현재 코드의 순서
            int c = queue.poll();
            // 해당 코드
            int curCode = codes[c];

            for (int i = 0; i < k; i++) {
                // 하나의 비트 값을 반전
                int next = (curCode & (1 << i)) == 0 ? (curCode | (1 << i)) : (curCode - (1 << i));
                // 해당 코드가 주어진 코드 중에 있고
                // 미방문 코드인 경우
                if (toIdx.containsKey(next) && !pre.containsKey(toIdx.get(next))) {
                    // 이전 경로 등록 및 큐에 추가
                    pre.put(toIdx.get(next), c);
                    queue.offer(toIdx.get(next));
                }
            }
        }

        // m개의 쿼리
        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        // 스택을 통해 역추적
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < m; i++) {
            // 1번 코드부터 j번 코드까지의 경로를 찾아야한다.
            int j = Integer.parseInt(br.readLine());
            // 만약 j번 코드에 도달하는 해밍 경로가 없다면 -1을 기록
            if (!pre.containsKey(j)) {
                sb.append(-1).append("\n");
                continue;
            }

            // 그 외의 경우엔 스택에 j를 담아 역추적
            stack.push(j);
            while (stack.peek() != 1)
                stack.push(pre.get(stack.peek()));

            // 꺼내 가며 경로 기록
            sb.append(stack.pop());
            while (!stack.empty())
                sb.append(" ").append(stack.pop());
            sb.append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}