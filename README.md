## Complete Solution For Bit Quantification Project
Cooperation Email:xiaopohai85707@gmail.com  
TG Account:ghost25O1
## 比特量化项目全套解决方案
合作邮箱:xiaopohai85707@gmail.com  
TG 账号:ghost25O1
## 运行环境
Centos 7
MySQL 8
Redis-x64-3.2.100
Mongodb 3.6.13
rabbitmq
zookeeper:latest
nginx-1.16.0
JRE 8u241
JDK 1.8
Vue
uni-app
## 文件目录说明
比特量化工具目录

└─———binance-sdk 币安sdk(基于官方sdk修改)

└─———okex-sdk 欧意sdk(基于官方sdk修改)

└─———huobi-sdk 火币sdk(基于官方sdk修改)

└─———unify-exchange 聚合三方sdk的交易接口

└─———bipay-core 优盾sdk

└─———bipay-core2 针对BTE新分出充提币sdk(对接自建节点)

└─———huoboi-market-socket 对接火币最新行情的工具

比特量化通用目录

└─———api-common 主要包含登录功能，跨域配置，白名单注解，文档功能，全局异常处理，国际化配置，mybaits配置等依赖于spring的功能

└─———common 项目全局通用方法

└─———generator 逆向工程

└─———test 测试

比特量化模块目录

└─———admin 后台管理api

└─———cloud SpringCloud微服务管理

└─———makret 市场行情api

└─———quant 量化服务api

└─———quant-core 量化服务核心

└─———quant-param 量化服务接口&参数

└─———quant-monitor 量化监控用户订单服务(主要监控委托成交)

└─———quant-points-analysis 量化k线指标分析服务

└─———ucenter 用户中心api

└─———ucenter-core 用户中心核心

