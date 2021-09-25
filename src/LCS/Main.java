/*
 Author : Ruel
 Problem : Baekjoon 9251번 LCS
 Problem address : https://www.acmicpc.net/problem/9251
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package LCS;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 오랜만에 최장  공통 부분 수열 문제 복습
        // 동적프로그래밍을 사용하면 된다!
        // 가로와 세로를 각각 집합의 원소의 개수 + 1만큼 할당해준다
        // 1, 1부터 연산을 시작한다
        // 가로 집합과 세로 집합의 현재 문자가 서로 같다면, 현재 문자 이후를 제외한 집합에서의 최대 길이 + 1값을 넣어주면 된다
        // 따라서 현재 row, col이라면, do[row][col] = dp[row-1][col-1] + 1을 해주면 된다
        // 만약 현재 문자가 같지 않다면, 이전까지 기록된 것중 가장 큰 길이가 얼마인지 기록해주면된다
        // 이는 자신보다 윗칸에 적혀있을 수도 있고, 왼쪽칸에 적혀있을 수도 있다.
        // dp[row][col] = Math.max(dp[row-1][col], dp[row][col-1])
        Scanner sc = new Scanner(System.in);

        String a = sc.nextLine();
        String b = sc.nextLine();
        int[][] check = new int[a.length() + 1][b.length() + 1];    // row, col은 각 집합에서 연산에 반영될 순서를 나타낸다.
        for (int i = 1; i < check.length; i++) {    // a라는 집합에서 i-1번까지 반영
            for (int j = 1; j < check[i].length; j++) {     // b라는 집합에서 j-1번까지 반영
                if (a.charAt(i - 1) == b.charAt(j - 1))     // 현재 순서의 두 문자가 같다면, 두 문자를 제외한 이전 순서 중 가장 큰 값인 check[i - 1][j - 1]에서 +1 값을 해준다.
                    check[i][j] = check[i - 1][j - 1] + 1;
                else
                    check[i][j] = Math.max(check[i - 1][j], check[i][j - 1]);       // 그렇지 않다면, 현재 문자가 반영되기 전인, check[i - 1][j], check[i][j - 1] 둘 중 큰 값을 가져온다.
            }
        }
        System.out.println(check[check.length - 1][check[check.length - 1].length - 1]);        // 가장 마지막에 기록된 숫자가 최장 공통 부분 수열의 길이.
    }
}