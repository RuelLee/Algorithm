/*
 Author : Ruel
 Problem : Baekjoon 16570번 앞뒤가 맞는 수열
 Problem address : https://www.acmicpc.net/problem/16570
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16570_앞뒤가맞는수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수열 (a1, a2, ⋯, aN) 이 다음의 성질을 가지면 그 수열은 k-앞뒤수열 이라고 한다.
        // (a1, a2, ⋯, ak) = (aN-k+1, aN-k+2, ⋯ , aN), 1 ≤ k < N
        // 우리는 수열의 앞뒤가 맞게 만들기 위해 수열의 연속된 앞부분을 자를 수 있다
        // 앞부분을 잘라서 잘라서 앞뒤수열을 만들 수 있다면 그렇게 자른 후 
        // 수열의 앞뒤계수 최댓값과 그렇게 자르는 방법의 수를 공백으로 구분하여 출력한다.
        //
        // KMP 알고리즘 문제
        // KMP 알고리즘은 접두사와 관련된 알고리즘이다.
        // 문제는 읽어보면 접미사와 관련된 문제이다.
        // 따라서 KMP 알고리즘을 역순으로 적용하거나, 입력받는 수열을 역순으로 배치하여
        // KMP 알고리즘을 사용하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 수열의 길이
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] nums = new int[n];
        // 접미사에 관한 문제를 접두사로 바꿔주기 위해
        // 수열을 역순으로 저장한다.
        for (int i = nums.length - 1; i >= 0; i--)
            nums[i] = Integer.parseInt(st.nextToken());
        
        // 앞뒤수열의 최대길이
        int maxLength = 0;
        // 최대 길이의 개수
        int count = 0;

        // KMP 알고리즘을 통해
        // pi 배열을 구한다.
        int j = 0;
        int[] pi = new int[n];
        for (int i = 1; i < nums.length; i++) {
            while (j > 0 && nums[i] != nums[j])
                j = pi[j - 1];
            if (nums[i] == nums[j])
                pi[i] = ++j;
            
            if (maxLength < pi[i]) {
                maxLength = pi[i];
                count = 1;
            } else if (maxLength == pi[i])
                count++;
        }
        
        StringBuilder sb = new StringBuilder();
        // 앞뒤수열을 만들 수 없다면 -1 출력
        if (maxLength == 0)
            sb.append(-1);
        // 만들 수 있다면 최대 길이와 그 개수 출력.
        else
            sb.append(maxLength).append(" ").append(count);
        System.out.println(sb);
    }
}