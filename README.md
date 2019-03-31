# sharedbikes租赁系统
## 功能实现
- 注册奖励
- 车损坏奖励
- 二维码
## 要修改的地方
- 所有的dao
- 实体类中不出现Database
- Menu中不出现Database，包括属性
- 更新位置车辆记录拿到Menu里来做
- 支付密码作为一个方法
- 转换菜单的时候，关闭

## 钱包系统
- 个人余额
- 优惠券余额
- 是否是会员
- 充值系统
- 消费记录
	- 充值记录
	- 消费记录
-

## 位置系统
- 管理员
	- 查看所有的位置
	- 查看调度建议
- 操作
	- 添加单车的时候，随机选择位置
	- 还车的时候，随机选择位置

## 流程图
### 选项
- [x]  输入选项：选项是否是数字
	- [x] 是数字： 
		- [x]  是否是存在该选项
			- [x] 是：执行对应的方法  
			- [x] 否： 重新输入：default
	- [x] 不是数字:重新输入 :ivv.isNumber()
	
- [ ] 价格判断
- [ ] 充值金额
- [ ] 
- [ ] 
- [ ] 
- [ ] 
- [ ] 

### 操作返回

#### 管理员
- [x] 添加单车 ：是否继续添加
	- [x] 是：继续添加
	- [x] 否：回到管理员菜单
- [x] 删除单车	：是否继续删除
	- [x] 是：继续删除
	- [x] 否：回到管理员菜单
- [x] 修改单车： 是否继续修改
	- [x] 是：继续修改
	- [x] 否：回到管理员菜单
#### 主界面
- [x] 注册用户
	- [x] 注册成功：login()
	- [x] 注册失败：是否重新注册
		- [x] 是：判断
			- [x] 用户名已经存在：
			- [x] 两次密码不一致：mainMenu()
		- [x] 否：回到主页面
- [ ] 登录用户
	- [x] 登录成功：根据用户属性返回对应界面
	- [ ]  登录失败：是否重新登录
		- [x]  是：重新登录：login()
			- [ ] 用户名不存在：
			- [ ] 密码不一致：
		- [x]  否：回到主菜单

#### 用户
- [ ] 租借单车
	- [ ] 是否继续租借
		- [ ] 是： 继续租借
		- [ ] 否： 回到用户菜单
- [ ] 归还单车
	- [ ] 是否继续归还
		- [ ] 是： 继续归还
		- [ ] 否： 回到用户菜单
#### 其他界面
- [x] 管理员查看所有单车：returnMenu()
- [x] 管理员查看所有单车租赁记录：returnMenu()