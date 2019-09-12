package com.demo.seckill;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.base.controller.BaseController;
import com.demo.config.RedissonConfig;
import com.demo.global.RtnResult;
import com.demo.global.enums.RtnResultCode;

import io.swagger.annotations.ApiOperation;

@RestController
public class SeckillController extends BaseController{

	@Autowired
	private ExecutorService executorService;
	@Autowired
	private RedissonService redissonService;
	@Autowired
	private RedisTemplate redisTemplate;
	
	private String killName="goods";//秒杀商品名称
	
	private String myLock="myLock";
	
	
	@ApiOperation("多长时间内取不到锁直接退出排队,提醒用户重新发起秒杀。适用于在线实时秒杀场景")
	@GetMapping("seckill2")
	public RtnResult seckill2(){
		logger.info("线程:{},进入",Thread.currentThread().getName());
		Integer count=(Integer) redisTemplate.opsForValue().get(killName);
		if(count<=0){
			logger.info("商品秒杀完毕");
			return RtnResult.Fail("商品秒杀完毕");
		}
		
		RLock lock=redissonService.getRLock(myLock);
		Boolean bs=false;
		try {
			bs=lock.tryLock(3,5, TimeUnit.SECONDS);
			if(!bs&&count>0){//用户这次没有获取到锁，但是还剩余有数量 提醒用户继续发起抢购
				logger.info("线程:{},获取锁失败,还有机会请重新抢购",Thread.currentThread().getName());
				return RtnResult.Fail(RtnResultCode.RETRY,"线程:"+Thread.currentThread().getName()+"获取锁失败,还有机会请重新抢购");
			}else if(!bs&&count<=0){
				logger.info("线程:{},获取锁失败,商品已卖完",Thread.currentThread().getName());
				return RtnResult.Fail("线程:"+Thread.currentThread().getName()+"获取锁失败,商品已卖完");
			}
			
			logger.info("线程:{},获取锁成功,锁状态:{},商品剩余数量:{},开始秒杀商品.....",Thread.currentThread().getName(),bs,count);
			count=(Integer) redisTemplate.opsForValue().get(killName);
			//if(count>=1){
				count--;
			//}
			redisTemplate.opsForValue().set(killName,count);
			//处理业务代码需要的时间
			Thread.sleep(30*1000);
			/*long start=System.currentTimeMillis();
			long end=System.currentTimeMillis();
			while(!((end-start)/1000==15)){
				end=System.currentTimeMillis();
			}*/
			return RtnResult.Success("线程:"+Thread.currentThread().getName()+"获取锁成功");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(bs){
				logger.info("线程:{},释放锁:{}",Thread.currentThread().getName(),lock.getName());
				lock.unlock();
			}
		}
		return RtnResult.Success();	
	}
	
	
	@ApiOperation("排队锁,直到商品秒杀完毕才退出，适用于登记秒杀场景")
	@GetMapping("seckill")
	public RtnResult seckill(){
		logger.info("线程:{},进入",Thread.currentThread().getName());
		Integer count=(Integer) redisTemplate.opsForValue().get(killName);
		if(count<=0){
			logger.info("商品秒杀完毕");
			return RtnResult.Fail("商品秒杀完毕");
		}
		
		RLock lock=redissonService.getRLock(myLock);
		Boolean bs=false;
		try {
			bs=lock.tryLock(0, 2, TimeUnit.SECONDS);
			if(!bs){
				bs=waitKill(this, lock, bs);
				count=(Integer) redisTemplate.opsForValue().get(killName);
				logger.info("线程:{},剩余商品数量:{},获取锁状态:{}",Thread.currentThread().getName(),count,bs);
				if(count<=0){
					logger.info("商品秒杀完毕,线程:{}退出。。。",Thread.currentThread().getName());
					return RtnResult.Fail("商品秒杀完毕，退出。。。");
				}
			}
			//获取到锁则库存-1，如超时未支持则在别的地方又还原到redis中
			Integer killNum=(Integer) redisTemplate.opsForValue().get(killName);
			//if(killNum>=1){
				killNum--;
			//}
			redisTemplate.opsForValue().set(killName,killNum);
			logger.info("线程:{},获取锁成功,开始秒杀商品.....",Thread.currentThread().getName());
			//处理业务代码需要的时间
			Thread.sleep(1000);
			return RtnResult.Success("线程:"+Thread.currentThread().getName()+"获取锁成功");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(bs){
				logger.info("线程:{},释放锁:{}",Thread.currentThread().getName(),lock.getName());
				lock.unlock();
				synchronized (this) {
					this.notifyAll();//唤醒正在排队的锁
				}
			}
		}
		return RtnResult.Success();	
	}
	
	public Boolean waitKill(Object obj,RLock lock,Boolean bs){
		synchronized (obj) {
			logger.info("线程:{},进入睡眠",Thread.currentThread().getName());
			try {
				obj.wait();
				logger.info("线程:{},被唤醒",Thread.currentThread().getName(),bs);
				bs=lock.tryLock(0, 2, TimeUnit.SECONDS);
				logger.info("线程:{},---获取锁状态:{}",Thread.currentThread().getName(),bs);
				if(!bs){
					waitKill(obj,lock,bs);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		return bs;
	}
	
	@ApiOperation("测试线程池")
	@GetMapping("/testExecutor")
	public RtnResult testExecutor(){
		executorService.testExecutor();
		return RtnResult.Success();
	}
	
	
}
