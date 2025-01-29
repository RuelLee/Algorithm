/*
 Author : Ruel
 Problem : Baekjoon 1323번 숫자 연결하기
 Problem address : https://www.acmicpc.net/problem/1323
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1323_숫자연결하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 어떤 수 n과 k가 주어질 때
        // n을 몇 번 이어써야 k로 나누어떨어지는지 구하라
        // 몇 번을 써도 나누어떨어지지 않는다면 -1을 출력한다.
        //
        // 수학 문제
        // n을 이어쓰게 된다면
        // 10의 (n의 길이)제곱 만큼을 곱한 후, n을 더한 값이랑 같다. 이를 multiple이라 하자.
        // 또한 i번 이어쓴 수를 ni라 할 때
        // ni % k == ((ni-1 % k) * multiple + n) % k와 같다.
        // 따라서 수가 long 범위를 벗어나지 않도록 나머지값들로만 계속하여 계산해준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 수 n, k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // n의 길이
        int length = getLength(n);
        // 한 번 이어쓸 때마다 곱해줘야하는 수
        long multiple = 1;
        for (int i = 0; i < length; i++)
            multiple *= 10;

        // 나머지가 순환하는지 확인한다.
        // 나누어 떨어지지 않고 계속 순환하게 된다.
        HashSet<Long> hashSet = new HashSet<>();
        
        // 이어쓴 횟수
        int count = 1;
        // 값
        long value = n % k;
        // 나누어떨어지지 않는 동아
        while (value % k != 0) {
            // conunt 증가.
            count++;
            // 원래 값에 multiple을 곱하고 n을 더한 뒤 그 값을 k로 나눈 나머지를 value 값으로 한다.
            value = (value * multiple + n) % k;
            // 만약 나머지가 순환한다면 종료
            if (hashSet.contains(value))
                break;
            // 그렇지 않다면 일단은 해쉬셋에 추가.
            hashSet.add(value);
        }
        // 반복문을 끝마치고 나왔을 때
        // value가 k로 나누어떨어진다면 이어쓴 횟수를
        // 그렇지 않다면 불가능한 경우이므로 -1을 출력한다.
        System.out.println(value % k == 0 ? count : -1);
    }

    // n의 길이를 반환한다.
    static int getLength(int n) {
        int count = 0;
        while (n > 0) {
            n /= 10;
            count++;
        }
        return count;
    }
}