整个依赖关系如下图：
![33fc928d3284ca57e833e71675c61087.png](https://app.yinxiang.com/FileSharing.action?hash=1/33fc928d3284ca57e833e71675c61087-325285)
## 部署
本项目使用微服务架构，便于分离部署及后期扩展
#### 搭建运行环境
* 首先需要安装jdk8
* 安装工具 mysql8,redis,mongodb,rabbitmq,zookeeper(可以使用docker快速安装)
#### 配置文件
分别在各微服务项目下的application-prod.yml修改连接配置

![0ff890c58720cd799332a43ec3c53b6e.png](https://app.yinxiang.com/FileSharing.action?hash=1/0ff890c58720cd799332a43ec3c53b6e-7011)

>注意，如果修改连接地址，比如修改mysql地址或端口号，对应的微服务**admin**/**ucenter**/**market**/**quant**/**quant-monitor**/**quant-points-analysis**下面的配置都需要进行修改，有些服务比如market，quant-monitor，quant-points-analysis下没有mysql配置，说明该微服务不需要依赖mysql可以略过

#### 项目打包
在项目根目录下执行
```
mvn clean package
```
或使用idea工具，在maven(root)下运行package
jar包总共为7个，**cloud**/**admin**/**ucenter**/**market**/**quant**/**quant-monitor**/**quant-points-analysis**，分别在对应目录的target下
#### 启动脚本
7个微服务项目需要都启动才能完成整个项目的启动(**有一定的先后顺序**)
可以将代码按文件夹分别放在不同的目录中进行统一管理，并编写每一个服务的启动/停止脚本
一共分为7个文件夹，每个文件夹分别放入对应的jar包和启动脚本，如quant下放着quant.jar和启动脚本，以quant为例，里面的文件如下
1.`quant.jar`微服务对应的jar包，来自于package命令生成
2.`start.sh`启动脚本，内容如下
```
nohup java -Djava.awt.headless=true -jar -Xmx1g -Xms1g -Xmn300m -Xss512k quant.jar > nohup.out --spring.profiles.active=prod &
```
可以根据实际情况修改内存占用的配置
3.`stop.sh`启动脚本，内容如下
```
PID=$(ps -ef | grep quant.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
echo Application is already stopped
else
echo kill $PID
kill -9 $PID
fi
```
即杀死quant.jar的进程
4.`nohup.out`日志文件，时间长最好手动清理一下
5.`log.sh`跟踪日志脚本
```
tail -f nohup.out
```
#### 启动
启动的顺序为**cloud**>**ucenter**>**market**>**quant-monitor**>**quant-points-analysis**>**quant**>**admin**
为了方便快速启动，新建文件夹boot,写了一些脚本，可以快速启动项目
```
cd /bit/boot # 来到目录下
./1cloud.sh 启动云服务，出现运行成功字样，启动下一个
./2ucenter.sh 启动用户服务
...
```
如此，按1,2,3..顺序启动，**启动时日志出现启动成功字样可以继续启动下一个**
#### 停止
boot文件加下有stop.sh脚本，可以一键关闭所有服务
```
cd /bit/boot # 来到目录下
./stop.sh #关闭所有服务
```
也可以进入每个微服务文件夹单独关闭某个服务(**尽量全部重启**)
#### nginx反向代理
由于微服务项目每个项目都有不同端口号，所以通过nginx进行反向代理，供前端访问
可进行如下配置
```
server {
        listen       80;
        server_name  127.0.0.1;
		location / { # h5
           gzip on;
           gzip_buffers 32 4K;
           gzip_comp_level 6;
           gzip_min_length 100;
           gzip_types application/javascript text/css text/xml;
          root  /bit/web-h5;
		  index  index.html;
		  try_files $uri $uri/ /index.html;
		}
		location /master { # ucenter
			client_max_body_size    5m;
			proxy_pass http://127.0.0.1:6602;
			proxy_set_header Host $http_host;
			proxy_set_header X-Real-IP $remote_addr;
			proxy_set_header X-Scheme $scheme;
			proxy_set_header Upgrade $http_upgrade;
			proxy_set_header Connection "upgrade";
		}
		location /admin { # admin
			client_max_body_size    5m;
			proxy_pass http://127.0.0.1:6603;
			proxy_set_header Host $host;
			proxy_set_header X-Real-IP $remote_addr;
		}
		location /market { # market
			client_max_body_size    5m;
			proxy_pass http://127.0.0.1:6604;
			proxy_set_header Host $http_host;
			proxy_set_header X-Real-IP $remote_addr;
			proxy_set_header X-Scheme $scheme;
			proxy_set_header Upgrade $http_upgrade;
			proxy_set_header Connection "upgrade";
		}
		location /quant { # quant
			client_max_body_size    5m;
			proxy_pass http://127.0.0.1:6605;
			proxy_set_header Host $http_host;
			proxy_set_header X-Real-IP $remote_addr;
			proxy_set_header X-Scheme $scheme;
			proxy_set_header Upgrade $http_upgrade;
			proxy_set_header Connection "upgrade";
		}
	}
```

到此整个比特量化后端项目搭建完成
## 接口文档
后端主要提供三个服务，**ucenter**,**admin**,**quant**,nginx搭建完成后对应的接口文档地址如下
* ucenter: http://8.210.183.40/master/doc.html
* admin: http://8.210.183.40/admin/doc.html
* quant: http://8.210.183.40/quant/doc.html

## 信号流处理
项目主要通过微服务之间信号流转完成整个工作，如
* `quant-points-analysis`负责分析k线发出涨跌信号
* `quant-monitor`负责监控用户订单，发出委托成交信号或强平信号
* 第三方推送发送托管买卖信号

信号统一用rabbitmq推送，处理流程如下

![7a4e146f801cee5bc1aee773e623b00a.png](https://app.yinxiang.com/FileSharing.action?hash=1/7a4e146f801cee5bc1aee773e623b00a-226428)

## 技术栈
#### spring cloud
微服务管理
#### swagger
接口文档
#### dubbo
微服务之前的调用
#### netty
实时数据服务
#### rabbitmq
微服务消息队列
#### zookeeper
微服务注册
#### redis
缓存
#### redisson
分布式锁
## 扩展点
#### 新增指标分析
当前系统支持CCI,MACD等，如果后期需要扩展其它指标，只需修改`quant-points-analysis`,

![4b6b863b19b9d84bcb3764038c546aa3.png](https://app.yinxiang.com/FileSharing.action?hash=1/4b6b863b19b9d84bcb3764038c546aa3-11629)

在包strategy下新增XX指标策略，继承`BaseStrategy`,实现`computeSignal`方法即可(当然接口也有做少量代码修改以便用户可以提交新策略的自设指标)
#### k线展示
当前系统只有k线的分析，app端并无k线展示的界面，如果后期想加，可以直接新增界面调用`market`中的k线数据接口，`market`以实现k线存储及接口，可以配置开启或关闭，只需要前端新增界面并对接接口，对应代码在`com/mzwise/controller/KlineController.java`
#### 日志
目前系统为了监控量化过程，记录日志通过日志系统完成对整个量化交易的日志监控，以便及时处理订单错误

![9598314b35c266c4e741525c03f5c53f.png](https://app.yinxiang.com/FileSharing.action?hash=1/9598314b35c266c4e741525c03f5c53f-38685)

当前日志已实现等级分类：包括**系统级**(供管理员查看)和**用户级**(供管理员及用户查看)
由于用户量化都是异步执行的，后期可能需要用户查看自己量化日志的功能。可以直接通过用户ID调用用户级的日志快速实现
#### 交易平台
目前系统支持火币，币安，OKEX三个平台,由`unify-exchange`对接三个平台并屏蔽各平台差异，如果后期需要对接其它平台只需修改`unify-exchange`，针对现货和合约分别实现对应接口即可，每个模式主要有三个接口
* IMarketService.java 行情接口
* IStreamService.java 实时数据接口
* ITradingService.java 交易接口






