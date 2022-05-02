/*
 Author : Ruel
 Problem : Baekjoon 1011번 Fly me to the Alpha Centauri
 Problem address : https://www.acmicpc.net/problem/1011
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1011_FlyMeToTheAlphaCentauri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 우주선을 통해 이동을 한다
        // 턴 마다 이동 가능한 거리는 전 턴의 이동 거리 -1, 0, +1이다. (이전 턴에 2칸을 이동했다면 1, 2, 3칸 이동 가능)
        // 마지막 도착 지점에는 이전 지점에서 1칸만 이동해서 도착하고 싶다
        // 테스트케이스, 시작지점, 도착지점이 주어졌을 때 최소 턴으로 이동하는 방법은?
        // 한 턴의 이동 거리는 1씩 완만하게 증가하거나 감소한다
        // 따라서 전체 이동거리는 0이나 1씩 증가해서 최대 거리를 이동 후 1까지 다시 줄어드는 형태이다
        // 따라서 등차수열의 합인 n(n+1)/2를 이용하여 무조건 1씩 증가시켜 최대로 이동할 수 있는 거리로 세고, *2를 해준다.(다시 속도를 줄이는 것까지)
        // 그 후 남은 거리가 (n + 1)보다 같거나 작은 거리가 남았다면 해당 거리를 중간에 가능한 위치에 삽입한다고 생각하면 된다
        // 예를 들어 n이 5 남은 거리가 6이라면, ((1, 2, 3, 4, 5), 6, (5, 4, 3, 2, 1)) 과 같은 형태가 될 것이고
        // 남은 거리가 3이라면 ((1, 2, 3, '3', 4, 5), (5, 4, 3, 2, 1)) 같은 형태가 된다 생각하면 된다.
        // 만약 남은 거리가 n + 1보다 크다면, n+1보다 같거나 작은 값 2개로 나눠 생각하는게 가능하다
        // 만약 남은 거리가 2 * (n + 1)보다 같거나 큰 값이라면 n이 증가했을 것이므로.
        // 이를 이용하여 최소 횟수를 계산하면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());

        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            st = new StringTokenizer(br.readLine());
            long length = -Integer.parseInt(st.nextToken()) + Integer.parseInt(st.nextToken());

            // n값을 구하기 위해 이분 탐색을 사용하자
            int left = 1;
            int right = (int) Math.ceil(Math.sqrt(length));     // n * (n + 1)이 length보다 같거나 작아야하므로, length에 루트를 씌워 올림해준 값을 최대값의 근사값으로 생각하자.
            while (left < right) {
                int mid = (left + right) / 2;

                if ((long) mid * (mid + 1) <= length)       // mid가 같거나 작다면 left에 mid +1 값을
                    left = mid + 1;
                else        // 크다면 right에 mid값을 넣어주자.
                    right = mid;
            }
            // 최종적으록 구해진 left는 원하는 n값보다 하나 큰 값이다. 하나 빼주자.
            left--;

            int count = left * 2;       // 오름차순으로 진행 후, 내림차순으로 진행하므로 연속적인 수열에 따른 이동 횟수는 left의 두배.
            long remains = length - (long) left * (left + 1);      // 남은 거리
            if (remains > 0) {      // 남은 거리가 0보다 크고
                if (remains <= left + 1)        // left + 1보다 같거나 작다면 한번만 더 이동하면 된다.
                    count++;
                else        // 그렇지 않다면 left + 1 보다 같거나 작은 거리 2개로 나눠 2번에 이동이 가능하다.
                    count += 2;
            }
            sb.append(count).append("\n");
        }
        System.out.println(sb);
    }
}