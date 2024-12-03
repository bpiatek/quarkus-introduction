package pl.bpiatek;

import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class DbServiceTest {

  @Inject
  MySQLPool pool;

  @Inject
  DbService dbService;

  @Test
  void shouldPrintOutFruits() {
    // given
    var apple = insertFruit("apple");
    var banana = insertFruit("banana");

    // when
    var fruits = dbService.getFruitNames().await().indefinitely();

    // then
    assertThat(fruits).containsExactlyInAnyOrder(apple, banana);
  }

  private DbService.Fruit insertFruit(String name) {
    pool.preparedQuery("INSERT INTO fruit (name) VALUES (?)")
        .execute(Tuple.of(name))
        .await().indefinitely();

    return new DbService.Fruit(name);
  }
}