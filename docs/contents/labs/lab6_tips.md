### Tips
- How does Servlet get the user? 
  It invokes `HttpServletRequest.getUserPrincipal()` to get the user info.

- Yon use always use the agent API to capture user info in your code. 

  ```
  @Trace(dispatcher = true)
  public void run() {
      NewRelic.setUserId("example-user-id");
  }

  ```

