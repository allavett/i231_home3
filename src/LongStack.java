/*
 * Implement an abstract data type "Stack of long integers" (LIFO) using linkedlists. String representation of a stack
 * (provided by toString method) must be ordered from bottom to top (tos is the last element).
 * List of compulsory operations:
 * Constructor for a new stack: LongStack()
 * Copy of the stack: Object clone()
 * Check whether the stack is empty: boolean stEmpty()
 * Adding an element to the stack: void push (long a)
 * Removing an element from the stack: long pop()
 * Arithmetic operation s ( + - * / ) between two topmost elements of the stack (result is left on top): void op
 * (String s)
 * Reading the top without removing it: long tos()
 * Check whether two stacks are equal: boolean equals (Object o)
 * Conversion of the stack to string (top last): String toString()
 *
 * Write a method
 * public static long interpret (String pol)
 * to calculate the value of an arithmetic expression pol in RPN (Reverse Polish Notation) using this stack type.
 * Expression is a string which contains long integers (including negative and multi-digit numbers) and arithmetic
 * operations  + - * /  separated by whitespace symbols. The result must be a long integer value of the expression or
 * throwing a RuntimeException in case the expression is not correct. Expression is not correct if it contains illegal
 * symbols, leaves redundant elements on top of stack or causes stack underflow.
 * Example. LongStack.interpret ("2 15 -") should return  -13 .
 *
 * Largely based on the LIFO example from: http://enos.itcollege.ee/~jpoial/algoritmid/adt.html
 * Getting useful information from SO: https://stackoverflow.com/questions/3481828/how-to-split-a-string-in-java
 * Regular expressions: https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
 *
 */

import java.util.LinkedList;

public class LongStack {

   private LinkedList<Long> lS;

   public static void main (String[] argum) {
      // TODO!!! Your tests here!
      LongStack longStack = new LongStack();
      longStack.push(12L);
      longStack.push(3);

      System.out.println(longStack.interpret(" + 1 2  3 + "));
   }

   private LongStack(LinkedList<Long> lS){ this.lS = lS;}

   LongStack() {
      this(new LinkedList<Long>());
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      LongStack lsClone = new LongStack();
      if (!this.stEmpty()){
         for (int i = this.lS.size() - 1; i >= 0; i--) {
            lsClone.push(this.lS.get(i));
         }
      }
      return lsClone;
   }

   public boolean stEmpty() {
      if (this.lS.size() <= 0) return true;
      return false;
   }

   public void push (long a) {
      this.lS.push(a);
   }

   public long pop() {
      if (this.stEmpty()) throw new RuntimeException("Not enough numbers in stack! Can't complete the action.");
      return this.lS.pop();
   } // pop

   public void op (String s) {
      if (this.stEmpty()) throw new RuntimeException("Not enough numbers in the stack preceding the operator \"" + s
              + "\"! " + "Can't complete the calculation.");
      Long pop1 = this.pop();
      Long pop2 = this.pop();
      switch (s) {
         case "+": this.push(pop1 + pop2);
         break;
         case "-": this.push(pop2 - pop1);
         break;
         case "*": this.push(pop1 * pop2);
         break;
         case "/": this.push(pop2 / pop1);
         break;
         default: throw new RuntimeException("\"" + s + "\" is not a valid operator!");
      }
   }
  
   public long tos() {
      if (this.stEmpty()) throw new RuntimeException("Top of the stack is empty!");
      return this.lS.get(0);
   }

   @Override
   public boolean equals (Object o) {
      LongStack obj = (LongStack)o;
      if (this.stEmpty() && obj.stEmpty()) return true;
      if (!this.stEmpty() && !obj.stEmpty()){
         if (this.lS.size() != obj.lS.size()) return false;
         for (int i = 0; i < this.lS.size(); i++) {
            if (this.lS.get(i) != obj.lS.get(i)){
               return false;
            }
         }
         return true;
      }
      return false;
   }

   @Override
   public String toString() {
      if (this.stEmpty()) return "";
      StringBuilder b = new StringBuilder();
      for (int i = this.lS.size() - 1; i >= 0; i--) {
         b.append(this.lS.get(i)).append(" ");
      }
      return b.toString();
   }

   public static long interpret (String pol) {
      int countNumbers = 0;
      int countOperators = 0;
      if (pol.isEmpty()) throw new RuntimeException("Input is empty!");
      LongStack longStack = new LongStack();
      String[] stringList = pol
              .replaceFirst("\\s*", "")
              .split("\\s++");
      if (stringList[0].isEmpty()) throw new RuntimeException("No valid elements found from input! " +
              "Input contains: \"" + pol + "\"");
      for (String item: stringList) {
         if (item.matches("[+-]?\\d+")) {
            countNumbers++;
         } else if (item.matches("[+-/*]?")) {
            countOperators++;
         } else {
            throw new RuntimeException("\"" + item + "\" is not a valid element! Input contains: \"" + pol + "\"");
         }
      }
      if (countNumbers <= countOperators) throw new RuntimeException("Too many operators for calculation! " +
              "Check Your input: \"" + pol + "\"");
      if (countNumbers > countOperators + 1) throw new RuntimeException("Too many numbers for calculation! " +
              "Check Your input: \"" + pol + "\"");
      for (String item: stringList) {
         if (item.matches("[+-]?\\d+")){
            longStack.push(Long.parseLong(item));
         } else {
            longStack.op(item);
         }
      }
      return longStack.tos();
   }

}

