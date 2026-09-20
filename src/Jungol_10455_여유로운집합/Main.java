/*
 Author : Ruel
 Problem : Jungol 10455번 여유로운 집합
 Problem address : https://jungol.co.kr/problem/10455
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_10455_여유로운집합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 집합 s의 서로 다른 두 원소 차의 절댓값이 k 이상이면 여유롭다고 한다.
        // n개의 서로 다른 원소와 k가 주어질 때
        // 각 원소를 포함하는 여유로운 집합의 최대 크기는?
        //
        // DP, 그리디, 정렬 문제
        // 왼쪽과 오른쪽에서 각각, 해당 원소를 포함하는 최대 집합의 크기를 구한다.
        // fromLeft[i] = i 이전의 원소들을 포함하며, i번째 원소를 포함하는 최대 집합의 크기
        // fromRight[i] = i 이후의 원소들을 포함하며, i번째 원소를 포함하는 최대 집합의 크기
        // 구하고, 두 dp의 합 - 1(자신이 중복하여 두번 들어갔으므로)를 답으로 취한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringTokenizer st;
        HashMap<Integer, List<Integer>> originalOrder = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // 원소의 개수 n, 여유로운 조건 k
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            // 각 원소들의 원래 순서를 기억해둔다.
            // 동일 원소가 존재할 수 있으므로 리스트로 받는다.
            originalOrder.clear();
            int[] array = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                array[i] = Integer.parseInt(st.nextToken());
                if (!originalOrder.containsKey(array[i]))
                    originalOrder.put(array[i], new ArrayList<>());
                originalOrder.get(array[i]).add(i);
            }
            // 정렬
            Arrays.sort(array);

            // 왼쪽에서부터 이전 원소들과 i를 포함하는 여유로운 집합의 최대 크기를 구한다.
            int[] fromLeft = new int[n];
            Arrays.fill(fromLeft, 1);
            int j = 0;
            for (int i = 0; i < fromLeft.length; i++) {
                while (j + 1 < i && array[i] - array[j + 1] >= k)
                    j++;

                if (array[i] - array[j] >= k)
                    fromLeft[i] = Math.max(fromLeft[i], fromLeft[j] + 1);
            }

            // 오른쪽에서부터도 구한다.
            int[] fromRight = new int[n];
            Arrays.fill(fromRight, 1);
            j = n - 1;
            for (int i = n - 1; i >= 0; i--) {
                while (j - 1 > i && array[j - 1] - array[i] >= k)
                    j--;
                if (array[j] - array[i] >= k)
                    fromRight[i] = Math.max(fromRight[i], fromRight[j] + 1);
            }

            // 각 원소의 원래 순서에 맞춰 답을 작성한다.
            int[] answer = new int[n];
            for (int i = 0; i < n; i++) {
                if (answer[originalOrder.get(array[i]).get(0)] != 0)
                    continue;

                // array[i]가 포함하는 여유로운 집합의 최대 크기
                int value = fromLeft[i] + fromRight[i] - 1;
                // 같은 값을 갖는 원소들에게 모두 기입
                for (int idx : originalOrder.get(array[i]))
                    answer[idx] = value;
            }

            // 답안 작성
            sb.append("Case #").append(testCase + 1).append(": ");
            sb.append(answer[0]);
            for (int i = 1; i < answer.length; i++)
                sb.append(" ").append(answer[i]);
            sb.append("\n");
        }
        // 출력
        System.out.print(sb);
    }
}