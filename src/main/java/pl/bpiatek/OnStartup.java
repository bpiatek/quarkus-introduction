package pl.bpiatek;

import io.quarkus.arc.profile.UnlessBuildProfile;
import io.quarkus.runtime.Startup;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@UnlessBuildProfile(value = "test") // run this only in non-test profiles
public class OnStartup {

  private final MySQLPool pool;

  public OnStartup(MySQLPool pool) {
    this.pool = pool;
  }

  @Startup
  void onStart() {
    System.out.println("Startup event fired! Inserting fruits...");
    pool.query("INSERT INTO fruit (id,name) VALUES (1,'apple'), (2,'banana')")
        .execute()
        .await().indefinitely();
  }
}
