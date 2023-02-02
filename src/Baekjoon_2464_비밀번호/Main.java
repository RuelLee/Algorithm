/*
 Author : Ruel
 Problem : Baekjoon 2464번 비밀번호
 Problem address : https://www.acmicpc.net/problem/2464
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2464_비밀번호;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 어떤 x에 대해서
        // x를 이진수로 나타냈을 때,
        // x와 1의 개수가 같으면서 가장 작은 x보다 큰 수와
        // 가장 큰 x보다 작은 수를 구하라
        //
        // 그리디 문제
        // 만약 43이라는 수가 주어진다면 이진수로 101011이다
        // 가장 큰 x보다 작은 수는 0 이후로 등장한 1을 0으로 바꾸고 그 뒤에 1의 개수만큼 1 + 2 +... + 해준 100111이고
        // 가장 작은 x보다 큰 수는 가장 먼저 나오는 0을 1로 바꾸고, 그 뒤에 있던 1을 가져온 101101이다.
        // 1. 가장 큰 x보다 작은 수는
        // 2^0의 자리부터, 1의 개수를 세어나가며, 해당 자리에 해당하는 값을 빼나간다.
        // 그러다 자신의 자리는 1, 자신보다 하나 작은 자리는 0인 자리를 만나면
        // 자신을 0으로 만난 후, 자신보다 작은 자리들을 내림차순으로 현재 세어놨던 1의 개수만큼 값을 더해나가면 된다.
        // 2. 가장 작은 x보다 큰 수는
        // 마찬가지로 1의 개수를 세어가며 해당 수를 빼나간다.
        // 그러다 이전에 센 1의 개수가 1이상이며, 현재 자리가 0인 자리를 만나면
        // 해당 자리를 1로 만들어주고, 세어진 1의 개수만큼 가장 낮은 자리부터 1을 채워나간다.
        //
        // * 주의 *
        // 이진수 계산을 위해 Math.pow를 활용했는데, 큰 수가 주어지다보니
        // 부동소수점으로 계산되는지 형변환을 하지 않으면 오차가 발생했다.
        // long으로 강제 형변환을 해주어, long타입 간의 계산이 될 수 있도록 해주어야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        long a = Long.parseLong(br.readLine());
        char[] binary = Long.toBinaryString(a).toCharArray();

        // 가장 큰 a보다 낮은 수를 찾는다.
        // 2^0 자리가 1이라면 해당 값에 맞게 lowerNum, oneCount를 초기화한다.
        long lowerNum = a - (binary[binary.length - 1] == '1' ? 1 : 0);
        int oneCount = binary[binary.length - 1] == '1' ? 1 : 0;
        // 2^1 자리부터 살펴보며
        for (int i = binary.length - 2; i >= 0; i--) {
            // 현재 자리가 1이라면
            if (binary[i] == '1') {
                // oneCount 증가
                oneCount++;
                // 해당하는 값을 lowerNum에서 빼준다.
                lowerNum -= (long) Math.pow(2, binary.length - 1 - i);
                // 만약 2^(i+1)자리가 0이었다면
                // ...10...이진수의 형태를 띈다.
                // 이를 ...01...으로 바꿔주고, 큰 자리부터 1을 채워나가면
                // a보다 작은 수 중 가장 큰 수를 만들 수 있다.
                if (binary[i + 1] == '0') {
                    // 자리를 내림차순으로 1을 채워나간다.
                    while (oneCount > 0) {
                        lowerNum += (long) Math.pow(2, binary.length - 1 - ++i);
                        oneCount--;
                    }
                    // 수가 완성됐으므로 반복문 종료.
                    break;
                }
            }
        }

        // a보다 큰 수 중 가장 작은 수를 찾는다.
        long higherNum = a;
        oneCount = 0;
        boolean zeroFound = false;
        // 1의 자리부터 살펴보며
        for (int i = binary.length - 1; i >= 0; i--) {
            // 자릿수가 1이라면
            if (binary[i] == '1') {
                // 해당하는 수를 제외하고
                higherNum -= (long) Math.pow(2, binary.length - 1 - i);
                // oneCount 증가
                oneCount++;
            } else if (oneCount > 0) {      // 자릿수가 0이며, 이전에 1이 한 번 이상 등장했다면
                // zero를 찾았다고 표시하고
                zeroFound = true;
                // 현재 자릿수에 1을 채워주고
                higherNum += (long) Math.pow(2, binary.length - 1 - i);
                // oneCount 하나 감소
                oneCount--;
                // 반복문 종료
                break;
            }
        }
        // 만약 이진수에 0를 찾지 못했다면, 전부 1이거나
        // ..01..의 형태를 띈 자리가 없는 경우
        // 이 경우에는 자릿수를 하나 늘리는 수밖에 방법이 없다.
        if (!zeroFound) {
            // 가장 앞에 1을 추가하고
            higherNum += (long) Math.pow(2, binary.length);
            // 1의 개수 감소
            // a가 1이상이라 조건으로 주어지므로 oneCount는 반드시 1이상.
            oneCount--;
        }

        // 그 후, 남은 1의 개수만큼 낮은 자릿수부터 1을 채워나가면 된다.
        // 2^x - 1값은 1의 자리부터 2^(x-1) 자리까지 1로 채워진 수이다.
        // 해당 수를 더해주면 된다.
        higherNum += (long) Math.pow(2, oneCount) - 1;

        // 답안 출력.
        System.out.println(lowerNum + " " + higherNum);
    }
}