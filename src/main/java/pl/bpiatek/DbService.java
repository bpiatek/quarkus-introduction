package pl.bpiatek;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
class DbService {


  private final MySQLPool pool;

  DbService(MySQLPool pool) {
    this.pool = pool;
  }

  Uni<List<Fruit>> getFruitNames() {
    return pool.query("SELECT name FROM fruit")
        .execute()
        .map(this::collectNames);
  }

  public record Fruit(String name) {
  }

  private List<Fruit> collectNames(RowSet<Row> rows) {
    var names = new ArrayList<Fruit>();
    for (Row row : rows) {
      names.add(new Fruit(row.getString("name")));
    }
    return names;
  }
}
