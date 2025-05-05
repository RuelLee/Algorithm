/*
 Author : Ruel
 Problem : Baekjoon 25181번 Swap the elements
 Problem address : https://www.acmicpc.net/problem/25181
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25181_Swaptheelements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 최대가 5000인 크기 n의 수열 A가 주어진다.
        // 원소의 위치를 바꿔 새로운 수열B 를 만들되
        // 모든 i(1<= i <= n)에 대하여, Ai != Bi인 수열을 만들고자한다.
        // 원소는 1 ~ 100_000으로 주어질 때, 불가능하다면 -1
        // 가능하다면 그러한 B를 아무거나 하나 출력한다.
        //
        // 애드 혹, 정렬 문제
        // 정렬로 푼 사람의 풀이를 봤는데 천재인 것 같다.
        // 한 원소의 최대 개수가 n / 2개를 넘어가면 안된다.
        // 넘어간다면 반드시 하나 이상은 같은 원소가 같은 순서에 위치하게 된다.
        // 이를 먼저 체크하고
        // A를 오름차순으로 정렬한다.
        // 그리고, i번 위치에 i + n / 2번째 수를 위치하면 무조건 다른 수가 위치하게끔 할 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 크기 n의 수열 A
        int n = Integer.parseInt(br.readLine());
        int[] a = new int[n];
        // 정렬
        int[] sorted = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            a[i] = sorted[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(sorted);

        // 각 수 별로 처음 등장하는 위치를 기록해둔다.
        // 그러면서, 한 종류의 원소가 n / 2개보다 많은지 체크한다.
        int[] startIdx = new int[100_001];
        startIdx[sorted[0]] = 0;
        boolean possible = true;
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i - 1] != sorted[i]) {
                if (i - startIdx[sorted[i - 1]] > n / 2) {
                    possible = false;
                    break;
                }
                startIdx[sorted[i]] = i;
            }
        }
        if (n - startIdx[sorted[n - 1]] > n / 2)
            possible = false;

        StringBuilder sb = new StringBuilder();
        // 한 종류의 수가 n / 2개를 넘을 때
        // 불가능한 경우.
        if (!possible)
            sb.append(-1);
        else {
            // 그 외의 경우
            // 해당 수보다 n / 2번째 뒤에 있는 수를 해당 위치에 끌어온다.
            for (int num : a)
                sb.append(sorted[(startIdx[num]++ + n / 2) % n]).append(" ");
            sb.deleteCharAt(sb.length() - 1);
        }
        // 답 출력
        System.out.println(sb);
    }
}