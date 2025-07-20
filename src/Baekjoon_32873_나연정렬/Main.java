/*
 Author : Ruel
 Problem : Baekjoon 32873번 나연 정렬
 Problem address : https://www.acmicpc.net/problem/32873
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32873_나연정렬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 원소를 갖는 수열 a가 주어진다.
        // 1. 원하는 만큼의 스택을 선언한다.
        // 2. 순서대로 각 원소를 원하는 스택에 담는다.
        // 3. 배열 b를 선언한다.
        // 4. 원하는 스택에서 원소를 꺼내, B의 맨 뒤에 추가한다.
        // 배열 b가 오름차순으로 정렬되기 위해선 최소 몇 개의 스택이 필요한가?
        //
        // 이분 탐색
        // 스택의 최상단 값만 갖고 있다 생각하고 비교한다.
        // 스택의 최상단 값 중 현재 순서의 수보다 크거나 같지만 가장 작은 수를 찾아 그 스택에 담는다.
        // 그러한 스택이 없다면 새로운 스택을 추가한다.
        // 위 과정을 반복.
        // 최종적으로 모든 수를 담았을 때, 스택의 개수가 답.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = Integer.parseInt(st.nextToken());
        
        // 각 스택의 최상단 값
        int[] tops = new int[n];
        // 필요한 스택의 수
        int size = 0;
        for (int i = 0; i < a.length; i++) {
            // 순서대로 모든 수를 살펴보며
            // 0 ~ size번까지의 스택 중 현재 수보다 같거나 크지만 가장 작은 수를 찾는다.
            int start = 0;
            int end = size;
            while (start < end) {
                int mid = (start + end) / 2;
                if (a[i] > tops[mid])
                    start = mid + 1;
                else
                    end = mid;
            }
            // 해당 스택의 최상단에 현재 수를 담는다.
            tops[start] = a[i];
            // 혹시 새로운 스택을 추가했는지 size와 현재 담은 스택의 위치를 비교한다.
            size = Math.max(size, start + 1);
        }
        // 추가한 스택의 총 개수 출력
        System.out.println(size);
    }
}