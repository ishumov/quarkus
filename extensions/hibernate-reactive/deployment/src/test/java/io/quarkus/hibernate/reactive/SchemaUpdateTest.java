package io.quarkus.hibernate.reactive;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.reactive.mutiny.Mutiny;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;
import io.smallrye.mutiny.Uni;

public class SchemaUpdateTest {

    @RegisterExtension
    static QuarkusUnitTest runner = new QuarkusUnitTest()
            .withApplicationRoot((jar) -> jar
                    .addClass(Hero.class))
            .withConfigurationResource("application.properties")
            .overrideConfigKey("quarkus.hibernate-orm.database.generation", "update");

    @Inject
    Mutiny.SessionFactory sessionFactory;

    @Test
    public void integerIdentifierWithStageAPI() {
        final Uni<List<Object>> result = sessionFactory.withSession(s -> s
                .createQuery("from Hero h where h.name = :name").setParameter("name", "Galadriel").getResultList());
        final List<Object> list = result.await().indefinitely();
        assertThat(list).isEmpty();
    }

    @Entity(name = "Hero")
    @Table(name = "hero")
    public static class Hero {

        @javax.persistence.Id
        @javax.persistence.GeneratedValue
        public java.lang.Long id;

        @Column(unique = true)
        public String name;

        public String otherName;

        public int level;

        public String picture;

        @Column(columnDefinition = "TEXT")
        public String powers;

    }

}
