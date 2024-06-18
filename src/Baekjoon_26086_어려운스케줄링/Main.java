/*
 Author : Ruel
 Problem : Baekjoon 26086번 어려운 스케줄링
 Problem address : https://www.acmicpc.net/problem/26086
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26086_어려운스케줄링;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n 범위에서 고유 번호를 할당받은 업무가 주어진다.
        // 스케줄러 LIFO 특징을 갖고 있다.
        // 명령은 3가지 종류가 있으며
        // 0 p : 고유 번호 p 업무가 제일 앞에 추가된다.
        // 1 : 고유 번호 기준으로 오름차순 정렬되어, 작은 번호가 먼저 처리된다.
        // 2 : 처리 순서를 뒤집는다.
        // 모든 명령들을 처리한 후, 업무를 처리할 때
        // k번째 처리한 업무는?
        //
        // 덱 문제
        // 상황에 따라 가장 앞 혹은 가장 뒤에 새로운 작업이 추가될 수 있다.
        // 따라서 덱을 통해 업무를 추가하며, 모든 명령을 처리한다.
        // 1 쿼리의 경우, 항상 처리할 필요는 없으며, 가장 마지막에 들어온 1만 처리하여 정렬해도 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 범위 n
        int n = Integer.parseInt(st.nextToken());
        // 명령의 개수 q
        int q = Integer.parseInt(st.nextToken());
        // 모든 명령을 처리한 후 구할 k번째 작업
        int k = Integer.parseInt(st.nextToken());
        
        // 명령들
        int[][] queries = new int[q][];
        int lastOneQuery = -1;
        for (int i = 0; i < q; i++) {
            queries[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            // 마지막 1 명령을 찾는다.
            if (queries[i][0] == 1)
                lastOneQuery = i;
        }

        // 덱 문제이지만 정렬이 안되므로
        // 그보다 상위 구조인 연결리스트로 덱의 기능과 정렬을 모두 사용한다.
        LinkedList<Integer> linkedList = new LinkedList<>();
        // 처음 순서는 앞에서부터 꺼내는 형태
        boolean firstOrder = true;
        for (int i = 0; i < q; i++) {
            switch (queries[i][0]) {
                // 0 명령인 경우
                case 0 -> {
                    // 앞이 우선인 경우, 앞에 추가
                    if (firstOrder)
                        linkedList.offerFirst(queries[i][1]);
                    else        // 뒤가 우선인 경우 뒤에 추가
                        linkedList.offerLast(queries[i][1]);
                }
                case 1 -> {     
                    // 1 명령은 마지막 명령만 처리
                    // 오름차순으로 정렬되므로 순서도 앞에서부터 처리로 변경
                    if (i == lastOneQuery) {
                        Collections.sort(linkedList);
                        firstOrder = true;
                    }
                }
                // 2 명령은 순서를 뒤집는다.
                case 2 -> firstOrder = !firstOrder;
            }
        }
        // 순서에 따라 k번째 작업을 출력
        System.out.println(firstOrder ? linkedList.get(k - 1) : linkedList.get(linkedList.size() - k));
    }
}