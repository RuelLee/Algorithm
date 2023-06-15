/*
 Author : Ruel
 Problem : Baekjoon 25556번 포스택
 Problem address : https://www.acmicpc.net/problem/25556
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25556_포스택;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이가 n인 수열과 네 개의 비어있는 스택이 주어진다.
        // 수들을 순서대로 네 개의 스택 중 임의의 스택에 모두 담은 후
        // 모든 스택들에서 모든 수를 꺼내 오른쪽에서 왼쪽으로 나열할 때,
        // 오름차순으로 정렬할 수 있는지 판별하라
        //
        // 그리디, 스택
        // 스택은 후입선출이므로 오름차순으로 정렬하고자 한다면
        // 스택의 top보다 더 큰 수만 스택에 들어올 수 있다.
        // 스택이 최대 4개 주어지므로 그렇지 않은 경우는 최대 3번까지만 가능하다.
        // 수들을 순서대로 스택에 담되, 현재 담는 수보다는 작지만, 그 중 가장 큰 스택에 담아나간다.
        // 더 이상 스택에 담을 수 없다면, 오름차순 정렬이 불가능한 경우
        // 모든 수들을 담을 수 있다면 가능한 경우다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 수열의 크기
        int n = Integer.parseInt(br.readLine());
        
        // 네 개의 스택 공간
        Stack<Integer>[] stacks = new Stack[4];
        for (int i = 0; i < stacks.length; i++) {
            stacks[i] = new Stack<>();
            // 비어있는 공간에 대한 조건을 따로 생각하지 않기 위해
            // 모두 0을 담아둔다.
            stacks[i].push(0);
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        boolean possible = true;
        for (int i = 0; i < n; i++) {
            // 이번에 담을 수
            int num = Integer.parseInt(st.nextToken());

            // num을 담을 스택을 찾는다.
            int idx = -1;
            // top이 num보단 작지만, 스택들 중에서는 가장 큰 수가 담겨있는 스택을 찾는다.
            for (int j = 0; j < stacks.length; j++) {
                if (num > stacks[j].peek() && (idx == -1 || stacks[j].peek() > stacks[idx].peek()))
                    idx = j;
            }
            
            // 그러한 스택을 찾았다면 해당 수를 스택에 담고
            if (idx != -1 && num > stacks[idx].peek())
                stacks[idx].push(num);
            else {      // 그렇지 않다면 불가능한 경우이므로 possible을 false로 만든 후, 반복문 종료
                possible = false;
                break;
            }
        }

        // possible 값에 따라 YES 혹은 NO를 출력한다.
        System.out.println(possible ? "YES" : "NO");
    }
}