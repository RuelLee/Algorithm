/*
 Author : Ruel
 Problem : Baekjoon 3066번 브리징 시그널
 Problem address : https://www.acmicpc.net/problem/3066
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3066_브리징시그널;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 설계자들의 잘못으로 두 개의 블록의 포트를 연결하는 칩 위의 시그널들이 서로 교차하게 만들어졌다.
        // 브리징은 시그널들이 서로 교차하는 경우, 하나의 시그널이 다른 시그널과 교차하지 않도록 수직으로 띄우는 작업이다.
        // 가능한 적은 수의 시그널만 브리징하려할 때, 서로 교차하지 않고 연결될 수 있는 최대 시그널의 개수는?
        //
        // 최장 증가 부분 수열 문제
        // 이분 탐색을 통해, 순서를 고려하며 만들 수 있는 증가하는 부분 수열을 찾고 그 길이를 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int n = Integer.parseInt(br.readLine());
            // 각 포티에 연결되야하는 반대편 포트.
            int[] ports = new int[n];
            for (int i = 0; i < ports.length; i++)
                ports[i] = Integer.parseInt(br.readLine());
            
            // 순서에 따른 최소 포트 번호. 큰 값으로 초기화
            int[] orders = new int[n];
            Arrays.fill(orders, Integer.MAX_VALUE);
            // 수열의 길이.
            int maxOrder = 0;
            for (int port : ports) {
                // 0부터, maxOrder + 1까지의 범위에서
                int start = 0;
                int end = maxOrder + 1;
                // port가 기록되어있는 값보다 같거나 작은 위치를 이분탐색을 통해 찾는다.
                while (start < end) {
                    int mid = (start + end) / 2;
                    if (orders[mid] >= port)
                        end = mid;
                    else
                        start = mid + 1;
                }
                // 해당 위치의 순서에서 port가 더 작은 값인지 확인한다.
                orders[end] = Math.min(orders[end], port);
                // 최대 길이를 갱신했는지 확인한다.
                maxOrder = Math.max(maxOrder, end);
            }
            // 전체 port를 살펴본 후, 찾은 최대 길이를 StringBuilder에 기록한다.
            sb.append(maxOrder + 1).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}