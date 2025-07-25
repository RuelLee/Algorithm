/*
 Author : Ruel
 Problem : Baekjoon 32406번 의좋은 형제
 Problem address : https://www.acmicpc.net/problem/32406
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32406_의좋은형제;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 형과 아우가 각각 n개의 논을 갖고 있다.
        // 각 논에 볏단이 쌓여있다.
        // i번째 논에 있는 볏단을 j번째 상대 논으로 옮겨둘 수 있다. 1<= i < j <= n
        // 모든 볏단이 n번째 논에 모였을 때, 차이를 최대화하고자할 때
        // 그 차이는?
        //
        // 그리디 문제
        // n번째 논에 있는 볏단은 그대로 있어야하고
        // n-1번째 논에 있는 볏단은 반드시 상대편 n번째 논으로 가야한다.
        // 하지만 1 ~ n-2번째 논에 있는 볏단들은 해당 볏단을 n-1번째 혹은 n번째에 두느냐에 따라
        // 자신의 논 혹은 상대방의 논으로 보낼 수가 있다.
        // 따라서 n-2번째까지는 그냥 양쪽의 논 중 큰 값과 작은 값을 따로 모아 합쳐 차이를 최대화시킨다.
        // 그 후, 큰 값을 형에게 줄 때와 아우에게 줄 때를 각각 비교해보면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 논
        int n = Integer.parseInt(br.readLine());
        
        // 각 논에 쌓인 볏단
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int[] b = new int[n];
        for (int i = 0; i < n; i++)
            b[i] = Integer.parseInt(st.nextToken());

        // n-2번째까지는 큰 값과 작은 값을 각각 모아 합친다.
        int max = 0;
        int min = 0;
        for (int i = 0; i < n - 2; i++) {
            max += Math.max(a[i], b[i]);
            min += Math.min(a[i], b[i]);
        }

        // 형의 논에 확정적으로 모이는 볏단과
        int up = a[n - 1] + b[n - 2];
        // 아우의 논에 확정적으로 모이는 볏단.
        int down = b[n - 1] + a[n - 2];

        // 1 ~ n-2번째까지의 볏단들 중 큰 값을 형에게 줄 때와 아우에게 줄 때를 각각 비교하여
        // 차이가 큰 값을 구한다.
        int answer = Math.max(Math.abs(up + max - down - min), Math.abs(up + min - down - max));
        // 답 출력
        System.out.println(answer);
    }
}