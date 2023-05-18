/*
 Author : Ruel
 Problem : Baekjoon 22862번 가장 긴 짝수 연속한 부분 수열 (large)
 Problem address : https://www.acmicpc.net/problem/22862
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22862_가장긴짝수연속한부분수열_large;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수로 이루어진 수열이 주어진다
        // 이 중 k개의 수를 지워 가장 긴 연속한 짝수 부분 수열을 만들고자 한다
        // 그 때 그 길이는?
        //
        // 두 포인터 문제
        // 첫포인터부터 두번째 포인터까지 안의 홀수를 k개 이하를 유지하며
        // 가장 긴 부분 수열의 길이를 찾아나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 수
        int n = Integer.parseInt(st.nextToken());
        // 지울 수 있는 수의 개수
        int k = Integer.parseInt(st.nextToken());
        // 수열
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 두 포인터 사이의 짝수
        int evenNums = 0;
        // 홀수
        int oddNums = 0;
        // 두번째 포인터
        int j = 0;
        // 부분 수열의 최대 길이
        int max = 0;
        // 첫 수로 초기화
        if (nums[0] % 2 == 0)
            evenNums++;
        else
            oddNums++;
        for (int i = 0; i < nums.length; i++) {
            // j + 1의 위치의 수가 짝수이거나
            // 홀수이라면 아직 홀수의 개수가 k를 넘지 않았을 때
            // 두번째 포인터 j의 위치를 점점 더 뒤로 이동시킨다.
            while (j + 1 < nums.length &&
                    (nums[j + 1] % 2 == 0 || oddNums < k)) {
                // 짝수라면 짝수 개수 증가
                if (nums[++j] % 2 == 0)
                    evenNums++;
                // 홀수일 경우
                else
                    oddNums++;
            }
            // j를 최대한 뒤로 이동시켰다면, 두 포인터 내의 짝수의 개수가
            // 최대 짝수 부분 수열의 길이인지 확인.
            max = Math.max(max, evenNums);

            // 첫 포인터를 i + 1로 이동시키며
            // i에 해당하는 짝수 혹은 홀수의 개수를 감소시킨다.
            if (nums[i] % 2 == 0)
                evenNums--;
            else
                oddNums--;
        }

        // 찾은 최대 짝수 부분 수열의 길이를 출력한다.
        System.out.println(max);
    }
}