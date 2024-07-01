/*
 Author : Ruel
 Problem : Baekjoon 17088번 등차수열 변환
 Problem address : https://www.acmicpc.net/problem/17088
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17088_등차수열변환;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] d = {-1, 0, 1};

    public static void main(String[] args) throws IOException {
        // 크기가 n인 수열이 주어진다.
        // 각각의 수에 대해 한 번 +1, -1을 하는 연산을 할 수 있다고 한다.
        // 수열을 등차수열로 만들고자할 때, 행해야하는 연산의 최소 횟수는?
        //
        // 브루트포스 문제
        // n이 10만개로 경우의 수가 많아보이지만
        // 연산을 각 수마다 1회만 적용할 수 있으므로
        // 사실상 첫 항과 두번째 항에서 공차가 정해져버린다.
        // -1, 0, 1 3가지 경우를 첫 항과 두번째 항에 적용하여 공차를 구하고
        // 그 때 해당 공차로 등차수열을 만들 수 있는지, 있다면 연산은 몇 번 적용해야하는지 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 크기 n인 수열
        int n = Integer.parseInt(br.readLine());
        int[] b = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 최소 연산 횟수
        // 크기가 1이라면 항상 성립하는 등차수열
        int min = b.length == 1 ? 0 : Integer.MAX_VALUE;
        for (int i = 0; i < d.length; i++) {
            // 첫 항
            int start = b[0] + d[i];
            for (int j = 0; j < d.length; j++) {
                // 공차
                // 만약 두번째 항이 존재하지 않는다면 b[1]에 접근할 수 없다.
                // 따라서 크기가 1 초과일 때만 b[1]에 접근
                int diff = b[b.length > 1 ? 1 : 0] + d[j] - start;
                // 모든 항을 살펴봤는지 여부
                boolean finished = true;
                // 총 연산 횟수
                int sum = Math.abs(d[i]);
                for (int k = 1; k < b.length; k++) {
                    // 해당 공차로 첫 항부터 계산했을 때의 값과
                    // 현재 값과의 차이
                    int currentDiff = Math.abs(start + diff * k - b[k]);
                    // 만약 1보다 크다면 연산 적용이 불가능한 경우.
                    // 해당 경우는 끝낸다.
                    if (currentDiff > 1) {
                        finished = false;
                        break;
                    }
                    // currentDiff가 0이라면 연산이 필요하지 않고
                    // 1이라면 연산이 필요했던 경우
                    // 어쨌건 연산 횟수에 더해준다.
                    sum += currentDiff;
                }
                // 모든 항을 살펴볼 수 있었다면
                // 등차수열을 만드는 것에 성공한 경우.
                // 연산 횟수가 최소값을 갱신했는지 확인.
                if (finished)
                    min = Math.min(min, sum);
            }
        }
        // 만약 min이 초기값이라면 등차수열을 만드는 것이 불가능한 경우이므로
        // -1을 출력
        // 그 외의 경우 min 값 자체를 출력
        System.out.println(min == Integer.MAX_VALUE ? -1 : min);
    }
}