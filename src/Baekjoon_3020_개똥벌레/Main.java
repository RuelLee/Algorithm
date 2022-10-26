/*
 Author : Ruel
 Problem : Baekjoon 3020번 개똥벌레
 Problem address : https://www.acmicpc.net/problem/3020
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3020_개똥벌레;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 개똥벌레 한 마리가 석순과 종유석으로 가득찬 동굴에 들어갔다.
        // 개똥벌레는 일정한 높이로 날아가며 부딪치는 종유석과 석순을 모두 부순다고한다.
        // 이 때 최소로 부숴도 되는 종유석과 석순의 개수와 그러한 지점의 개수는?
        // 입력으로 석순과 종유석이 번갈아가면서 나타난다고 한다.
        //
        // 누적합 문제
        // 종유석과 석순을 각각 누적합을 구한 뒤
        // 모든 높이에 대해 부숴야하는 종유석과 석순의 개수를 구한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        
        // 종유석과 석순의 입력 처리
        int[] stalagmites = new int[h + 1];
        int[] stalactites = new int[h + 1];
        for (int i = 0; i < n; i++) {
            if ((i & 1) == 0)
                inputStalagmite(Integer.parseInt(br.readLine()), stalagmites);
            else
                inputStalactite(Integer.parseInt(br.readLine()), stalactites);
        }
        
        // 최소로 부숴야하는 돌의 개수
        int min = Integer.MAX_VALUE;
        // 해당하는 높이의 개수
        int count = 0;
        for (int i = 0; i < h; i++) {
            // 종유석과 석순을 양수로 주어지므로
            // 개똥벌레는 그 양수 사이로 날아간다고 하자.
            // 예를 들어 i일 경우, 개똥벌레는 i + 0.5의 높이로 날아가며
            // 높이가 i인 석순과 길이가 h - (i + 1)인 종유석은 부수지 않아도 된다.
            int sum = n - getStalagmites(i, stalagmites) - getStalactites(h - (i + 1), stalactites);
            // 만약 부수는 돌의 개수가 이전 기록과 같다면
            // count 증가
            if (sum == min)
                count++;
            // 더 작은 값이 나타났다면, min과 count 갱신
            else if (min > sum) {
                min = sum;
                count = 1;
            }
        }
        // 답안 출력.
        System.out.println(min + " " + count);
    }

    // 종유석과 석순들은 펜윅 트리를 사용하여 누적합을 구했다.
    // 종유석과 석순 입력.
    static void inputStalagmite(int height, int[] stalagmites) {
        while (height < stalagmites.length) {
            stalagmites[height] += 1;
            height += (height & -height);
        }
    }

    static void inputStalactite(int length, int[] stalactites) {
        while (length < stalactites.length) {
            stalactites[length] += 1;
            length += (length & -length);
        }
    }

    // 해당하는 길이의 종유석과 석순 출력.
    static int getStalagmites(int height, int[] stalagmites) {
        int sum = 0;
        while (height > 0) {
            sum += stalagmites[height];
            height -= (height & -height);
        }
        return sum;
    }

    static int getStalactites(int length, int[] stalactites) {
        int sum = 0;
        while (length > 0) {
            sum += stalactites[length];
            length -= (length & -length);
        }
        return sum;
    }
}