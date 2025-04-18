/*
 Author : Ruel
 Problem : Baekjoon 1101번 카드 정리 1
 Problem address : https://www.acmicpc.net/problem/1101
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1101_카드정리1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 박스와 m 종류의 카드가 주어진다.
        // 각 박스에 담겨있는 카드의 종류와 개수가 주어진다.
        // 카드를 정리하고자 한다.
        // 1. 하나의 박스를 조커 박스로 지정할 수 있고, 색이 다른 카드를 보관해도 된다.
        // 2. 조커 박스를 제외한 모든 박스는 비어있거나, 같은 색의 카드만 보관해야한다.
        // 3. 조커 박스에 담긴 카드를 제외한 모든 같은 색의 카드는 하나의 박스에 담겨있어야 한다.
        // 박스에 담긴 1장 이상의 카드(색이 같지 않아도 된다)를 빼내는 행위를 이동이라할 때, 최소 몇 번의 이동으로 카드들을 정리할 수 있는가.
        //
        // 브루트 포스, 그리디 문제
        // 문제를 차분히 생각해보면
        // 박스에 있는 모든 카드를 조커 박스로 옮기나, 한 종류의 카드만 남기고 옮기나 어차피 모두 1번의 이동이다.
        // 따라서 여러 종류의 카드가 있는 경우, 그냥 모두 조커 박스로 카드를 옮겨버리면 된다.
        // 그 외의 이동을 안해도 되는 경우는 한 종류의 카드만 있는 박스이며, 
        // 해당 박스에 담겨있는 카드의 종류가 이전에 등장한 적이 없어야한다.
        // 같은 한 종류의 카드만 담겨있는 박스가 또 있다면, 해당 박스로 카드들을 옮겨야하기 때문.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 박스, m 종류의 카드
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 박스의 카드 정보
        // boxes[][0] = 박스에 담긴 카드들 종류의 개수
        // boxes[][1] = 해당 종류들을 bitmask를 통해 표시
        long[][] boxes = new long[n][2];
        for (int i = 0; i < boxes.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                if (Integer.parseInt(st.nextToken()) != 0) {
                    boxes[i][0]++;
                    boxes[i][1] |= (1L << j);
                }
            }
        }

        int minCount = Integer.MAX_VALUE;
        // i번 박스가 조커 박스가 될 경우.
        for (int i = 0; i < boxes.length; i++) {
            int count = 0;
            long bitmask = 0;
            for (int j = 0; j < boxes.length; j++) {
                // j번 박스가 조커 박스거나, 담긴 카드가 없는 경우 건너뛴다.
                if (j == i || boxes[j][0] == 0)
                    continue;

                // 박스에 담긴 카드가 한 종류이며, 이전에 등장한 적이 없다면
                // 해당 박스의 카드들을 그냥 냅둬도 된다.
                // 대신 해당 카드의 종류를 bitmask에 기록한다.
                if (boxes[j][0] == 1 && ((bitmask & boxes[j][1]) == 0))
                    bitmask |= boxes[j][1];
                else        // 그 외의 경우는 조커 박스로 이동을 한다.
                    count++;
            }
            // i번 박스가 조커 박스일 때의 이동 횟수가
            // 전체 이동 횟수의 최솟값을 갱신하는지 확인
            minCount = Math.min(minCount, count);
        }
        // 최솟값 출력
        System.out.println(minCount);
    }
}