/*
 Author : Ruel
 Problem : Baekjoon 15703번 주사위 쌓기
 Problem address : https://www.acmicpc.net/problem/15703
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15703_주사위쌓기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 주사위가 주어지며, 각 주사위는 모든 면에 같은 수가 적혀있다.
        // 주사위들로 탑을 쌓는데, 해당 주사위의 위로는 주사위에 적힌 수만큼의 주사위만 쌓을 수 있다.
        // 예를 들어, 1 2 4 5인 경우, 2 1 4 5로 쌓는 것은 가능하나, 4 1 5 2로 쌓는 것은 불가능하다.
        // 최소한의 주사위 탑을 세우려고 할 때, 그 수는?
        //
        // 정렬, 그리디 문제
        // 주사위들을 정렬하여
        // 적힌 수가 작은 주사위부터 살펴보며, 현재 쌓은 주사위 탑을 위에 얹을 수 있는지 확인해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 주사위
        int n = Integer.parseInt(br.readLine());
        int[] dices = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < dices.length; i++)
            dices[i] = Integer.parseInt(st.nextToken());
        // 주사위 정렬
        Arrays.sort(dices);
        
        // 각 주사위가 이미 선택되었는지 여부
        boolean[] selected = new boolean[n];
        // 전체 선택된 주사위
        int count = 0;
        // 주사위 탑의 개수
        int towers = 0;
        // 전체 주사위를 선택할 때까지
        while (count < n) {
            // 현재 쌓은 탑의 높이
            int currentDice = 0;
            for (int i = 0; i < dices.length; i++) {
                // i번 주사위가 아직 선택되지 않았고,
                // 현재 쌓은 주사위 탑을 i번 주사위에 얹을 수 있다면
                if (!selected[i] && dices[i] >= currentDice) {
                    // 선택 체크
                    selected[i] = true;
                    // 현재 주사위 탑의 높이 증가
                    currentDice++;
                    // 전체 선택된 주사위의 수 증가
                    count++;
                }
            }
            // 한 턴이 끝나면, 해당 턴에 주사위 탑 하나가 만들어진 셈.
            towers++;
        }
        // 전체 탑의 개수 출력
        System.out.println(towers);
    }
}