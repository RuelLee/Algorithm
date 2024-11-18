/*
 Author : Ruel
 Problem : Baekjoon 2179번 비슷한 단어
 Problem address : https://www.acmicpc.net/problem/2179
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2179_비슷한단어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 문자열이 주어진다.
        // 두 단어의 비슷한 정도는 같은 접두사의 길이로 측정한다.
        // 비슷한 정도가 같다면 입력된 순서가 우선인 것을 답으로 한다.
        //
        // 정렬 문제
        // 일단 단어들을 원래의 순서에 따라 단어와 순서를 기록해둔다.
        // 그 후, 사전순으로 단어를 정렬한 뒤
        // 단어를 비교해나가며 가장 비슷한 두 단어를 골라낸다.
        // 입력된 순서가 우선됨에 주의하자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 단어
        int n = Integer.parseInt(br.readLine());

        // 단어
        String[] words = new String[n];
        // 단어의 원래 순서
        HashMap<String, Integer> hashMap = new HashMap<>();
        HashMap<Integer, String> reversed = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            hashMap.put(words[i] = br.readLine(), i);
            reversed.put(i, words[i]);
        }
        // 정렬
        Arrays.sort(words);
        
        // 가장 긴 같은 접두사의 길이
        int maxSame = 0;
        // 해당하는 두 단어의 원래 순서
        int[] idxes = new int[2];
        // i번째 단어와
        for (int i = 0; i < words.length - 1; i++) {
            // i번째 단어와 비교하여 얻을 수 있는 최대 같은 접두사의 길이
            int currentSame = 0;
            // 후보 단어의 원래 순서
            int[] candidateIdxes = new int[2];
            candidateIdxes[0] = hashMap.get(words[i]);
            // i번째 단어와 j번째 단어를 비교한다.
            for (int j = i + 1; j < words.length; j++) {
                // 같은 접두사의 길이
                int same = sameCount(words[i], words[j]);

                // 같은 접두사의 길이가 0이거나
                // 이전에 찾은 같은 접두사의 길이보다 더 적어졌다면
                // 정렬된 순서로 찾고 있기 때문에, 같은 접두사의 길이가 더 커질 수 없다.
                // 따라서 i번째 단어에서 찾는 것은 그만한다.
                if (same == 0 || same < currentSame)
                    break;
                // 같은 접두사의 길이가 더 길어졌거나
                // 길이는 같지만, 입력 순이 더 이른 단어가 찾아졌다면 값을 교체
                else if (currentSame < same || hashMap.get(words[j]) < candidateIdxes[1]) {
                    currentSame = same;
                    candidateIdxes[1] = hashMap.get(words[j]);
                }
            }
            // 후보자 단어들의 순서 정렬
            Arrays.sort(candidateIdxes);

            // 여태 찾았던 기록보다 더 긴 같은 접두사의 길이는 갖는지
            // 혹은 같다면, 더 입력이 이른지 비교하여 값을 교체한다.
            if (maxSame < currentSame ||
                    (maxSame == currentSame && (candidateIdxes[0] < idxes[0] || (candidateIdxes[0] == idxes[0] && candidateIdxes[1] < idxes[1])))) {
                maxSame = currentSame;
                idxes = candidateIdxes;
            }
        }
        // 찾은 두 단어 출력
        System.out.println(reversed.get(idxes[0]) + "\n" + reversed.get(idxes[1]));
    }

    // 두 문자열의 같은 접두사의 길이를 출력한다.
    static int sameCount(String a, String b) {
        int length = Math.min(a.length(), b.length());
        for (int i = 0; i < length; i++) {
            if (a.charAt(i) != b.charAt(i))
                return i;
        }
        return length;
    }
}