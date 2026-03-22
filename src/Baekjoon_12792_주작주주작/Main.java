/*
 Author : Ruel
 Problem : Baekjoon 12792번 주작 주 주작
 Problem address : https://www.acmicpc.net/problem/12792
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12792_주작주주작;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1이상 100만 이하의 정수 n이 주어진다.
        // 추첨기는 정해진 순서에 따라 공을 섞는다.
        // 만약 2 3 1과 같이 주어진다면
        // 1 2 3이 -> 2 3 1 -> 3 1 2 -> 1 2 3과 같이 섞인다.
        // 주어진 순서대로 섞을 때, 최소 몇 번을 섞어야 모든 공이 처음 위치와 다른 위치에 가 있게 되는가.
        //
        // 수학 문제
        // 순열 사이클 분할... 인 것 같지만 더 간단한 문제
        // 먼저, 섞는 공들 중에 섞이지 않는 공이 있다면 바로 불가능해진다.
        // 해당 경우는 -1을 출력
        // 그렇지 않고 전체 공이 섞이긴한다면, 순열 사이클 분할을 통해
        // 모든 사이클의 배수가 아닌 수를 찾아야한다.
        // 하지만 최소 섞음 횟수를 구하라고는 하지 않았다.
        // 따라서 100만보다 큰 소수를 아무거나 출력해줘도 답이 된다. ex) 1_000_003

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 공
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 추첨기를 통해 섞이는 방식
        int[] machine = new int[n + 1];
        for (int i = 1; i <= n; i++)
            machine[i] = Integer.parseInt(st.nextToken());

        boolean selfCycle = false;
        // 자기 자신을 가르키는 수가 하나라도 있다면 불가능한 경우
        for (int i = 1; i <= n; i++) {
            if (machine[i] == i) {
                selfCycle = true;
                break;
            }
        }

        // 불가능하다면 -1, 그렇지 않다면 1_000_003을 출력
        System.out.println(selfCycle ? -1 : 1000003);
    }
}