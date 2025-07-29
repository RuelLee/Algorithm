/*
 Author : Ruel
 Problem : Baekjoon 32865번 컴소인의 크리스마스
 Problem address : https://www.acmicpc.net/problem/32865
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32865_컴소인의크리스마스;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 문제가 주어진다.
        // 각 문제에 대해 제출할 때, 정해진 횟수만큼을 틀린 후, 마지막 제출에서 맞는다고 한다.
        // 채점 로그를 초록색(맞았습니다)와 빨간색(틀렸습니다)를 반복하여 띄우고 싶다.
        // 첫번째와 마지막은 초록색이어야한다.
        // 그렇게 출력하는 것이 불가능하다면 -1,
        // 가능하다면 해당 번째에 제출해야하는 문제 번호를 출력한다.
        //
        // 정렬 문제
        // 결국 로그에는 n개의 초록색 로그와 n-1개의 빨간색 로그가 남아야한다.
        // 문제에 대해 먼저 빨간색 로그의 개수가 n-1개인지 확인한다.
        // 그 개수가 맞다면, 각 문제마다 틀린 횟수만큼 해당 문제와 바로 맞추는 문제를 번갈아가면서 제출하고
        // 최종적으로 해당 문제를 맞는 문제로서 제출하는 과정을 반복하면 된다.
        // 따라서 정렬하여, 앞쪽에는 바로 맞추는 문제에 대한 포인터
        // 뒤쪽에는 틀린 후 맞추는 문제에 대한 포인터로 문제 번호를 관리해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 문제
        int n = Integer.parseInt(br.readLine());
        
        // 각 문제의 번호와 틀리는 횟수
        int[][] counts = new int[n][2];
        // 틀린 횟수의 합
        long sum = 0;
        for (int i = 0; i < counts.length; i++) {
            counts[i][0] = i + 1;
            sum += (counts[i][1] = Integer.parseInt(br.readLine()));
        }

        StringBuilder sb = new StringBuilder();
        // 틀린 횟수의 합이 n-1인 경우
        if (sum == n - 1) {
            Arrays.sort(counts, Comparator.comparingInt(o -> o[1]));
            // 바로 맞추는 문제의 idx
            int zeroIdx = 0;
            // 틀린 후 맞추는 문제의 idx
            int notZeroIdx = 0;
            while (notZeroIdx < counts.length && counts[notZeroIdx][1] == 0)
                notZeroIdx++;
            
            // 첫번째 로그는 초록색
            sb.append(counts[zeroIdx++][0]).append("\n");
            // 틀린 후 맞추는 문제가 마지막 문제에 이를 때까지
            while (notZeroIdx < counts.length) {
                // 해당 횟수 -1만큼 해당 문제와 바로 맞추는 문제를 번갈아가며 제출하고
                for (int i = 0; i < counts[notZeroIdx][1] - 1; i++) {
                    sb.append(counts[notZeroIdx][0]).append("\n");
                    sb.append(counts[zeroIdx++][0]).append("\n");
                }
                // 해당 문제를 두 번 제출한다.
                // 첫번째는 마지막 틀리는 경우이고, 그 다음은 맞추는 경우이다.
                sb.append(counts[notZeroIdx][0]).append("\n");
                sb.append(counts[notZeroIdx++][0]).append("\n");
            }
        } else      // 불가능한 경우이므로 -1을 기록
            sb.append(-1).append("\n");
        // 답 출력
        System.out.print(sb);
    }
}