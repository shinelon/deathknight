package com.shinelon.deathknight.demo;

import org.junit.jupiter.api.Test;

public class SeqTest {
  /***
   * 找出字符串中最长连续字符串
   */
  @Test
  public void seqSearch() {
    String str = "ddabcedefghioa";
    int maxLength = 0;
    int startIdx = 0;
    int endIdx = 0;
    for (int i = 0; i < str.length(); i++) {
      int j = i + 1;
      while (j < str.length()) {
        int current = (int) str.charAt(j);
        int pre = (int) str.charAt(j - 1);
        if (current - pre == 1) {
          j++;
          int tmpLength = j - i;
          if (tmpLength > maxLength) {
            startIdx = i;
            endIdx = j;
            maxLength = tmpLength;
          }
        } else {
          break;
        }
      }
    }
    System.out.println("maxLength:" + maxLength + " startIdx:" + startIdx + " endIdx:" + endIdx);
    System.out.println("string:" + str.substring(startIdx, endIdx + 1));
  }
}
