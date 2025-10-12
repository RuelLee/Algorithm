/*
 Author : Ruel
 Problem : Baekjoon 32654번 게임 오브 데쓰 (Easy)
 Problem address : https://www.acmicpc.net/problem/32654
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32654_게임오브데쓰Easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 게임 참가자 주어진다.
        // 각각은 두 손으로 두 명을 가르킨다.
        // 10 <= k <= 99 범위의 k를 말한 뒤
        // 1번 참가자로부터 두 명 중 랜덤한 한 명을 가르키며 k를 1감소한다.
        // k가 0이 되며 차례를 받은 사람이 진다.
        // 1번 참가자가 지지 않는 k를 찾아보자. 그러한 경우가 없다면 -1을 출력한다
        //
        // 브루트 포스
        // 로 풀어도 크기가 크지 않아 상관없는 문제인 거 같다.
        // 0번 차례일 때는 1만 가르킬 수 있고
        // 1번 차례일 때는 1번이 선택 가능한 두 명이 가르킬 수 있다.
        // 중복이 발생할 수 있으므로 해쉬셋을 통해 중복을 제거하며 99번까지 진행한다.
        // 그 중 1번이 없는 경우가 발생한다면 해당 경우를 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 각 사람이 가르키는 두 사람
        int[][] arrows = new int[n + 1][2];
        StringTokenizer st;
        for (int i = 1; i < arrows.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < arrows[i].length; j++)
                arrows[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 1이 포함되지 않는 경우가 발생하면 바로 탐색을 종료할 것이다.
        // 각 탐색에 필요한 것은 이전 상태 하나이다.
        // 따라서 두 개의 해쉬셋으로 번갈아가면서 탐색
        HashSet<Integer>[] hashSets = new HashSet[2];
        hashSets[0] = new HashSet<>();
        hashSets[0].add(1);
        hashSets[1] = new HashSet<>();

        int idx = 0;
        while (idx < 99) {
            // idx가 10이상이고, 해쉬셋에 1이 포함되지 않은 경우 반복문 종료
            if (idx > 9 && !hashSets[idx % 2].contains(1))
                break;

            // 그 외의 경우, 모든 인원들이 가르킬 수 있는 사람들을 찾는다.
            for (int p : hashSets[idx % 2]) {
                for (int a : arrows[p])
                    hashSets[(idx + 1) % 2].add(a);
            }
            // 이번 해쉬셋은 초기화시키고 다음 순서로 넘어간다.
            hashSets[idx++ % 2].clear();
        }

        // idx번째에 1이 포함되어있지 않다면 해당 idx 번호
        // 포함된다면 -1을 출력한다.
        System.out.println(!hashSets[idx % 2].contains(1) ? idx : -1);
    }
}