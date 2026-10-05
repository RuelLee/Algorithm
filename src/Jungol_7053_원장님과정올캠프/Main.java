/*
 Author : Ruel
 Problem : Jungol 7053번 원장님과 정올 캠프
 Problem address : https://jungol.co.kr/problem/7053
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_7053_원장님과정올캠프;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 학생들이 원하는 온도가 주어진다.
        // 각 학생들은 옷을 원하는 만큼 더 입어, 원하는 온도를 t도씩 낮출 수 있다.
        // 현재 온도와 원하는 온도의 차이 절대값을 불편도라고 하자.
        // 최대 불편도를 최소화하고자할 때, 그 값은?
        //
        // 정렬 문제
        // 원하는 만큼 옷을 껴입을 수 있으므로, 최대 불편도는 t를 넘을 수 없다.
        // 따라서, 모든 학생의 원하는 온도를 t로 나눈 나머지로 정리한다.
        // 그리고 정렬하여, 원하는 온도의 범위에 중간에 온도를 맞추면 된다.
        // 옷을 껴입는 걸로 인해 t 주기로 순환하기 때문에
        //원하는 최소 온도를 오름차순으로 + t를 해 범위가 줄어드는지 확인해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 학생, 옷을 입을 때마다 줄어드는 온도 t
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        // 각 학생이 원하는 온도
        // t로 나눈 나머지로 정리한다.
        int[] students = new int[n];
        for (int i = 0; i < n; i++)
            students[i] = Integer.parseInt(st.nextToken()) % t;
        // 정렬
        Arrays.sort(students);

        // 적정 온도들을 오름차순으로 데크로 관리
        Deque<Integer> deque = new LinkedList<>();
        deque.offerLast(students[0]);
        for (int i = 1; i < n; i++) {
            if (deque.peekLast() != students[i])
                deque.offerLast(students[i]);
        }

        // 데크의 크기.
        int size = deque.size();
        int ans = Integer.MAX_VALUE;
        // 크기만큼 반복
        for (int i = 0; i < size; i++) {
            // 원하는 적정온도의 최소, 최대 온도의 가운데에 온도를 맞추면 최소 불편도가 구해진다.
            ans = Math.min(ans, (deque.peekLast() - deque.peekFirst() + 1) / 2);
            // 원하는 만큼 옷을 껴입음으로써 불편도가 t로 순환한할 수 있다.
            // t를 더해 최소 적정 온도를 최대 적정 온도로 바꿔 범위를 계산.
            deque.offerLast(deque.pollFirst() + t);
        }
        // 답 출력
        System.out.println(ans);
    }
}