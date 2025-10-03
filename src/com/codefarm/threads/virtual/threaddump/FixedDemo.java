package com.codefarm.threads.virtual.threaddump; 

import java.util.*;
import java.util.concurrent.*;

public class FixedDemo {

  public static class TextProcessor {
    private Set<String> specialChars = Set.of("$", "*", "_", "^");

    public String processText(String input) {
      if (input == null || input.isEmpty()) return "";

      long startTime = System.currentTimeMillis();

      try {
        return doProcessText(input);
      } finally {
        long duration = System.currentTimeMillis() - startTime;
        if (duration > 100) { // Log operations taking >100ms
          System.out.println("Processing took " + duration + "ms for length " + input.length());
        }
      }
    }

    private String doProcessText(String input) {
      Stack<String> markdownStack = new Stack<>();
      StringBuilder result = new StringBuilder();
      int processedChars = 0;
      int targetLength = input.length() / 3;

      int i = 0;
      int iterationCount = 0;
      final int MAX_ITERATIONS = input.length() * 2;

      while ((processedChars < targetLength && i < input.length())
          || (!markdownStack.isEmpty()
              && i < input.length()
              && (peekStack(markdownStack).equals("$") || peekStack(markdownStack).equals("$$")))) {

        // Safety check for infinite loops
        if (++iterationCount > MAX_ITERATIONS) {
          System.err.println("WARNING: Breaking loop to prevent infinite processing");
          break;
        }

        if (i >= input.length()) break;

        char currentChar = input.charAt(i);
        String currentStr = String.valueOf(currentChar);

        if (specialChars.contains(currentStr)) {
          if (currentStr.equals("$")) {
            String stackTop = peekStack(markdownStack);
            if (stackTop.equals("$")) {
              markdownStack.pop();
            } else {
              markdownStack.push("$");
            }
          }
        } else {
          if (markdownStack.isEmpty()) {
            processedChars++;
          }
          result.append(currentChar);
        }

        i++;
      }

      return result.toString();
    }

    // FIXED: Efficient stack peeking without exceptions
    private String peekStack(Stack<String> stack) {
      if (stack.isEmpty()) {
        return ""; // No exception thrown
      }
      return stack.peek();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    TextProcessor processor = new TextProcessor();

    String[] testInputs = {
      "$",
      "$$",
      "Text with unmatched $ symbols $ $ $ throughout",
      "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$",
      "Normal text with $math$ and $$display$$",
      "**Bold** text with $inline$ math"
    };

    System.out.println("Testing fixed version...");

    ExecutorService executor = Executors.newFixedThreadPool(4);
    CountDownLatch latch = new CountDownLatch(testInputs.length);

    for (int i = 0; i < testInputs.length; i++) {
      final String input = testInputs[i];
      final int index = i;

      executor.submit(
          () -> {
            try {
              long start = System.currentTimeMillis();
              String result = processor.processText(input);
              long duration = System.currentTimeMillis() - start;

              System.out.println(
                  "Test "
                      + index
                      + " completed in "
                      + duration
                      + "ms, result length: "
                      + result.length());
            } finally {
              latch.countDown();
            }
          });
    }

    // Wait for all tests to complete
    if (latch.await(10, TimeUnit.SECONDS)) {
      System.out.println("All tests completed successfully!");
    } else {
      System.err.println("Some tests timed out - possible infinite loop still present");
    }

    executor.shutdown();
  }
}
