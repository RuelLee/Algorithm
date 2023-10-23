/*
 Author : Ruel
 Problem : Baekjoon 24023번 아기 홍윤
 Problem address : https://www.acmicpc.net/problem/24023
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24023_아기홍윤;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기 n인 배열에서 연속한 구간을 잡아 내부의 값을 모두 or 연산했을 때
        // k가 되는 구간을 구하라
        //
        // 비트 연산
        // 전부 or 연산을 했을 때 k가 되기 위해서는
        // 포함되는 구간의 모든 수가 k의 비트값의 부분집합여야한다.
        // 따라서 순차적으로 값들을 살펴보며, 비트값이 k의 부분집합이라면 포함시켜나가고
        // 그렇지 않다면 끊고, 다음 수부터 새로운 연속 구간을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n개의 수로 구성도니 수열
        int n = Integer.parseInt(st.nextToken());
        // 원하는 값 k
        int k = Integer.parseInt(st.nextToken());
        
        // 수열
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // or 연산값
        int or = 0;
        // start ~ end 구간
        int start = 0;
        int end = 0;
        for (int i = 0; i < nums.length; i++) {
            // 이번에 포함되는 수 i
            end = i;
            // 만약 i번째 수가 k 비트값의 부분 집합이라면
            // and 연산한 값이 자기 자신이 나와야한다.
            // 그렇다면 or값에 i번째를 | 연산값 반영
            if ((k & nums[i]) == nums[i]) {
                or |= nums[i];
                // or 값이 k가 되었다면 종료
                if (or == k)
                    break;
            } else {        // 그렇지 않다면 i+1부터 새로운 구간을 찾기 시작한다.
                start = i + 1;
                or = 0;
            }
        }
        
        // or 값이 k가 되는 구간을 찾았다면
        // 해당 구간 출력. 그렇지 않다면 -1 출력
        System.out.println(or == k ? ((start + 1) + " " + (end + 1)) : -1);
    }
}