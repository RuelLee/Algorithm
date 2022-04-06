/*
 Author : Ruel
 Problem : Baekjoon 1572번 중앙값
 Problem address : https://www.acmicpc.net/problem/1572
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1572_중앙값;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // n개의 수열이 주어진다
        // 그 중 크기 k인 연속한 부분 수열들의 중앙값(정렬된 상태에서 (k+1)/2번째 수)의 합을 구하라
        // 수는 최대 65536보다 작거나 같은 음이 아닌 정수이다.
        // ex) {1, 2, 3, 4, 5}가 주어졌을 때 k가 3라면, {1, 2, 3} = 3, {2, 3, 4} = 3, {3, 4, 5} = 4, 답은 3 + 4 + 5 = 12
        //
        // 세그먼트 트리와 이분 탐색을 활용하는 문제.
        // 단순히 수가 들어있음을 표시할 거라, 구현이 간단한 펜윅 트리로 풀자
        // 펜윅 트리에 해당 수가 들어있음을 표시하고, (k+1)/2번째 수를 이분탐색으로 찾는다
        // 그리고 펜윅 트리에 넣는 수의 개수는 k개로 제한하며 처음 주어지는 수부터 마지막 주어지는 수까지 살펴본다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int median = (k + 1) / 2;       // 중앙값의 순서.
        fenwickTree = new int[65536 + 2];
        // 펜윅 트리에서 빼줄 수를 큐로 관리하자.
        Queue<Integer> queue = new LinkedList<>();
        // k-1번째 수까진 펜윅 트리에 그냥 집어넣는다.
        for (int i = 0; i < k - 1; i++) {
            // 수가 0부터 65536까지 주어지는데, 펜윅 트리에서는 0번 인덱스를 끝나는 기준점으로 사용해야한다
            // 따라서 수를 하나씩 뒤로 밀어 생각하자.
            int num = Integer.parseInt(br.readLine()) + 1;
            queue.offer(num);
            inputValue(num, true);
        }

        long sum = 0;       // 중앙값의 합
        for (int i = 0; i < n - k + 1; i++) {
            // k번째 수 
            int num = Integer.parseInt(br.readLine()) + 1;
            // 큐에 표시.
            queue.offer(num);
            // 해당 값 펜윅 트리에 삽입
            inputValue(num, true);
            // 펜윅 트리에서 중앙값 순서에 해당하는 수를 찾아 sum에 더해주자
            sum += getMedian(median) - 1;
            // 현재 펜윅 트리에는 k개의 수가 들어있다
            // 가장 먼저 들어왔던 수를 펜윅 트리에서 빼줘야한다
            // 큐의 선입선출로 수들을 관리했으므로, 큐에서 꺼낸 수를 펜윅트리에서 제거해주면 된다.
            inputValue(queue.poll(), false);
        }
        System.out.println(sum);
    }

    static int getMedian(int median) {      // 중앙값을 찾는 메소드
        int left = 1;
        int right = 65537;
        // 최대 65537번 idx까지(값을 하나씩 뒤로 밀었으므로 사실은 65536) 사용한다.
        // 이분 탐색을 이용하여 중앙값을 찾는다.
        while (left < right) {
            int mid = (left + right) / 2;
            if (getOrderN(mid) < median)        // 찾아진 순서가 median보다 작다면
                left = mid + 1;     // left를 mid + 1로 조정
            else        // median보다 크거나 같다면 
                right = mid;        // right 를 mid로 조정
        }
        // 찾아진 중앙값
        return left;
    }

    static int getOrderN(int n) {           // 숫자 n보다 작은 수의 개수를 센다.
        int sum = 0;
        while (n > 0) {
            sum += fenwickTree[n];
            n -= (n & -n);
        }
        return sum;
    }

    // 값을 펜윅 트리에 넣거나(input == true), 빼준다(input == false)
    static void inputValue(int value, boolean input) {
        while (value < fenwickTree.length) {
            fenwickTree[value] += input ? 1 : -1;
            value += (value & -value);
        }
    }
}