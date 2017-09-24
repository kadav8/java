package com.example.stack;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class StackWalk {

  public List<String> getStackTrace() {
    return StackWalker.getInstance(EnumSet.of(StackWalker.Option.SHOW_HIDDEN_FRAMES))
		    .walk(s -> s.map(frame -> formatFrame(frame)).collect(Collectors.toList()));
  }

  private String formatFrame(StackWalker.StackFrame frame) {
    return frame.getClassName() + "." + frame.getMethodName() + ":" + frame.getLineNumber();
  }

  public void printStackTrace() {
    getStackTrace().forEach(line -> System.out.println(line));
  }

  public static void main(String args[]) {
    new StackWalk().printStackTrace();
  }
}
