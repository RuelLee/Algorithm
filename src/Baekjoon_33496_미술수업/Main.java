/*
 Author : Ruel
 Problem : Baekjoon 33496번 미술 수업
 Problem address : https://www.acmicpc.net/problem/33496
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_33496_미술수업;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 점(x1, y1), ... (xn, yn)이 주어진다. yi > 0 이다.
        // 각 점으로부터 x축까지 이르는 기울기가 -1, 1 인 두 직선을 긋는다.
        // 모든 직선들이 이루는 교점의 개수는?
        //
        // 스위핑, 해쉬맵 문제
        // 모든 점에 대해 두 직선을 그을 수 있고, 이 때, 동일 직선 상에 있다면 하나의 직선만 나타날 수도 있다.
        // 따라서 기준이 되는 점을, x축 위의 한 점으로 보고,
        // 그 점으로부터 오른쪽 위로 뻗어나가는 직선인지, 왼쪽 위로 뻗어나가는 직선인지 구분한다.
        // 그 후, 오른쪽 위로 뻗어나가는 직선을 기준으로 해당 직선보다 오른쪽에 있는, 왼쪽으로 뻗어나가는 직선의 개수를 세어준다.
        // 두 대각선 끼리 교점이 생기지 않더라도 x축과 만나는 교점 또한 있으므로 이를 고려한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 왼쪽으로 뻗어나가는 직선과 x축이 만나는 점
        HashSet<Integer> left = new HashSet<>();
        // 오른쪽으로 뻗어나가는 직선과 x축이 만나는 점
        HashSet<Integer> right = new HashSet<>();
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            right.add(x - y);
            left.add(x + y);
        }

        // 직선들을 방향에 따라 배열로 표현하고 정렬.
        int[] toRight = new int[right.size()];
        int idx = 0;
        for (int xpos : right)
            toRight[idx++] = xpos;
        int[] toLeft = new int[left.size()];
        idx = 0;
        for (int xpos : left)
            toLeft[idx++] = xpos;
        Arrays.sort(toRight);
        Arrays.sort(toLeft);

        idx = 0;
        long answer = 0;
        // 오른쪽으로 뻗어나가는 직선들을 살펴보며
        for (int i = 0; i < toRight.length; i++) {
            // 해당 직선보다 오른쪽에 있는
            // 첫번째 직선을 찾는다.
            // toRight[i]보다 왼쪽에 존재한다면
            // idx를 하나 증가시키면서, 해당 직선이 x축과 만나는 점이 있으므로 answer을 하나 증가
            while (idx < toLeft.length && toLeft[idx] < toRight[i]) {
                idx++;
                answer++;
            }
            
            // idx가 아직 toLeft의 범위 내에 있다면
            if (idx < toLeft.length) {
                // 해당 idx부터 마지막 직선까지의 개수를 answer에 누적.
                answer += (toLeft.length - idx);

                // 만약 toLeft[idx] == toRight[i]가
                // x축 위에서 한 점으로 만난다면
                // 원래는 각각이 x축에서 만나는 점 2개가 추가되어야하나
                // 한 점에서 만나는데다, 교점으로 위에서 계산되었으므로
                // idx를 증가시켜 다음 toLeft 직선을 살펴본다.
                if (toLeft[idx] == toRight[i])
                    idx++;
                else    // 일치하지 않는다면, toRight 직선이 x축과 만나는 한 점의 개수를 추가한다.
                    answer++;
            }
        }
        // 마지막으로 남은 왼쪽으로 뻗어나가는 직선들이 x축과 만나는 점의 개수 추가.
        answer += (toLeft.length - idx);
        // 답 출력
        System.out.println(answer);
    }
}