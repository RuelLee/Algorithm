/*
 Author : Ruel
 Problem : Baekjoon 14864번 줄서기
 Problem address : https://www.acmicpc.net/problem/14864
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14864_줄서기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명이 학생이 일렬로 서 있으면서, 1 ~ n 까지의 서로 다른 카드를 손에 들고 있다.
        // 학생들은 자신보다 뒤에 서있으면서 더 작은 카드를 가진 다른 학생들을 모두 알고 있다고 한다.
        // 각 학생들이 갖고 있는 카드를 출력하라. 불가능한 경우라면 -1을 출력한다.
        //
        // 애드 혹 문제
        // 처음에는 위상 정렬문제인 줄 알고 풀려고 했으나, 위상 정렬 + 추가적인 계산이 필요했다.
        // 고려해야할 점은 자신보다 뒤에 서있으면서 더 작은 카드를 가진 학생들 '모두'를 안다는 점이다.
        // n명의 학생이 자신보다 뒤에, 더 작은 카드를 가진 학생이 하나도 없는 경우는
        // 1 ~ n까지 오름차순으로 카드를 배정받은 경우이다.
        // 위 경우에서 자신보다 작은 순서쌍이 주어질 때마다 자신은 +1, 뒤의 학생은 -1를 시켜나간다.
        // 조건에 모든 쌍이 주어진다했으므로, 결국에는 각각 다른 숫자의 카드를 갖게되고
        // 같은 카드를 갖게 되는 학생들이 생긴다면 불가능한 경우이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각자 들고 있는 카드.
        // 초기값은 오름차순
        int[] cards = new int[n];
        for (int i = 0; i < cards.length; i++)
            cards[i] = i + 1;

        // 쌍이 주어질 때마다
        // 전자는 +1, 후자는 -1
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;

            cards[x]++;
            cards[y]--;
        }

        // 같은 카드를 갖고 있는 학생들이 존재하는지 확인한다.
        boolean[] visited = new boolean[n + 1];
        visited[0] = true;
        for (int card : cards) {
            if (visited[card]) {
                visited[0] = false;
                break;
            }

            visited[card] = true;
        }

        StringBuilder sb = new StringBuilder();
        // 가능한 경우, 각 학생의 카드를 출력한다.
        if (visited[0]) {
            for (int card : cards)
                sb.append(card).append(" ");
            sb.deleteCharAt(sb.length() - 1);
        } else      // 불가능한 경우. -1 출력.
            sb.append(-1);
        System.out.println(sb);
    }
}