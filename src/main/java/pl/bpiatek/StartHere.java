package pl.bpiatek;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

//@QuarkusMain it is commented out since we have web server running
class StartHere {
  public static void main(String... args) {
    System.out.println("Hello Quarkus!");
    Quarkus.run(args);
  }
}
