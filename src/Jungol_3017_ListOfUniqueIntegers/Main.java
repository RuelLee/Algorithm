/*
 Author : Ruel
 Problem : Jungol 3017번 List of Unique Integers
 Problem address : https://jungol.co.kr/problem/3017
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3017_ListOfUniqueIntegers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 연속한 1개 이상의 수를 골랐을 때, 동일한 수가 여러번 등장하지 않는 경우의 수를 구하라
        //
        // 두 포인터 문제
        // 두 포인터를 통해 구간 내의 동일한 원소가 포함되지않게끔 관리하며 경우의 수를 센다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 수열
        int[] array = new int[n];
        for (int i = 0; i < n; i++)
            array[i] = Integer.parseInt(st.nextToken());

        // 범위 내 포함된 수
        boolean[] inRange = new boolean[100001];
        // 오른쪽 포인터
        int right = 0;
        inRange[array[0]] = true;
        long ans = 0;
        // 왼쪽 포인터를 하나씩 증가시켜가며
        for (int left = 0; left < n; left++) {
            // 포인터가 추월된 경우, left에서 다시 시작.
            if (right < left)
                inRange[array[right = left]] = true;
            // 그 외의 경우, right + 1이 새로운 수인 경우, right를 증가시켜나가며 범위를 확장한다.
            while (right + 1 < n && !inRange[array[right + 1]])
                inRange[array[++right]] = true;
            // 범위의 너비 = 경우의 수 합산
            ans += (right - left + 1);

            // left+1을 위해 left를 이제 제외
            inRange[array[left]] = false;
        }
        // 답 출력
        System.out.println(ans);
    }
}