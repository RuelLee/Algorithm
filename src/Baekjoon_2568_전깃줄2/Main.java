/*
 Author : Ruel
 Problem : Baekjoon 2568번 전깃줄 - 2
 Problem address : https://www.acmicpc.net/problem/2568
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2568_전깃줄2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

public class Main {
    static int[] minValueEachOrder;

    public static void main(String[] args) throws IOException {
        // A 전봇대와 B전봇대에 전선이 연결되어있다
        // 많은 전선이 연결되어있다보니, 서로 교차하는 전선들이 많아졌다
        // 가장 적은 수의 전선을 제거하여 교차하는 전선이 없게 만들고 싶다
        // 이 때 제거해야하는 전선의 수와 해당하는 전선들의 A지점 값을 출력하라
        // 최장 증가 부분 수열의 응용 문제다
        // A 전봇대에 연결된 위치에 따라 정렬해준 뒤, B 전봇대에 연결된 위치를 살펴본다
        // 순차적으로 살펴보며 B 전봇대에 연결된 위치의 값이 증가한다면 교차하지 않는 경우다
        // 하지만 이전보다 더 작은 값이 나왔다면 교차하는 경우이다
        // A 지점의 오름차순으로 B값을 살펴보며 최장 증가 수열을 구하고
        // 그 때의 포함되지 않는 전선들을 출력해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] cables = new int[n][2];     // 각 케이블의 정보를 저장

        for (int i = 0; i < n; i++)
            cables[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // A전봇대에 연결된 지점을 오름차순으로 정렬한다.
        Arrays.sort(cables, Comparator.comparingInt(o -> o[0]));

        // 각 전선이 위치할 수 있는 가장 마지막 순서를 저장한다.
        int[] order = new int[n];
        // 최장 증가 부분 수열의 길이를 저장한다.
        int maxLength = 0;
        // 각 순서별 최소값을 저장해준다.
        // 그래야 들어오는 수가 최대 몇번째에 위치할 수 있는지 알 수 있다.
        minValueEachOrder = new int[n + 1];
        Arrays.fill(minValueEachOrder, Integer.MAX_VALUE);
        // 최대값으로 초기해둔다.
        for (int i = 0; i < cables.length; i++) {
            // i번째 케이블이 위치할 수 있는 가장 늦은 순서를 찾아본다.
            order[i] = findOrder(cables[i][1]);
            // 최대 개수가 갱신되었는지 확인.
            maxLength = Math.max(maxLength, order[i]);
            // order[i]번째 수들 중 최소인지 확인하고 그렇다면
            // minValueEachOrder에 반영해준다.
            minValueEachOrder[order[i]] = Math.min(minValueEachOrder[order[i]], cables[i][1]);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(n - maxLength).append("\n");      // 끊어내야하는 전선의 수
        Stack<Integer> stack = new Stack<>();       // 뒤에서부터 살펴봐야하므로 스택으로 담자
        for (int i = order.length - 1; i >= 0; i--) {       // 뒤의 순서부터 살펴보며
            if (order[i] == maxLength)      // 최대길이에 해당한다면 최장 증가 부분 수열에 포함된 전선. maxLength를 하나 줄여주자.
                maxLength--;
            else        // 그렇지 않다면 끊어내야하는 전선. 해당 전선의 A 지점 값을 스택에 넣어두자.
                stack.push(cables[i][0]);
        }

        // 스택에 값을 모두 꺼내면 해당 값들이 오름차순으로 정렬된 끊어내야할 전선들의 A 지점 값.
        while (!stack.isEmpty())
            sb.append(stack.pop()).append("\n");

        System.out.println(sb);
    }

    static int findOrder(int n) {       // 최대 몇번째 순서에 위치할 수 있는지 찾아주는 메소드.
        int start = 1;      // 시작은 1번부터
        int end = minValueEachOrder.length - 1;     // 배열의 길이만큼.
        while (start < end) {       // 이분 탐색으로 찾아준다.
            int mid = (start + end) / 2;
            if (minValueEachOrder[mid] < n)     // mid값이 n보다 작은 값이라면
                start = mid + 1;        // start에 mid + 1을 넣어 뒤의 범위를 살펴본다.
            else        // 같거나 큰 값이라면 mid값보다 같거나 작은 순서이다.
                end = mid;
        }
        // 최종적으로 구해지는 start가 n이 위치할 순서.
        return start;
    }
}