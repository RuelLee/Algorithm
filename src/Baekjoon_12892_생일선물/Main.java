/*
 Author : Ruel
 Problem : Baekjoon 12892번 생일 선물
 Problem address : https://www.acmicpc.net/problem/12892
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12892_생일선물;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 친구들에게 선물을 받는다.
        // 각 선물에 대한 가격과 만족도가 있다.
        // 선물을 받을 때, 어떤 친구가 준 선물과 가격 차이가 d 이상 나면 다른 선물을 한 친구가
        // 미안함을 느낄 수 있으므로 선물들을 일부 친구에게서만 받기로 했다.
        // 누구에게도 미안함을 느끼게 하지 않으면서 받는 만족도의 최대 합은?
        //
        // 두 포인터 문제
        // 선물들을 가격 오름차순으로 정렬한 뒤
        // 두포인터로 왼쪽부터 오른쪽까지 가격 차이가 d 미만은 구간을 지속적으로 만들어가며
        // 그 때의 만족도합을 계속 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 선물
        int n = Integer.parseInt(st.nextToken());
        // 가격 차이 d
        int d = Integer.parseInt(st.nextToken());
        
        // 선물들
        int[][] presents = new int[n][];
        for (int i = 0; i < presents.length; i++)
            presents[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 가격에 대해 오름차순 정렬
        Arrays.sort(presents, Comparator.comparingInt(o -> o[0]));

        // 두 포인터가 가르키는 만족도 합
        long satisfactionSum = 0;
        // 최대 값
        long max = 0;
        // 오른쪽 포인터
        int idx = 0;

        // 왼쪽 포인터
        // 는 하나씩 전진시킨다
        for (int[] present : presents) {
            // 오른쪽 포인터는 왼쪽 포인터가 가르키는 선물에서 가격차이가 d 미만인
            // 최대 지점을 가르킨다.
            // 그러면서 만족합을 더해나간다.
            while (idx < presents.length && presents[idx][0] - present[0] < d)
                satisfactionSum += presents[idx++][1];
            // 현재 만족도 합이 최대값인지 확인
            max = Math.max(max, satisfactionSum);
            // 왼쪽 포인터가 오른쪽으로 넘어가므로
            // 만족도합에서 현재 왼쪽 포인터가 가르키는 선물의 만족도 합을 제외시킨다.
            satisfactionSum -= present[1];
        }
        
        // 얻을 수 있는 만족도 합의 최대값 출력
        System.out.println(max);
    }
}