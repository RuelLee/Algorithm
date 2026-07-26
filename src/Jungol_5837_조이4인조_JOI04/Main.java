/*
 Author : Ruel
 Problem : Jungol 5837번 조이 4인조 (JOI04)
 Problem address : https://jungol.co.kr/problem/5837
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5837_조이4인조_JOI04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 4개의 반에 각각 n명의 학생과 각자의 키가 주어진다.
        // 각 반에서 대표를 뽑아 댄스를 추는데, 서로 간의 키를 최대한 차이가 적게끔 하고자한다.
        // 뽑을 수 있는 대표들 중 최대키와 최소키의 차이를 최소화했을 때, 그 값은?
        //
        // 두 포인터 문제
        // 모든 반의 키를 오름차순으로 정렬한 뒤
        // 각각의 포인터를 두고, 모든 반에서의 최소 키와 최대 키를 찾은 후
        // 최소 키를 가르키는 포인터를 하나씩 증가시켜가며 최대 키와 최소 키의 차이를 모두 계산해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 각 반의 인원 n
        int n = Integer.parseInt(br.readLine());
        // 모든 학생들의 키
        int[][] arrays = new int[4][n];
        StringTokenizer st;
        for (int i = 0; i < 4; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < arrays[i].length; j++)
                arrays[i][j] = Integer.parseInt(st.nextToken());
            // 정렬
            Arrays.sort(arrays[i]);
        }

        // 각 반의 대표를 가르키는 포인터
        int[] pointers = new int[4];
        // 최소 키의 반
        int min = 0;
        // 최대 키의 반
        int max = 0;
        for (int i = 0; i < 4; i++) {
            if (arrays[min][pointers[min]] > arrays[i][pointers[i]])
                min = i;
            else if (arrays[max][pointers[max]] < arrays[i][pointers[i]])
                max = i;
        }
        // 모두 제일 작은 키를 대표를 선정했을 때의 키 차이
        int ans = arrays[max][pointers[max]] - arrays[min][pointers[min]];

        while (true) {
            // 가장 작은 키를 가르키는 포인터를 하나 증가
            // 만약 그게 n 범위를 지나친다면 종료
            if (++pointers[min] >= n)
                break;

            // 포인터를 둘러보며 최소 키와 최대 키를 갱신
            for (int i = 0; i < 4; i++) {
                if (arrays[min][pointers[min]] > arrays[i][pointers[i]])
                    min = i;
                else if (arrays[max][pointers[max]] < arrays[i][pointers[i]])
                    max = i;
            }
            // 키 차이 계산
            ans = Math.min(ans, arrays[max][pointers[max]] - arrays[min][pointers[min]]);
        }
        // 답 출력
        System.out.println(ans);
    }
}