/*
 Author : Ruel
 Problem : Baekjoon 4342번 유클리드 게임
 Problem address : https://www.acmicpc.net/problem/4342
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4342_유클리드게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 자연수가 주어지고, 두 명의 플레이어가 번갈아가며 게임을 진행한다.
        // 각 턴마다 플레이어는 큰 수에서 작은 수의 배수만큼을 뺀다.
        // 이 때 큰 수는 전보다 작아져야 한다.
        // 큰 수를 0으로 만든 사람이 승리한다.
        // 두 사람 모두 최적으로 게임을 할 때, 이기는 사람은?
        //
        // 게임 이론, 백트래킹
        // 유클리드 호제법을 이용한 문제
        // 먼저 각 사람이 최적의 방법으로 진행하기 때문에 각 턴마다 자신이 이길 가능성이 있는 경우
        // 해당 경우를 택하게 된다.
        // 따라서 해당 턴을 진입한 플레이어가 이길 수 있는지 여부를 판별하는 함수를 만든다.
        // 가장 먼저 큰 수가 작은 수의 배수인 경우, 해당 턴의 플레이어가 승리한다.
        // 두번째, 큰 수가 작은 수의 2배 이상일 경우, 작은 수를 그대로 작은 수로 남겨두는 것이 가능하다.
        // 1배일 경우는 무조건 큰 수가 변화해야하므로 큰 수가 작은 수가 되고, 작은 수가 큰 수로 바뀐다.
        // 2배 이상일 경우, 수의 대소가 바뀌는 경우의 유불리를 따져 자신이 선택할 수 있고
        // 1배일 경우는 자신이 택할 수 없다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        while (true) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            // a와 b가 모두 0이 들어오는 경우 반복문 종료
            if (a == 0 && b == 0)
                break;
            
            // a와 b에 따른 결과 기록
            sb.append(firstWin(Math.max(a, b), Math.min(a, b)) ? "A" : "B").append(" wins").append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
    
    // max, min이 주어질 때, 해당 턴의 플레이어가 승리하는지 여부
    static boolean firstWin(int max, int min) {
        // max가 min의 배수라면 해당 플레이어 승리
        if (max % min == 0)
            return true;
        
        // 수의 대소가 바뀌는 경우의 승리 여부
        boolean next = firstWin(min, max % min);
        // 수의 대소가 바뀔 때,
        // 다음 턴의 플레이어가 지거나, 
        // 혹은 다음 턴의 플레이어가 승리하는데 max가 min의 2배 이상이라 
        // 내가 그 다음 턴의 플레이어가 될 수 있는 경우  
        if (!next || max / min > 1)
            return true;
        // 그 외의 경우는 패배
        return false;
    }
}