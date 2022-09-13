/*
 Author : Ruel
 Problem : Baekjoon 2565번 전깃줄
 Problem address : https://www.acmicpc.net/problem/2565
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2565_전깃줄;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class Cable {
    int poleA;
    int poleB;

    public Cable(int poleA, int poleB) {
        this.poleA = poleA;
        this.poleB = poleB;
    }
}

public class Main {
    static int[] minvalueByOrder;

    public static void main(String[] args) throws IOException {
        // 전봇대 A, B가 있다.
        // 서로 연결된 전깃줄의 위치가 주어질 때, 남은 전깃줄을 최대한 많이 남긴 채,
        // 교차하는 전깃줄들을 제거하고 싶다.
        // 이 때 제거해야하는 전깃줄들의 수는?
        //
        // 최장 증가 수열 문제
        // A의 위치 대로 정렬한 뒤, B의 위치가 증가하게 만들 수 있는 최대 개수를 찾아내고
        // 전체 전깃줄에서 해당 전깃줄들을 제외한 나머지 전깃줄들을 잘라내주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        // 주어지는 입력
        // 전깃줄들의 위치.
        Cable[] cables = new Cable[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            cables[i] = new Cable(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        // A 전봇대의 위치에 대해 오름차순 정렬한다.
        Arrays.sort(cables, Comparator.comparing(c -> c.poleA));

        // 각 순서에 해당하는 가장 작은 B 전봇대의 위치를 저장한다.
        minvalueByOrder = new int[n];
        // 초기화.
        Arrays.fill(minvalueByOrder, Integer.MAX_VALUE);
        int maxOrder = 0;
        // i번째 케이블에 해당하는 B 전봇대의 위치로 최장 증가 수열을 찾는다.
        for (int i = 0; i < cables.length; i++) {
            // poleB를 포함하는 최장 증가 수열에서의 poleB의 순서를 찾는다.
            int order = findOrder(cables[i].poleB, maxOrder);
            // 해당 순서에 poleB 값이 최소인지 확인하고,
            minvalueByOrder[order] = Math.min(minvalueByOrder[order], cables[i].poleB);
            // 최장 증가 수열의 길이가 갱신되었는지 확인한다.
            maxOrder = Math.max(maxOrder, order + 1);
        }

        // 찾아진 최장 증가 수열의 최대 길이를 전체 케이블의 수에서 빼준다.
        // 나온 값은 제거해야하는 전깃줄의 수.
        System.out.println(n - maxOrder);
    }

    // 이분 탐색을 통해 value에 해당하는 최장 증가 수열 내의 순서를 찾는다.
    static int findOrder(int value, int maxOrder) {
        // 처음부터
        int start = 0;
        // 여태까지 찾은 수열의 길이 + 1의 위치까지(= 이번에 길이가 늘어날 수 있으므로)
        int end = maxOrder;
        while (start < end) {
            int mid = (start + end) / 2;
            // 값이 같거나 작다면 end = mid를 해 최대값의 범위를 줄이고
            if (value <= minvalueByOrder[mid])
                end = mid;
            // 더 크다면 start = mid + 1을 해 최소값의 범위를 줄인다.
            else
                start = mid + 1;
        }
        // 최종적으로 start == end인 값이 찾아지는데
        // 이 값이 value 값이 최장 증가 수열에서 해당하는 순서이다.
        // 해당 값 리턴.
        return start;
    }
}