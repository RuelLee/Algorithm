/*
 Author : Ruel
 Problem : Baekjoon 12015번 가장 긴 증가하는 부분 수열 2
 Problem address : https://www.acmicpc.net/problem/12015
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12015_가장긴증가하는부분수열2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] orders;
    static int[] values;
    static int maxOrder = 0;

    public static void main(String[] args) throws IOException {
        // 최장 증가 부분 수열 찾기 문제
        // 수열을 처음부터 살펴보며, 이전에 등장했던 수들과 비교하여 어느 순서에 해당 수가 위치할 것인가를 정한다
        // 이전에 등장했던 수들 중 자신보다 작은 수 중 가장 큰 수를 찾는다.
        // 해당 수의 다음 위치에 현재 순서의 수를 위치시킨다. -> 검색할 때 이분 탐색 활용.
        // 그리고 해당 수의 순서를 저장해둔다
        // 최종적으로 모든 연산이 끝났을 때 각 수가 증가 수열에서 몇 번째에 위치할 수 있는지를 얻을 수있다
        // 여기서 최장 증가 수열의 길이를 원한다면, 위의 결과에서 가장 큰 값을 가져오면 되고
        // 최장 증가 수열 자체를 원한다면, 위 결과의 끝에서부터 역순으로 순서가 감소하는 형태로 수를 모으면 된다. (당연히 여러가지 경우가 생길 수 있다.)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        orders = new int[n];            // 각 수가 증가 수열에서 위치할 수 있는 최대 순서.
        values = new int[n];        // n번째 수 오는 수들 중 가장 작은 값을 저장해둔다.
        Arrays.fill(values, Integer.MAX_VALUE);
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());     // 이번 순서의 수
            int order = findOrder(num);     // 해당 수가 이전에 등장했던 수들 중 몇 번째로 작은지 세어본다.
            orders[i] = order;      // 그리고 해당 수의 순서에 기록하고
            values[order] = Math.min(values[order], num);       // order번째 수들 중 num이 가장 작았다면 num으로 순서의 수를 업데이트 해준다.
            maxOrder = Math.max(maxOrder, order + 1);       // 최장 증가 수열의 원소의 개수가 더 늘어났는지 확인.
        }
        // 최종적으로 얻어진 maxOrder가 최장 증가 수열의 길이.
        System.out.println(maxOrder);
    }

    static int findOrder(int n) {       // n이라는 수가 몇번째로 작은 수인지 이분 탐색으로 찾는다.
        int start = 0;      // 시작은 0부터
        int end = maxOrder;     // 끝은 현재까지의 최장 증가 수열의 길이만큼.
        while (start < end) {
            int mid = (start + end) / 2;
            if (values[mid] < n)
                start = mid + 1;
            else
                end = mid;
        }
        // 이분 탐색으로 얻은 값을 리턴
        return start;
    }
}