/*
 Author : Ruel
 Problem : Jungol 11360번 이중 우선순위 큐
 Problem address : https://jungol.co.kr/problem/11360
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_11360_이중우선순위큐;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스가 주어진다.
        // 테스트케이스마다 k가 주어지며, k개의 쿼리를 실행한다.
        // I n : 정수 n을 추가한다
        // d 1 : 최댓값을 하나 삭제한다.
        // d -1 : 최솟값을 하나 삭제한다.
        // 값을 삭제하는 쿼리인데, 존재하는 값이 없는 경우, 무시한다.
        // 테스트케이스를 마칠 때마다, 남은 최댓값과 최솟값을 출력한다. 값이 없다면 EMPTY를 출력한다.
        //
        // 트리 맵 문제
        // 최솟값, 최댓값 두개를 동시에 다루므로 트리맵을 통해 최솟값 최댓값도 동시에 접근할 수 있게끔한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        // 트리맵
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (int testCase = 0; testCase < t; testCase++) {
            // 테스트케이스마다 트리맵 초기화
            treeMap.clear();

            // k개의 쿼리
            int k = Integer.parseInt(br.readLine());
            for (int i = 0; i < k; i++) {
                st = new StringTokenizer(br.readLine());
                // 값 삽입 명령
                if (st.nextToken().equals("I")) {
                    int n = Integer.parseInt(st.nextToken());
                    // 기존 삽입 여부를 판단하여 개수 조절
                    treeMap.put(n, treeMap.getOrDefault(n, 0) + 1);
                } else if (!treeMap.isEmpty()) {
                    // 삭제 명령
                    int n = Integer.parseInt(st.nextToken());
                    // 최댓값 삭제
                    if (n == 1) {
                        int key = treeMap.lastKey();
                        if (treeMap.get(key) == 1)
                            treeMap.pollLastEntry();
                        else
                            treeMap.put(key, treeMap.get(key) - 1);
                    } else {        // 최솟값 삭제
                        int key = treeMap.firstKey();
                        if (treeMap.get(key) == 1)
                            treeMap.pollFirstEntry();
                        else
                            treeMap.put(key, treeMap.get(key) - 1);
                    }
                }
            }
            // 테스트케이스의 답 기록
            sb.append(treeMap.isEmpty() ? "EMPTY" : (treeMap.lastKey() + " " + treeMap.firstKey())).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}