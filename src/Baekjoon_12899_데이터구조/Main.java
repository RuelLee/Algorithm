/*
 Author : Ruel
 Problem : Baekjoon 12899번 데이터 구조
 Problem address : https://www.acmicpc.net/problem/12899
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12899_데이터구조;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] fenwickTree = new int[2_000_001];

    public static void main(String[] args) throws IOException {
        // 데이터베이스 s에 대해 다음의 쿼리를 처리한다
        // 1 x : 자연수 x를 추가한다
        // 2 x : x번째로 작은 수를 출력하고, 해당 수를 삭제한다.
        // n개의 쿼리 (1 ≤ N ≤ 2,000,000)와 해당하는 x(1 ≤ X ≤ 2,000,000)가 주어질 때
        // 쿼리에 대한 답을 출력하라.
        //
        // 세그먼트 트리 문제
        // 세그먼트 트리를 통해 현재 입력된 수들의 개수(누적합)를 빠르게 세고 응답한다
        // 이 때 펜윅 트리 + 이분 탐색을 통해 답을 구할 수도 있고
        // 세그먼트 트리의 구조를 이용해서 세그먼트 트리만을 이용해서 풀 수도 있다.
        // 일단은 펜윅 트리 + 이분 탐색을 통해 풀어보자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            
            // 데이터베이스에 수가 들어오는 경우
            // x 값이 하나 늘었다고 표시.
            if (t == 1)
                modifyValue(x, 1);
            else {
                // x번째 수를 세야하는 경우
                // 이분 탐색을 통해 x번째 수를 찾는다.
                int start = 1;
                int end = 2_000_000;
                while (start < end) {
                    int mid = (start + end) / 2;
                    if (x <= countNums(mid))
                        end = mid;
                    else
                        start = mid + 1;
                }
                // 찾아진 x번째 수는 start
                // 해당 수를 StringBuilder에 기록하고
                sb.append(start).append("\n");
                // start 수를 하나 제거한다.
                modifyValue(start, -1);
            }
        }
        // 전체 답을 출력.
        System.out.print(sb);
    }

    // 펜윅 트리를 이용해서 loc에 위치한 수를 value만큼 증감시킨다.
    static void modifyValue(int loc, int value) {
        while (loc < fenwickTree.length) {
            fenwickTree[loc] += value;
            loc += (loc & -loc);
        }
    }

    // loc이하의 수가 펜윅 트리에 총 몇 개 있는지 센다.
    static int countNums(int loc) {
        int sum = 0;
        while (loc > 0) {
            sum += fenwickTree[loc];
            loc -= (loc & -loc);
        }
        return sum;
    }
}