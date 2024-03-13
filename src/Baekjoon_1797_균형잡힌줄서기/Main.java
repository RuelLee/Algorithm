/*
 Author : Ruel
 Problem : Baekjoon 1797번 균형잡힌 줄서기
 Problem address : https://www.acmicpc.net/problem/1797
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1797_균형잡힌줄서기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 팬과 해당 팬의 성별, 그리고 x 위치가 주어진다.
        // 연속한 팬들을 그룹으로 묶었을 때, 남벼의 수가 같은 그룹이 보기 좋다고 한다.
        // 가장 x의 너비가 긴 보기 좋은 그룹의 너비는?
        //
        // 누적합 문제
        // 성별에 따라 누적합을 취하며, 이전에 등장한 같은 누적합을 갖는 곳과의 최대 길이를 계산한다.
        // 가장 긴 길이를 갖기 위해서는 같은 누적합일 경우, 먼저 등장한 값만 남겨두어도 좋다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 팬들의 성별과 위치.
        int[][] fans = new int[n + 1][2];
        for (int i = 1; i < fans.length; i++)
            fans[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 위치에 따라 정렬
        Arrays.sort(fans, Comparator.comparingInt(o -> o[1]));

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        // 아무 팬도 없는 위치.
        hashMap.put(0, 0);

        // 최대 그룹의 너비
        int length = 0;
        // 누적합
        int sum = 0;
        for (int i = 0; i < fans.length; i++) {
            // i번째 팬의 성별에 따른 누적합.
            sum += (fans[i][0] == 1 ? 1 : -1);
            // 이전에 등장한 같은 누적합이 있다면
            // 해당 누적합 idx의 다음 idx부터, i까지를 더하면
            // 보기 좋은 그룹이 된다.
            // 해당 너비를 계산하고 최대 너비를 갱신하는지 확인한다.
            if (hashMap.containsKey(sum))
                length = Math.max(length, fans[i][1] - fans[hashMap.get(sum) + 1][1]);
            else        // 처음 등장한 누적합 값이라면 해쉬맵에 해당 idx를 기록
                hashMap.put(sum, i);
        }
        // 보기 좋은 그룹의 최대 너비 출력
        System.out.println(length);
    }
}