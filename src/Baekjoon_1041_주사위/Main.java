/*
 Author : Ruel
 Problem : Baekjoon 1041번 주사위
 Problem address : https://www.acmicpc.net/problem/1041
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1041_주사위;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n * n개의 주사위가 주어진다.
        // 주사위 전개도가
        //   D
        // E A B F
        //   C
        // 와 같이 주어지고 A부터 F까지 순서대로 각 수가 주어진다.
        // 이를 통해 n * n * n 크기의 정육면체를 만들고자 한다.
        // 이 때 만드는 정육면체의 아랫면을 제외한 보이는 5면의 수의 합을 최소화하고자 한다.
        // 그 값은?
        //
        // 그리디 문제
        // 주사위의 전개도가 주어지므로
        // 보이는 면의 개수에 따라 각 주사위의 수에 따른 최소합을 구해둔다.
        // 전개도를 잘 살펴보면 순서에 따라 A = 0, ... , F = 5라고 할 때
        // 순서의 합이 5가 되는 경우가 서로 마주보는 경우이다. 이를 활용한다.
        // 그리고 n에 따라 이를 통해 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n * n 개의 주사위
        int n = Integer.parseInt(br.readLine());
        
        // 주사위의 눈금
        int[] dice = new int[6];
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주사위의 수 전체 합
        int totalSum = 0;
        for (int i = 0; i < dice.length; i++)
            totalSum += dice[i] = Integer.parseInt(st.nextToken());
        
        // 보이는 면에 따른 주사위 수의 최소합
        long[] minSideSums = new long[6];
        Arrays.fill(minSideSums, Integer.MAX_VALUE);
        for (int i = 0; i < dice.length; i++) {
            // 한 면만 보이는 경우
            minSideSums[1] = Math.min(minSideSums[1], dice[i]);
            // 5 면이 보이는 경우
            minSideSums[5] = Math.min(minSideSums[5], totalSum - dice[i]);
        }
        
        // 두 면이 보이는 경우
        // 주사위 전개도에 따라 순서의 합이 5일 경우 서로 마주보는 면이다.
        // 해당 경우는 두 면이 보이는 경우에 포함될 수 없다.
        for (int i = 0; i < dice.length; i++) {
            for (int j = i + 1; j < dice.length; j++) {
                // 서로 맞주보는 면인 경우 건너뛴다.
                if (i + j == 5)
                    continue;
                // 그 외의 경우 합을 구해 최솟값을 갱신하는지 확인.
                minSideSums[2] = Math.min(minSideSums[2], dice[i] + dice[j]);
            }
        }

        for (int i = 0; i < dice.length; i++) {
            for (int j = i + 1; j < dice.length; j++) {
                // i와 j가 마주보는 면이 아니고
                if (i + j == 5)
                    continue;

                for (int k = j + 1; k < dice.length; k++) {
                    // k와 i, k와 j가 서로 마주보는 면이 아닌 경우
                    if (i + k == 5 || j + k == 5)
                        continue;

                    // 세 면의 합을 구해 최솟값을 갱신하는지 확인.
                    minSideSums[3] = Math.min(minSideSums[3], dice[i] + dice[j] + dice[k]);
                }
            }
        }
        
        // n이 1인 경우.
        // 다섯 면이 보이는 주사위 하나 놓이게 된다.
        // 그 때의 값 출력
        if (n == 1)
            System.out.println(minSideSums[5]);
        else {
            // 그 외의 경우
            // 최상단층과 그 외의 층으로 나눌 수 있다.
            
            // 최상단 층이 아닌 경우
            // 2면이 보이는 주사위가 4개, 1면이 보이는 주사위가 (n - 2) * 4개
            long notTopFloor = minSideSums[2] * 4 + minSideSums[1] * (n - 2) * 4;
            // 최상단 층인 경우
            // 3면이 보이는 주사위가 4개, 2면이 보이는 주사위가 (n - 2) * 4개, 1면이 보이는 주사위가 (n - 2) * (n - 2)개
            long TopFloor = minSideSums[3] * 4 + minSideSums[2] * (n - 2) * 4 + minSideSums[1] * (n - 2) * (n - 2);
            // 최상단 층이 아닌 층이 (n-1)개이고 최상단 층이 하나
            // 합을 구해 출력
            System.out.println(notTopFloor * (n - 1) + TopFloor);
        }
    }
}