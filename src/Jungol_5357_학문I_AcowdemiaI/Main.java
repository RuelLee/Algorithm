/*
 Author : Ruel
 Problem : Jungol 5357번 학문 I (Acowdemia I)
 Problem address : https://jungol.co.kr/problem/5357
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5357_학문I_AcowdemiaI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] papers;
    static int l;

    public static void main(String[] args) throws IOException {
        // n개의 논문이 주어진다.
        // 학업 성취도를 h-지수로 표현할 수 있으며 h-지수는
        // h개의 논문이 인용된 횟수 h번 이상일 경우 h라고 측정할 수 있다.
        // l개의 논문을 추가로 한번씩 인용할 수 있다고 할 때
        // 주어진 논문들의 h-지수는 얼마인가?
        //
        // 이분탐색 문제
        // 이분 탐색을 통해 h-지수를 찾는다.
        // 해당 h-지수가 성립하는지는 살펴보기 위해선, 논문들을 인용 횟수에 따라 오름차순 정렬한 뒤
        // 뒤에서 h번째 논문이 최소 h-1번 이상 인용되었는가, 그리고 해당 인덱스로부터 l번째 뒤의 논문의 인용횟수가 h이상인가
        // 를 따져보면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 논문
        int n = Integer.parseInt(st.nextToken());
        // 최대 l개의 논문을 한번씩 추가 인용할 수 있다.
        l = Integer.parseInt(st.nextToken());

        // 논문
        papers = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            papers[i] = Integer.parseInt(st.nextToken());
        // 오름차순 정렬
        Arrays.sort(papers);

        // 이분 탐색
        int start = 1;
        int end = n;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (possible(mid))
                start = mid + 1;
            else
                end = mid - 1;
        }
        // 답 출력
        System.out.println(end);

    }

    // h-지수가 n이 성립하는지 확인
    static boolean possible(int n) {
        // 뒤에서 n번째 논문
        int minIdx = papers.length - n;
        // minIdx의 인용 횟수가 n-1번 이상 되어야하고
        // minIdx + l이 전체 논문의 수 이상이거나
        // 혹은 해당 순서의 논문이 n번 이상 인용되었어야한다.
        if (papers[minIdx] >= n - 1 &&
                (minIdx + l >= papers.length || papers[minIdx + l] >= n))
            return true;
        // 그렇지 않다면 false 반환
        return false;
    }
}