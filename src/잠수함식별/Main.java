/*
 Author : Ruel
 Problem : Baekjoon 2671번 잠수함식별
 Problem address : https://www.acmicpc.net/problem/2671
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 잠수함식별;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 문자열 관련 문제
        // KMP를 사용하는 것도 아니었지만 예외사항들이 많아서 이들을 잘 고려해야했다.
        // 특정 패턴을 갖는 소리를 잠수함 소리로 판별하기로 한다
        // 어떤 문자 뒤에 '~' 표시가 있다면 해당 문자는 여러번 반복해서 나올 수 있다는 것을 의미한다
        // 또한 (A | B)라면 A나 B 둘 중 하나가 나올 수 있음을 의미한다
        // 잠수함 소리는 (100~1~|01)~로 한다
        // ex) "01""100'00'1'11'"01
        Scanner sc = new Scanner(System.in);

        char[] sound = sc.nextLine().toCharArray();
        int idx = 0;
        // 패턴이 여러번 반복될 수 있으므로 while문으로 계속 반복.
        while (idx < sound.length) {
            // 100~1~이 되려면, 최소한 4개의 문자가 있어야하고, 앞부분이 100으로 시작해야한다.
            if (idx + 3 < sound.length && sound[idx] == '1' && sound[idx + 1] == '0' && sound[idx + 2] == '0') {
                idx += 3;
                // 중간에 오는 0은 여러번 반복될 수도 or 없을 수도 있다.
                while (idx + 1 < sound.length && sound[idx] == '0')
                    idx++;

                // 1이 최소 한번은 나와야한다.
                if (sound[idx] == '1')
                    idx++;
                else        // 안나온다면 패턴 성립 X
                    break;

                // 그 뒤에 1이 여러번 반복될 수도 있다. 하지만 100으로 시작되서는 안된다.
                while (idx < sound.length && sound[idx] == '1' && !(idx + 2 < sound.length && sound[idx] == '1' && sound[idx + 1] == '0' && sound[idx + 2] == '0'))
                    idx++;
                // 100~1~이 아니라 01이라면 두 글자만 확인하면 된다.
            } else if (idx + 1 < sound.length && sound[idx] == '0' && sound[idx + 1] == '1')
                idx += 2;
            // 100~1~ 도 아니고 01도 아니라면 패턴 성립 X
            else
                break;
        }
        // idx가 전체 문자를 확인해서 sound.length에 도달했다면 잠수함 소리.
        System.out.println(idx == sound.length ? "SUBMARINE" : "NOISE");
    }
}