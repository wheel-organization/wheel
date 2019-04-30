## dubbo泛化调用
---

### 此util存在的意义和局限性
意义：
期望是和http调用的方式互补的去使用

优点：
1. 支持group、version、无token情况。联调时更方便配合其他系统。
2. dubbo协议调用，可在代码里泛化调用
3. 泛型支持

局限性：
1. 传入的参数列表顺序必须和方法上的参数顺序相同（问题不大）
2. 拿不到具体类型的泛型反序列化还是会失败（这种情况很少）
3. 需要自己多写一个helper（问题不大）
4. 必须项目引入了对应的api（问题不大）
5. dubbo源码有大量变动或结构性改变时，此util也要维护（Apache的dubbo可以兼容alibaba的dubbo，所以我用了alibaba的dubbo）

### 如何使用
注意：

引入的时候一定要去掉api里的dubbo

![](http://ww1.sinaimg.cn/large/ee36fa1bly1g2kghhr5ouj20a804paad.jpg)

1. 配合swagger
![](http://ww1.sinaimg.cn/large/ee36fa1bly1g2kf99yvwsj20u704iq3s.jpg)
![](http://ww1.sinaimg.cn/large/ee36fa1bly1g2kfhjn68hj21mm0ycgpn.jpg)
2. 代码单测调用
![](http://ww1.sinaimg.cn/large/ee36fa1bly1g2kfcy6l6mj20nd047whd.jpg)