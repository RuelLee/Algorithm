/*
 Author : Ruel
 Problem : Jungol 14012번 Flathead grey mullet
 Problem address : https://jungol.co.kr/problem/14012
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_14012_FlatheadGreyMullet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n 크기를 갖는 n마리의 숭어가 있다.
        // 왼쪽부터 순서대로 놓여있으며
        // 1. 가장 왼쪽의 숭어를 판매한다. 이 크기를 x라고 한다.
        // 2. x가 현재까지 판매한 최대 크기라면 남아있는 숭어의 순서를 뒤집는다.
        // 3. 숭어가 남아있다면 가장 왼쪽 숭어를 오른쪽으로 넘긴다.
        // 1 ~ 3의 과정을 모든 숭어가 팔릴 때까지 반복하며
        // 판매한 기록이 주어진다. 원래 놓여있던 순서를 구하라
        //
        // 데크 문제
        // 데크에 숭어가 아닌, 각 자리 위치를 넣는다고 생각하고 넣고, 그대로 따라서 시뮬레이션한다.
        // 데크로 앞 뒤 모두 꺼낼 수 있으므로 남은 걸 실제로 뒤집지 않고, 어느 쪽이 앞 부분이 바꾼다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 1 ~ n 크기의 n 마리 숭어
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 판매 기록
        int[] log = new int[n + 1];
        // 데크
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 1; i < n + 1; i++) {
            log[i] = Integer.parseInt(st.nextToken());
            // 데크에 위치를 담는다.
            deque.offerLast(i);
        }

        // 현재 판매된 최대 크기 숭어
        int max = 0;
        int[] original = new int[n + 1];
        // 실제로 순서를 뒤집지 않고, 데크를 통해 앞뒤를 바꾼다.
        boolean front = true;
        for (int i = 1; i <= n; i++) {
            int loc = -1;
            // 어느 쪽이 앞인지 판단하고 꺼낸다.
            if (front)
                loc = deque.pollFirst();
            else
                loc = deque.pollLast();

            // 현재 순서에 판매된 숭어가
            // 원래 loc 위치에 존재하던 숭어.
            original[loc] = log[i];

            // 최대 크기 숭어인지 비교
            // 맞다면 순서를 뒤집는다.
            if (max < log[i]) {
                front = !front;
                max = log[i];
            }

            // 데크가 비어있지 않다면 앞에서 꺼내 뒤에 넣는다.
            if (!deque.isEmpty()) {
                if (front)
                    deque.offerLast(deque.pollFirst());
                else
                    deque.offerFirst(deque.pollLast());
            }
        }

        // 답 기록
        StringBuilder sb = new StringBuilder();
        sb.append(original[1]);
        for (int i = 2; i <= n; i++)
            sb.append(" ").append(original[i]);
        // 출력
        System.out.println(sb);
    }
}