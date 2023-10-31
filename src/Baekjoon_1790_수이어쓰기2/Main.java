/*
 Author : Ruel
 Problem : Baekjoon 1790번 수 이어 쓰기 2
 Problem address : https://www.acmicpc.net/problem/1790
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1790_수이어쓰기2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1부터 n개의 수를 이어서 하나의 수로 만든다.
        // 그 후, 앞에서부터 k번째 숫자가 무엇인지 출력하라
        // 예를 들어 n = 10, k = 10이라면
        // 12345678910 이라는 수가 만들어지고, 10번째 숫자인 1이 답이다.
        //
        // 구현 문제
        // n이 최대 1억, k가 최대 10억으로 주어지므로
        // 당연히 직접 이어붙여 수를 만든다는 건 말이 안된다.
        // 따라서 한자리 수, 두자리 수, ... , 등을 이어붙인 경우에 몇번째 자리까지 가는지 확인하고
        // 그 이후에 몇번째 수인지 확인하여 해당하는 숫자를 출력하는 방법으로 구했다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 수, k번째 숫자
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // n까지 이어붙이므로 최대 몇자리까지 구해야하는지 확인.
        int size = (int) (Math.log(n) / Math.log(10)) + 1;
        int[] counts = new int[size];
        // 한자리 수는 1 ~ 9, 총 9 * 1자리
        // 두자리 수는 10 ~ 99 총 90개의 수 * 2자리
        // 자리수에 해당하는 길이를 계산한다.
        // 마지막 자리수는 ~ ..99가 아니라  ~ n으로 끝나는 것을 고려해야 계산한다.
        for (int i = 0; i < counts.length - 1; i++)
            counts[i] = (i + 1) * (int) (Math.pow(10, i + 1) - Math.pow(10, i));
        counts[size - 1] = size * (n - (int) (Math.pow(10, size - 1)) + 1);

        StringBuilder sb = new StringBuilder();
        int idx = 0;
        // k번째 자리가 몇자리 수로 이루어져있는지 계산한다.
        while (idx < counts.length && k - counts[idx] > 0)
            k -= counts[idx++];
        
        // 만약 idx가 size와 같아졌다면
        // 수가 적어 k번째 자리가 없는 경우
        // -1 기록
        if (idx >= size)
            sb.append(-1);
        else {
            // 그렇지 않다면 k의 수가 남아있다.
            // idx + 1자리 수이고,
            // k / (idx + 1) - 1번째 수이다.
            // 만약 idx가 2, k = 3이라면
            // 세자리 수 중에 첫번째 수인 100이다.
            // 만약 k가 (idx + 1)로 나누어떨어지지 않는다면 다음 수이다.
            // ex) idx = 2, k = 4 -> 101
            int num = (int) Math.pow(10, idx) + k / (idx + 1) - (k % (idx + 1) == 0 ? 1 : 0);
            // 나누어 떨어진다면 일의 자리 숫자가 해당하는 k번째 숫자.
            if (k % (idx + 1) == 0)
                sb.append(num % 10);
            else {
                // 그렇지 않다면 현재 수에서
                // 나머지에 해당하는 번째의 수를 출력해야한다.
                // 해당하는 만큼 10을 나누기를 반복하여
                // 숫자를 가져온다.
                for (int i = k % (idx + 1); i <= idx; i++)
                    num /= 10;
                sb.append(num % 10);
            }
        }

        // 답안 출력
        System.out.println(sb);
    }
}