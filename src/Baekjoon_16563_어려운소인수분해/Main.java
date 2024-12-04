/*
 Author : Ruel
 Problem : Baekjoon 16563번 어려운 소인수분해
 Problem address : https://www.acmicpc.net/problem/16563
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16563_어려운소인수분해;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 자연수가 주어진다.
        // 해당 자연수들을 소인수분해한 결과를 출력하라
        //
        // 에라토스테네스의 체 문제
        // 인데 조금 다르다.
        // n이 최대 100만개, 각 자연수가 최대 500만으로 주어지므로
        // 각 자연수에 대해 모든 소수를 비교해나가다간 시간 초과가 난다.
        // 따라서 에라토스테네스의 체를 이용하되, 소수 여부를 적어두는 것이 아니라
        // 해당 자연수의 가장 작은 인수를 남겨두는 방식으로 하여, 인수를 지속하여 찾아나가는 방법으로 만든다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 자연수
        int n = Integer.parseInt(br.readLine());
        // 각각의 자연수 k
        int[] ks = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < ks.length; i++)
            ks[i] = Integer.parseInt(st.nextToken());
        
        // 에라토스테네스의 체
        int[] eratosthenes = new int[5_000_001];
        for (int i = 1; i < eratosthenes.length; i++)
            eratosthenes[i] = i;

        for (int i = 2; i < eratosthenes.length; i++) {
            // 이미 자신이 아닌 다른 값으로, 인수가 들어와있다면 건너뛴다.
            if (eratosthenes[i] != i)
                continue;
            
            // 그 외의 경우
            // i * j의 가장 작은 인수는 i이므로, i를 기록
            for (int j = 2; i * j < eratosthenes.length; j++)
                eratosthenes[i * j] = i;
        }

        StringBuilder sb = new StringBuilder();
        // 스택을 사용하는 이유는 오름차순 정렬을 하기 때문.
        // 각 에레토스테네스의 체 공간에는 가장 작은 인수가 들어있다.
        // 따라서 원래 값부터 인수로 나눠가면서 찾아가면
        // 자연스럽게 오름차순으로 정렬이 되긴 때문.
        Stack<Integer> stack = new Stack<>();
        // n개의 자연수 소인수 분해
        for (int k : ks) {
            // k가 1이 될 때까지
            while (k > 1) {
                // 체에 기록된 값을 스택에 넣는다
                stack.push(eratosthenes[k]);
                // k는 체에 기록된 값 만큼을 나눈다.
                k /= eratosthenes[k];
            }

            // 스택에서 값을 꺼내며 답안 작성
            sb.append(stack.pop());
            while (!stack.isEmpty())
                sb.append(" ").append(stack.pop());
            sb.append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}