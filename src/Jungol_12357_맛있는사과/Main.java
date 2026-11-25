/*
 Author : Ruel
 Problem : Jungol 12357번 맛있는 사과
 Problem address : https://jungol.co.kr/problem/12357
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_12357_맛있는사과;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 사과에 대해 맛과 크기가 주어진다.
        // q개의 쿼리가 들어올 때
        // 맛이 q이상인 사과들 중, 최대 크기인 사과의 개수를 출력하라
        //
        // 오프라인 쿼리 문제
        // q가 큰 값일수록 적은 범위의 사과들에서 작은 값으로 가며 점점 범위를 확장해가는 문제이다.
        // 따라서 사과를 입력 받아 -> 맛 별로 정렬
        // 쿼리들을 입력받아 크기 순으로 정렬
        // 그 후, 쿼리를 크기의 역순으로 처리해가며, 쿼리보다 크거나 작은 사과들에서 가장 큰 사과와 그 개수를 세어가면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 사과, q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        // 사과 입력
        int[][] appples = new int[n][2];
        for (int i = 0; i < 2; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                appples[j][i] = Integer.parseInt(st.nextToken());
        }
        // 맛에 대해 정렬
        Arrays.sort(appples, Comparator.comparingInt(o -> o[0]));

        // 쿼리
        int[][] quries = new int[q][3];
        for (int i = 0; i < quries.length; i++) {
            // 원래 문제 번호
            quries[i][0] = i;
            quries[i][1] = Integer.parseInt(br.readLine());
        }
        // 크기 별로 정렬
        Arrays.sort(quries, Comparator.comparingInt(o -> o[1]));

        // 현재까지의 최대 사과 크기와 개수
        int maxSize = 0;
        int cnt = 0;
        // 아직 포함 안된 사과의 idx
        int idx = n - 1;
        // 쿼리를 크기의 역순으로 처리
        for (int i = q - 1; i >= 0; i--) {
            // 쿼리가 원하는 값보다 같거나 큰 값을 갖는 사과들의 최대 크기와 개수를 계산한다.
            while (idx >= 0 && appples[idx][0] >= quries[i][1]) {
                if (appples[idx][1] > maxSize) {
                    maxSize = appples[idx][1];
                    cnt = 1;
                } else if (appples[idx][1] == maxSize)
                    cnt++;
                idx--;
            }
            // 답 기록
            quries[i][2] = cnt;
        }

        // 쿼리들을 원래 순서대로 다시 정렬
        Arrays.sort(quries, Comparator.comparingInt(o -> o[0]));
        // 답 기록
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < quries.length; i++)
            sb.append(quries[i][2]).append("\n");
        // 출력
        System.out.print(sb);
    }
}