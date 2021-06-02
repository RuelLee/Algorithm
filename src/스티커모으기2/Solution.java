package 스티커모으기2;

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        // 스티커의 시작과 끝이 이어진 형태
        // 해당 스티커를 뜯어내면 양쪽의 스티커는 사용하지 못한다.
        // 첫번째 스티커를 뜯어냈을 경우(마지막 스티커를 사용 가능)와 뜯지 않았을 경우(마지막 스티커 사용 불가)로 나눠서 계산해야한다.
        int[] sticker = {14, 6, 5, 11, 3, 9, 2, 10};

        int answer;
        if (sticker.length <= 3)    // 3이하라면 하나의 숫자만 선택 가능하므로 그 중 가장 큰 수를 뽑아내면 된다.
            answer = Arrays.stream(sticker).max().getAsInt();
        else {
            int[] dpFirstDetached = new int[sticker.length];
            int[] dpFirstNotDetached = new int[sticker.length];
            dpFirstDetached[0] = dpFirstDetached[1] = sticker[0];
            dpFirstNotDetached[1] = sticker[1];

            for (int i = 2; i < sticker.length; i++) {      // 현재 스티커를 뜯어냈을 경우와, 이전 스티커를 뜯어냈을 경우 중 큰 값을 저장한다.
                dpFirstDetached[i] = Math.max(dpFirstDetached[i - 2] + sticker[i], dpFirstDetached[i - 1]);
                dpFirstNotDetached[i] = Math.max(dpFirstNotDetached[i - 2] + sticker[i], dpFirstNotDetached[i - 1]);
            }
            // 첫번째 스티커를 뜯어냈을 경우엔 마지막 - 1번째 값이 최대, 안 뜯었을 땐 마지막 값이 최대. 둘 중 큰 값을 가져온다.
            answer = Math.max(dpFirstDetached[dpFirstDetached.length - 2], dpFirstNotDetached[dpFirstDetached.length - 1]);
        }
        System.out.println(answer);
    }
}