/*
 Author : Ruel
 Problem : Baekjoon 9426번 중앙값 측정
 Problem address : https://www.acmicpc.net/problem/9426
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9426_중앙값측정;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] fenwickTree;
    static final int MAX_TEMPERATURE = 65535;

    public static void main(String[] args) throws IOException {
        // 0부터 65535까지의 온도가 n개 주어진다.
        // 이 때 개수가 k인 모든 구간의 중앙값의 합은?
        // (1, 2, 6, 5, 4, 3)일 경우, (1, 2, 6) -> 2, (2, 6, 5) -> 5, (6, 5, 4) -> 5, (5, 4, 3) -> 4, 이 때 합은 16
        // 중앙값은 (k + 1) / 2번째 숫자를 뜻한다
        // 따라서 펜윅 트리로 각 온도를 표시한 뒤, 해당하는 온도가 들어올 때마다, 해당 펜윅트리 값을 +1, k 범위를 벗어날 때마다 -1를 해준다
        // 그리고 순서는 이분탐색을 통해 (k + 1) / 2보다 가장 큰 작은 순서를 찾은 후, 그 다음 숫자를 가져온다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        fenwickTree = new int[MAX_TEMPERATURE + 2];     // 0부터 65535까지의 숫자가 주어진다. 우리는 펜윅트리에서 0도 사용해야하므로, 2칸을 더 크게 할당하자.

        int medianOrder = (k + 1) / 2;
        Queue<Integer> nums = new LinkedList<>();       // queue를 통해 k개의 숫자를 관리한다.
        for (int i = 0; i < k; i++) {       // 첫 k개의 숫자는 그대로 넣어준다.
            int num = Integer.parseInt(br.readLine()) + 1;    // 0도가 있음을 유의하고 +1도씩 해주자
            nums.offer(num);        // 큐에 num을 넣어주고
            inputValue(num, true);      // 펜윅트리에 표시
        }
        long sum = getMedian(medianOrder);      // 첫 k개의 숫자 중 중앙값을 sum의 초기값으로 설정해준다.
        for (int i = 0; i < n - k; i++) {
            inputValue(nums.poll(), false);     // k개의 범위를 벗어나는 숫자를 펜윅트리에서 -1 해준다.
            int num = Integer.parseInt(br.readLine()) + 1;
            nums.offer(num);    // 새로운 숫자를 queue에 삽입.
            inputValue(num, true);      // 펜윅트리에 표시
            sum += getMedian(medianOrder);      // 중앙값을 구해 sum에 더해준다.
        }
        sum -= (n - k + 1);     // 우리가 더한 온도들은 모두 +1이 되어있으므로, (n - k + 1)를 해 원래 값을 보정해준다.
        System.out.println(sum);
    }

    static int getMedian(int order) {       // order번째 숫자를 구한다.
        int start = 1;
        int end = MAX_TEMPERATURE + 1;      // 범위는 1부터 65535 + 1까지.
        while (start < end) {
            int mid = (start + end) / 2;
            if (getOrder(mid) < order)      // 원하는 순서보다 더 작은 순서라면
                start = mid + 1;        // 해당 순서보단 더 큰 값이다.
            else        // 순서가 같거나 크다면, 더 작은 순서의 수일 수도 있지만, 해당 수일 수도 있다. end에 mid값을 넣어준다.
                end = mid;
        }
        // 최종적으로 start가 도달하는 곳이 order번째 수이다.
        return start;
    }

    static int getOrder(int n) {        // 수 n의 순서를 구한다.
        int sum = 0;
        while (n > 0) {
            sum += fenwickTree[n];
            n -= (n & -n);
        }
        // 펜윅트리 n의 값이 n의 순서이다.
        return sum;
    }

    static void inputValue(int n, boolean input) {
        // n값을 추가하거나 제거한다.
        while (n < fenwickTree.length) {
            // input이 true라면 +1,
            // false라면 -1
            fenwickTree[n] += input ? 1 : -1;
            n += (n & -n);
        }
    }
}