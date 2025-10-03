package com.codefarm.threads.virtual.threaddump; 
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProblemDemo {

  public static class TextProcessor {
    private Set<String> specialChars = Set.of("$", "*", "_", "^");
    private boolean forceInfiniteLoop = false;

    public String processText(String input) {
      if (input == null || input.isEmpty()) return "";

      Stack<String> markdownStack = new Stack<>();
      StringBuilder result = new StringBuilder();
      int processedChars = 0;
      int targetLength = input.length() / 3;

      int i = 0;

      // THE ACTUAL PROBLEMATIC LOOP CONDITION
      // The key issue: peekStack() is called in the condition BEFORE checking if stack is empty
      while ((processedChars < targetLength && i < input.length())
          || (forceInfiniteLoop
              && (peekStack(markdownStack).equals("$") || peekStack(markdownStack).equals("$$")))
          || (peekStack(markdownStack).equals("$") || peekStack(markdownStack).equals("$$"))) {

        // Process current character
        if (i < input.length()) {
          char currentChar = input.charAt(i);
          String currentStr = String.valueOf(currentChar);

          if (specialChars.contains(currentStr)) {
            if (currentStr.equals("$")) {
              if (!markdownStack.isEmpty() && peekStack(markdownStack).equals("$")) {
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

        // CRITICAL BUG: When we reach end of input, trigger infinite loop
        if (i >= input.length()) {
          // Clear the stack to ensure peekStack() throws exceptions
          markdownStack.clear();

          // Force the loop to continue - this creates the infinite loop, intentionally added to simulate the prod issue
          forceInfiniteLoop = true;

        }
      }

      return result.toString();
    }

    // THE PROBLEMATIC METHOD - This will be called millions of times on empty stack
    private String peekStack(Stack<String> stack) {
      try {
        return stack.peek(); // EmptyStackException when stack is empty
      } catch (Exception e) {
        // This catch block executes millions of times
        // Each exception creation calls expensive fillInStackTrace()

        // CRITICAL: Return "$" to keep loop condition true and create infinite loop
        if (forceInfiniteLoop) {
          return "$"; // This makes loop condition true, causing infinite loop
        }
        return ""; // Returns empty string, so condition fails, but damage is done
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    TextProcessor processor = new TextProcessor();

    // These inputs will trigger the infinite loop
    // Key: inputs that will process some characters then hit end-of-input condition
    String[] problematicInputs = {
      "x$", // Will push $ then clear it, ending with empty stack
      "y$z", // Will process chars, push $, then hit empty stack condition
      "$x$",
    };

    System.out.println("Starting TRULY problematic processing...");
    System.out.println("This WILL cause infinite loops and high CPU usage!");
    System.out.println("Monitor with: top -p $(pgrep java)");
    System.out.println("Capture thread dump with: jstack $(pgrep java)");

    ExecutorService executor =
        Executors.newFixedThreadPool(4); // Fewer threads to see the effect clearly

    for (int i = 0; i < 4; i++) {
      final int threadId = i;
      executor.submit(
          () -> {
            String input = problematicInputs[threadId % problematicInputs.length];
            System.out.println(
                "Thread " + threadId + " starting infinite loop with input: '" + input + "'");

            long startTime = System.currentTimeMillis();

            // This should never complete - it will run forever throwing exceptions
            String result = processor.processText(input);

            // This line should never be reached
            long duration = System.currentTimeMillis() - startTime;
            System.out.println(
                "Thread " + threadId + " unexpectedly completed in " + duration + "ms");
          });
    }

    // Let it run to demonstrate the infinite loop
    System.out.println("Letting threads run for 60 seconds to demonstrate infinite loop...");
    Thread.sleep(60000);

    System.out.println("Force shutting down - threads should be stuck in infinite loops");
    executor.shutdownNow();

    if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
      System.out.println("SUCCESS: Threads are stuck in infinite loops as expected!");
      System.out.println("Check CPU usage and capture thread dumps to see the problem");
    }
  }
}
