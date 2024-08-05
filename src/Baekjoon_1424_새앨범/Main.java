/*
 Author : Ruel
 Problem : Baekjoon 1424번 새 앨범
 Problem address : https://www.acmicpc.net/problem/1424
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1424_새앨범;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // l초짜리 n개의 노래를 최대 c초 간의 음악을 담을 수 있는 cd에 나누어 담고자한다.
        // 노래 사이에는 1초의 공백이 반드시 필요하고, 각 시디에 담기는 노래의 수는 13의 배수가 되지 않게 하고자한다.
        // 필요한 최소 cd의 수는?
        //
        // 수학 문제
        // 하나의 cd에 담을 수 있는 최대 곡의 수를 계산한다.
        // 그 후, 최대 곡 수만큼 담을 수 있는 cd를 통해 담긴 노래의 수를 계산하고
        // 남은 노래의 곡 수가 13의 배수인지와 마지막 cd에 남은 여유 공간을 비교하여
        // 다른 최대 곡 수가 담긴 cd의 노래를 옮겨온다면 13의 배수가 아니게 되는지 확인한다.
        // 만약 다른 cd에서 옮겨올 수 있는 경우가 없다면, 남은 노래는 2개의 cd로 나눠서 담는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 노래, 한 곡의 길이 l, cd에 담을 수 있는 노래의 길이 c
        int n = Integer.parseInt(br.readLine());
        int l = Integer.parseInt(br.readLine());
        int c = Integer.parseInt(br.readLine());

        // 필요한 cd의 수
        int count = 0;
        // 한 cd에 담기는 최대 노래 수
        int songs = c / (l + 1);
        // 공백 1초를 계산하여 마지막에 한 곡이 더 들어가는지 확인.
        if ((l + 1) * songs + l <= c)
            songs++;
        // 13의 배수일 경우, 하나를 줄인다.
        if (songs % 13 == 0)
            songs--;
        // 최대 곡 수만큼 담을 수 있는 cd 계산
        count += n / songs;
        // 남은 노래의 수
        n -= (n / songs) * songs;
        
        // 만약 노래가 남았다면
        if (n > 0) {
            // 남은 노래가 13의 배수가 아닐 경우
            // 그냥 하나의 cd에 담으면 된다.
            if (n % 13 != 0)
                count++;
            else if (count > 0) {       // 13의 배수이고, 노래를 담은 다른 cd가 있는 경우
                boolean found = false;
                // 다른 cd에서 i곡을 옮겨와 현재 cd에 담을 경우
                // 노래를 빼온 cd와 담는 cd 모두 13의 배수가 아닌지 확인한다.
                for (int i = 1; i <= Math.min(songs - n, 12); i++) {
                    if ((songs + i) % 13 != 0 && (songs - i) % 13 != 0) {
                        found = true;
                        break;
                    }
                }
                // 만약 그러한 경우가 있다면
                // 하나의 cd만 추가한다면 모든 노래를 담을 수 있다.
                if (found)
                    count++;
                else        // 그렇지 않다면 두 개의 cd에 n곡을 나눠 담아야한다.
                    count += 2;
            } else      // 이전에 노래를 담은 cd가 존재하지 않는다면 노래를 2개의 cd로 나눠 담는 수밖에 없다.
                count += 2;
        }
        // 답 출력
        System.out.println(count);
    }
}