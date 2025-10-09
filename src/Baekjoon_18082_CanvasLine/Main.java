/*
 Author : Ruel
 Problem : Baekjoon 18082번 Canvas Line
 Problem address : https://www.acmicpc.net/problem/18082
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18082_CanvasLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 캔버스가 빨랫줄에 걸려있다.
        // 각 캔버스는 최소 10이상의 가로 길이를 갖고 있고, 이를 빨랫줄에 널기 위해서는 2개의 핀으로 고정되어야한다.
        // 핀의 길이는 1보다는 조금 작은 길이로, 한 점에서 두 캔버스가 만날 경우, 한 번에 두 캔버스를 고정시킬 수 있다.
        // 캔버스의 왼쪽 끝l 과 오른쪽 끝 위치r 이 주어지고, 미리 꽂혀있는 핀의 위치가 주어질 때
        // 모든 캔버스를 고정시키기 위한 최소 핀의 수와 그 위치를 출력하라
        // 불가능하면 impossible을 출력한다.
        //
        // 그리디 문제
        // 캔버스마다 꽂혀있는 핀의 개수를 세어준다.
        // 만약 3이상이 꽂혀있는 캔버스가 발견된다면 불가능한 경우.
        // 그 외의 경우
        // 각 캔버스를 살펴보며 부족한 핀을 꽂는다.
        // 왼쪽 끝에 꽂을 때는 이전 캔버스와 혹시 겹치지 않는지 확인.
        // 오른쪽 끝에 꽂을 때는 다음 캔버스와 겹치지 않는지 확인해야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 캔버스
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        int[][] canvases = new int[n][3];
        for (int i = 0; i < canvases.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                canvases[i][j] = Integer.parseInt(st.nextToken());
        }

        // p개의 핀
        int p = Integer.parseInt(br.readLine());
        int[] pins = new int[p];
        // 핀 위치를 해시셋으로 처리
        HashSet<Integer> hashSet = new HashSet<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < pins.length; i++)
            hashSet.add(pins[i] = Integer.parseInt(st.nextToken()));

        int idx = 0;
        // 핀이 3개 꽂혀있는 캔버스를 만날 경우
        // 원하는 조건대로 핀을 꽂기 불가능
        boolean impossible = false;
        for (int[] c : canvases) {
            // 현재 캔버스보다 왼쪽에 있는 핀들은 건너뜀
            while (idx < pins.length && pins[idx] < c[0])
                idx++;

            // 현재 캔버스에 꽂혀있는 핀들을 세어준다.
            // 만약 오른쪽 끝이라면 다음 캔버스에 걸칠수도 있으므로
            // 반복문 종료
            while (idx < pins.length && pins[idx] <= c[1]) {
                c[2]++;
                if (pins[idx] == c[1])
                    break;
                else
                    idx++;
            }

            // 핀이 3개 이상 꽂혀있는 캔버스를 만난 경우.
            // 조건을 만족하기 불가능하므로 반복문 종료
            if (c[2] > 2) {
                impossible = true;
                break;
            }
        }

        // 불가능한 경우
        if (impossible)
            System.out.println("impossible");
        else {
            // 그 외의 경우
            // 핀을 꽂는다.
            // 핀의 개수
            int count = 0;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < canvases.length; i++) {
                // 이미 2개가 꽂혀있다면 건너뜀
                if (canvases[i][2] == 2)
                    continue;

                // 핀이 하나도 안 꽂혀있는 경우
                if (canvases[i][2] == 0) {
                    // 이전 캔버스가 왼쪽 끝에 맞닿아있는지 확인하고
                    // 그렇지 않다면 끝에 핀을 꽂고
                    if (i - 1 >= 0 && canvases[i - 1][1] < canvases[i][0]) {
                        sb.append(canvases[i][0]).append(" ");
                        hashSet.add(canvases[i][0]);
                    } else {
                        // 맞닿아있다면 +1 위치에 꽂는다.
                        sb.append(canvases[i][0] + 1).append(" ");
                        hashSet.add(canvases[i][0] + 1);
                    }
                    count++;
                    canvases[i][2]++;
                }

                // 핀이 하나만 꽂혀있는 경우
                // 오른쪽 끝에 하나 더 꽂는다.
                if (canvases[i][2] == 1) {
                    // 오른쪽 끝에 다른 캔버스와 맞닿아있는지 확인
                    // 만약 맞닿아있지만, 해당 캔버스에 아직 핀이 2개 다 꽂히지 않았다면
                    // 하나의 핀으로 두 개의 캔버스를 고정 가능.
                    if (i + 1 < canvases.length && canvases[i][1] == canvases[i + 1][0] && canvases[i + 1][2] < 2 && !hashSet.contains(canvases[i][1])) {
                        sb.append(canvases[i][1]).append(" ");
                        hashSet.add(canvases[i][1]);
                        canvases[i][2]++;
                        canvases[i + 1][2]++;
                    } else if (!hashSet.contains(canvases[i][1] - 1))       // 그 외의 경우 -1 위치에 핀이 안 꽂혀있다면 그 위치 꽂는다.
                        sb.append(canvases[i][1] - 1).append(" ");
                    else    // 핀이 -1 위치에 꽂혀있는 경우 -2 위치에 꽂음.
                        sb.append(canvases[i][1] - 2).append(" ");
                    // 핀 개수 증가
                    count++;
                }
            }
            if (!sb.isEmpty())
                sb.deleteCharAt(sb.length() - 1);
            
            // 핀의 개수 및 꽂은 위치 출력
            System.out.println(count);
            System.out.println(sb);
        }
    }
}