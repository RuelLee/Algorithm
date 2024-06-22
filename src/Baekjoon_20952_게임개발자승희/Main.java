/*
 Author : Ruel
 Problem : Baekjoon 20952번 게임 개발자 승희
 Problem address : https://www.acmicpc.net/problem/20952
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20952_게임개발자승희;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int LIMIT = 1000000000 + 7;

    public static void main(String[] args) throws IOException {
        // 길이 n의 수열 A와 길이 m의 수열 B가 주어진다.
        // 연산은
        // A의 모든 수에 대해 Bi를 더해, 7의 배수가 되는 수를 A에서 제외한다.
        // 만약 A의 모든 원소가 사라진다면 해당 연산은 수행하지 않는다.
        // m번 연산을 수행했을 때, 그 결과를 출력하라
        //
        // 수학 문제
        // 그냥 나머지에 대한 이해만 갖고 있으면 되는 문제
        // 연산을 시행할 때, 사라지는 수는 나머지에 따라 결정된다.
        // A에 7의 나머지가 2인 수가 존재한다면 이들은
        // B의 누적합이 5가 되는 순간 사라지게 된다.
        // 따라서 B의 누적합에 나머지와, A 원소들의 나머지를 비교하여 합이 7의 배수가 되는 순간 사라지게 된다.
        // 먼저 A에 대해 나머지에 따라 원소의 개수를 계산한다.
        // 그 후, B에 대해 누적합을 구하며,
        // 두 나머지를 더해 7이 되는 경우, 해당하는 A의 나머지 그룹을 제외시켜가며
        // 만약 해당 그룹을 제외할 경우 모든 A 원소가 사라진다면 해당 연산은 시행하지 않는다.
        // 최종적으로 살아남은 원소들에 대해
        // 누적합을 각각 더한 수열이 답.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 n과 m의 수열 A, B
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] A = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] B = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // A를 나머지에 따라 분류
        int[] remains = new int[7];
        for (int i = 0; i < A.length; i++)
            remains[A[i] % 7]++;
        
        // B 누적합
        long sum = 0;
        // 아직 사라지지 않은 A의 원소 개수
        int survived = n;
        // 사라진 나머지 그룹
        boolean[] deleted = new boolean[7];
        for (int i = 0; i < B.length; i++) {
            // 누적합
            sum += B[i];

            // 누적합의 나머지
            int plus = (int) (sum % 7);
            // 이번에 사라지게 되는 A의 나머지 그룹
            int target = (7 - plus) % 7;
            // 만약 이미 사라진 그룹이라면
            // 그냥 누적합만 더하고 끝남.
            if (deleted[target])
                continue;
            else if (survived == remains[target])       // 만약 모든 원소가 사라진다면 해당 연산은 시행하지 않음.
                sum -= B[i];
            else {
                // 위 두 경우가 아니라면
                // target에 해당하는 수만큼 survived에서 제하며
                survived -= remains[target];
                // 해당 그룹이 사라졌음을 표시.
                deleted[target] = true;
            }
        }

        StringBuilder sb = new StringBuilder();
        // 최종적으로 남은 원소의 개수
        sb.append(survived).append("\n");
        // 사라지지 않은 그룹만 누적합을 더해 기록
        for (int i = 0; i < A.length; i++) {
            if (!deleted[A[i] % 7])
                sb.append((A[i] + sum) % LIMIT).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 전체 답안 출력
        System.out.print(sb);
    }
}