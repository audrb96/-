# TIL

## 2022.09.05
# **혼동되는 synchronized 키워드 정리**

> 참고 블로그 및 레퍼런스:
>

[[Java] 혼동되는 synchronized 동기화 정리](https://jgrammer.tistory.com/entry/Java-%ED%98%BC%EB%8F%99%EB%90%98%EB%8A%94-synchronized-%EB%8F%99%EA%B8%B0%ED%99%94-%EC%A0%95%EB%A6%AC)

[[Java] synchronized 키워드란?](https://steady-coding.tistory.com/556)

---

# 🕍synchronized

자바는 크게 3가지 메모리 영역을 가지고 있다.

- static 영역
- heap 영역
- stack 영역

자바 멀티 스레드 환경에서는 스레드끼리 static 영역과 heap 영역을 공유하므로 공유 자원에 대한 동기화 문제를 신경 써야 한다. 이번 페이지에서는 synchronized 키워드를 통해 lock을 이용해 동기화를 수행하는 4가지 방법을 알아보겠다.

## 1️⃣. synchronized method

```java
public class Method {
    public static void main(String[] args) {
        Method method = new Method();

        Thread thread1 = new Thread(() -> {
            System.out.println("스레드1 시작 " + LocalDateTime.now());
            method.syncMethod1("스레드1");
            System.out.println("스레드1 종료 " + LocalDateTime.now());
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("스레드2 시작 " + LocalDateTime.now());
            method.syncMethod2("스레드2");
            System.out.println("스레드2 종료 " + LocalDateTime.now());
        });

        thread1.start();
        thread2.start();
    }

    private synchronized void syncMethod1(String msg) {
        System.out.println(msg + "의 syncMethod1 실행중" + LocalDateTime.now());
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println(msg + "의 syncMethod1 실행완료" + LocalDateTime.now());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void syncMethod2(String msg) {
        System.out.println(msg + "의 syncMethod2 실행중" + LocalDateTime.now());
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println(msg + "의 syncMethod2 실행완료" + LocalDateTime.now());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

Method 인스턴스를 한개 생성하고, 두 개의 스레드를 만들어 각각 synchronized 키워드가 붙은 **syncMethod1()**, **syncMethod2()**를 호출하고 결과를 보자.

```csharp
스레드2 시작 2022-03-26T00:49:45.347198200
스레드1 시작 2022-03-26T00:49:45.347198200
스레드2의 syncMethod2 실행중2022-03-26T00:49:45.348191400
스레드2의 syncMethod2 실행완료2022-03-26T00:49:50.350685800
스레드2 종료 2022-03-26T00:49:50.350685800
스레드1의 syncMethod1 실행중2022-03-26T00:49:50.350685800
스레드1의 syncMethod1 실행완료2022-03-26T00:49:55.354796100
스레드1 종료 2022-03-26T00:49:55.354796100
```

스레드 1이 syncMethod1()을 호출한 후, **종료된 다음** 스레드 2가 syncMethod2()를 호출한 것을 볼 수 있다.

위 예시는 하나의 인스턴스를 각각의 다른 스레드로 실행한 경우이다. 이제 각각의 인스턴스를 만들고 스레드들이 메소드를 호출하도록 해보자.

```java
public static void main(String[] args) {

        Method method1 = new Method();
        Method method2 = new Method();

        Thread thread1 = new Thread(() -> {
            System.out.println("스레드1 시작 " + LocalDateTime.now());
            method1.syncMethod1("스레드1");
            System.out.println("스레드1 종료 " + LocalDateTime.now());
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("스레드2 시작 " + LocalDateTime.now());
            method2.syncMethod2("스레드2");
            System.out.println("스레드2 종료 " + LocalDateTime.now());
        });
		....
}
```

```csharp
스레드2 시작 2022-03-26T18:47:10.651163900
스레드2의 syncMethod2 실행중2022-03-26T18:47:10.653162300
스레드1 시작 2022-03-26T18:47:10.653162300
스레드1의 syncMethod1 실행중2022-03-26T18:47:10.653162300
스레드1의 syncMethod1 실행완료2022-03-26T18:47:15.658362400
스레드2의 syncMethod2 실행완료2022-03-26T18:47:15.658362400
스레드1 종료 2022-03-26T18:47:15.658362400
스레드2 종료 2022-03-26T18:47:15.658362400
```

이 상황에서는 **lock을 공유하지 않기 때문**에 스레드 간의 동기화가 발생하지 않는다. 결과를 보면 알 수 있듯이, synchronized method는 인스턴스에 lock을 건다. 인스턴스에 lock을 건다고 표현해서 인스턴스 접근 자체에 lock이 걸린다고 혼동할 수 있는데 그건 아니다.

아래 예제를 살펴 보자.

```java
public class Method {
    public static void main(String[] args) {

        Method method = new Method();

        Thread thread1 = new Thread(() -> {
            System.out.println("스레드1 시작 " + LocalDateTime.now());
            method.syncMethod1("스레드1");
            System.out.println("스레드1 종료 " + LocalDateTime.now());
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("스레드2 시작 " + LocalDateTime.now());
            method.syncMethod2("스레드2");
            System.out.println("스레드2 종료 " + LocalDateTime.now());
        });

        Thread thread3 = new Thread(() -> {
            System.out.println("스레드3 시작 " + LocalDateTime.now());
            method.method3("스레드3");
            System.out.println("스레드3 종료 " + LocalDateTime.now());
        });

        thread1.start();
        thread2.start();
        thread3.start();
 }

...

private void method3(String msg) {
        System.out.println(msg + "의 method3 실행중" + LocalDateTime.now());
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println(msg + "의 syncMethod3 실행완료" + LocalDateTime.now());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
```

```csharp
스레드3 시작 2022-03-27T00:14:02.951159800
스레드1 시작 2022-03-27T00:14:02.951159800
스레드1의 syncMethod1 실행중2022-03-27T00:14:02.952160700
스레드2 시작 2022-03-27T00:14:02.950161300
스레드3의 method3 실행중2022-03-27T00:14:02.952160700
스레드1의 syncMethod1 실행완료2022-03-27T00:14:07.965880400
스레드3의 syncMethod3 실행완료2022-03-27T00:14:07.965880400
스레드2의 syncMethod2 실행중2022-03-27T00:14:07.965880400
스레드1 종료 2022-03-27T00:14:07.965880400
스레드3 종료 2022-03-27T00:14:07.965880400
스레드2의 syncMethod2 실행완료2022-03-27T00:14:12.969255600
스레드2 종료 2022-03-27T00:14:12.970237800
```

출력된 값을 보면 스레드3의 method3는 동기화가 발생하지 않은 것을 확인할 수 있다.

**즉, synchronizeed 메소드는 인스턴스 단위로 lock을 걸지만, synchronized 키워드가 붙은 메소드들에 대해서만 lock을 공유한다.**

쉽게 말해서, 한 스레드가 synchronzied 메소드를 호출하는 순간, 모든 synchronized 메소드에 lock이 걸린므로 다른 스레드에 어떠한 synchronized 메소드를 호출 할 수 없고, **일반 메소드는 호출이 가능하다.**

## 2️⃣. static synchronized method

- **static 키워드가 포함된 synchronized 메소드는 인스턴스가 아닌 클래스 단위로 lock을 공유**한다.

```java
public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            System.out.println("스레드1 시작 " + LocalDateTime.now());
            syncStaticMethod1("스레드1");
            System.out.println("스레드1 종료 " + LocalDateTime.now());
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("스레드2 시작 " + LocalDateTime.now());
            syncStaticMethod2("스레드2");
            System.out.println("스레드2 종료 " + LocalDateTime.now());
        });

        thread1.start();
        thread2.start();
}

private **static** synchronized void syncStaticMethod1(String msg){ ... }
private **static** synchronized void syncStaticMethod2(String msg){ ... }
```

method의 코드는 동일하고 변한 건 함수에 static 키워드와 main에서 static call만 한 것이다.

```csharp
스레드1 시작 2022-03-27T00:41:08.723371100
스레드2 시작 2022-03-27T00:41:08.722368900
스레드1의 syncStaticMethod1 실행중2022-03-27T00:41:08.724383100
스레드1의 syncStaticMethod1 실행완료2022-03-27T00:41:13.730391500
스레드2의 syncStaticMethod2 실행중2022-03-27T00:41:13.730391500
스레드1 종료 2022-03-27T00:41:13.730391500
스레드2의 syncStaticMethod2 실행완료2022-03-27T00:41:18.737957500
스레드2 종료 2022-03-27T00:41:18.737957500
```

**synchronized 메소드처럼 lock을 공유하여 메소드 간이 동기화가 지켜지고 있다**. 다만, 여기서 중요한 점은 static synchronized 메소드의 경우 **인스턴스 단위로 lock을 공유하는 것이 아닌 클래스 단위로 lock을 공유한다는 사실**을 기억해야 한다.

만약 이 상태에서 synchronized 메소드를 추가한다면 어떻게 될까?

```csharp
public static void main(String[] args) {
     StaticSynchronizedMethod staticSynchronizedMethod = new StaticSynchronizedMethod();

     Thread thread1 = new Thread(() -> {
         System.out.println("스레드1 시작 " + LocalDateTime.now());
			...

     Thread thread3 = new Thread(() -> {
         System.out.println("스레드3 시작 " + LocalDateTime.now());
         staticSynchronizedMethod.syncStaticMethod3("스레드2");
         System.out.println("스레드3 종료 " + LocalDateTime.now());
     })

     thread1.start();
     thread2.start();
		 thread3.start();
}
private synchronized void syncStaticMethod3(String msg) {
        System.out.println(msg + "의 syncMethod3 실행중" + LocalDateTime.now());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

```

```csharp
스레드1 시작 2022-03-27T01:02:23.199931800
스레드2 시작 2022-03-27T01:02:23.198940
스레드3 시작 2022-03-27T01:02:23.199931800
스레드1의 syncStaticMethod1 실행중2022-03-27T01:02:23.200926100
스레드2의 syncMethod3 실행중2022-03-27T01:02:23.200926100
스레드3 종료 2022-03-27T01:02:28.204575400
스레드1의 syncStaticMethod1 실행완료2022-03-27T01:02:28.204575400
스레드2의 syncStaticMethod2 실행중2022-03-27T01:02:28.204575400
스레드1 종료 2022-03-27T01:02:28.204575400
스레드2의 syncStaticMethod2 실행완료2022-03-27T01:02:33.219410900
스레드2 종료 2022-03-27T01:02:33.219410900
```

static synchronized 메소드를 사용하는 스레드1과 스레드 2간에는 동기화가 잘 지켜지는 것을 확인할 수 있다. 하지만, **synchronized 메소드를 사용한 스레드 3은 개발자가 의도한 동기화가 지켜지지 않았다**. 정리하자면, **클래스 단위에 거는 lock과 인스턴스 단위에 거는 lock은 공유가 되지 않기 때문**에 혼용해서 사용하면 동기화 이슈가 생긴다.

## 3️⃣. synchronized block - (this)

synchronzied block은 인스턴스의 block 단위로 lock을 걸며, 2가지의 사용방법이 있다.

- synchronized(this)
- synchronized(Object)

### 1) synchronized(this)

```java
public class SynchronizedBlock {
    public static void main(String[] args) {
        SynchronizedBlock synchronizedBlock = new SynchronizedBlock();

        Thread thread1 = new Thread(() -> {
            System.out.println("스레드1 시작 " + LocalDateTime.now());
            synchronizedBlock.syncBlockMethod1("스레드1");
            System.out.println("스레드1 종료 " + LocalDateTime.now());
        });

        Thread thread2 = new Thread(() ->{
            System.out.println("스레드2 시작 " + LocalDateTime.now());
            synchronizedBlock.syncBlockMethod2("스레드2");
            System.out.println("스레드2 종료 " + LocalDateTime.now());
        });
        thread1.start();
        thread2.start();
    }

    private void syncBlockMethod1(String message) {
        synchronized (this){
            System.out.println(message + "의 syncBlockMethod1 실행중" + LocalDateTime.now());
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(message + "의 syncBlockMethod1 실행완료"+ LocalDateTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void syncBlockMethod2(String message) {
        synchronized (this){
            System.out.println(message + "의 syncBlockMethod2 실행중" + LocalDateTime.now());
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(message + "의 syncBlockMethod2 실행완료"+ LocalDateTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

```csharp
스레드2 시작 2022-03-27T15:03:07.321336500
스레드2의 syncBlockMethod2 실행중2022-03-27T15:03:07.325359700
스레드1 시작 2022-03-27T15:03:07.321336500
스레드2의 syncBlockMethod2 실행완료2022-03-27T15:03:12.326752200
스레드2 종료 2022-03-27T15:03:12.326752200
스레드1의 syncBlockMethod1 실행중2022-03-27T15:03:12.326752200
스레드1의 syncBlockMethod1 실행완료2022-03-27T15:03:17.339315700
스레드1 종료 2022-03-27T15:03:17.339315700
```

sychronized 인자 값으로 this를 사용하면 모든 synchronized block에 lock이 걸린다. 쉽게 말하면, **여러 스레드가 들어와서 서로 다른 synchronized block을 호출해도 this를 사용해 자기 자신에 lock을 걸었으므로 기다려야 한다**.

## 4️⃣. synchronized block - (Object)

synchronized(this)는 모든 synchronized 블록에 lock이 걸리므로 상황에 따라 비효율적일 수 있다. 따라서, **synchronized(Object) 방식으로 블록마다 다른 lock이 걸리게 하여 훨씬 효율적**이게 할 수 있다.

```java
public class SynchronizedObjectBlock {
    private final Object obj1 = new Object();
    private final Object obj2 = new Object();

    public static void main(String[] args) {
        SynchronizedObjectBlock synchronizedObjectBlock = new SynchronizedObjectBlock();
        Thread thread1 = new Thread(() -> {
            System.out.println("스레드1 시작 " + LocalDateTime.now());
            synchronizedObjectBlock.syncBlockMethod1("스레드1");
            System.out.println("스레드1 종료 " + LocalDateTime.now());
        });

        Thread thread2 = new Thread(() ->{
            System.out.println("스레드2 시작 " + LocalDateTime.now());
            synchronizedObjectBlock.syncBlockMethod2("스레드2");
            System.out.println("스레드2 종료 " + LocalDateTime.now());
        });
        thread1.start();
        thread2.start();
    }

    private void syncBlockMethod1(String msg) {
        synchronized (obj1) {
            System.out.println(msg + "의 syncBlockMethod1 실행중" + LocalDateTime.now());
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(msg + "의 syncBlockMethod1 실행완료" + LocalDateTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void syncBlockMethod2(String msg) {
        synchronized (obj2) {
            System.out.println(msg + "의 syncBlockMethod2 실행중" + LocalDateTime.now());
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(msg + "의 syncBlockMethod2 실행완료" + LocalDateTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

```csharp
스레드1 시작 2022-03-27T15:39:08.857358400
스레드2 시작 2022-03-27T15:39:08.858353700
스레드1의 syncBlockMethod1 실행중2022-03-27T15:39:08.858353700
스레드2의 syncBlockMethod2 실행중2022-03-27T15:39:08.858353700
스레드2의 syncBlockMethod2 실행완료2022-03-27T15:39:13.860237500
스레드1의 syncBlockMethod1 실행완료2022-03-27T15:39:13.860237500
스레드2 종료 2022-03-27T15:39:13.860237500
스레드1 종료 2022-03-27T15:39:13.860237500
```

도익화가 지쳐지지 않은 것을 확인할 수 있다. 따라서 this가 아닌 객체를 만들어 인자를 넘겨주면 동시에 lock이 걸려야 하는 부분을 따로 지정해줘야한다.

# 결론:

### synchronized method

- **synchronized method = synchronized(this) { }**
- **현재 인스턴스에서만 동기화가 발생**. 이 경우 2개 이상의 인스턴스가 있다면, 각각의 Instance에 대해 동기화가 일어나므로 두 개의 Instance가 각각의 method를 실행하는 것이 가능

### static synchronized method

- **해당 클래스의 클래스 객체에 대해 동기화 발생.**
- 따라서, static한 member 변수를 동기화 하려면 static synchronized를 사용해야한다.

### 추가

**public synchronized void methodA() { ... }** 는
**public void methodA() { synchronized(this) { ... } }** 와 동일하며,

**public static synchronized void methodA() { ... }** 는
**public static void methodA() { synchronized(TargetClass.class) { ... } }** 와 동일하다.
