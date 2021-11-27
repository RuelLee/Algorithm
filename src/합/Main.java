/*
 Author : Ruel
 Problem : Baekjoon 1132번 합
 Problem address : https://www.acmicpc.net/problem/1132
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 합;

import java.util.*;

class Alphabet {
    long count;
    boolean cannotZero;

    public Alphabet(long count, boolean cannotZero) {
        this.count = count;
        this.cannotZero = cannotZero;
    }
}

public class Main {
    public static void main(String[] args) {
        // 어느 수에 숫자들이 알파벳으로 치환되어 주어진다
        // 이 때 수들의 합을 최대로 만들 때 합을 구하라
        // 첫자리의 알파벳은 0이 될 수 없다.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        Alphabet[] alphabets = new Alphabet[10];        // 0 ~ 9에 해당하는 알파벳들 10개가 주어질 수 있다.
        for (int i = 0; i < alphabets.length; i++)
            alphabets[i] = new Alphabet(0, false);
        for (int i = 0; i < n; i++) {
            String input = sc.nextLine();
            // 처음에 주어지는 알파벳은 0이 될 수 없다.
            alphabets[input.charAt(0) - 'A'].cannotZero = true;
            // 해당하는 알파벳에 현재 수의 자리수를 더해준다.
            // 예를 들어 ABC에서 A위치에는 100, B의 위치에는 10, C의 위치에는 1을 더해준다.
            for (int j = 0; j < input.length(); j++)
                alphabets[input.charAt(j) - 'A'].count += (long) Math.pow(10, input.length() - (j + 1));
        }
        // 그 후 누적된 값만큼을 내림차순 정렬한다.
        Arrays.sort(alphabets, (o1, o2) -> Long.compare(o2.count, o1.count));
        // 만약 0에 할당된 알파벳이 다른 수의 첫글자라 0이 불가능한 경우, 가장 작은 0이 가능한 수와 위치를 바꿔준다.
        if (alphabets[9].count !=0 && alphabets[9].cannotZero) {
            for (int i = 8; i >= 0; i--) {
                if (!alphabets[i].cannotZero) {
                    Alphabet temp = alphabets[i];
                    alphabets[i] = alphabets[9];
                    alphabets[9] = temp;
                    break;
                }
            }
        }

        // 그 후 0이 할당된 알파벳을 제외한 다른 알파벳들을 다시 내림차순 정렬해준다.
        Arrays.sort(alphabets, 0, 9, (o1, o2) -> Long.compare(o2.count, o1.count));
        // 그 후 큰 수부터 9부터 0까지를 할당하며 값을 더해준다.
        long answer = 0;
        for (int i = 0; i < alphabets.length; i++)
            answer += alphabets[i].count * (alphabets.length - 1 - i);
        System.out.println(answer);
    }
}