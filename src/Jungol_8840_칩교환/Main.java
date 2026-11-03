/*
 Author : Ruel
 Problem : Jungol 8840번 칩 교환
 Problem address : https://jungol.co.kr/problem/8840
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8840_칩교환;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // A칩을 A개, B칩을 B개 갖고 있다.
        // B칩 cb 개를 A칩 ca개로 무한히 바꿀 수 있다고 한다.
        // 무작위로 x개의 칩을 받았을 때, 최소 fa개의 A칩을 가지려면 x의 최소값은 얼마인가?
        //
        // 수학 문제
        // 조건에 대해서 많이 고려해야하는 문제
        // 무작위로 x개의 칩을 받기 때문에, 최악의 경우를 골라내야한다.
        // 먼저 현재 가진 칩만으로도 fa를 만족하는 경우를 골라낸다.
        // 그 후, ca와 cb의 비율을 봐야한다.
        // 이 때 최악의 경우는, ca가 cb보다 큰 경우, B칩을 cb - 1개의 칩을 받아, ca를 달성하지는 못한 후, A칩을 fa개 받는 경우이다.
        // cb가 ca보다 같거나 큰 경우
        // (fa - 1) / ca = cycle. B칩으로 A칩을 교환하는데, fa를 만족하기 직전까지만 교환한다. 그 후, B칩을 cb - 1개 받은 후, 남은 fa를 A칩을 받아 채우는 경우이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // 각 칩의 개수
            long A = Long.parseLong(st.nextToken());
            long B = Long.parseLong(st.nextToken());
            // A칩과 B칩의 교환 비
            long ca = Long.parseLong(st.nextToken());
            long cb = Long.parseLong(st.nextToken());
            // 얻고자하는 A칩의 개수
            long fa = Long.parseLong(st.nextToken());

            // 현재 칩을 모두 A칩으로 교환
            fa -= A;
            A = 0;
            fa -= (B / cb) * ca;
            B %= cb;

            // 현재 갖고 있는 칩만으로 개수를 만족할 때.
            if (fa <= 0) {
                sb.append(0).append("\n");
                continue;
            }

            // cb가 ca보다 같거나 클 때
            if (ca <= cb) {
                // fa를 만족하기 직전까지만 교환을 함.
                long cycle = (fa - 1) / ca;
                fa -= cycle * ca;
                // 그 후, cycle만큼의 cb 칩 개수 + A칩으로 남은 fa를 채우고 + cb를 만족하기 직전의 칩 개수 - 현재 갖고있는 B칩의 개수
                sb.append(cycle * cb + Math.min(fa, ca) + cb - 1 - B).append("\n");
            } else      // ca가 cb보다 큰 경우. B칩을 A칩으로 교환 가능 개수 직전까지만 받고, 남은 fa만큼 A칩을 받는 경우.
                sb.append(fa + cb - 1 - B).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}