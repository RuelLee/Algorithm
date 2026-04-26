/*
 Author : Ruel
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Good_Bye_BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2 * n - (i + 1); j++)
                sb.append(" ");
            sb.append("*");
            for (int j = 0; j < n; j++)
                sb.append(" ");
            sb.append("*");
            for (int j = 0; j < i * 2 + 1; j++)
                sb.append(" ");
            sb.append("*");
            for (int j = 0; j < n - 1 - i; j++)
                sb.append(" ");
            sb.append("\n");
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - (i + 1); j++)
                sb.append(" ");
            sb.append("*");
            for (int j = 0; j < n + i * 2 + 1; j++)
                sb.append(" ");
            sb.append("*");
            for (int j = 0; j < n * 2 - i * 2 - 1; j++)
                sb.append(" ");
            sb.append("*");
            for (int j = 0; j < i; j++)
                sb.append(" ");
            sb.append("\n");
        }
        System.out.print(sb);
    }
}