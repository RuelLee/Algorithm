/*
 Author : Ruel
 Problem : Baekjoon 2479번 경로 찾기
 Problem address : https://www.acmicpc.net/problem/2479
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2479_경로찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이가 k로 같은 n개의 이진코드가 주어진다.
        // 두 이진 코드를 비교할 때, 서로 다른 값을 갖는 비트의 수를 해밍 거리라고 한다.
        // 해밍 경로는 인접한 두 코드 사이의 거리가 1인 경로이다.
        // a번 이진 코드와 b번 이진 코드 사이에 가장 짧은 해밍 경로를 구하라.
        // 그러한 경로가 여러개라면 그 중 하나만 출력. 존재하지 않는다면 -1 출력.
        //
        // BFS 문제
        // 거리가 직접적으로 주어지지 않고, 두 이진 코드를 비교하여 거리를 계산해내면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 주어지는 이진 코드들
        String[] binaryCodes = new String[n + 1];
        for (int i = 1; i < binaryCodes.length; i++)
            binaryCodes[i] = br.readLine();

        // 시작 코드 a, 도착 코드 b
        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        // 이전 위치를 기록.
        int[] prePoints = new int[n + 1];
        // 방문 여부 체크.
        boolean[] checked = new boolean[n + 1];

        Queue<Integer> queue = new LinkedList<>();
        // 큐에 a부터 담아 너비 우선 탐색 시작.
        queue.offer(a);
        checked[a] = true;
        while (!queue.isEmpty()) {
            // 현재 지점
            int current = queue.poll();

            for (int i = 1; i < prePoints.length; i++) {
                // 아직 방문하지 않았으며, 해밍 거리가 1인 이진 코드를 찾는다.
                if (!checked[i] && calcDistance(binaryCodes[current], binaryCodes[i]) == 1) {
                    // 해당 이진 코드의 이전 위치에 current 기록.
                    prePoints[i] = current;
                    // 큐에 삽입.
                    queue.offer(i);
                    // 방문 체크.
                    checked[i] = true;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        // b 지점에 도착하는 것이 가능하다면
        if (prePoints[b] != 0) {
            // 기록해두었던 이전 이점들과 스택을 사용하여 경로를 추산해낸다.
            Stack<Integer> stack = new Stack<>();
            stack.push(b);
            while (prePoints[stack.peek()] != 0)
                stack.push(prePoints[stack.peek()]);

            while (!stack.isEmpty())
                sb.append(stack.pop()).append(" ");
            sb.deleteCharAt(sb.length() - 1);
        } else      // 불가능한 경우 -1 기록.
            sb.append(-1);
        // 가능한 경우에는 경로, 불가능한 경우에는 -1 출력.
        System.out.println(sb);
    }

    // 두 이진 코드 사이의 거리를 계산한다.
    static int calcDistance(String a, String b) {
        int count = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i))
                count++;
        }
        return count;
    }
}