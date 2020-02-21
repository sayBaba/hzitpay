package com.hzit.pay.service;

import com.hzit.pay.service.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PayServiceApplicationTests {

	@Autowired
	private RedisUtil redisUtil;


	@Test
	void contextLoads() {
		// 对账文件中的流水：

		//1.从t_pay_order 中拿出的数据
		//返回流水号，请求流水号，金额，状态
		//"2020022022001403781000062896,790d9c35c5354a83a8b8a86d82be4c82,1，1",  "2020022022001403781000062810,790d9c35c5354a83a8b8a86d82be4c85,10，1"

//		String localSet  = ，1，2020022022001403781000062810,790d9c35c5354a83a8b8a86d82be4c85,10，1";
		redisUtil.sadd("{account}:localSet","2020022022001403781000062896,790d9c35c5354a83a8b8a86d82be4c82,1","2020022022001403781000062810,790d9c35c5354a83a8b8a86d82be4c85,10，1");

		//2.从对账临时表中取出对账流水 t_recon_temp
		redisUtil.sadd("{account}:outerSet","2020022022001403781000062896,790d9c35c5354a83a8b8a86d82be4c82,1","2020022022001403781000062811,790d9c35c5354a83a8b8a86d82be4c85,10，1");

		//3.进行2个集合的比对，得出交集union，将交集放入key”{account}:union”中
		redisUtil.sinterstore("{account}:union", "{account}:localSet", "{account}:outerSet");

		//4.对出 localSet 和 union 中的差集,本地交易的差役（localDiff）。
		redisUtil.sdiffstore("{account}:localDiff", "{account}:localSet", "{account}:union");

		//5.对出对账文件（outerSet）和 union 中的差集,
		redisUtil.sdiffstore("{account}:outerDiff", "{account}:outerSet", "{account}:union");


		// 最终得出差集





	}

}
