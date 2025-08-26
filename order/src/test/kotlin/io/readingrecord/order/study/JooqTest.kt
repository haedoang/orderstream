package io.readingrecord.order.study

import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.Table
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class JooqTest {

    @Autowired
    private lateinit var dslContext: DSLContext

    private val USER: Table<*> = DSL.table("TB_USER")
    private val ID: Field<Int> = DSL.field("ID", SQLDataType.INTEGER)
    private val USERNAME: Field<String> = DSL.field("USERNAME", SQLDataType.VARCHAR(100))

    @BeforeEach
    fun setup() {
        // 기존 테이블이 있으면 삭제
        dslContext.execute("DROP TABLE IF EXISTS TB_USER")

        // 테이블 생성
        dslContext.query("CREATE TABLE TB_USER (ID INT PRIMARY KEY, USERNAME VARCHAR(100))").execute()
    }

    @DisplayName("저장된 row를 조회할 수 있다")
    @Test
    fun testInsertAndSelect() {
        // given
        dslContext.insertInto(USER)
            .set(ID, 1)
            .set(USERNAME, "tester")
            .execute()

        // when
        val username: String? = dslContext.select(USERNAME)
            .from(USER)
            .where(ID.eq(1))
            .fetchOne(USERNAME)

        // then
        assertThat(username).isEqualTo("tester")
    }

    @DisplayName("여러 데이터를 삽입할 수 있다")
    @Test
    fun testMultipleInsertAndSelect() {
        // gi ven
        dslContext.batch(
            dslContext.insertInto(USER)
                .set(ID, 1)
                .set(USERNAME, "user1"),
            dslContext.insertInto(USER)
                .set(ID, 2)
                .set(USERNAME, "user2"),
            dslContext.insertInto(USER)
                .set(ID, 3)
                .set(USERNAME, "user3")
        ).execute()

        // when
        val users = dslContext.select(ID, USERNAME)
            .from(USER)
            .orderBy(ID)
            .fetch()

        // then
        assertThat(users.size).isEqualTo(3)
        assertThat(users[0].get(USERNAME)).isEqualTo("user1")
        assertThat(users[1].get(USERNAME)).isEqualTo("user2")
        assertThat(users[2].get(USERNAME)).isEqualTo("user3")
    }
}
