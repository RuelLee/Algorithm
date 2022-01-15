/*
 Author : Ruel
 Problem : Baekjoon 20917번 사회적 거리 두기
 Problem address : https://www.acmicpc.net/problem/20917
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 사회적거리두기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 콘센트가 주어지고, s명의 사람을 최대한 멀리 떨어진 콘센트에 배치하려 한다
        // 이 때의 최소 간격의 길이를 출력하라.
        // 콘센트의 위치값이 매우 큰 값(10억)까지 주어지므로 이분 탐색을 통해 답을 찾아야한다!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int testCase = Integer.parseInt(br.readLine());
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());

            // 콘센트들의 위치
            int[] plugs = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < plugs.length; i++)
                plugs[i] = Integer.parseInt(st.nextToken());
            // 순서대로 정렬해두자.
            Arrays.sort(plugs);

            // 최소 거리는 1부터이고,
            int start = 1;
            // 최대 간격은 선택된 콘센트들이 모두 같은 간격으로 있을 때이다.
            int end = (plugs[0] + plugs[n - 1]) / (s - 1);
            while (start < end) {
                int mid = (start + end) / 2;
                if (isPossible(plugs, mid, s))      // mid 간격으로 배치가 가능하다면
                    start = mid + 1;        // start에 mid + 1 값을 넘겨주자.
                else        // 불가능하다면, end에 mid 값을 넣어주자.
                    end = mid;
                System.out.println(start + " " + end);
            }
            sb.append(start - 1).append("\n");
        }
        System.out.println(sb);
    }

    static boolean isPossible(int[] plugs, int minLength, int s) {
        int previousPlug = plugs[0];        // 첫번째 콘센트 선택
        s--;
        for (int i = 1; i < plugs.length; i++) {
            if (s < 1)      // 인원 배치가 끝났다면 반복문 탈출.
                break;

            if (plugs[i] - previousPlug < minLength)        // 이전 콘센트와 이번 콘센트 간의 거리가 minLength보다 작다면 선택해서는 안된다. continue로 넘어가자.
                continue;

            // minLength 이상 떨어진 콘센트를 발견했다면
            // previousPlug에 이번 콘센트 위치를 넣고
            // s값을 하나 감소시킨다.
            previousPlug = plugs[i];
            s--;
        }
        // 아직 배치해야하는 사람이 남았다면 불가능한 경우.
        // 모두 배치가 끝났다면 true 반환.
        return s <= 0;
    }
}