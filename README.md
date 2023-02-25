# Moka
[![MIT License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/license/mit/)

A framework to ease integration tests of applications using [Web3j](https://github.com/web3j/web3j).

Built with [Testcontainers](https://www.testcontainers.org/) and [Ganache](https://trufflesuite.com/ganache/).

## Getting started

### Pre-requisites
Using Moka requires **JDK 11 or greater**.

### Installing

To use Moka with JUnit 5/Jupiter, add the following dependencies to your project:
```xml
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>${testcontainters-junit-jupiter.version}</version>
</dependency>

<dependency>
    <groupId>com.github.maximevw</groupId>
    <artifactId>moka</artifactId>
    <version>${moka.version}</version>
</dependency>
```

## Usage

With JUnit 5/Jupiter:

```java
import com.github.maximevw.moka.GanacheContainer;
import com.github.maximevw.moka.asserts.BalanceAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.web3j.protocol.Web3j;

@Testcontainers
class Web3Test {

    private Web3ExampleService underTest;

    @Container
    private static final GanacheContainer<?> container = new GanacheContainer<>();

    @BeforeAll
    static void setupTests() {
        container.waitUntilGanacheIsReady();
    }

    @Test
    void exampleTest() {
        TestingAccount account1 = container.getTestingAccount(0);
        TestingAccount account2 = container.getTestingAccount(1);
        Web3j web3j = container.getWeb3j();
        underTest.transfer(web3j, account1.getPrivateKey(), account1.getAddress(), account2.getAddress(), 10);
        BalanceAssertions.assertBalanceDecreased(account1);
        BalanceAssertions.assertBalanceIncreased(account2);
    }

}
```

## Versioning

Moka uses [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## License

Moka is distributed under [MIT License](https://opensource.org/license/mit/).

## Known issues

### The container fails to start

In some circumstances (depending on the Docker and/or Linux distribution/version), you might fail to start the Ganache
container with the following error: `cgroups: cgroup mountpoint does not exist: unknown`. In this, case a solution is to
execute the following commands:
```bash
sudo mkdir /sys/fs/cgroup/systemd
sudo mount -t cgroup -o none,name=systemd cgroup /sys/fs/cgroup/systemd
```
Source: [Docker for Linux - issue #219](https://github.com/docker/for-linux/issues/219).

It's a quick fix and probably not the best solution, alternative solutions can be found here:
* [Enabling CPU, CPU set and IO delegation with cgroup v2](https://rootlesscontaine.rs/getting-started/common/cgroup2/#enabling-cpu-cpuset-and-io-delegation)
* [Re-installing Docker](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-22-04)
