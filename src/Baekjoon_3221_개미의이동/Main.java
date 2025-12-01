/*
 Author : Ruel
 Problem : Baekjoon 3221번 개미의 이동
 Problem address : https://www.acmicpc.net/problem/3221
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3221_개미의이동;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 l인 줄 위에 n마리의 개미가 서 있다.
        // 개미들은 1mm/s로 이동하며, 총 t초 동안 이동한다.
        // 두 개미가 서로 마주치거나 줄의 끝에 도달한 경우, 방향을 반대로 바꾼다.
        // 각 개미의 위치와 향하고 있는 방향이 주어질 때
        // t초 후, 개미의 위치를 1번 개미부터 n번 개미까지 출력하라
        //
        // 애드 혹
        // 처음 생각할 때는 방향이 반대인 서로 가까워지는 개미들의 가까운 순서대로
        // 해당 시간 만큼 계산을 해야하나 였다.
        // 하지만 좀 더 생각해보니, 서로 두 개미가 서로 만나 탄성충돌한다면,
        // 그냥 각각의 두 개미가 서로를 통과해 지나가는거랑 다름이 없었다.
        // 개미들의 순서는 바뀌지 않으므로, 마지막 개미들의 위치를 오름차순해, 정렬하면 되었다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 l의 줄, t의 시간
        int l = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // n마리의 개미
        int n = Integer.parseInt(br.readLine());
        int[] ants = new int[n];
        for (int i = 0; i < n; i++) {
            // 각 개미의 위치와 방향
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int d = st.nextToken().charAt(0) == 'L' ? -1 : 1;
            // 변동량 누적
            p += t * d;
            // 위치가 줄을 벗어나는 경우에
            while (p < 0 || p > l) {
                // 음의 방향인 경우
                // 0번 위치에서 반전시켜 양의 방향으로 나아간다.
                if (p < 0)
                    p = -p;
                // l을 넘어선 경우
                // l위치에서 반전시켜 반대 방향으로 이동한다.
                else
                    p = l - (p - l);
            }
            // 현재 위치 기록
            ants[i] = p;
        }
        // 정렬하여 오름차순으로 만듦
        Arrays.sort(ants);
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int ant : ants)
            sb.append(ant).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 답 출력
        System.out.println(sb);
    }
}