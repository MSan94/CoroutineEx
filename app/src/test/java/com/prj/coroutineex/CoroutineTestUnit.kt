package com.prj.coroutineex

import kotlinx.coroutines.*
import org.junit.Test
// https://zladnrms.tistory.com/116
class CoroutineTestUnit {

    /**
     * A , B 모두 끝까지 출력을 보장하지 않는다.
     * runBlocking은 내부 로직이 끝날 동안 외부에게 기다리게 한다.
     * 그에반해, CoroutineScope는 비동기적으로 돌아가는데, runBlocking 과는 별개로 또다른 쓰레드 A, B 스코프 2개가 비동기적으로 돌고 있기때문이다.
     * 따라서, CoroutineScope의 종료는 테스트 코드 런타임의 관심 밖이라, runBlocking의 내부 로직이 마친 직후에 런타임이 종료되면 해당 프로세스가 종료되어
     * A, B 모두 정상 출력을 보장하지 않는다.
     */
    @Test
    fun assert_test(){
        runBlocking {
            println("start")

            // 스코프 A
            CoroutineScope(Dispatchers.IO).launch {
                println("Scope A : Start")
                for(i in 0..1000){
                    println("Scope A : $i")
                }
            }
            // 스코프 B
            CoroutineScope(Dispatchers.IO).launch {
                println("Scope B : Start")
                for(i in 0..1000){
                    println("Scope B : $i")
                }
            }
        }
    }

    /**
     *  A는 모두 출력  , B  끝까지 모두 출력 보장 X , 동시에 출력 가능
     *  CoroutineScope의 async/await을 주문하였으므로 해당 Scope는 출력이 보장된다.
     *  둘다 CoroutineScope를 사용하였고, A Scope의 await 함수가 B Scope의 호출 이후 선언되어 동시 출력 가능
     */
    @Test
    fun assert_test2(){
        runBlocking {
            println("start")
            // 스코프 A
            val job = CoroutineScope(Dispatchers.IO).async {
                println("Scope A : Start")
                for(i in 0..1000){
                    println("Scope A : $i")
                }
            }
//            job.await()  // 여기에 선언하면 A 출력 후 B출력
            // 스코프 B
            CoroutineScope(Dispatchers.IO).launch {
                println("Scope B : Start")
                for(i in 0..1000){
                    println("Scope B : $i")
                }
            }
            job.await()
        }
    }

    /**
     * A 정상출력, B 정상출력 보장X , 동시출력X
     * A 스코프에서 await를 요청하여 A Scope 내부 로직이 종료된 후 B 스코프가 실행된다.
     */
    @Test
    fun assert_test3(){
        runBlocking {
            println("start")
            // 스코프 A
            CoroutineScope(Dispatchers.IO).async {
                println("Scope A : Start")
                for(i in 0..1000){
                    println("Scope A : $i")
                }
            }.await()
            println("A End")
            CoroutineScope(Dispatchers.IO).launch {
                println("Scope B : Start")
                for (item in 0..5000) {
                    println("Scope B : $item")
                }
            }
        }
    }

}