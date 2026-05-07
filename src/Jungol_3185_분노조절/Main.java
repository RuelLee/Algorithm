/*
 Author : Ruel
 Problem : Jungol 3185번 분노 조절
 Problem address : https://jungol.co.kr/problem/3185
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3185_분노조절;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 빵이 주어진다. 각각의 빵은 맛의 값이 주어진다.
        // k명의 학생에 대해 하나씩 빵을 나눠준다.
        // 이 때 불만도는 모든 빵들의 서로 간 맛 차이의 합이다.
        // 불만도를 최소화하고자 할 때, 그 값은?
        //
        // 정렬, 슬라이딩 윈도우, 누적합 문제
        // 한마디로 빵들을 최대한 맛 차이가 덜 나는 k개의 구간을 구하라는 말과 같다.
        // 이 때 식에 따라 그 기준이 바뀌므로
        // 1 ~ k, 2 ~ k+1, ... , n - k +1 ~ n 까지의 구간에 대해 직접 계산한다.
        // 단 불만족도를 1 ~ k에 대해 구하고 이후는 변동량만 가지고 계산한다.
        // 빵들을 입력 받은 후, 정렬하고, 누적합 처리 한다.
        // 각 빵들에 대해 차이합을 구할 때, 정렬이 되어있다면, 자신보다 값이 작은 빵의 개수만큼 자신이 +되고, 자신보다 값이 큰 빵의 개수 만큼 -가 된다.
        // 이를 정렬된 idx를 통해 계산할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 빵, k명의 학생
        int n = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());
        long[] breads = new long[n + 1];
        for (int i = 0; i < n; i++)
            breads[i] = Long.parseLong(br.readLine());
        // 정렬
        Arrays.sort(breads);

        // 누적합
        long[] psums = new long[n + 1];
        for (int i = 1; i < psums.length; i++)
            psums[i] = psums[i - 1] + breads[i];

        // 1 ~ k까지의 구간은 직접 계산.
        long sum = 0;
        for (int i = 1; i <= k; i++)
            sum += (i - 1 - (k - i)) * breads[i];

        // 최소 불만도 값
        long min = sum;
        for (int i = 2; i + k - 1 <= n; i++) {
            // 범위에서 제외되는 빵
            // i-1번 빵이고, 이 빵에 대해 (k - 1)만큼 -값이 되어있다 이를 복구.
            sum += breads[i - 1] * (k - 1);
            // 범위에서 그대로이지만 순서가 바뀐 빵들.
            // 자신보다 작은 빵이 하나 줄고, 큰 빵이 하나 늘어난다.
            // 따라서 누적합을 통해 i ~ i + k -2까지의 범위에 대해 합을 구하고 2배를 해 빼준다.
            sum -= (psums[i + k - 2] - psums[i - 1]) * 2;
            // 추가되는 빵.
            // i + k - 1번 빵이 추가되고 가장 큰 값이므로 k-1배로 더해진다.
            sum += breads[i + k - 1] * (k - 1);
            // 최소 불만값 비교
            min = Math.min(min, sum);
        }
        // 답 출력
        System.out.println(min);
    }
}