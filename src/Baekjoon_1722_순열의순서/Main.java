/*
 Author : Ruel
 Problem : Baekjoon 1722번 순열의 순서
 Problem address : https://www.acmicpc.net/problem/1722
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1722_순열의순서;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1부터 n까지의 수가 주어진다.
        // n이 3이라면 1 2 3 / 1 3 2 / 2 1 3 / 2 3 1 / 3 1 2 / 3 2 1 같은 순열을 만들 수 있고
        // 위와 같이 순서가 정해진다.
        // 그 때 다음 쿼리를 처리하라
        // 1 k : 해당 수로 만든 n번째 순열을 출력
        // 2 a b c ... d : n개의 수 a ~ d로 이루어진 순열의 순서를 출력
        //
        // 수학 문제
        // n개의 수가 주어질 때 첫번째 수는
        // n개의 수를 오름차순으로 정렬한 순열이다.
        // 만약 첫번째 수가 가장 작은 수가 아니라 두번째로 작은 수로 바뀐다면
        // (n - 1)! 만큼의 순서가 더해지게 된다.
        // 첫번째 수가 가장 작은 수인 동안, 두번째부터 n번째 수들로 만들 수 있는 순열이 (n - 1)!개이기 때문
        // 따라서 위의 상관관계를 고려해여 문제를 풀면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        // 팩토리얼을 미리 구해둔다.
        // 해당 순서의 수가 가장 작은 수가 아니라면 바뀌는 순서
        // 즉, (n - 1 - i)! 값을 구해 넣어둔다.
        long[] factorial = new long[n];
        // 계산의 편의상 마지막 순서에도 0이 아닌 1을 넣어둔다.
        for (int i = factorial.length - 1; i >= Math.max(factorial.length - 2, 0); i--)
            factorial[i] = 1;
        for (int i = n - 3; i >= 0; i--)
            factorial[i] = factorial[i + 1] * (n - 1 - i);

        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        // k번째 순서의 순열을 구하는 쿼리
        if (Integer.parseInt(st.nextToken()) == 1) {
            long k = Long.parseLong(st.nextToken()) - 1;
            
            // 선택된 수들을 표시
            boolean[] selected = new boolean[n + 1];
            // 총 n개의 수들을 선택한다.
            for (int i = 0; i < n; i++) {
                // k가 factorial[i]보다 몇 배 이상 큰지를 통해
                // 현재 남아있는 수들 중 몇 번째 수를 선택해야하는지 알 수 있다.
                // order번째 수를 선택해야한다.
                long order = k / factorial[i];
                int idx = 1;
                int count = 0;
                // 남아있는 수들 중 order번째 수를 찾는다.
                for (; idx < selected.length; idx++) {
                    if (!selected[idx] && order == count++)
                        break;
                }
                // 선택 표사
                selected[idx] = true;
                // k에서 해당하는 순서만큼 차감
                k -= factorial[i] * order;
                // 해당 수를 기록
                sb.append(idx).append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
        } else {
            // 주어지는 순열의 순서를 맞추는 쿼리
            // 순열 입력
            int[] array = new int[n];
            for (int i = 0; i < array.length; i++)
                array[i] = Integer.parseInt(st.nextToken());
            
            // 선택된 수 표시
            boolean[] selected = new boolean[n + 1];
            // 순서 합
            long sum = 1;
            // array의 i번째 수가 남은 수들 중 몇번째 수인지 파악한다.
            for (int i = 0; i < array.length; i++) {
                int order = 0;
                int idx = 1;
                for (; idx < selected.length; idx++) {
                    if (selected[idx])
                        continue;
                    else if (array[i] != idx)
                        order++;
                    else
                        break;
                }
                // array의 i번째 수는 남은 수들 중 order번째로 작은 수이다.
                // 순서에 해당하는 만큼 factorial 배 하여 순서를 누적한다.
                selected[idx] = true;
                sum += order * factorial[i];
            }
            sb.append(sum);
        }
        // 답 출력
        System.out.println(sb);
    }
}