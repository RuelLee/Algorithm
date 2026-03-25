/*
 Author : Ruel
 Problem : Baekjoon 1802번 종이 접기
 Problem address : https://www.acmicpc.net/problem/1802
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1802_종이접기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] array;

    public static void main(String[] args) throws IOException {
        array = new int[3000];
        // 종이를 두고 오른쪽을 위쪽이 되게끔 혹은 오른쪽이 아래로 되게끔 반씩 접는다.
        // 종이를 접은 뒤 펼쳤을 때, 접힌 모양에 따라 안으로 접힌 경우 0, 밖으로 접힌 경우 1로 주어진다.
        // 주어진 접힌 모양이 가능한 모양인지 확인하라
        //
        // 분할 정복
        // 종이를 한 번 접은 이후로부터는 대칭되는 곳은 서로 반대로 접히게 된다.
        // 따라서 분할 정복을 통해 중앙 지점을 기준으로 양 옆이 서로 데칼코마니와 같이
        // 서로 다른 값으로 대칭이 되는지 확인한다.
        // 대칭이 된다면 이제 왼쪽부분과 오른쪽 부분에 대해서도 각각 대칭인지 확인한다.
        // 대칭이지 않은 부분이 확인된다면 불가능한 경우이고, 모두 대칭이라면 가능한 경우이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트 케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // 주어진 접힌 모양
            String input = br.readLine();
            for (int i = 0; i < input.length(); i++)
                array[i] = (char) (input.charAt(i) - '0');

            // 모든 범위에 대해 대칭 체크
            sb.append(check(0, input.length() - 1) ? "YES" : "NO").append("\n");
        }
        System.out.print(sb);
    }

    static boolean check(int start, int end) {
        // start와 end가 같아졌다면 가능
        if (start == end)
            return true;

        // 중앙 지점
        int mid = (start + end) / 2;
        // 현재 길이에 따라 양 쪽이 서로 다른 값으로 대칭인지 확인
        for (int i = 1; i <= (end - start) / 2; i++) {
            // 그렇지 않은 값이 발견되면 false 반환
            if (array[mid - i] == array[mid + i])
                return false;
        }
        // mid가 중앙일 때, 양쪽이 서로 다른 값으로 대칭이었다면
        // 이제 왼쪽 부분과 오른쪽 부분을 나눠 각각이 대칭인지 확인
        // 그렇지 않은 값이 하나라도 주어지면 false 반환
        return check(start, mid - 1) && check(mid + 1, end);
    }
}