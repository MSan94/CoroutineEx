# 스레드와 루퍼

- 안드로이드의 스레드는 크게 1개만 존재하는 메인스레드와 여러개의 백그라운드 스레드로 나뉜다.

## 메인스레드(UI 스레드)
- 화면의 UI 처리 담당
- 안드로이드 UI 툴킷 구성요소와 상호작용하고 UI 이벤트를 사용자에게 응답
- UI 이벤트 및 작업에 대해 수 초 내에 응답하지 않으면 안드로이드 시스템은 ANR 표시
- 즉, 오래 걸리는 코드는 새로운 스레드 생성으로 처리해야 함

## 백그라운드 스레드
- 네트워크 작업, 파일 업로드와 다운로드, 이미지 처리, 데이터 로딩등을 수행
```
// 객체
class WorkerThread : Thread() {
    override fun run() {
        ....
    }
}

// Runnable 인터페이스
class WorkerRunnable : Runnable{
    override fun run(){
        ...
    }
}

var thread = Thread(WorkerRunnable())
thread.start()

// 람다식 Runnable 익명 객체
Thread {
    ...
}

// 코틀린 thread()
thread(start = true){
    ...
}
```


# 핸들러와 루퍼
- 안드로이드는 메인 스레드와 백그라운드 스레드 및 스레드간 통신을 위해 핸들러와 루퍼 제공

## 작동 원리
- 메인 스레드는 내부적으로 루퍼를 가지며 루퍼는 Message Queue를 포함
- Message Queue는 다른 스레드 혹은 스레드 자기 자신으로부터 전달받은 메시지를 보관하는 Queue이다.
- 루퍼는 Message Queue에서 메시지, Runnable 객체를 차례로 꺼내서 핸들러가 처리하도록 전달
- 핸들러는 루퍼로부터 받은 메시지, Runnable 객체를 처리하거나 메시지를 받아서 Message Queue에 넣는 스레드 간 통신 장치

## 루퍼
- MainActivity가 실행됨과 동시에 for문 하나가 무한루프를 돌고 있는 서브 스레드
- 대기하고 있다가 자신의 큐에 쌓인 메시지를 핸들러에 전달
- 여러 개의 백그라운드에서 큐에 메시지를 입력하면, 입력된 순서대로 하나씩 꺼내서 핸들러에 전달

## 핸들러
- 핸들러는 루퍼가 있는 메인 스레드에서 주로 사용되며 새로 생성된 스레드들과 메인 스레드와의 통신 담당
- 핸들러는 루퍼를 통해 전달되는 메시지를 받아서 처리하는 일종의 명령어 처리기로 사용
- 루퍼와 다르게 핸들러는 개발자가 직접 생성해서 사용

## 메시지
- 루퍼의 큐에 값을 전달하기 위해 사용되는 클래스
- 메시지 객체에 미리 정의해둔 코드를 입력하고 큐에 담아두면 루퍼가 거내서 핸들러에 전달



# 코루틴
- 스레드를 경량화 한 새로운 도구
- 동시성 프로그래밍 개념을 도입한 것
- 코루틴에서 스레드는 단지 코루틴이 실행되는 공간을 제공하는 역할이며, 실행 중인 스레드를 중단시키지 않기때문에 하나의 스레드에 여러 코루틴 존재 가능

## 코루틴 버저닝
- 4.1버전에는 코루틴 내장
- CoroutineScope 입력시 자동 완정이 안되면 그래들 추가
- 버전 정보는 https://developer.android.com/kotlin/coroutines?hl=ko
```
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9" //버전은 공부시 버전
```

## 코루틴 스코프
- 코루틴은 정해진 스코프 안에서 실행되는데 이것을 코루틴 스코프라 한다.
- GlobalScope.launch
```
GlobalScope.launch{
    // 코루틴 실행 코드
}
```
- 글로벌 스코프 : 앱의 생명주기와 함게 동작하므로 앱이 실행되는 동안은 별도의 생명 주기 관리가 필요 없다
- 코루틴 스코프 : 버튼을 클릭해서 서버의 정보를 가져오거나 파일을 여는 용도라면 필요할 때만 열고 완료되면 닫는 CoroutineScope를 사용
```
binding.btnDownload.setOnClickListener{
    CoroutineScope(Dispatchers.IO).launch{
        // 처리 코드
    }
}
```
- 코루틴 스코프에는 Dispatchers.IO 라는 상수값이 있는데, 이것을 디스패처라고 하며 코루틴이 실행될 스레드를 지정하는 것

