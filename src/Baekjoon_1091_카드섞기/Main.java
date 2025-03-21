/*
 Author : Ruel
 Problem : Baekjoon 1091번 카드 섞기
 Problem address : https://www.acmicpc.net/problem/1091
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1091_카드섞기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 3명의 플레이어와 n(n은 48보다 같거나 작은 3의 배수)장의 카드가 주어진다.
        // 지민은 카드가 섞이는 순서를 알고 있다고 한다.
        // 이는 길이 n인 수열 s로 주어지며, i번 위치에 있던 카드가 s[i]번 위치로 이동함을 뜻한다.
        // 최종적으로 원하는 카드 배치는 길이 n의 수열 p로 주어지며
        // 처음에 i번째에 있던 카드가 p[i]번 플레이어에게 보내져야함을 뜻한다.
        // 최소 몇 번 섞으면 해당 카드를 원하는대로 보낼 수 있는지 출력한다.
        // 불가능하다면 -1을 출력한다.
        //
        // 구현 문제
        // p 수열 때문에 조금 헷갈렸는데
        // 2 0 1과 같이 주어진다면, 
        // 첫번째 카드는 세번째 플레이어에게, 두번째 카드는 첫번째 플레이어에게, 세번째 카드는 두번째 플레이어에게 가야함을 뜻한다.
        // 따라서 이는 처음 카드가 0 1 2 에서 1 2 0가 되어야한다.
        // 이를 조금 다르게 생각한다면 
        // 현재의 p를 섞어 0 1 2 0 1 2 ... 같은 형태가 되는데 몇 번 섞음이 필요한지 계산하면 되는지랑 같다.
        // 위 경우를 계산하면 된다.
        // 단 같은 배치가 다시 등장할 동안, 원하는 배치를 찾지 못한다면 불가능한 경우이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 원하는 카드 상태
        int[] p = new int[n];
        // 카드를 셔플하는 동안 상태를 저장해둘 배열
        int[][] states = new int[2][n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < p.length; i++)
            p[i] = states[0][i] = Integer.parseInt(st.nextToken());
        
        // 셔플로 바뀌는 카드 위치
        int[] s = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < s.length; i++)
            s[i] = Integer.parseInt(st.nextToken());
        
        // 원하는 카드의 최종 배치(0, 1, 2, 0, 1, 2, ...)
        int[] finish = new int[n];
        for (int i = 1; i < finish.length; i++)
            finish[i] = i % 3;
        
        // 셔플 횟수
        int count = 0;
        boolean found = false;
        // count가 0이거나 현재 카드 상태가 초기 배치가 아닌 경우.
        while (count == 0 || !isSame(states[count % 2], p)) {
            // 원하는 형태가 되었는지 체크
            if (isSame(states[count % 2], finish)) {
                found = true;
                break;
            }

            // 아니라면 카드를 섞고
            for (int i = 0; i < s.length; i++)
                states[(count + 1) % 2][s[i]] = states[count % 2][i];
            // count 증가.
            count++;
        }

        // 원하는 카드 배열이 되었다면 셔플 횟수를
        // 아니라면 -1을 출력한다.
        System.out.println(found ? count : -1);
    }

    // a와 b의 카드 배치가 같은지 확인한다.
    static boolean isSame(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i])
                return false;
        }
        return true;
    }
}