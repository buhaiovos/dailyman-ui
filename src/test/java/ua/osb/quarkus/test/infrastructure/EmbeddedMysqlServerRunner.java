package ua.osb.quarkus.test.infrastructure;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EmbeddedMysqlServerRunner {
    private static volatile EmbeddedMysqlServerRunner instance;

    private final EmbeddedMysql mysqld;

    public void close() {
        this.mysqld.stop();
    }

    public static EmbeddedMysqlServerRunner start() {
        EmbeddedMysql server;
        synchronized (EmbeddedMysqlServerRunner.class) {
            if (instance == null) {
                server = startServer();
                instance = new EmbeddedMysqlServerRunner(server);
            }
        }
        return instance;
    }

    private static EmbeddedMysql startServer() {
        MysqldConfig config = configureMysql57WithTestUser();
        return EmbeddedMysql.anEmbeddedMysql(config)
                .addSchema("dailyman")
                .start();
    }

    private static MysqldConfig configureMysql57WithTestUser() {
        Version version = Version.v5_7_latest;
        return aMysqldConfig(version)
                .withCharset(UTF8)
                .withPort(3307)
                .withUser("testU", "testP")
                .withTimeZone("UTC")
                .build();
    }
}