## 디스패처 종류
- Dispatchers.Default = CPU를 많이 먹는 작업을 백그라운드 스레드에서 실행하도록 최적화 되어 있음
- Dispatchers.IO = 이미지 다운, 파일 입출력 등의 입출력에 최적화
- Dispatchers.Main = 기본 스레드에서 코루틴을 실행하고 UI와 상호작용에 최적화 되어있는 디스패처, 텍스트뷰에 글자 입력할 경우 사용
- Dispatchers.Unconfined = 자신을 호출한 컨텍스트를 기본으로 사용하며, 중단 후 다시 실행하는 시점에 컨텍스트가 바뀌면 자신의 컨텍스트도 다시 실행하는 컨텍스트를 따라감

## launch와 상태 관리
- 코루틴은 launch와 async로 시작 가능
- launch는 상태를 관리할 수 있고, async는 상태를 관리하고 연산 결과까지 반환 받을 수 있다.
- launch는 호출하는 것만으로 코루틴을 생성할 수 있고, 반환되는 job을 변수에 저장해두고 상태 관리용으로 사용할 수 있다.

- cancel : 코루틴의 동작을 멈추는 상태 관리 메서드, 하나의 스코프안에 여러 코루틴이 있으면 하위 코루틴도 모두 멈춘다.
```
val job = CoroutineScope(Dispatchers.Default).launch{
    val job1 = launch{
        ....
    }
}
job.cancel()
```

- join : 각각의 코루틴이 순차적으로 실행
```
CoroutineScope(Dispatchers.Default).launch(){
    launch {
        ....
    }.join()

    launch {
        ...
    }
}
```

## async와 반환값 처리
- 코루틴 스코프의 연산 결과를 받아서 사용가능하다.
- 예를 들어, 오래 걸리는 2개의 코루틴을 async로 선언하고, 결과값을 처리하는 곳에서 await 함수를 사용하면 결과 처리가 완료된 후에 await를 호출한 줄의 코드 실행
```
CoroutineScope(Dispatchers.Default).async{
    val deferred1 = async {
        delay(500)
        350
    }
    val deferred2 = async {
        delay(1000)
        200
    }
    Log.d("코루틴", "연산결과 = ${deffered1.await() + deffered2.await()}")
}
```

## suspend
- 코루틴 안에서 suspend 키워드로 선언된 함수가 호출되면 이전가지의 코드 실행이 멈추고, suspend 함수의 처리가 완료된 후 멈춰있던 스코프의 다음 코드 실행
```
suspend fun subRoutine(){
    ...
}
CoroutineScope(Dispatcher.Main).launch{
    // 코드 1
    subRoutine()
    // 코드 2
}
```
- 코드1 실행 후 subRoutine() 호출
- subRoutine() 완료 후 코드2 호출
- subRoutine()은 suspend 키워드로 인해 CoroutineScope 안에서 자동으로 백그라운드 스레드처럼 동작
- ** subRoutine()이 실행되며 호출한 측의 코드를 잠시 멈췄지만 스레드의 중단은 없다!!!!
- 코루틴에서 부모 루틴의 상태 값을 저장 후 subRoutine()을 실행하고, 다시 subRoutine()이 종료된 후 부모 루틴의 값을 복원하여 스레드에 영향을 주지 않는다.

## withContext로 디스패처 분리
- suspend 함수를 코루틴 스코프에서 호출할 때 호출한 스코프와 다른 디스패처를 사용할 때가 있다.
- ex) 호출 측 코루틴은 Main 디스패처에서 UI제어를 하는데, 호출되는 suspend 함수는 디스크에서 파일을 읽는다.
- 이럴경우 withContext를 사용하여 호출되는 suspend 함수의 디스패처를 IO로 변경가능
- 호출되는 SUSPEND 함수에 반환값이 있다면 변수에 저장하고 사용 가능
```
suspend fun readFile() : String {
    reutnr "파일 내용"
}
CoroutineScope(Dispatchers.Main).launch{
    // 화면 처리
    val result = withContext(Dispatchers.IO){
        readFile()
    }
    Log.d("코루틴","파일 경과 = $result")
}
```



```
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Test2 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int N,idx;
	static String[] arr;
	static List<Object> list;
	static Map<String,List<Object>> map;
	static Set<String> set;
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws Exception{
		init();
		dataInput();
		print();
	}
	
	static void init() throws Exception{
		N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine(), " ");
		arr = new String[st.countTokens()];
		list = new ArrayList<>();
		map = new HashMap<>();
		set = new HashSet<>();
	}
	
	static void dataInput() throws Exception{
		
		
		while(st.hasMoreElements()) {
			arr[idx++] = st.nextToken();
		}
		
		for(int i=0; i<N; i++) {
			list.add(arr[i]);
		}
		
		map.put("test1", list);
		map.put("test2", list);
		map.put("test1", list);
		
		for(int i=0; i<N; i++) {
			set.add(arr[i]);
		}
		
	}
	
	static void print() throws Exception{
		for(int i=0; i<list.size(); i++) {
			sb.append(list.get(i)+" ");
		}
		System.out.println(sb.toString());
		
		System.out.println(map.toString());
		
		System.out.println(set.toString());
	}
	
}

```
